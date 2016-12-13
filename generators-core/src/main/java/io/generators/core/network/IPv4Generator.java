package io.generators.core.network;

import static io.generators.core.Generators.positiveInts;
import static java.lang.String.format;

import io.generators.core.Generator;

/**
 * Class generates random valid IPv4 addresses. Currently includes all the types of IP addresses including broadcast,
 * loopback etc in the default generation strategy.
 *
 * @author Saket
 */
public class IPv4Generator implements Generator<String> {

    private static final String IP_ADDRESS_FORMAT = "%s.%s.%s.%s";
    private final Generator<Integer> octetGenerator;

    public IPv4Generator() {
        this(positiveInts(0, 256));
    }

    public IPv4Generator(Generator<Integer> delegate) {
        octetGenerator = delegate;
    }

    @Override
    public String next() {
        return format(IP_ADDRESS_FORMAT, octetGenerator.next(), octetGenerator.next(), octetGenerator.next(), octetGenerator.next());
    }
}
