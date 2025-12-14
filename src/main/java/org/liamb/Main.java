package org.liamb;

import java.nio.charset.CoderResult;
import java.sql.SQLOutput;

public class Main {
    public static void main(String[] args) {
        //Test displayScores() & toString & toSimplifiedString
        Department math = new Department("math");

        Course programming = new Course("programming", 2.5, math);

        Student student1 = new Student("ethan collins", Student.Gender.MALE,
                new Address(1, "street", "city", Address.Province.QC, "A1B2C3"), math);
        Student student2 = new Student("lucas bennett", Student.Gender.MALE,
                new Address(1, "street", "city", Address.Province.QC, "A1B2C3"), math);
        Student student3 = new Student("ava harrington", Student.Gender.MALE,
                new Address(1, "street", "city", Address.Province.QC, "A1B2C3"), math);

        student1.registerCourse(programming);
        student2.registerCourse(programming);
        student3.registerCourse(programming);

        programming.addAssignment("Assignment01", 10);
        programming.addAssignment("Assignment02", 10);
        programming.addAssignment("Assignment03", 10);
        programming.addAssignment("Exam01", 35);
        programming.addAssignment("Exam02", 35);

        programming.generateScores();
        programming.displayScores();

        System.out.println(student1.toString());
        System.out.println(student1.toSimplifiedString());
        System.out.println(programming.toString());
        System.out.println(programming.toSimplifiedString());

    }
}