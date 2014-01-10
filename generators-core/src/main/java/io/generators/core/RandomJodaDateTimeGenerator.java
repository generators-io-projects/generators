package io.generators.core;

import org.joda.time.DateTime;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static io.generators.core.Generators.ofInstance;
import static org.joda.time.DateTime.now;

/**
 * Generates random {@link org.joda.time.DateTime}.
 * <p/>
 * When no constructor parameters are provided DateTime are generated in range +- 100 years.
 * It also supports generating random DateTime in custom range, future and past DateTimes.
 *
 * @author Tomas Klubal
 */
public class RandomJodaDateTimeGenerator implements Generator<DateTime> {
    private static final int YEAR_BOUND = 100;
    private static final int ZERO_DURAION = 0;
    private final PositiveLongRandom random = new PositiveLongRandom();
    private final Generator<DateTime> from;
    private final Generator<DateTime> to;

    /**
     * Created instance generates DateTime in range -+ 100 years
     */
    public RandomJodaDateTimeGenerator() {
        this.from = new DurationFromNow(-YEAR_BOUND);
        this.to = new DurationFromNow(+YEAR_BOUND);
    }

    /**
     * Created instance generates DateTime in range <code>from</code> to <code>to</code>
     *
     * @throws IllegalArgumentException when {@code from} is after {@code to}
     */
    public RandomJodaDateTimeGenerator(DateTime from, DateTime to) {
        checkArgument(checkNotNull(from, "Date 'from' can't be null").isBefore(checkNotNull(to, "Date 'to' can't be null")) || from.isEqual(to), "Date 'from' can't be after date 'to'");
        this.from = ofInstance(from);
        this.to = ofInstance(to);
    }

    /**
     * Created instance generates DateTime in range  provided by generators <code>from</code> to <code>to</code>
     *
     * @param from generator of the from/lower bound DateTime
     * @param to   generator of the to/upper bound DateTime
     */
    public RandomJodaDateTimeGenerator(Generator<DateTime> from, Generator<DateTime> to) {
        this.from = from;
        this.to = to;
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
    public static Generator<DateTime> futureDates(DateTime to) {
        return new RandomJodaDateTimeGenerator(new DurationFromNow(ZERO_DURAION), ofInstance(to));
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
    public static Generator<DateTime> pastDates(DateTime from) {
        return new RandomJodaDateTimeGenerator(ofInstance(from), new DurationFromNow(ZERO_DURAION));
    }

    @Override
    public DateTime next() {
        long lowerBound = from.next().getMillis();
        long upperBound = to.next().getMillis();

        long millis = random.nextLong(upperBound - lowerBound + 1) + lowerBound;
        return new DateTime(millis);
    }

    private static class DurationFromNow implements Generator<DateTime> {
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
