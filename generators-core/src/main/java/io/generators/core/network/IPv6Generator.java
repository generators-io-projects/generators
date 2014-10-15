package io.generators.core.network;

import static java.lang.String.format;

import com.google.common.base.Joiner;
import io.generators.core.Generator;
import io.generators.core.RandomPositiveIntegerGenerator;

/**
 * Class generates random valid IPv6 addresses. Currently includes all the types of IP addresses including broadcast,
 * loopback etc in the default generation strategy. And it does not support the abbreviated notation.
 *
 * @author Saket
 */
public class IPv6Generator implements Generator<String> {

    private static final String IP_ADDRESS_FORMAT = "%s:%s:%s:%s:%s:%s:%s:%s";
    private final Generator<Integer> hexDigitGenerator;

    public IPv6Generator() {
        this(new RandomPositiveIntegerGenerator(0, 16));
    }

    public IPv6Generator(Generator<Integer> delegate) {
        hexDigitGenerator = delegate;
    }

    @Override
    public String next() {
        return format(IP_ADDRESS_FORMAT, generateGroup(), generateGroup(), generateGroup(), generateGroup(),
                generateGroup(), generateGroup(), generateGroup(), generateGroup());
    }

    private String generateGroup() {
        return Joiner.on("").join(hexAsString(), hexAsString(), hexAsString(), hexAsString());
    }

    private String hexAsString() {
        return Integer.toHexString(hexDigitGenerator.next());
    }
}
