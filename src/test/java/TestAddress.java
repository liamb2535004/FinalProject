import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.liamb.Address;

public class TestAddress {

    @Test
    @DisplayName("\"\" -> null")
    void testIsPostalCodeValid() {
        Address address = new Address(-1, "Street", "City", Address.Province.QC, "");
        String expectedPostalCode = null;
        String actualPostalCode = address.getPostalCode();

        Assertions.assertEquals(expectedPostalCode, actualPostalCode);
    }

    @Test
    @DisplayName("null -> null")
    void testIsPostalCodeValid2() {
        Address address = new Address(-1, "Street", "City", Address.Province.QC, null);
        String expectedPostalCode = null;
        String actualPostalCode = address.getPostalCode();

        Assertions.assertEquals(expectedPostalCode, actualPostalCode);
    }

    @Test
    @DisplayName("QQQQQQ -> null")
    void testIsPostalCodeValid3() {
        Address address = new Address(-1, "Street", "City", Address.Province.QC, "QQQQQQ");
        String expectedPostalCode = null;
        String actualPostalCode = address.getPostalCode();

        Assertions.assertEquals(expectedPostalCode, actualPostalCode);
    }

    @Test
    @DisplayName("111111 -> null")
    void testIsPostalCodeValid4() {
        Address address = new Address(-1, "Street", "City", Address.Province.QC, "111111");
        String expectedPostalCode = null;
        String actualPostalCode = address.getPostalCode();

        Assertions.assertEquals(expectedPostalCode, actualPostalCode);
    }

    @Test
    @DisplayName("A1B2C3D4 -> null")
    void testIsPostalCodeValid5() {
        Address address = new Address(-1, "Street", "City", Address.Province.QC, "A1B2C3D4");
        String expectedPostalCode = null;
        String actualPostalCode = address.getPostalCode();

        Assertions.assertEquals(expectedPostalCode, actualPostalCode);
    }

    @Test
    @DisplayName("A1B2C3 -> A1B2C3")
    void testIsPostalCodeValid6() {
        Address address = new Address(-1, "Street", "City", Address.Province.QC, "A1B2C3");
        String expectedPostalCode = "A1B2C3";
        String actualPostalCode = address.getPostalCode();

        Assertions.assertEquals(expectedPostalCode, actualPostalCode);
    }
}
