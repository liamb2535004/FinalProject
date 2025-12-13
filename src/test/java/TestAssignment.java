import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.liamb.Assignment;
import org.liamb.util.Util;

import java.util.ArrayList;
import java.util.List;

public class TestAssignment {
    @Test
    @DisplayName("0 -> 0")
    void testCalcAssignmentAvg() {
        Assignment assignment = new Assignment("Test Assignment", 5.0);
        double expected = 0.0;
        double actual = assignment.calcAssignmentAvg();
    }

    @Test
    @DisplayName("1, 2, 3, 4 -> 2.5")
    void testCalcAssignmentAvg2() {
        Assignment assignment = new Assignment("Test Assignment", 5.0);
        List<Integer> scores = assignment.getScores();
        scores.clear();
        List<Integer> newScores = List.of(1, 2, 3, 4);
        scores.addAll(newScores);

        double expected = 2.5;
        double actual = assignment.calcAssignmentAvg();

        Assertions.assertEquals(expected, actual, 0.001);
    }

    @Test
    @DisplayName("10 students -> 10 scores")
    void testGenerateRandomScore1() {
        Assignment assignment = new Assignment("Test Assignment", 5.0);
        int numStudents = 10;
        assignment.generateRandomScore(numStudents);
        List<Integer> scores = assignment.getScores();

        double expected = 10;
        double actual = scores.size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("scores > 0 && scores < 100")
    void testGenerateRandomScore2() {
        Assignment assignment = new Assignment("Test Assignment", 5.0);
        int numStudents = 10;
        assignment.generateRandomScore(numStudents);
        List<Integer> scores = assignment.getScores();

        boolean actual = true;
        for (Integer score : scores) {
            if (score > 100 || score < 0) {
                actual = false;
            }
        }
        boolean expected = true;

        Assertions.assertEquals(expected, actual);
    }
}
