package MVC;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class Controller {
    private Model controllerModel;
    private View controllerView;
    private int fieldWidth;
    private int fieldHeight;
    private int score = 0;
    private boolean enableButtons = true;
    private boolean isCellChose = false;
    private boolean isCellMoved = false;
    private boolean isRestartPressed = false;
    private boolean isExitPressed = false;
    private int chosenCell[];
    private int coloursCount;
    private int nextCircles[];
    private int circleSpawnCounter = 0;

    public Controller(int xInputValue, int yInputValue, int inputCountOfColours) {
        chosenCell = new int[2];
        nextCircles = new int[3];
        fieldWidth = xInputValue;
        fieldHeight = yInputValue;
        coloursCount = inputCountOfColours;
        controllerModel = new Model(xInputValue, yInputValue);
        controllerView = new View(xInputValue, yInputValue, inputCountOfColours);
        //controllerView.initialize();
        setMouseListenerOnCells();
        setMouseListenerOnRestart();
        fillRandomCircles();
        for (int i = 0; i < 3; i++) placeRandomCircle();
        while (true) {
            if (!makeTurn()) break;
        }
    }

    public boolean makeTurn() {
        if (isGameEnded()) return false;
        if (isCellMoved) {
            isCellMoved = false;
            fillRandomCircles();
            for (int c = 0; c < 3; c++) {
                checkLines();
                if (!placeRandomCircle()) return false;
                if (isGameEnded()) return false;
            }
            enableButtons = true;
            return true;
        }
        return true;
    }

    private boolean placeCircle(int x, int y, int condition) {
        if (controllerModel.getCellCondition(x, y) != 0) return false;
        if ((x > fieldWidth) || (y > fieldHeight)) return false;
        controllerModel.setCellCondition(x, y, condition);
        controllerView.setButtonIcon(x, y, condition);
        return true;
    }

    private boolean placeRandomCircle() {
        if (isGameEnded()) return false;
        while (true) {
            //if (!checkLines()) return false;
            if (isGameEnded()) return false;
            Random random = new Random();
            int x = random.nextInt(fieldWidth);
            int y = random.nextInt(fieldHeight);
            //int colour = random.nextInt(coloursCount) + 1;
            //if (placeCircle(x, y, colour)) {
            if (placeCircle(x, y, nextCircles[circleSpawnCounter])) {
                checkLines();
                System.out.println("random circle placed on:" + x + " " + y);
                if (circleSpawnCounter == 2) circleSpawnCounter = 0;
                else circleSpawnCounter++;
                return true;
            }
        }
    }

    private void fillRandomCircles() {
        for (int c = 0; c < 3; c++) {
            Random random = new Random();
            int colour = random.nextInt(coloursCount) + 1;
            nextCircles[c] = colour;
        }
        controllerView.setNextCircle1(nextCircles[0]);
        controllerView.setNextCircle2(nextCircles[1]);
        controllerView.setNextCircle3(nextCircles[2]);
    }

    private boolean checkLines() {
        int x = fieldWidth;
        int y = fieldHeight;
        boolean lineFound = false;

        //horizontal
        for (int i = 0; i < x; i++) {
            for (int j = 0; j <= y - 5; j++) {
                int value = controllerModel.getCellCondition(i, j);
                lineFound = true;
                if (value == 0) lineFound = false;
                for (int k = 1; k < 5; k++) {
                    int tmp = controllerModel.getCellCondition(i, j + k);
                    if (tmp != value) {
                        lineFound = false;
                        break;
                    }
                }
                if (lineFound) {
                    score = score + (2 * 5);
                    for (int k = 1; k < 5; k++) deleteCell(i, j + k);
                    controllerView.setScore(score);
                    return true;
                }
            }
        }

        //vertical
        for (int i = 0; i <= x - 5; i++) {
            for (int j = 0; j < y; j++) {
                int value = controllerModel.getCellCondition(i, j);
                lineFound = true;
                if (value == 0) lineFound = false;
                for (int k = 1; k < 5; k++) {
                    int tmp = controllerModel.getCellCondition(i + k, j);
                    if (tmp != value) {
                        lineFound = false;
                        break;
                    }
                }
                if (lineFound) {
                    score = score + (2 * 5);
                    for (int k = 1; k < 5; k++) deleteCell(i + k, j);
                    controllerView.setScore(score);
                    return true;
                }
            }
        }

        //diagonal l-r,u-d
        for (int i = 0; i <= x - 5; i++) {
            for (int j = 0; j <= y - 5; j++) {
                int value = controllerModel.getCellCondition(i, j);
                lineFound = true;
                if (value == 0) lineFound = false;
                for (int k = 1; k < 5; k++) {
                    int tmp = controllerModel.getCellCondition(i + k, j + k);
                    if (tmp != value) {
                        lineFound = false;
                        break;
                    }
                }
                if (lineFound) {
                    score = score + (2 * 5);
                    for (int k = 1; k < 5; k++) deleteCell(i + k, j + k);
                    controllerView.setScore(score);
                    return true;
                }
            }
        }

        //diagonal l-r,d-u
        for (int i = 4; i < x; i++) {
            for (int j = 0; j <= y - 5; j++) {
                int value = controllerModel.getCellCondition(i, j);
                lineFound = true;
                if (value == 0) lineFound = false;
                for (int k = 1; k < 5; k++) {
                    int tmp = controllerModel.getCellCondition(i - k, j + k);
                    if (tmp != value) {
                        lineFound = false;
                        break;
                    }
                }
                if (lineFound) {
                    score = score + (2 * 5);
                    for (int k = 1; k < 5; k++) deleteCell(i - k, j + k);
                    controllerView.setScore(score);
                    return true;
                }
            }
        }
        return lineFound;
    }

    private void deleteCell(int x, int y) {
        controllerModel.setCellCondition(x, y, 0);
        controllerView.setButtonIcon(x, y, 0);
    }

    private boolean isGameEnded() {
        return controllerModel.isFieldFull();
    }

    private void setMouseListenerOnCells() {
        for (int i = 0; i < fieldWidth; i++) {
            for (int j = 0; j < fieldHeight; j++) {
                controllerView.cells[i][j].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (enableButtons) {
                            Point windowPoint = controllerView.getWindowPoint();
                            int xPoint = (int) ((e.getLocationOnScreen().getX() - windowPoint.getX()) / 100.0);
                            int yPoint = (int) ((e.getLocationOnScreen().getY() - 30 - windowPoint.getY()) / 100.0);
                            System.out.println("in points " + xPoint + " " + yPoint);

                            if (isCellChose) {
                                int absX = Math.abs(chosenCell[0] - xPoint);
                                int absY = Math.abs(chosenCell[1] - yPoint);
                                System.out.println("absX: " + absX + "; absY: " + absY);
                                if ((absX <= 1) && (absY <= 1)) {
                                    if (controllerModel.getCellCondition(xPoint, yPoint) == 0) {
                                        int previousValue = controllerModel.getCellCondition(chosenCell[0], chosenCell[1]);
                                        controllerView.setButtonIcon(xPoint, yPoint, previousValue);
                                        controllerModel.setCellCondition(xPoint, yPoint, previousValue);

                                        controllerView.setButtonIcon(chosenCell[0], chosenCell[1], 0);
                                        controllerModel.setCellCondition(chosenCell[0], chosenCell[1], 0);
                                        isCellMoved = true;
                                        isCellChose = false;
                                        System.out.println();
                                        //enableButtons = false;
                                        if (!makeTurn()) System.out.println("That's all...");
                                    }
                                }
                            } else {
                                if (controllerModel.getCellCondition(xPoint, yPoint) != 0) {
                                    isCellChose = true;
                                    isCellMoved = false;
                                    chosenCell[0] = xPoint;
                                    chosenCell[1] = yPoint;
                                } else System.out.println("empty cell...");
                            }
                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });
            }
        }
    }

    private void setMouseListenerOnRestart() {
        controllerView.getRestartButton().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                score = 0;
                controllerView.setScore(score);
                for (int i = 0; i < fieldWidth; i++) {
                    for (int j = 0; j < fieldHeight; j++) {
                        controllerModel.setCellCondition(i, j, 0);
                        controllerView.setButtonIcon(i, j, 0);
                    }
                }
                for (int i = 0; i < 3; i++) placeRandomCircle();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

}
