package io.generators.core;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static io.generators.core.Generators.ofInstance;
import static org.joda.time.DateTime.now;

import javax.annotation.Nonnull;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * Generates random {@link org.joda.time.DateTime} in the time zone of the from date.
 * <p/>
 * When no constructor parameters are provided DateTime are generated in range +- 100 years in the default time zone
 * It also supports generating random DateTime in custom range, future and past DateTimes in cases where from DateTime is not provided it uses default time zone.
 * Time zone can be explicitly set to desired time zone using {@link #withTimeZone(org.joda.time.DateTimeZone)}
 *
 *
 * @author Tomas Klubal
 */
public class RandomJodaDateTimeGenerator implements Generator<DateTime> {
    private static final int YEAR_BOUND = 100;
    private static final int ZERO_DURAION = 0;
    private final PositiveLongRandom random = new PositiveLongRandom();
    private final Generator<DateTime> from;
    private final Generator<DateTime> to;
    private final DateTimeZone timeZone;

    /**
     * Created instance generates DateTime in range -+ 100 years in the default time zone
     *
     * @see org.joda.time.DateTimeZone#getDefault()
     */
    public RandomJodaDateTimeGenerator() {
        this(new DurationFromNow(-YEAR_BOUND), new DurationFromNow(+YEAR_BOUND));
    }

    /**
     * Created instance generates DateTime in range <code>from</code> to <code>to</code>
     *
     * @throws IllegalArgumentException when {@code from} is after {@code to}
     */
    public RandomJodaDateTimeGenerator(@Nonnull DateTime from, @Nonnull DateTime to) {
        this(ofInstance(from), ofInstance(to));
        //Late check to allow reuse of constructor
        checkArgument(checkNotNull(from, "Date 'from' can't be null").isBefore(checkNotNull(to, "Date 'to' can't be null")) || from.isEqual(to), "Date 'from' can't be after date 'to'");
    }

    /**
     * Created instance generates DateTime in range  provided by generators <code>from</code> to <code>to</code>
     *
     * @param from generator of the from/lower bound DateTime
     * @param to   generator of the to/upper bound DateTime
     */
    public RandomJodaDateTimeGenerator(@Nonnull Generator<DateTime> from, @Nonnull Generator<DateTime> to) {
        this(from, to, null);
    }

    private RandomJodaDateTimeGenerator(Generator<DateTime> from, Generator<DateTime> to, DateTimeZone timeZone) {
        this.from = checkNotNull(from);
        this.to = checkNotNull(to);
        this.timeZone = timeZone;
    }

    /**
     * Created instance generates future DateTimes up to 100 years in future including now
     *
     * @return instance that generates future DateTimes up to 100 years in future including now
     */
    public static Generator<DateTime> futureDates() {
        return new RandomJodaDateTimeGenerator(new DurationFromNow(ZERO_DURAION), new DurationFromNow(+YEAR_BOUND));
    }

    /**
     * Created instance generates future DateTimes up to <code>to</code> in future including now
     *
     * @return instance that generates future DateTimes up to <code>to</code> in future including now
     */
    public static Generator<DateTime> futureDates(@Nonnull DateTime to) {
        return new RandomJodaDateTimeGenerator(new DurationFromNow(ZERO_DURAION), ofInstance(checkNotNull(to)));
    }

    /**
     * Created instance generates DateTimes in past from before 100 years up to now
     *
     * @return instance that generates DateTimes in past from before 100 years up to now
     */
    public static Generator<DateTime> pastDates() {
        return new RandomJodaDateTimeGenerator(new DurationFromNow(-YEAR_BOUND), new DurationFromNow(ZERO_DURAION));
    }

    /**
     * Created instance generates DateTimes in past from <code>from</code> up to now
     *
     * @return instance that generates DateTimes in past from <code>from</code> up to now
     */
    public static Generator<DateTime> pastDates(@Nonnull DateTime from) {
        return new RandomJodaDateTimeGenerator(ofInstance(checkNotNull(from)), new DurationFromNow(ZERO_DURAION));
    }

    @Override
    public DateTime next() {
        DateTime fromTime = from.next();
        long lowerBound = fromTime.getMillis();
        long upperBound = to.next().getMillis();

        long millis = random.nextLong(upperBound - lowerBound + 1) + lowerBound;
        return new DateTime(millis, timeZone == null ? fromTime.getZone() : timeZone);
    }

    /**
     * Creates new instance of this generator that will generate DateTime using provided TimeZone.
     *
     * @param timeZone to be used when generating the date
     * @return new generator with specific time zone
     */
    public Generator<DateTime> withTimeZone(DateTimeZone timeZone) {
        return new RandomJodaDateTimeGenerator(from, to, timeZone);
    }

    private static final class DurationFromNow implements Generator<DateTime> {
        private final int yearBound;

        private DurationFromNow(int numberOfYears) {
            yearBound = numberOfYears;
        }

        @Override
        public DateTime next() {
            return now().plusYears(yearBound);
        }
    }

}
