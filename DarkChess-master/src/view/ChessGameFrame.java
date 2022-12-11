package view;


import controller.ClickController;
import controller.GameController;
import model.ChessColor;


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
    private static JLabel statusLabel = new JLabel();
    private static JLabel blackScore = new JLabel();
    private static JLabel redScore = new JLabel();
    Chessboard chessboard;
    public ChessGameFrame(int width, int height) {
        setTitle("Dark Chess");
        this.WIDTH = width;
        this.HEIGHT = height;
        this.CHESSBOARD_SIZE = HEIGHT * 4 / 5;
        chessboard = new Chessboard(CHESSBOARD_SIZE / 2, CHESSBOARD_SIZE, gameController);
        ClickController clickController = new ClickController(chessboard);

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        initialUI();

        /*
        Image image = Toolkit.getDefaultToolkit().getImage("resources/image-chess-1/chess-bishop-black.png").getScaledInstance(100, 100, Image.SCALE_FAST);
        JComponent imageComponent = new ImageComponent(image);// create an instance of ImageComponent
        imageComponent.setSize(100, 100);
        imageComponent.setLocation(50, 50); // set absolute location
        this.add(imageComponent);// add Jcomponent into Jframe
         */
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
            classicMode();
            remove(gameName);remove(classicModeButton);remove(aiModeButton);
            repaint();
        });

        aiModeButton.setLocation(WIDTH / 3, HEIGHT * 3 / 8);
        aiModeButton.setSize(WIDTH / 3, HEIGHT / 8);
        aiModeButton.setFont(new Font("Colonna MT", Font.BOLD,30));
        add(aiModeButton);
        aiModeButton.addActionListener(e -> {
            System.out.println("Start AI Mode");
            classicMode();
            remove(gameName);remove(classicModeButton);remove(aiModeButton);
            repaint();
        });

        gameName.setVisible(true);
        classicModeButton.setVisible(true);
        aiModeButton.setVisible(true);
    }



    /**
     * 在游戏窗体中添加棋盘
     */
    public void classicMode() {
        addChessboard();
        addLabel();
        addScore();
        addMenuButton();
        addMusic();


        Container ct = this.getContentPane();
        BackgroundPanel backGround = new BackgroundPanel(new ImageIcon("resources/pictures/background.jpg").getImage());
        backGround.setBounds(0,0,WIDTH,HEIGHT);
        ct.add(backGround);
    }
    private void addChessboard() {
        gameController = new GameController(chessboard);
        chessboard.setLocation(210, HEIGHT / 10);
        add(chessboard);
    }
    /**
     * 在游戏窗体中添加标签
     */
    private void addLabel() {
        statusLabel = new JLabel("The First TURN");
        statusLabel.setLocation(290, HEIGHT / 50);
        statusLabel.setSize(400, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 30));
        add(statusLabel);
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
        blackScore = new JLabel("Black's Score: 0");
        blackScore.setLocation(50, HEIGHT / 40);
        blackScore.setSize(250,50);
        blackScore.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(blackScore);
        redScore = new JLabel("Red's Score: 0");
        redScore.setLocation(580, HEIGHT / 40);
        redScore.setSize(250,50);
        redScore.setFont(new Font("Rockwell",Font.BOLD, 20));
        redScore.setForeground(new Color(255,10,10));
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

    public static void restartLabels(ChessColor color, int R, int B) {
        statusLabel.setText("The " +color+ " Turn");
        blackScore.setText(("Black's Score: "+B));
        redScore.setText("Red's Score: "+R);
    }
    private void addMenuButton(){
        JButton button = new JButton("...");
        button.setLocation(WIDTH - 100, HEIGHT - 100);
        button.setSize(60, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.RED);
        add(button);

        button.addActionListener(e -> {
            MenuFrame menuFrame = new MenuFrame(gameController, chessboard);
            menuFrame.setVisible(true);
        });
    }

}
