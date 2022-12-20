package view;

import chessComponent.*;
import controller.GameController;
import model.ChessColor;
import model.ChessboardPoint;
import user.UserFrame;
import static user.UserFrame.*;

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
    private static JLabel blackScoreLabel = new JLabel();
    private static JLabel redScoreLabel = new JLabel();

    public static JButton userButton = new JButton("登录/注册");
    public static final EatenComponent[][] eatenComponents=new EatenComponent[2][7];
    public static final JLabel[][] eatenNumber = new JLabel[2][7];
    public static final int[][] eatenChessNumber = new int[2][7];

    public static String userName1="",userName2="";
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

    }

    private void initialUI() {


        JButton aiModeButton = new JButton("人机模式");
        JButton classicModeButton = new JButton("经典模式");

        classicModeButton.setLocation(WIDTH / 3, HEIGHT * 2 / 8 - HEIGHT / 30 + 300);
        classicModeButton.setSize(WIDTH / 3, HEIGHT / 8);
        classicModeButton.setBackground(new Color(245, 226, 178));
        classicModeButton.setFont(new Font("华文行楷", Font.BOLD,40));
        classicModeButton.setBorderPainted(false);

        addUserButton();
        addMusic();
        BackgroundPanel backGround = new BackgroundPanel(new ImageIcon("resources/pictures/start.png").getImage());

        add(classicModeButton);
        classicModeButton.addActionListener(e -> {
            System.out.println("Start Classic Mode");
            remove(backGround);remove(classicModeButton);
            remove(aiModeButton);remove(userButton);
            repaint();
            classicMode();
        });

        aiModeButton.setLocation(WIDTH / 3, HEIGHT * 3 / 8 + 300);
        aiModeButton.setSize(WIDTH / 3, HEIGHT / 8);
        aiModeButton.setBackground(new Color(245, 226, 178));
        aiModeButton.setFont(new Font("华文行楷", Font.BOLD,40));
        aiModeButton.setBorderPainted(false);
        add(aiModeButton);
        aiModeButton.addActionListener(e -> {
            System.out.println("Start AI Mode");
            remove(backGround);remove(classicModeButton);
            remove(aiModeButton);remove(userButton);
            repaint();
            classicMode();
        });

        Container ct = this.getContentPane();
        backGround.setBounds(0,0,WIDTH,HEIGHT - 20);
        ct.add(backGround);
    }
    /**
     * 在游戏窗体中添加棋盘
     */
    public void classicMode() {
        if (!(user1 == null) && !(user2 == null)) {
            userName1 = user1.userName;
            userName2 = user2.userName;
        }
        addChessboard();
        addLabel();
        addScore();
        addMenuButton();
        addBackground();
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
    static public void changeStatusLabel (ChessColor color,int blackSorce,int redScore){
        if (blackSorce >= 60 || redScore >= 60) {
            getStatusLabel().setForeground(Color.black);
            getStatusLabel().setText("胜负已定");
            return;
        }
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
        JLabel user1 = new JLabel(userName1);
        user1.setLocation(50, HEIGHT / 40 + 100);
        user1.setSize(250,50);
        user1.setFont(new Font("华文行楷", Font.BOLD, 35));
        add(user1);
        JLabel user2 = new JLabel(userName2);
        user2.setLocation(640, HEIGHT / 40 + 100);
        user2.setSize(250,50);
        user2.setFont(new Font("华文行楷", Font.BOLD, 35));
        user2.setForeground(new Color(159, 24, 24));
        add(user2);
        blackScoreLabel = new JLabel("0 / 60");
        blackScoreLabel.setLocation(40, 70);
        blackScoreLabel.setSize(250,50);
        blackScoreLabel.setFont(new Font("华文行楷", Font.BOLD, 40));
        add(blackScoreLabel);
        redScoreLabel = new JLabel("0 / 60");
        redScoreLabel.setLocation(635, 70);
        redScoreLabel.setSize(250,50);
        redScoreLabel.setFont(new Font("华文行楷",Font.BOLD, 40));
        redScoreLabel.setForeground(new Color(159, 24, 24));
        add(redScoreLabel);

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
    public static JLabel getBlackScoreLabel() {
        return blackScoreLabel;
    }
    public static JLabel getRedScoreLabel() {
        return redScoreLabel;
    }

    public static void restartLabels(ChessColor color, int R, int B) {
        changeStatusLabel(color,0,0);
        blackScoreLabel.setText(B + " / 60");
        redScoreLabel.setText(R + " / 60");

        for (int i = 0; i <= 1; i++) {
            for (int j = 0; j <= 6; j++) {
                try{
                    eatenNumber[i][j].setText(String.format("- %d", eatenChessNumber[i][j]));
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

    private void addUserButton(){
        userButton.setLocation(WIDTH - 160, HEIGHT - 880);
        userButton.setSize(110, 65);
        userButton.setBackground(new Color(245, 226, 178));
        userButton.setFont(new Font("华文行楷", Font.BOLD,15));
        userButton.setBorderPainted(false);
        add(userButton);

        userButton.addActionListener(e -> {
            UserFrame userFrame=new UserFrame(chessboard);
            userFrame.setVisible(true);
        });
    }

    public static void changeUserLabel(char a,char b,boolean turn) {
        if (turn) {
            userButton.setText(a + "&" + b);
            userButton.setFont(new Font("Rockwell", Font.BOLD, 25));
        } else {
            userButton.setText("登录/注册");
            userButton.setFont(new Font("华文行楷", Font.BOLD,15));
        }
    }
}
