package MVC;

import java.util.Random;

public class Controller {
    private Model controllerModel;
    private View controllerView;
    private int fieldWidth;
    private int fieldHeight;
    private int score = 0;
    int coloursCount;

    public Controller(int xInputValue, int yInputValue, int inputCountOfColours) {
        fieldWidth = xInputValue;
        fieldHeight = yInputValue;
        coloursCount = inputCountOfColours;
        controllerModel = new Model(xInputValue, yInputValue);
        controllerView = new View(xInputValue, yInputValue, inputCountOfColours);
    }

    public boolean makeTurn() {


        return true;
    }

    private boolean placeCircle(int x, int y, int condition) {
        if ((x > fieldWidth) || (y > fieldHeight)) return false;
        controllerModel.setCellCondition(x, y, condition);
        return true;
    }

    private boolean placeRandomCircle() {
        if (!isGameEnded()) return false;
        while (true) {
            Random random = new Random();
            int x = random.nextInt(fieldWidth);
            int y = random.nextInt(fieldHeight);
            int colour = random.nextInt(coloursCount) + 1;
            if (placeCircle(x, y, colour)) return true;
        }
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
                for (int k = 1; k < 5; k++) {
                    int tmp = controllerModel.getCellCondition(i, j + k);
                    if ((tmp != value) && (tmp != 0)) {
                        lineFound = false;
                        break;
                    }
                }
                if (lineFound) {
                    for (int k = 1; k < 5; k++) deleteCell(i, j + k);
                    return true;
                }
            }
        }

        //vertical
        for (int i = 0; i <= x - 5; i++) {
            for (int j = 0; j < y; j++) {
                int value = controllerModel.getCellCondition(i, j);
                lineFound = true;
                for (int k = 1; k < 5; k++) {
                    int tmp = controllerModel.getCellCondition(i + k, j);
                    if (tmp != value) {
                        lineFound = false;
                        break;
                    }
                }
                if (lineFound) {
                    for (int k = 1; k < 5; k++) deleteCell(i + k, k);
                    return true;
                }
            }
        }

        return lineFound;
    }

    private void deleteCell(int x, int y) {
        controllerModel.setCellCondition(x, y, 0);
    }

    private boolean isGameEnded() {
        return controllerModel.isFieldFull();
    }
}
