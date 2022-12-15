package view;

import chessComponent.*;
import controller.GameController;
import model.ChessColor;
import model.ChessboardPoint;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;

import java.io.File;
import java.lang.reflect.MalformedParametersException;


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
    private final Chessboard chessboard;
    private static JLabel statusLabel = new JLabel();
    private static JLabel blackScore = new JLabel();
    private static JLabel redScore = new JLabel();
    private static final EatenComponent[][] eatenComponents=new EatenComponent[2][7];
    private static final JLabel[][] eatenNumber = new JLabel[2][7];
    public static final int[][] eatenChessNumber = new int[2][7];
    public ChessGameFrame(int width, int height) {
        setTitle("Dark Chess");
        this.WIDTH = width;
        this.HEIGHT = height;
        this.CHESSBOARD_SIZE = HEIGHT * 6 / 7 -20;
        chessboard = new Chessboard(CHESSBOARD_SIZE / 2 + 30, CHESSBOARD_SIZE + 60);

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        setResizable(false);

        initialUI();
        //classicMode();


    }

    private void initialUI() {


        JLabel gameName= new JLabel("DARK CHESS");
        JButton aiModeButton = new JButton("AI Mode");
        JButton classicModeButton = new JButton("Classic Mode");


        gameName.setLocation(WIDTH  / 5, HEIGHT / 8 - HEIGHT /20);
        gameName.setSize(WIDTH* 3/ 5, HEIGHT / 6);
        gameName.setFont(new Font("Colonna MT", Font.BOLD,70));
        add(gameName);


        classicModeButton.setLocation(WIDTH / 3, HEIGHT * 2 / 8 - HEIGHT / 30);
        classicModeButton.setSize(WIDTH / 3, HEIGHT / 8);
        classicModeButton.setFont(new Font("Colonna MT", Font.BOLD,30));
        add(classicModeButton);
        classicModeButton.addActionListener(e -> {
            System.out.println("Start Classic Mode");
            remove(gameName);remove(classicModeButton);remove(aiModeButton);
            repaint();
            classicMode();
        });

        aiModeButton.setLocation(WIDTH / 3, HEIGHT * 3 / 8);
        aiModeButton.setSize(WIDTH / 3, HEIGHT / 8);
        aiModeButton.setFont(new Font("Colonna MT", Font.BOLD,30));
        add(aiModeButton);
        aiModeButton.addActionListener(e -> {
            System.out.println("Start AI Mode");
            remove(gameName);remove(classicModeButton);remove(aiModeButton);
            repaint();
            classicMode();
        });

    }
    /**
     * 在游戏窗体中添加棋盘
     */
    public void classicMode() {
        addChessboard();
        addLabel();
        addScore();
        addMusic();
        addMenuButton();
        addBackground();
        endGame();
    }
    private void addChessboard() {
        gameController = new GameController(chessboard);
        chessboard.setLocation(185, 88);
        add(chessboard);
    }
    private void addBackground(){
        Container ct = this.getContentPane();
        BackgroundPanel backGround = new BackgroundPanel(new ImageIcon("resources/pictures/background.jpg").getImage());
        backGround.setBounds(0,0,WIDTH,HEIGHT - 20);
        ct.add(backGround);
    }
    /**
     * 在游戏窗体中添加标签
     */
    private void addLabel() {
        statusLabel = new JLabel("先手定色");
        statusLabel.setLocation(283, HEIGHT / 50);
        statusLabel.setSize(400, 60);
        statusLabel.setFont(new Font("华文行楷", Font.BOLD, 50));
        add(statusLabel);
    }
    static public void changeStatusLabel (ChessColor color){
        if (color == ChessColor.NONE){
            getStatusLabel().setForeground(Color.black);
            getStatusLabel().setText("先手定色");
            return;
        }
        if (color == ChessColor.RED)    getStatusLabel().setForeground(new Color(159, 24, 24));
        else getStatusLabel().setForeground(Color.black);
        getStatusLabel().setText(String.format("%s方执子",color.getName()));
    }

    public static void changeEatenNumber(int type,ChessColor color,boolean turn) {
        if (turn) {
            eatenChessNumber[color == ChessColor.RED ? 0 : 1][type]++;
        } else {
            eatenChessNumber[color == ChessColor.RED ? 0 : 1][type]--;
        }
        eatenNumber[color == ChessColor.RED ? 0 : 1][type].setText(String.format("- %d", eatenChessNumber[color == ChessColor.RED ? 0 : 1][type]));
    }
    /**
     * 在游戏窗体中播放音乐
     */
    public void addMusic() throws MalformedParametersException {
        try
        {
            File musicPath = new File("resources/music/Gymnopedies No.1.wav");
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }


    private void addScore() {
        //绘制双方得分情况
        JLabel bScore = new JLabel("黑方得分");
        bScore.setLocation(22, HEIGHT / 40);
        bScore.setSize(250,50);
        bScore.setFont(new Font("华文行楷", Font.BOLD, 35));
        add(bScore);
        JLabel rScore = new JLabel("红方得分");
        rScore.setLocation(610, HEIGHT / 40);
        rScore.setSize(250,50);
        rScore.setFont(new Font("华文行楷",Font.BOLD, 35));
        rScore.setForeground(new Color(159, 24, 24));
        add(rScore);
        blackScore = new JLabel("0 / 60");
        blackScore.setLocation(40, 70);
        blackScore.setSize(250,50);
        blackScore.setFont(new Font("华文行楷", Font.BOLD, 40));
        add(blackScore);
        redScore = new JLabel("0 / 60");
        redScore.setLocation(635, 70);
        redScore.setSize(250,50);
        redScore.setFont(new Font("华文行楷",Font.BOLD, 40));
        redScore.setForeground(new Color(159, 24, 24));
        add(redScore);

        //绘制被吃棋子的计数
        for (int i = 0; i <= 1; i++) {
            for (int j = 0; j <= 6; j++) {
                eatenComponents[i][j] = new EatenComponent(new ChessboardPoint(0, 0), new Point(0, 0), i == 0 ? ChessColor.RED : ChessColor.BLACK, chessboard.clickController, 50, j);
                eatenComponents[i][j].setLocation(i == 0 ? 20 : 620, 100 + 90 * (j + 1));
                eatenComponents[i][j].setSize(100,100);
                eatenComponents[i][j].setVisible(true);
                add(eatenComponents[i][j]);

                eatenNumber[i][j] = new JLabel("- 0");
                eatenNumber[i][j].setLocation(i == 0 ? 110 : 710, 100 + 90 * (j + 1));
                eatenNumber[i][j].setSize(100,100);
                eatenNumber[i][j].setFont(new Font("华文行楷", Font.BOLD, 40));
                eatenNumber[i][j].setVisible(true);
                add(eatenNumber[i][j]);
            }
        }
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

    public static void restartLabels(ChessColor color, int R, int B) {
        changeStatusLabel(color);
        blackScore.setText(B + " / 60");
        redScore.setText(R + " / 60");
        for (int i = 0; i <= 1; i++) {
            for (int j = 0; j <= 6; j++) {
                eatenChessNumber[i][j]=0;
                try {
                    eatenNumber[i][j].setText("- 0");
                } catch (Exception ignored) {}
            }
        }

    }
    private void addMenuButton(){
        JButton button = new JButton("...");
        button.setLocation(WIDTH - 75, HEIGHT - 100);
        button.setSize(60, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(new Color(246, 245, 238));
        add(button);

        button.addActionListener(e -> {
            MenuFrame menuFrame = new MenuFrame(gameController, chessboard);
            menuFrame.setVisible(true);
        });
    }

    private void endGame() {
        //todo:endGame
        /*PropertyChangeListener end = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (gameController.chessboard.getBlackScore() >= 1 || gameController.chessboard.getRedScore() >= 1) {
                    JButton end = new JButton(String.format("%s Win", gameController.chessboard.getBlackScore() >= 60 ? "Black" : "Red"));
                    end.setLocation(WIDTH, HEIGHT);
                    end.setSize(WIDTH, HEIGHT);
                    end.setFont(new Font("Rockwell", Font.BOLD,50));
                    end.setForeground(Color.LIGHT_GRAY);
                    add(end);
                    end.addActionListener(e -> {
                        System.out.println("Restarting Game!");
                        gameController.restartGame();
                    });
                }
            }
        };*/
    }

}
