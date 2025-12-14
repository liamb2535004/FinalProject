import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.liamb.*;

import java.util.ArrayList;
import java.util.List;

public class TestStudent {
    @Test
    @DisplayName("registerCourse(not registered) -> true -> student added to course's student list" +
            "and course list updated")
    void testRegisterCourse1() {
        Department math = new Department("Math");
        Course linearAlgebra = new Course("Linear Algebra", 2.5, math);
        Student student1 = new Student("John", Student.Gender.MALE,
                new Address(1, "street", "city", Address.Province.QC, "A1B2C3"), math);

        Assertions.assertEquals(0, student1.getRegisteredCourses().size());
        Assertions.assertTrue(student1.registerCourse(linearAlgebra));
        Assertions.assertTrue(student1.getRegisteredCourses().contains(linearAlgebra));
        Assertions.assertTrue(linearAlgebra.getRegisteredStudents().contains(student1));
    }

    @Test
    @DisplayName("registerCourse(already registered) -> false")
    void testRegisterCourse2() {
        Department math = new Department("Math");
        Course linearAlgebra = new Course("Linear Algebra", 2.5, math);
        Student student1 = new Student("John", Student.Gender.MALE,
                new Address(1, "street", "city", Address.Province.QC, "A1B2C3"), math);

        student1.registerCourse(linearAlgebra);
        Assertions.assertFalse(student1.registerCourse(linearAlgebra));
    }

    @Test
    @DisplayName("registerCourse(null) -> false")
    void testRegisterCourse3() {
        Department math = new Department("Math");
        Student student1 = new Student("John", Student.Gender.MALE,
                new Address(1, "street", "city", Address.Province.QC, "A1B2C3"), math);

        Assertions.assertFalse(student1.registerCourse(null));
    }

    @Test
    @DisplayName("DropCourse(registered) -> true -> remove student from course list and student list")
    void testDropCourse1() {
        Department math = new Department("Math");
        Course linearAlgebra = new Course("Linear Algebra", 2.5, math);
        Student student1 = new Student("John", Student.Gender.MALE,
                new Address(1, "street", "city", Address.Province.QC, "A1B2C3"), math);

        student1.registerCourse(linearAlgebra);
        Assertions.assertTrue(student1.getRegisteredCourses().contains(linearAlgebra));
        Assertions.assertTrue(linearAlgebra.getRegisteredStudents().contains(student1));

        Assertions.assertTrue(student1.dropCourse(linearAlgebra));
        Assertions.assertFalse(student1.getRegisteredCourses().contains(linearAlgebra));
        Assertions.assertEquals(0, student1.getRegisteredCourses().size());
        Assertions.assertFalse(linearAlgebra.getRegisteredStudents().contains(student1));
    }

    @Test
    @DisplayName("DropCourse(not registered) -> false")
    void testDropCourse2() {
        Department math = new Department("Math");
        Course linearAlgebra = new Course("Linear Algebra", 2.5, math);
        Student student1 = new Student("John", Student.Gender.MALE,
                new Address(1, "street", "city", Address.Province.QC, "A1B2C3"), math);


        Assertions.assertFalse(student1.dropCourse(linearAlgebra));
        Assertions.assertFalse(student1.getRegisteredCourses().contains(linearAlgebra));
        Assertions.assertEquals(0, student1.getRegisteredCourses().size());
        Assertions.assertFalse(linearAlgebra.getRegisteredStudents().contains(student1));
    }

    @Test
    @DisplayName("DropCourse(null) -> false")
    void testDropCourse3() {
        Department math = new Department("Math");
        Student student1 = new Student("John", Student.Gender.MALE,
                new Address(1, "street", "city", Address.Province.QC, "A1B2C3"), math);

        Assertions.assertFalse(student1.dropCourse(null));
    }
}
