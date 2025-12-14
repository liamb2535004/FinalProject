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
    private int[] finalScores;

    public Course(String courseName, double credits, Department department) {
        this.courseId = String.format("C-%s-%02d", department.getDepartmentId(), nextId++);
        this.courseName = Util.toTitleCase(courseName);
        this.credits = credits;
        this.department = department;
        this.assignments = new ArrayList<>();
        this.registeredStudents = new ArrayList<>();
        this.finalScores = new int[0];
    }

    public boolean isAssignmentWeightValid() {
        double totalWeight = 0.0;
        for (Assignment assignment : assignments) {
            totalWeight += assignment.getWeight();
        }
        return (totalWeight == 100);
    }

    public boolean registerStudent(Student student) {
        if (this.registeredStudents.contains(student)) {
            return false;
        }
        this.registeredStudents.add(student);

        for (Assignment assignment : this.assignments) {
            assignment.getScores().add(null);
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

    public boolean addAssignment(String assignmentName, double weight) {
        if (assignmentName == null || assignmentName.isBlank() || weight <= 0) {
            return false;
        }

        Assignment newAssignment = new Assignment(assignmentName, weight);
        for (Student student : this.registeredStudents) {
            newAssignment.getScores().add(null);
        }

        this.assignments.add(newAssignment);
        return true;
    }

    public void generateScores() {
        if (this.assignments.isEmpty() || this.registeredStudents.isEmpty() || !isAssignmentWeightValid()) {
            return;
        }

        for (Assignment assignment : this.assignments) {
            assignment.generateRandomScore(this.registeredStudents.size());
        }

        this.finalScores = this.calcStudentsAverage();
        if (this.finalScores.length != this.registeredStudents.size()) {
            this.finalScores = new int[0];
        }
    }
}
