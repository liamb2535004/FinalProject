package org.liamb;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.liamb.util.Util;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public boolean registerCourse(Course course) {
        if (course == null || this.registeredCourses.contains(course)) {
            return false;
        }
        this.registeredCourses.add(course);
        return course.registerStudent(this);
    }

    public boolean dropCourse(Course course) {
        if (course == null || !this.registeredCourses.contains(course)) {
            return false;
        }

        this.registeredCourses.remove(course);
        return course.dropStudent(this);
    }

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
                '}';
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

    //custom Equals method that only checks studentId to prevent bug in dropStudent method where Student cannot be found
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(studentId, student.studentId);
    }

    @Override
    public int hashCode() {
        return studentId.hashCode();
    }


}
