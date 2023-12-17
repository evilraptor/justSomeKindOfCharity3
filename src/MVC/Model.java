package MVC;

public class Model {
    private int[][] cellCondition;
    private int fieldWidth;
    private int fieldHeight;

    public Model(int Width, int Height) {
        fieldWidth = Width;
        fieldHeight = Height;
        cellCondition = new int[fieldHeight][fieldWidth];
    }

    public int getFieldWidth() {
        return fieldWidth;
    }

    public void setFieldWidth(int x) {
        fieldWidth = x;
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    public void setFieldHeight(int y) {
        fieldHeight = y;
    }

    public int getCellCondition(int x, int y) {
        return cellCondition[y][x];
    }

    public boolean setCellCondition(int x, int y, int value) {
        if ((x > fieldWidth) || (y > fieldHeight)) return false;
        else {
            cellCondition[y][x] = value;
            return true;
        }
    }

    public boolean isFieldFull() {
        for (int i = 0; i < fieldHeight; i++) {
            for (int j = 0; j < fieldWidth; j++) {
                if (cellCondition[i][j] == 0) return false;
            }
        }
        return true;
    }
}
