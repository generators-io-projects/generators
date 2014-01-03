package io.generators.core;

import com.google.common.base.Function;

/**
 * Appends Luhn Check Digit to the supplied input,
 */
public class LuhnCheckDigitFunction implements Function<Long, Long> {

    @Override
    public Long apply(Long partialAccountNumber) {
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

        int check = total * 9 % 10 ;

        return partialAccountNumber * 10 + check;
    }

    private boolean isTwoDigit(long multipliedDigit) {
        return multipliedDigit > 9;
    }

    private long sumUpDigits(long multipliedDigit) {
        return multipliedDigit / 10 + multipliedDigit % 10;
    }
}
