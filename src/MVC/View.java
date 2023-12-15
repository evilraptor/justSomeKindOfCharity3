package MVC;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class View {
    private int fieldWidth;
    private int fieldHeight;
    JFrame frame = new JFrame("The Game");
    public JButton[][] cells;
    private int coloursCount;
    private ImageIcon[] containerOfIcons;

    public View(int xInputValue, int yInputValue, int colours) {
        fieldWidth = xInputValue;
        fieldHeight = yInputValue;
        cells = new JButton[fieldWidth][fieldHeight];
        for (int i = 0; i < fieldWidth; i++) {
            for (int j = 0; j < fieldHeight; j++) {
                cells[i][j] = new JButton();
                cells[i][j].setBounds(i * 100, j * 100, 100, 100);
                frame.add(cells[i][j]);
            }
        }

        coloursCount = colours;
        containerOfIcons = createContainerOfIcons(coloursCount);

        frame.setSize((fieldWidth + 2) * 100, (fieldHeight) * 100 + 60);//30 плашка сверху и 30 пустота снизу
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private ImageIcon[] createContainerOfIcons(int count) {
        int radius = 40;
        ImageIcon[] containerOfCircles = new ImageIcon[coloursCount + 1];
        containerOfCircles[0]=null;//0 is an empty cell fill
        for (int i = 1; i < count; i++) {
            Random random = new Random();
            int r = random.nextInt(256);
            int g = random.nextInt(256);
            int b = random.nextInt(256);
            Color circleColor = new Color(r, g, b);

            BufferedImage image = new BufferedImage(radius * 2, radius * 2, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();

            g2d.setColor(circleColor);
            g2d.fillOval(0, 0, radius * 2, radius * 2);
            g2d.dispose();

            ImageIcon icon = new ImageIcon(image);
            containerOfCircles[i] = icon;
        }
        return containerOfCircles;
    }

    ImageIcon[] getContainerOfIcons() {
        return containerOfIcons;
    }
}
