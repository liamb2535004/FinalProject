package org.liamb;

import java.util.ArrayList;

public class Assignment {
    private String assignmentId;
    private String assignmentName;
    private double weight;
    private ArrayList<Integer> scores;
    private static int nextId = 1;

    void calcAssignmentAvg() {
        int sum = 0;
        for (int score : scores) {
            sum += score;
        }
        int avg = sum / scores.size();
    }
}
