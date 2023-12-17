package MVC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Random;

public class View implements Runnable {
    private int fieldWidth;
    private int fieldHeight;
    JFrame frame = new JFrame("The Game");
    public JButton[][] cells;
    private int coloursCount;
    private ImageIcon[] containerOfIcons;
    private JButton restartButton,exitButton;
    private JTextField scoreTextArea;
    private boolean isExitPressed=false;
    private boolean freeExit=false;

    @Override
    public void run() {
        initialize();
    }

    public void initialize() {
        cells = new JButton[fieldWidth][fieldHeight];
        for (int i = 0; i < fieldWidth; i++) {
            for (int j = 0; j < fieldHeight; j++) {
                cells[i][j] = new JButton();
                cells[i][j].setBounds(i * 100, j * 100, 100, 100);
                frame.add(cells[i][j]);
            }
        }
        restartButton=new JButton();
        restartButton.setBounds(fieldWidth*100+20,120,150,50);
        restartButton.setText("restart");
        frame.add(restartButton);
        exitButton=new JButton();
        exitButton.setBounds(fieldWidth*100+20,200,150,50);
        exitButton.setText("exit");
        frame.add(exitButton);
        setMouseListenerOnExit();

        scoreTextArea= new JTextField(10);
        scoreTextArea.setBounds(fieldWidth*100+20,50, 150, 50);
        scoreTextArea.setText("0");
        frame.add(scoreTextArea);

        containerOfIcons = createContainerOfIcons(coloursCount);
        frame.setSize((fieldWidth + 2) * 100, (fieldHeight) * 100 + 60);//30 плашка сверху и 30 пустота снизу
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public View(int xInputValue, int yInputValue, int colours) {
        fieldWidth = xInputValue;
        fieldHeight = yInputValue;
        coloursCount = colours;
        run();
    }

    private ImageIcon[] createContainerOfIcons(int count) {
        count++;
        int radius = 40;
        ImageIcon[] containerOfCircles = new ImageIcon[coloursCount + 1];
        containerOfCircles[0] = null;//0 is an empty cell fill
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

    Point getWindowPoint() {
        return frame.getLocation();
    }

    ImageIcon[] getContainerOfIcons() {
        return containerOfIcons;
    }
    public void setScore(int score){scoreTextArea.setText(String.valueOf(score));}

    public void setButtonIcon(int x, int y, int i) {
        cells[x][y].setIcon(containerOfIcons[i]);
    }
    public JButton getRestartButton(){return restartButton;}

    private void setMouseListenerOnExit() {
        exitButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(freeExit)System.exit(0);
                freeExit=true;
                isExitPressed = true;
                JButton notification= new JButton();
                //notification.setBounds(fieldWidth*50,fieldHeight*50, 150, 50);
                notification.setBounds(fieldWidth*100,250,200,25);
                notification.setText("exit? auto quit in 10sec");
                frame.add(notification);
                frame.revalidate();

                Timer timer = new Timer(10000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (isExitPressed) {
                            System.exit(0);
                        }
                    }
                });
                timer.setRepeats(false);
                notification.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        isExitPressed = false;
                        timer.stop();
                        notification.setVisible(false);
                        notification.setEnabled(false);
                    }
                });
                timer.start();
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
