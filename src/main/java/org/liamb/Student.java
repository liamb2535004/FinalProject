package org.liamb;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String studentId;
    private String studentName;
    private Gender gender;
    private Address address;
    private Department department;
    private List<Course> registeredCourses;
    private static int nextId = 1;

    public Student(String studentName, Gender gender, Address address, Department department) {
        this.studentId = String.format("S%06d", nextId++);
        this.studentName = studentName;
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

    public enum Gender {
        FEMALE,
        MALE
    }
}
