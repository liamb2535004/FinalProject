package org.liamb;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
@Setter
public class Address {
    private int streetNo;
    private String street;
    private String city;
    private Province province;
    private String postalCode;

    public Address(int streetNo, String street, String city, Province province, String postalCode) {
        if (isPostalCodeValid(postalCode)) {
            this.streetNo = streetNo;
            this.street = street;
            this.city = city;
            this.province = province;
            this.postalCode = postalCode.toUpperCase();
        } else {
            this.streetNo = 0;
            this.street = null;
            this.city = null;
            this.province = null;
            this.postalCode = null;
        }
    }

    /**
     * checks if the postal code has a length of 6 and follows the CDCDCD format, where C is a letter and D is a digit
     * @param postalCode the postal code to be verified
     * @return true if the postal code is valid, false if it is invalid
     */
    public static boolean isPostalCodeValid(String postalCode) {
        if (postalCode == null || postalCode.length() != 6) {
            return false;
        }

        for (int idx = 0; idx < postalCode.length(); idx++) {

            if (idx % 2 == 0) {
                if (!Character.isLetter(postalCode.charAt(idx))) {
                    return false;
                }
            }

            if (idx % 2 != 0) {
                if (!Character.isDigit(postalCode.charAt(idx))) {
                    return false;
                }
            }
        }

        return true;
    }

    public enum Province {
        AB,
        MB,
        ON,
        SK,
        BC,
        NB,
        NS,
        PE,
        QC,
        NL,
        NT,
        NU,
        YT
    }
}
