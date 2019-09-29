import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertFalse;

public class GameOfLifeTest {

    private static final String[][] TEST_ARRAY = {
            {"X", "X", "X", "X", "X", "X"},
            {"X", "X", "O", "X", "X", "X"},
            {"X", "X", "X", "O", "O", "X"},
            {"X", "X", "O", "O", "X", "X"},
            {"X", "X", "X", "X", "X", "X"},
            {"X", "X", "X", "X", "X", "X"},
    };

    @Test
    public void readFromFile() {
        String[][] actualArray = GameOfLife.readFromFile();
        StringBuilder actualString = new StringBuilder();
        for (String[] lines : actualArray) {
            for (String line : lines) {
                actualString.append(line);
            }
        }
        StringBuilder expectedString = new StringBuilder();
        for (String[] lines : TEST_ARRAY) {
            for (String line : lines) {
                expectedString.append(line);
            }
        }
        assertEquals(expectedString.toString(), actualString.toString());
    }

    @Test
    public void getLength() {
        int actualLength = GameOfLife.getLength(7, 9);
        int expectedLength = 7;
        assertEquals(expectedLength, actualLength);
    }

    @Test
    public void isInsideBorder() {
        boolean actualIsInsideBorder = GameOfLife.isInsideBorder(-1, -1, TEST_ARRAY);
        assertFalse(actualIsInsideBorder);
    }

    @Test
    public void isCellAlive() {
        boolean actualIsCellAlive = GameOfLife.isCellAlive(1, 2, TEST_ARRAY);
        assertTrue(actualIsCellAlive);
    }

    @Test
    public void countOfNeighbours() {
        int actualCountNeighbours = GameOfLife.countOfNeighbours(1, 2, TEST_ARRAY);
        int expectedCountNeighbours = 1;
        assertEquals(expectedCountNeighbours, actualCountNeighbours);
    }

    @Test
    public void doIteration() {
        String[][] actualArray = GameOfLife.doIteration(TEST_ARRAY);
        String[][] expectedArray = {
                {"X", "X", "X", "X", "X", "X"},
                {"X", "X", "X", "O", "X", "X"},
                {"X", "X", "X", "X", "O", "X"},
                {"X", "X", "O", "O", "O", "X"},
                {"X", "X", "X", "X", "X", "X"},
                {"X", "X", "X", "X", "X", "X"}
        };
        StringBuilder actualString = new StringBuilder();
        for (String[] strings : actualArray) {
            for (String string : strings) {
                actualString.append(string);
            }
        }
        StringBuilder expectedString = new StringBuilder();
        for (String[] strings : expectedArray) {
            for (String string : strings) {
                expectedString.append(string);
            }
        }
        assertEquals(expectedString.toString(), actualString.toString());
    }

    @Test
    public void checkFieldSize() {
        boolean actualFieldSize = GameOfLife.checkFieldSize(7, 9, TEST_ARRAY);
        assertTrue(actualFieldSize);
    }
}