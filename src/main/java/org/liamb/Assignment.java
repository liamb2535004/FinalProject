package org.liamb;

import java.util.ArrayList;
import java.util.Random;

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

    void generateRandomScore() {
        Random random = new Random();
        for (int i = 0; i < scores.size(); i++) {
            int rand1 = random.nextInt(11);

            int rand2 = switch (rand1) {
                case 0 -> random.nextInt(60);
                case 1, 2 -> random.nextInt(60, 70);
                case 3, 4 -> random.nextInt(70, 80);
                case 5, 6, 7, 8 -> random.nextInt(80, 90);
                case 9, 10 -> random.nextInt(90, 101);
                default -> 0;
            };

            scores.set(i, rand2);
        }
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "assignmentId='" + assignmentId + '\'' +
                ", assignmentName='" + assignmentName + '\'' +
                ", weight=" + weight +
                '}';
    }
}
