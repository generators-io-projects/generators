package io.generators.core;

import com.google.common.base.Function;
import com.google.common.base.Strings;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Converts given object to string and pads from left or right it up to specified minimum length if required
 *
 * @author Tomas Klubal
 */
public class PaddingFunction implements Function<Object, String> {

    private final PaddingType paddingType;
    private final int minimumLength;
    private final char padChar;

    /**
     * Creates function that pads from left with {@code padChar} up to specified {@code minimumLength}.
     *
     * @param minimumLength of the passed in {@link Object#toString()}
     * @param padChar       character to pad with
     * @return the padding function
     */
    public static PaddingFunction padStart(int minimumLength, char padChar) {
        return new PaddingFunction(PaddingType.Start, minimumLength, padChar);
    }

    /**
     * Creates function that pads from right with {@code padChar} up to specified {@code minimumLength}.
     *
     * @param minimumLength of the passed in {@link Object#toString()}
     * @param padChar       character to pad with
     * @return the padding function
     */
    public static PaddingFunction padEnd(int minimumLength, char padChar) {
        return new PaddingFunction(PaddingType.End, minimumLength, padChar);
    }

    private PaddingFunction(@Nonnull PaddingType paddingType, int minimumLength, char padChar) {
        this.paddingType = checkNotNull(paddingType, "paddingType");
        this.minimumLength = minimumLength;
        this.padChar = padChar;
    }

    /**
     * Pads to string representation of the {@code toPad} object
     *
     * @param toPad object which string representation will be padded if less than minimum length
     * @return padded string
     * @throws java.lang.NullPointerException when {@code toPad} is {@code null} or {@see Objects#toString()}
     */
    @Override
    public String apply(Object toPad) {
        checkNotNull(toPad, "Can't pad null");
        return paddingType.pad(toPad, minimumLength, padChar);
    }

    /**
     * Padding from left/start or right/end
     */
    private enum PaddingType {
        Start {
            @Override
            public String pad(Object toPad, int minimumLength, char padChar) {
                return Strings.padStart(toPad.toString(), minimumLength, padChar);
            }
        },
        End {
            @Override
            public String pad(Object toPad, int minimumLength, char padChar) {
                return Strings.padEnd(toPad.toString(), minimumLength, padChar);
            }
        };

        abstract String pad(Object toPad, int minimumLength, char padChar);
    }
}
