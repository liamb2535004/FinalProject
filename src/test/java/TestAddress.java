import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.liamb.Address;

public class TestAddress {

    @Test
    @DisplayName("\"\" -> false")
    void testIsPostalCodeValid() {
        String postalCode = "";
        boolean expected = false;
        boolean actual = Address.isPostalCodeValid(postalCode);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("null -> false")
    void testIsPostalCodeValid1() {
        String postalCode = null;
        boolean expected = false;
        boolean actual = Address.isPostalCodeValid(postalCode);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("QQQQQQ -> false")
    void testIsPostalCodeValid2() {
        String postalCode = "QQQQQQ";
        boolean expected = false;
        boolean actual = Address.isPostalCodeValid(postalCode);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("111111 -> false")
    void testIsPostalCodeValid3() {
        String postalCode = "111111";
        boolean expected = false;
        boolean actual = Address.isPostalCodeValid(postalCode);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("A1B2C3D4 -> false")
    void testIsPostalCodeValid4() {
        String postalCode = "A1B2C3D4";
        boolean expected = false;
        boolean actual = Address.isPostalCodeValid(postalCode);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("A1B2C3 -> true")
    void testIsPostalCodeValid5() {
        String postalCode = "A1B2C3";
        boolean expected = true;
        boolean actual = Address.isPostalCodeValid(postalCode);

        Assertions.assertEquals(expected, actual);
    }
}
