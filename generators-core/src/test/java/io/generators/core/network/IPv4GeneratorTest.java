package io.generators.core.network;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

import io.generators.core.CyclicGenerator;
import io.generators.core.Generator;
import org.junit.Test;

/**
 * @author Saket
 */
public class IPv4GeneratorTest {

    @Test
    public void shouldReturnAnIPAddressWithValidOctets() {
        Generator<String> generator = new IPv4Generator();

        for (int i = 0; i < 100; i++) {
            String ipAddress = generator.next();
            assertThatHasValidOctets(ipAddress);
        }
    }

    @Test
    public void shouldReturnAnIPAddressUsingTheDelegateOctetGenerator() {
        Generator<String> generator = new IPv4Generator(new CyclicGenerator<>(10));

        for (int i = 0; i < 100; i++) {
            String ipAddress = generator.next();
            assertThatHasValidOctets(ipAddress);
            assertThat(ipAddress, is("10.10.10.10"));
        }
    }

    private void assertThatHasValidOctets(String ipAddress) {
        String[] octets = ipAddress.split(".");
        for (String octet : octets) {
            int intOctet = Integer.parseInt(octet);
            assertThat(intOctet, lessThanOrEqualTo(256));
            assertThat(intOctet, greaterThanOrEqualTo(0));
        }
    }
}
