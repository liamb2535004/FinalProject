package org.liamb;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.liamb.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EqualsAndHashCode
public class Course {
    @Getter private String courseId;
    @Getter private String courseName;
    @Getter @Setter private double credits;
    @Getter private Department department;
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
        this.finalScores = null;
    }

    public boolean isAssignmentWeightValid() {
        double totalWeight = 0.0;
        for (Assignment assignment : assignments) {
            totalWeight += assignment.getWeight();
        }
        return Math.abs(totalWeight - 100.0) < 0.001;
    }

    public boolean registerStudent(Student student) {
        if (this.registeredStudents.contains(student)) {
            return false;
        }
        this.registeredStudents.add(student);

        for (Assignment assignment : this.assignments) {
            assignment.getScores().add(null);
        }
        return student.registerCourse(this);
    }

    public boolean dropStudent(Student student) {
        if (student == null) {
            return false;
        }

        int index = this.registeredStudents.indexOf(student);
        if (index == -1) {
            return false;
        }

        this.registeredStudents.remove(index);

        for (Assignment assignment : this.assignments) {
            List<Integer> assignmentScores = assignment.getScores();
            assignmentScores.remove(index);
        }
        return student.dropCourse(this);
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

        Assignment newAssignment = new Assignment(Util.toTitleCase(assignmentName), weight);
        for (Student student : this.registeredStudents) {
            newAssignment.getScores().add(null);
        }

        this.assignments.add(newAssignment);
        return true;
    }

    public void generateScores() {
        if (this.assignments.isEmpty() || this.registeredStudents.isEmpty() || !isAssignmentWeightValid()) {
            this.finalScores = null;
            return;
        }

        for (Assignment assignment : this.assignments) {
            assignment.generateRandomScore(this.registeredStudents.size());
        }

        this.finalScores = this.calcStudentsAverage();
        if (this.finalScores.length != this.registeredStudents.size()) {
            this.finalScores = null;
        }
    }

    public void displayScores() {
        if (this.registeredStudents.isEmpty() || this.assignments.isEmpty()) {
            System.out.println("\nCourse: " + this.courseName + "(" + this.courseId + ")");
            System.out.println("Cannot display scores: No students or no assignments");
            return;
        }

        if (this.finalScores == null || this.finalScores.length == 0) {
            System.out.println("\nCourse: " + this.courseName + "(" + this.courseId + ")");
            System.out.println("Cannot display scores: scores not calculated. call generateScores()");
            return;
        }

        int studentNameWidth = 0;
        for (Student student : this.registeredStudents) {
            if (student.getStudentName().length() > studentNameWidth) {
                studentNameWidth = student.getStudentName().length();
            }
        }
        studentNameWidth += 2;

        int scoreColumnWidth = 0;
        for (Assignment assignment : this.assignments) {
            if (assignment.getAssignmentName().length() > scoreColumnWidth) {
                scoreColumnWidth = assignment.getAssignmentName().length();
            }
            if (4 > scoreColumnWidth) {
                scoreColumnWidth = 4;
            }
        }
        scoreColumnWidth += 2;

        int finalScoreColumnWidth = scoreColumnWidth;
        if ("Final Score".length() + 2 > finalScoreColumnWidth) {
            finalScoreColumnWidth = "Final Score".length() + 2;
        }

        String nameFormat = "%-" + studentNameWidth + "s";
        String scoreFormat = "%" + scoreColumnWidth + "s";
        String finalScoreFormat = "%" + finalScoreColumnWidth + "s";

        System.out.println("\nCourse: " + this.courseName + "(" + this.courseId + ")");
        System.out.printf(nameFormat, "");
        for (Assignment assignment : this.assignments) {
            System.out.printf(scoreFormat, assignment.getAssignmentName());
        }
        System.out.printf(finalScoreFormat, "Final Score\n");

        for (int i = 0; i < this.registeredStudents.size(); i++) {
            Student student = this.registeredStudents.get(i);
            System.out.printf(nameFormat, student.getStudentName());

            for (int j = 0; j < this.assignments.size(); j++) {
                Assignment assignment = this.assignments.get(j);
                Integer score = assignment.getScores().get(i);
                String scoreStr = (score != null) ? String.valueOf(score) : "null";
                System.out.printf(scoreFormat, scoreStr);
            }

            int finalScore = this.finalScores[i];
            System.out.printf(finalScoreFormat, finalScore);
            System.out.println();
        }

        System.out.printf(nameFormat, "Average");
        for (Assignment assignment : this.assignments) {
            int averageScore = (int) assignment.calcAssignmentAvg();
            System.out.printf(scoreFormat, averageScore);
        }
        System.out.printf(finalScoreFormat, "");
        System.out.println("\n");
    }

    public String toSimplifiedString() {
        return "Course{" +
                "courseName='" + courseName + '\'' +
                ", courseId='" + courseId + '\'' +
                ", Credits=" + credits +
                ", departmentName=" + department.getDepartmentName() +
                '}';
    }

    @Override
    public String toString() {
        String studentsStr = "";
        int numStudents = this.registeredStudents.size();
        for (int i = 0; i < numStudents; i++) {
            Student student = this.registeredStudents.get(i);
            studentsStr += student.toSimplifiedString();

            if (i < numStudents - 1) {
                studentsStr += "\n";
            }
        }

        String assignmentsStr = "";
        int numAssingments = this.assignments.size();
        for (int i = 0; i < numAssingments; i++) {
            Assignment assignment = this.assignments.get(i);
            assignmentsStr += assignment.toString();

            if (i < numAssingments - 1) {
                assignmentsStr += "\n";
            }
        }

        double totalWeight = 0.0;
        for (Assignment assignment : this.assignments) {
            totalWeight += assignment.getWeight();
        }

        String weightStatus;
        if (this.isAssignmentWeightValid()) {
            weightStatus = "VALID: Total: 100%";
        } else {
            weightStatus = "INVALID (Total: " + String.format("%.1f", totalWeight) + "%)";
        }

        return "\ncourseId='" + this.courseId + "'" + ", CourseName='" + this.courseName + "'" +
                 ", credits=" + credits +
                ", "+ department +
                "\nassignments=\n" + assignmentsStr +
                "\nregisteredStudents=\n" + studentsStr +
                "\nAssignment Weight=" + weightStatus +
                "\n";
    }

    //unmodifiable getters
    public List<Assignment> getAssignments() {
        return List.copyOf(assignments);
    }

    public List<Student> getRegisteredStudents() {
        return List.copyOf(registeredStudents);
    }

    public int[] getFinalScores() {
        if (this.finalScores == null) {
            return new int[0];
        }
        return Arrays.copyOf(finalScores, finalScores.length);
    }

    //setters with Util.toTitleCase
    public void setDepartment(Department department) {
        this.department = department;
        Util.toTitleCase(department.getDepartmentName());
    }

    public void setCourseName(String courseName) {
        this.courseName = Util.toTitleCase(courseName);
    }
}
