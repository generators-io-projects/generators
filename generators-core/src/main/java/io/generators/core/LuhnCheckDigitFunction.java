package io.generators.core;

import com.google.common.base.Function;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Appends Luhn Check Digit to the supplied input,
 *
 * @author Tomas Klubal
 */
public class LuhnCheckDigitFunction implements Function<Long, Long> {

    /**
     * Appends check digit to {@code partialAccountNumber}
     *
     * @param partialAccountNumber number without check digit
     * @return number with check digit
     * @throws java.lang.NullPointerException if partialAccountNumber is null
     */
    @Override
    public Long apply(Long partialAccountNumber) {
        checkNotNull(partialAccountNumber, "partialAccountNumber can't be null");
        boolean isEven = true;
        int total = 0;
        Long temp = partialAccountNumber;

        while (temp > 0) {
            long digit = temp % 10;
            if (isEven) {
                long multipliedDigit = digit * 2;
                total += isTwoDigit(multipliedDigit) ? sumUpDigits(multipliedDigit) : multipliedDigit;
            } else {
                total += digit;
            }
            temp /= 10;
            isEven = !isEven;
        }

        int check = total * 9 % 10;

        return partialAccountNumber * 10 + check;
    }

    private boolean isTwoDigit(long multipliedDigit) {
        return multipliedDigit > 9;
    }

    private long sumUpDigits(long multipliedDigit) {
        return multipliedDigit / 10 + multipliedDigit % 10;
    }
}
