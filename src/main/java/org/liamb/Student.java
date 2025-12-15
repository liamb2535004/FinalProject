package org.liamb;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.liamb.util.Util;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
public class Student {
    @Getter private final String studentId;
    @Getter private String studentName;
    @Getter private final Gender gender;
    @Getter private final Address address;
    @Setter @Getter private Department department;
    private final List<Course> registeredCourses;
    private static int nextId = 1;

    public Student(String studentName, Gender gender, Address address, Department department) {
        this.studentId = String.format("S%06d", nextId++);
        this.studentName = Util.toTitleCase(studentName);
        this.gender = gender;
        this.address = address;
        this.department = department;
        this.registeredCourses = new ArrayList<>();
    }

    /**
     * registers a student for a course. Adds the student to
     * the registeredStudents list for the course and adds the course to the
     * student's list of registeredCourses
     * @param course the course to be added to the student's list of registered courses and
     * that will add the student to their registered students.
     * @return true if the course has been registered, false if the course has not been
     * registered due to an invalid course or if the course is already registered.
     */
    public boolean registerCourse(Course course) {
        if (course == null || this.registeredCourses.contains(course)) {
            return false;
        }
        this.registeredCourses.add(course);
        course.registerStudent(this);
        return true;
    }

    /**
     * Drops a course from a students list of registeredCourses and removes the student from
     * the course's list of registered Students.
     * @param course the course to be removed from the list of registered courses and that
     * will remove the student from their list of registered students.
     * @return true if the course has been dropped and false if the course has not been dropped due to
     * an invalid course or if the course to be dropped is not registered.
     */
    public boolean dropCourse(Course course) {
        if (course == null || !this.registeredCourses.contains(course)) {
            return false;
        }

        this.registeredCourses.remove(course);
        course.dropStudent(this);
        return true;
    }

    /**
     * toString method that only includes the studentId, studentName and departmentName
     * @return a string containing the studentId, studentName and departmentName
     */
    public String toSimplifiedString() {
        return "Student{" +
                ", studentId=" + studentId +
                ", studentName=" + studentName +
                "departmentName=" + department.getDepartmentName() +
                '}';
    }

    @Override
    public String toString() {
        String coursesStr = "";
        int numCourses = this.registeredCourses.size();

        for (int i = 0; i < numCourses; i++) {
            Course course = this.registeredCourses.get(i);
            coursesStr += course.toSimplifiedString();

            if (i < numCourses - 1) {
                coursesStr += "\n";
            }
        }

        return "Student{" +
                "studentId=" + studentId +
                ", studentName=" + studentName +
                ", gender=" + gender +
                "\naddress=" + address +
                "\ndepartment=" + department +
                "\nregisteredCourses=\n" + coursesStr +
                '}' + "\n";
    }

    public enum Gender {
        FEMALE,
        MALE
    }

    public void setStudentName(String studentName) {
        this.studentName = Util.toTitleCase(studentName);
    }

    //to avoid calling getRegisteredCourses.clear()
    public List<Course> getRegisteredCourses() {
        return List.copyOf(registeredCourses);
    }
}
