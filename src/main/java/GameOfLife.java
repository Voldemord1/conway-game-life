import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameOfLife {

    private static int x;
    private static int y;
    private static int iterations;

    void start() {
        String[][] resultCells = doIteration(readFromFile());
        for (int i = 1; i < iterations; i++) {
            String[][] initialCells = resultCells;
            //get last x and y indexes of the array
            int lastX = initialCells.length - 1;
            int lastY = initialCells[0].length - 1;
            //the difference by which we need to move alive cell
            int deltaK = 0;
            int deltaL = 0;
            String[][] temp = new String[x][y];
            int length = getLength(lastX, lastY);
            for (int j = 0; j < length; j++) {
                if (initialCells[lastX][j].equals("O") || initialCells[j][lastY].equals("O")) {
                    //looking for the first alive cell
                    for (int k = 0; k < initialCells.length; k++) {
                        for (int l = 0; l < initialCells[k].length; l++) {
                            if (initialCells[k][l].equals("O")) {
                                deltaK = k;
                                deltaL = l;
                                break;
                            }
                        }
                        if (deltaK != 0 || deltaL != 0) {
                            break;
                        }
                    }

                    //moving all alive cells
                    for (int k = 0; k < initialCells.length; k++) {
                        for (int l = 0; l < initialCells[k].length; l++) {
                            if (initialCells[k][l].equals("O")) {
                                temp[k - deltaK][l - deltaL] = initialCells[k][l];
                                temp[k][l] = "X";
                            } else {
                                temp[k][l] = initialCells[k][l];
                            }
                        }
                    }
                    initialCells = temp;
                }
            }
            resultCells = doIteration(initialCells);
        }
        writeToFile(resultCells);
    }

    public static String[][] readFromFile() {
        String line;
        List<String> lines = new ArrayList<>();
        try (final BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] dimension = lines.get(0).split(" ");
        x = Integer.parseInt(dimension[0]);
        y = Integer.parseInt(dimension[1]);
        iterations = Integer.parseInt(lines.get(1));
        lines.remove(0);
        lines.remove(0);
        String[][] arrayFromFile = new String[lines.get(0).length()][lines.size()];
        for (int i = 0; i < arrayFromFile.length; i++) {
            String[] s = lines.get(i).split("");
            System.arraycopy(s, 0, arrayFromFile[i], 0, arrayFromFile[i].length);
        }
        return arrayFromFile;
    }

    public int getLength(int lastX, int lastY) {
        return (lastX < lastY) ? lastX : lastY;
    }

    //is the cell inside the field
    public static boolean isInsideBorder(int i, int j, String[][] array) {
        return i >= 0 && j >= 0 && i < array.length && j < array.length;
    }

    public static boolean isCellAlive(int i, int j, String[][] array) {
        return array[i][j].equals("O");
    }

    public static int countOfNeighbours(int x, int y, String[][] array) {
        int count = 0;
        for (int stepX = -1; stepX < 2; stepX++) {
            for (int stepY = -1; stepY < 2; stepY++) {
                int newX = x + stepX;
                int newY = y + stepY;
                if (isInsideBorder(newX, newY, array) && (newX != x || newY != y)) {
                    count += (isCellAlive(newX, newY, array)) ? 1 : 0;
                }
            }
        }
        return count;
    }

    public static String[][] doIteration(String[][] initCells) {
        // if field > two-dimensional array of characters "X" and "O"
        String[][] resultArray;
        if (checkFieldSize(x, y, initCells)) {
            resultArray = new String[x][y];
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    if (i < initCells.length && j < initCells[0].length) {
                        resultArray[i][j] = initCells[i][j];
                    } else {
                        resultArray[i][j] = "X";
                    }
                }
            }
        } else {
            // field < two-dimensional array of characters "X" and "O"
            resultArray = new String[initCells.length][initCells[0].length];
            for (int i = 0; i < initCells.length; i++) {
                System.arraycopy(initCells[i], 0, resultArray[i], 0, initCells[0].length);
            }
        }

        //main process of the "Game of Life"
        for (int i = 0; i < initCells.length; i++) {
            for (int j = 0; j < initCells[i].length; j++) {
                if (initCells[i][j].equals("X")) {
                    int countOfNeighbors = countOfNeighbours(i, j, initCells);
                    if (countOfNeighbors == 3) {
                        resultArray[i][j] = "O";
                    }
                } else if (initCells[i][j].equals("O")) {
                    int countOfNeighbors = countOfNeighbours(i, j, initCells);
                    if (countOfNeighbors < 2 || countOfNeighbors > 3) {
                        resultArray[i][j] = "X";
                    }
                }
            }
        }
        return resultArray;
    }

    public static boolean checkFieldSize(int x, int y, String[][] array) {
        return x >= array.length && y >= array[0].length;
    }

    public static void writeToFile(String[][] resultArray) {
        try (final FileWriter writer = new FileWriter("output.txt", false)) {
            for (String[] array : resultArray) {
                for (String s : array) {
                    writer.write(s);
                }
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
