package Major;

import MVC.Controller;

public class Game {
    private final int fieldWidth = 9;
    private final int fieldHeight = 9;
    private final int coloursCount = 3;
    //FIXME mb needs to be through config||start input

    private Controller gameController;

    void startNewGame() {
        gameController = null;
        play();
    }

    void play() {
        gameController = new Controller(fieldWidth, fieldHeight, coloursCount);
        System.out.println("That's all.");
    }
}
