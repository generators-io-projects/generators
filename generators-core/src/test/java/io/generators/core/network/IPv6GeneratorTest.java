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
public class IPv6GeneratorTest {

    @Test
    public void shouldReturnAnIPAddressWithValidOctets() {
        Generator<String> generator = new IPv6Generator();

        for (int i = 0; i < 100; i++) {
            String ipAddress = generator.next();
            assertThatHasValidHexDigitsInGroups(ipAddress);
        }
    }

    @Test
    public void shouldReturnAnIPAddressUsingTheDelegateOctetGenerator() {
        Generator<String> generator = new IPv6Generator(new CyclicGenerator<>(10));

        for (int i = 0; i < 100; i++) {
            String ipAddress = generator.next();
            assertThatHasValidHexDigitsInGroups(ipAddress);
            assertThat(ipAddress, is("aaaa:aaaa:aaaa:aaaa:aaaa:aaaa:aaaa:aaaa"));
        }
    }

    private void assertThatHasValidHexDigitsInGroups(String ipAddress) {
        String[] groups = ipAddress.split(":");
        for (String group : groups) {
            for (char c : group.toCharArray()) {
                int digitInDecimal = Integer.parseInt(String.valueOf(c), 16);
                assertThat(digitInDecimal, lessThanOrEqualTo(16));
                assertThat(digitInDecimal, greaterThanOrEqualTo(0));
            }
        }
    }
}
