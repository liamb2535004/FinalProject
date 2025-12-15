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
    @Getter private final String courseId;
    @Getter private String courseName;
    @Getter @Setter private double credits;
    @Getter @Setter private Department department;
    private final List<Assignment> assignments;
    private final List<Student> registeredStudents;

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

    /**
     * verifies if the total weight of the assignments adds up to 100%.
     * @return if the total weight of the assignments doesn't add up to 100%, returns false. Otherwise, returns true.
     */
    public boolean isAssignmentWeightValid() {
        double totalWeight = 0.0;
        for (Assignment assignment : assignments) {
            totalWeight += assignment.getWeight();
        }
        return Math.abs(totalWeight - 100.0) < 0.001;
    }

    /**
     * registers a student for a course. The student will be added to the course's
     * registeredStudents list and the course will be added to the student's registeredCourses list
     * @param student the student to be added to the course's registeredStudents list and
     *  that will add the course to their registeredCourses list.
     * @return true if the student has been registered, false if they have not been
     * registered due to an invalid student or if the student has already been registered.
     */
    public boolean registerStudent(Student student) {
        if (student == null || this.registeredStudents.contains(student)) {
            return false;
        }
        this.registeredStudents.add(student);

        for (Assignment assignment : this.assignments) {
            assignment.getScores().add(null);
        }
        this.finalScores = null;
        student.registerCourse(this);
        return true;
    }

    /**
     * drops a student from the course, removing them from the registeredStudents list of the course
     * and removing the course from their registeredCourses list.
     * @param student the student to be dropped from the course.
     * @return true if the student has been dropped, false if the student has not been dropped because
     * of an invalid student or a non-existant student.
     */
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
        student.dropCourse(this);
        return true;
    }

    /**
     * calculates the average score of all students for an assignment
     * @return the average score of all students for an assignment
     */
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
                    Integer studentScore = currentAssignment.getScores().get(i);
                    double weight = currentAssignment.getWeight();
                    double weightedScore = studentScore * (weight / 100.0);
                    weightedSum += weightedScore;
                }
            }
            finalScores[i] = (int) Math.round(weightedSum);
        }
        return finalScores;
    }

    /**
     * adds an assignment to a course. Creates null placeholder values for the students scores.
     * @param assignmentName the name of the assignment
     * @param weight the weight of the assignment out of the total 100%
     * @return true if the assignment has been added, false if the assignment has not been
     * added due to an invalid assignment name or weight.
     */
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

    /**
     * generates random scores for all the assignments of all students.
     */
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

    /**
     * prints a formatted table of a course containing all students, assignments and scores
     */
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

    public void setCourseName(String courseName) {
        this.courseName = Util.toTitleCase(courseName);
    }
}
