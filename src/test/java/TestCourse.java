import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.liamb.*;
import org.liamb.util.Util;

import java.util.ArrayList;
import java.util.List;

public class TestCourse {
    @Test
    @DisplayName("Weight: 70 + 30 -> valid")
    void testIsAssignmentWeightValid1() {
        Department math = new Department("math");
        Course testCourse1 = new Course("test course", 2.5, math);
        testCourse1.addAssignment("testAssignment1", 30.0);
        testCourse1.addAssignment("testAssignment2", 70.0);

        Assertions.assertTrue(testCourse1.isAssignmentWeightValid());
    }

    @Test
    @DisplayName("Weight: 30 + 30 -> invalid")
    void testIsAssignmentWeightValid2() {
        Department math = new Department("math");
        Course testCourse1 = new Course("test course", 2.5, math);
        testCourse1.addAssignment("testAssignment1", 30.0);
        testCourse1.addAssignment("testAssignment2", 30.0);

        Assertions.assertFalse(testCourse1.isAssignmentWeightValid());
    }

    @Test
    @DisplayName("Weight: -30 + -30 -> invalid")
    void testIsAssignmentWeightValid3() {
        Department math = new Department("math");
        Course testCourse1 = new Course("test course", 2.5, math);
        testCourse1.addAssignment("testAssignment1", -30.0);
        testCourse1.addAssignment("testAssignment2", -30.0);

        Assertions.assertFalse(testCourse1.isAssignmentWeightValid());
    }

    @Test
    @DisplayName("No assignment -> invalid")
    void testIsAssignmentWeightValid4() {
        Department math = new Department("math");
        Course testCourse1 = new Course("test course", 2.5, math);

        Assertions.assertFalse(testCourse1.isAssignmentWeightValid());
    }

    @Test
    @DisplayName("Weight: 99.999999 -> valid")
    void testIsAssignmentWeightValid5() {
        Department math = new Department("math");
        Course testCourse1 = new Course("test course", 2.5, math);
        testCourse1.addAssignment("testAssignment1", 33.333333);
        testCourse1.addAssignment("testAssignment2", 33.333333);
        testCourse1.addAssignment("testAssignment3", 33.333333);

        Assertions.assertTrue(testCourse1.isAssignmentWeightValid());
    }

    @Test
    @DisplayName("registerStudent (not already registered) -> course contains student " +
            "and student's registered courses contains course")
    void testRegisterStudent() {
        Department math = new Department("math");
        Course testCourse1 = new Course("test course", 2.5, math);
        Student testStudent = new Student("ethan collins", Student.Gender.MALE,
                new Address(1, "street", "city", Address.Province.QC, "A1B2C3"), math);
        testCourse1.registerStudent(testStudent);

        Assertions.assertTrue(testCourse1.getRegisteredStudents().contains(testStudent));
        Assertions.assertTrue(testStudent.getRegisteredCourses().contains(testCourse1));
    }

    @Test
    @DisplayName("registerStudent (already registered) -> false")
    void testRegisterStudent2() {
        Department math = new Department("math");
        Course testCourse1 = new Course("test course", 2.5, math);
        Student testStudent = new Student("ethan collins", Student.Gender.MALE,
                new Address(1, "street", "city", Address.Province.QC, "A1B2C3"), math);
        testCourse1.registerStudent(testStudent);

        Assertions.assertFalse(testCourse1.registerStudent(testStudent));
    }

    @Test
    @DisplayName("registerStudent -> Initialize scores for assignments as null")
    void testRegisterStudent3() {
        Department math = new Department("math");
        Course testCourse1 = new Course("test course", 2.5, math);
        Student testStudent = new Student("ethan collins", Student.Gender.MALE,
                new Address(1, "street", "city", Address.Province.QC, "A1B2C3"), math);
        testCourse1.addAssignment("testAssignment1", 33.333333);
        testCourse1.registerStudent(testStudent);

        List<Integer> scores = testCourse1.getAssignments().getFirst().getScores();

        Assertions.assertEquals(1, scores.size());
        Assertions.assertNull(scores.get(0));
    }

    @Test
    @DisplayName("registerStudents -> maintain student order, score order," +
            " student name stored in title case")
    void testRegisterStudent4() {
        Department math = new Department("math");
        Course testCourse1 = new Course("test course", 2.5, math);
        Student testStudent1 = new Student("ethan collins", Student.Gender.MALE,
                new Address(1, "street", "city", Address.Province.QC, "A1B2C3"), math);
        Student testStudent2 = new Student("lucas bennett", Student.Gender.MALE,
                new Address(1, "street", "city", Address.Province.QC, "A1B2C3"), math);
        testCourse1.addAssignment("testAssignment1", 33.333333);

        testCourse1.registerStudent(testStudent1);
        testCourse1.registerStudent(testStudent2);
        List<Integer> scores = testCourse1.getAssignments().getFirst().getScores();

        Assertions.assertEquals(2, testCourse1.getRegisteredStudents().size());
        Assertions.assertEquals(Util.toTitleCase("ethan collins"), testCourse1.getRegisteredStudents().get(0).getStudentName());
        Assertions.assertEquals(Util.toTitleCase("lucas bennett"), testCourse1.getRegisteredStudents().get(1).getStudentName());

        Assertions.assertEquals(2,scores.size());
        scores.set(0, 75);

        //[75, null]
        Assertions.assertEquals(75, scores.get(0));
        Assertions.assertNull(scores.get(1));
    }

    @Test
    @DisplayName("dropStudent() -> removes student from course and studentList")
    void testDropStudent1() {
        Department math = new Department("math");
        Course testCourse1 = new Course("test course", 2.5, math);
        Student testStudent1 = new Student("ethan collins", Student.Gender.MALE,
                new Address(1, "street", "city", Address.Province.QC, "A1B2C3"), math);

        testCourse1.registerStudent(testStudent1);
        Assertions.assertTrue(testCourse1.getRegisteredStudents().contains(testStudent1));
        Assertions.assertTrue(testStudent1.getRegisteredCourses().contains(testCourse1));

        testCourse1.dropStudent(testStudent1);
        Assertions.assertFalse(testCourse1.getRegisteredStudents().contains(testStudent1));
        Assertions.assertFalse(testStudent1.getRegisteredCourses().contains(testCourse1));
    }

    @Test
    @DisplayName("dropStudent(unregistered student) -> false")
    void testDropStudent2() {
        Department math = new Department("math");
        Course testCourse1 = new Course("test course", 2.5, math);
        Student testStudent1 = new Student("ethan collins", Student.Gender.MALE,
                new Address(1, "street", "city", Address.Province.QC, "A1B2C3"), math);

        Assertions.assertFalse(testCourse1.dropStudent(testStudent1));
        Assertions.assertEquals(0, testCourse1.getRegisteredStudents().size());
    }

    @Test
    @DisplayName("dropStudent(null) -> false")
    void testDropStudent3() {
        Department math = new Department("math");
        Course testCourse1 = new Course("test course", 2.5, math);

        Assertions.assertFalse(testCourse1.dropStudent(null));
    }

    @Test
    @DisplayName("registeredStudents={s1, s2, s3} + scores=[0, 1, 2] -> DropStudent(s2) -> s1, s3 + scores=[0, 2]")
    void testDropStudent4() {
        Department math = new Department("math");
        Course testCourse1 = new Course("test course", 2.5, math);
        Student testStudent1 = new Student("ethan collins", Student.Gender.MALE,
                new Address(1, "street", "city", Address.Province.QC, "A1B2C3"), math);
        Student testStudent2 = new Student("lucas bennett", Student.Gender.MALE,
                new Address(1, "street", "city", Address.Province.QC, "A1B2C3"), math);
        Student testStudent3 = new Student("ava harrington", Student.Gender.MALE,
                new Address(1, "street", "city", Address.Province.QC, "A1B2C3"), math);
        testCourse1.addAssignment("testAssignment1", 33.333333);

        testCourse1.registerStudent(testStudent1);
        testCourse1.registerStudent(testStudent2);
        testCourse1.registerStudent(testStudent3);

        List<Integer> scores = testCourse1.getAssignments().getFirst().getScores();
        List<Integer> newScores = List.of(0, 1, 2);
        scores.clear();
        scores.addAll(newScores);

        testCourse1.dropStudent(testStudent2);
        //Students={Ethan Collins, Ava HArrington}
        Assertions.assertEquals(2, testCourse1.getRegisteredStudents().size());
        Assertions.assertEquals("Ethan Collins", testCourse1.getRegisteredStudents().get(0).getStudentName());
        Assertions.assertEquals("Ava Harrington", testCourse1.getRegisteredStudents().get(1).getStudentName());

        //Scores=[0, 2]
        Assertions.assertEquals(0, scores.get(0));
        Assertions.assertEquals(2, scores.get(1));
    }

    @Test
    @DisplayName("addAssignment -> adds new assignment (in titleCase) and initializes scores to null")
    void testAddAssignment1() {
        Department math = new Department("math");
        Course testCourse1 = new Course("test course", 2.5, math);
        Student testStudent1 = new Student("ethan collins", Student.Gender.MALE,
                new Address(1, "street", "city", Address.Province.QC, "A1B2C3"), math);
        testCourse1.addAssignment("test assignment1", 33.333333);
        testCourse1.registerStudent(testStudent1);

        List<Integer> scores = testCourse1.getAssignments().getFirst().getScores();
        Assertions.assertEquals(1, testCourse1.getAssignments().size());
        Assertions.assertEquals(1, scores.size());
        Assertions.assertNull(scores.get(0));
        Assertions.assertEquals(Util.toTitleCase("test assignment1"), testCourse1.getAssignments().get(0).getAssignmentName());
    }

    @Test
    @DisplayName("addAssignment, weight=-10 -> false")
    void testAddAssignment2() {
        Department math = new Department("math");
        Course testCourse1 = new Course("test course", 2.5, math);

        Assertions.assertFalse(testCourse1.addAssignment("quiz", -10));
    }

    @Test
    @DisplayName("addAssignment, name=null -> false")
    void testAddAssignment3() {
        Department math = new Department("math");
        Course testCourse1 = new Course("test course", 2.5, math);

        Assertions.assertFalse(testCourse1.addAssignment(null, 10));
    }

    @Test
    @DisplayName("addAssignment with 0 students -> true, no scores")
    void testAddAssignment4() {
        Department math = new Department("math");
        Course testCourse1 = new Course("test course", 2.5, math);

        Assertions.assertTrue(testCourse1.addAssignment("quiz", 10));
        List<Integer> scores = testCourse1.getAssignments().getFirst().getScores();
        Assertions.assertEquals(0, scores.size());
    }

    @Test
    @DisplayName("invalid weight -> empty array")
    void testGenerateScores1() {
        Department math = new Department("math");
        Course testCourse1 = new Course("test course", 2.5, math);
        Student testStudent1 = new Student("ethan collins", Student.Gender.MALE,
                new Address(1, "street", "city", Address.Province.QC, "A1B2C3"), math);
        Student testStudent2 = new Student("lucas bennett", Student.Gender.MALE,
                new Address(1, "street", "city", Address.Province.QC, "A1B2C3"), math);
        Student testStudent3 = new Student("ava harrington", Student.Gender.MALE,
                new Address(1, "street", "city", Address.Province.QC, "A1B2C3"), math);
        testCourse1.addAssignment("testAssignment1", 50);

        testCourse1.registerStudent(testStudent1);
        testCourse1.registerStudent(testStudent2);
        testCourse1.registerStudent(testStudent3);

        testCourse1.generateScores();
        Assertions.assertEquals(0, testCourse1.getFinalScores().length);
    }

    @Test
    @DisplayName("no students -> empty array")
    void testGenerateScores2() {
        Department math = new Department("math");
        Course testCourse1 = new Course("test course", 2.5, math);
        testCourse1.addAssignment("testAssignment1", 100);

        testCourse1.generateScores();
        Assertions.assertEquals(0, testCourse1.getFinalScores().length);
    }

    @Test
    @DisplayName("valid case -> generate random scores -> multiply each score by weight " +
            "and add up to get finalScore")
    void testGenerateScores3() {
        Department math = new Department("math");
        Course testCourse1 = new Course("test course", 2.5, math);
        Student testStudent1 = new Student("ethan collins", Student.Gender.MALE,
                new Address(1, "street", "city", Address.Province.QC, "A1B2C3"), math);
        testCourse1.addAssignment("testAssignment1", 50);
        testCourse1.addAssignment("testAssignment2", 50);

        testCourse1.registerStudent(testStudent1);

        testCourse1.generateScores();

        List<Integer> scores1 = testCourse1.getAssignments().get(0).getScores();
        List<Integer> scores2 = testCourse1.getAssignments().get(1).getScores();

        int[] generatedScores = testCourse1.getFinalScores();
        int expected = (int) (scores1.getFirst() * 0.50 + scores2.getFirst() * 0.5);
        int actual = generatedScores[0];

        Assertions.assertEquals(expected, actual, 1);
    }
}
