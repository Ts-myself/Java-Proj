package view;

import controller.GameController;
import javax.swing.*;
import java.awt.*;

/**
 * 这个类表示游戏窗体，窗体上包含：
 * 1 Chessboard: 棋盘
 * 2 JLabel:  标签
 * 3 JButton： 按钮
 */
public class ChessGameFrame extends JFrame {
    private final int WIDTH;
    private final int HEIGHT;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;
    private static JLabel statusLabel = new JLabel();
    private static JLabel blackScore = new JLabel();
    private static JLabel redScore = new JLabel();
    public ChessGameFrame(int width, int height) {
        setTitle("2022 CS109 Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGHT = height;
        this.CHESSBOARD_SIZE = HEIGHT * 4 / 5;

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        addChessboard();
        addLabel();
        addScore();
        addHelloButton();
        addLoadButton();
        addReatartButton();
    }
    /**
     * 在游戏窗体中添加棋盘
     */
    private void addChessboard() {
        Chessboard chessboard = new Chessboard(CHESSBOARD_SIZE / 2, CHESSBOARD_SIZE);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10);
        add(chessboard);
    }

    /**
     * 在游戏窗体中添加标签
     */
    private void addLabel() {
        statusLabel = new JLabel("The First TURN");
        statusLabel.setLocation(WIDTH * 3 / 5, HEIGHT / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    private void addScore() {
        blackScore = new JLabel("Black's Score: 0");
        blackScore.setLocation(WIDTH / 4 - WIDTH / 6, HEIGHT / 40);
        blackScore.setSize(150,50);
        blackScore.setFont(new Font("Rockwell", 1, 18));
        add(blackScore);
        redScore = new JLabel("Red's Score: 0");
        redScore.setLocation(WIDTH / 2 - WIDTH / 7, HEIGHT / 40);
        redScore.setSize(150,50);
        redScore.setFont(new Font("Rockwell",1, 18));
        add(redScore);
    }

    public static JLabel getStatusLabel() {
        return statusLabel;
    }
    public static JLabel getBlackScore() {
        return blackScore;
    }
    public static JLabel getRedScore() {
        return redScore;
    }

    //todo:fix this method
    public static void restartLabels() {
        statusLabel.setText("The First Turn");
        blackScore.setText("Black's Score: 0");
        redScore.setText("Red's Score: 0");
    }
    /**
     * 在游戏窗体中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addHelloButton() {
        JButton button = new JButton("Show Hello Here");
        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Hello, world!"));
        button.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 120);
        button.setSize(180, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }


    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 240);
        button.setSize(180, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.LIGHT_GRAY);
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this, "Input Path here");
            gameController.loadGameFromFile(path);
        });
    }

        private void addReatartButton(){
            JButton button = new JButton("Restart");
            button.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 360);
            button.setSize(180, 60);
            button.setFont(new Font("Rockwell", Font.BOLD, 20));
            button.setBackground(Color.RED);
            add(button);

            button.addActionListener(e -> {
                System.out.println("Restarting Game!");
                gameController.restartGame();
            });
        }
}
