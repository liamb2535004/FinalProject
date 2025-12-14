package org.liamb;

import org.liamb.util.Util;

import java.lang.module.FindException;
import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseId;
    private String courseName;
    private double credits;
    private Department department;
    private List<Assignment> assignments;
    private List<Student> registeredStudents;
    private static int nextId = 1;
    private List<Double> finalScores;

    public Course(String courseName, double credits, Department department) {
        this.courseId = String.format("C-%s-%02d", department.getDepartmentId(), nextId++);
        this.courseName = Util.toTitleCase(courseName);
        this.credits = credits;
        this.department = department;
        this.assignments = new ArrayList<>();
        this.registeredStudents = new ArrayList<>();
    }

    public boolean isAssignmentWeightValid() {
        double totalWeight = 0.0;
        for (Assignment assignment : assignments) {
            totalWeight += assignment.getWeight();
        }
        return (totalWeight == 100);
    }

    public boolean registerStudent(Student student) {
        if (student == null || this.registeredStudents.contains(student)) {
            return false;
        }
        this.registeredStudents.add(student);
        this.finalScores.add(-1.0);

        for (Assignment assignment : this.assignments) {
            List<Integer> assignmentScores = assignment.getScores();
            assignmentScores.add(-1);
        }
        return true;
    }

    public boolean dropStudent(Student student) {
        if (student == null) {
            return false;
        }
        this.registeredStudents.remove(student);

        int index = this.registeredStudents.indexOf(student);
        for (Assignment assignment : this.assignments) {
            List<Integer> assignmentScores = assignment.getScores();
            assignmentScores.remove(index);
        }
        return true;
    }

    public int[] calcStudentsAverage() {
        if (!isAssignmentWeightValid()) {
            return new int[0];
        }

        int numStudents = this.registeredStudents.size();
        if (numStudents == 0) {
            return new int[0];
        }

        int[] finalScores = new int[numStudents];

        for (int i = 0; i < numStudents; i++) {
            double weightedSum = 0.0;

            for (int j = 0; j < this.assignments.size(); j++) {
                Assignment currentAssignment = this.assignments.get(j);

                if (currentAssignment.getScores().size() > i && currentAssignment.getScores().get(i) != null) {
                    int studentScore = currentAssignment.getScores().get(i);
                    double weight = currentAssignment.getWeight();
                    double weightedScore = studentScore * (weight / 100.0);
                    weightedSum += weightedScore;
                }
            }
            finalScores[i] = (int) Math.round(weightedSum);
        }
        return finalScores;
    }
}
