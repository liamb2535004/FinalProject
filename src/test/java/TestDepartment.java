import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.liamb.Address;
import org.liamb.Department;

public class TestDepartment {
    @Test
    @DisplayName("\"\" -> null")
    void testIsDepartmentNameValid() {
        Department dept = new Department("");
        String expectedName = null;
        String actualName = dept.getDepartmentName();
        Assertions.assertEquals(expectedName, actualName);
    }

    @Test
    @DisplayName("123 -> null")
    void testIsDepartmentNameValid2() {
        Department dept = new Department("123");
        String expectedName = null;
        String actualName = dept.getDepartmentName();
        Assertions.assertEquals(expectedName, actualName);
    }

    @Test
    @DisplayName("# -> null")
    void testIsDepartmentNameValid3() {
        Department dept = new Department("#");
        String expectedName = null;
        String actualName = dept.getDepartmentName();
        Assertions.assertEquals(expectedName, actualName);
    }

    @Test
    @DisplayName("Mathematics -> Mathematics")
    void testIsDepartmentNameValid4() {
        Department dept = new Department("Mathematics");
        String expectedName = "Mathematics";
        String actualName = dept.getDepartmentName();
        Assertions.assertEquals(expectedName, actualName);
    }
}
