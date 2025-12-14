package org.liamb;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.liamb.util.Util;

@ToString
@EqualsAndHashCode
@Getter
public class Department {
    private final String departmentId;
    @Setter private String departmentName;
    private static int nextId = 1;

    public Department(String departmentName) {
        if (isDepartmentNameValid(departmentName)) {
            this.departmentId = String.format("D%02d", nextId++);
            this.departmentName = Util.toTitleCase(departmentName);
        } else {
            this.departmentId = null;
            this.departmentName = null;
        }
    }
    /**
     * Checks if the department name is valid
     * @param departmentName the department name to be checked
     * @return true if name only contains letters and spaces, false if not
     */
    static boolean isDepartmentNameValid(String departmentName) {
        if (departmentName == null || departmentName.isBlank()) {
            return false;
        }

        for (int idx = 0; idx < departmentName.length(); idx++) {
            if (!Character.isLetter(departmentName.charAt(idx)) && departmentName.charAt(idx) != ' ') {
                return false;
            }
        }

        return true;
    }
}
