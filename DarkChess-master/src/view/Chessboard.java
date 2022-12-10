package view;


import chessComponent.*;
import model.*;
import controller.ClickController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static model.ChessColor.RED;

/**
 * 这个类表示棋盘组建，其包含：
 * SquareComponent[][]: 4*8个方块格子组件
 */
public class Chessboard extends JComponent{


    private static final int ROW_SIZE = 8;
    private static final int COL_SIZE = 4;

    private final SquareComponent[][] squareComponents = new SquareComponent[ROW_SIZE][COL_SIZE];
    private ChessColor currentColor = ChessColor.NONE;

    //all chessComponents in this chessboard are shared only one model controller
    public final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;
    //记录红黑双方分数
    private int blackScore;
    private int redScore;
    public int getBlackScore() {
        return blackScore;
    }
    public void setBlackScore(int blackScore) {
        this.blackScore = blackScore;
    }
    public int getRedScore() {
        return redScore;
    }
    public void setRedScore(int redScore) {
        this.redScore = redScore;
    }

    public SquareComponent[][] getSquareComponents() {
        return squareComponents;
    }

    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width + 2, height);
        CHESS_SIZE = (height - 6) / 8;
        SquareComponent.setSpacingLength(CHESS_SIZE / 12);
        System.out.printf("chessboard [%d * %d], chess size = %d\n", width, height, CHESS_SIZE);

        initAllChessOnBoard(null);
        KeyboardPanel keyboardPanel=new KeyboardPanel();
        add(keyboardPanel);
        keyboardPanel.setFocusable(true);
    }

    public SquareComponent[][] getChessComponents() {
        return squareComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(ChessColor currentColor) {
        this.currentColor = currentColor;
    }

    /**
     * 将SquareComponent 放置在 ChessBoard上。里面包含移除原有的component及放置新的component
     */
    public void putChessOnBoard(SquareComponent squareComponent) {
        int row = squareComponent.getChessboardPoint().getX(), col = squareComponent.getChessboardPoint().getY();
        if (squareComponents[row][col] != null) {
            remove(squareComponents[row][col]);
        }
        add(squareComponents[row][col] = squareComponent);
    }

    /**
     * 交换chess1 chess2的位置
     */
    public void swapChessComponents(SquareComponent chess1, SquareComponent chess2) {
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE,-1));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        squareComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        squareComponents[row2][col2] = chess2;

        //只重新绘制chess1 chess2，其他不变
        chess1.repaint();
        chess2.repaint();

        //todo:fix the method for ending
        if (blackScore >= 1 || redScore >= 1) {
            JLabel end = new JLabel(String.format("%s Win", blackScore >= 60 ? "Black" : "Red"));
            end.setLocation(WIDTH / 10, HEIGHT / 10);
            end.setSize(WIDTH / 50, HEIGHT / 50);
            end.setFont(new Font("Rockwell", Font.BOLD,40));
            end.setForeground(Color.LIGHT_GRAY);
            repaint();
        }
    }

    public void initAllChessOnBoard(List<String> chessData) {
        int[][] board = new int[8][4];
        if (chessData == null) {
            board = Chessboard.randomIntBoard();
            blackScore = 0;
            redScore = 0;
            currentColor = ChessColor.NONE;
        }
        else{
            for (int i=0;i<chessData.size();i++){
                String[] info = chessData.get(i).split(" ");
                if (i == 0){
                    setCurrentColor(ChessColor.valueOf(info[0]));
                    setRedScore(Integer.parseInt(info[1]));
                    setBlackScore(Integer.parseInt(info[2]));
                }
                else{
                    for (int j=0;j<4;j++){
                        board[i-1][j] = Integer.parseInt(info[j]);
                    }
                }
            }
        }
        for (int i = 0; i < ROW_SIZE; i++) {
            for (int j = 0; j < COL_SIZE; j++) {
                SquareComponent squareComponent = null;
                int component = board[i][j];
                if (component / 10 == 0){
                    squareComponent = new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE, -1);
                    putChessOnBoard (squareComponent);
                }
                else {
                    ChessColor color = (component/10==1||component/10==3) ? ChessColor.RED : ChessColor.BLACK;
                    if (component % 10 == 6)
                        squareComponent = new GeneralChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE, 6);
                    else if (component % 10 == 5)
                        squareComponent = new AdvisorChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE, 5);
                    else if (component % 10 == 4)
                        squareComponent = new MinisterChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE, 4);
                    else if (component % 10 == 3)
                        squareComponent = new ChariotChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE, 3);
                    else if (component % 10 == 2)
                        squareComponent = new HorseChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE, 2);
                    else if (component % 10 == 0)
                        squareComponent = new CannonChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE, 1);
                    else if (component % 10 == 1)
                        squareComponent = new SoldierChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE, 0);
                    assert squareComponent != null;
                    if (component/10 == 3 || component/10 == 4){
                        squareComponent.setReversal(true);
                    }
                    squareComponent.setVisible(true);
                    putChessOnBoard (squareComponent);
                }
            }
        }
        ChessGameFrame.restartLabels(getCurrentColor(),getRedScore(),getBlackScore());
        repaint();
    }

    /**
     * 绘制棋盘格子
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    /**
     * 将棋盘上行列坐标映射成Swing组件的Point
     */
    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE + 3, row * CHESS_SIZE + 3);
    }

    public static int[][] randomIntBoard(){
        //十位 0:空 1:红（未翻） 2:黑（未翻） 3:红 4:黑
        //个位/兵：0 炮：1 马：2 车：3 象：4 士：5 将：6
        Random random = new Random();
        int[][] chessboard = new int[8][4];
        int red=0;
        //add same random color
        while (red<16){
            int site = random.nextInt(0,32); //1~32
            int col=site%4;  int row=(site-col)/4;
            if (chessboard[row][col]==0){
                chessboard[row][col]=1;
                red++;
            }
        }
        for (int i=0;i<8;i++){
            for (int j=0;j<4;j++){
                if (chessboard[i][j]==0) chessboard[i][j]=2;
                chessboard[i][j]*=10;
            }
        }
        //add chess type
        int[] redPosition = new int[16];
        boolean[] isValid = new boolean[16];
        int[] blackPosition = new int[16];
        for (int i = 0; i < 16; i++) {isValid[i]=false;}
        for (int i = 0; i < 16; ) {
            int position = random.nextInt(0, 16);
            if (!isValid[position]) {
                redPosition[i] = position;
                i++;
                isValid[position] = true;
            }
        }
        for (int i = 0; i < 16; i++) {isValid[i]=false;}
        for (int i = 0; i < 16; ) {
            int position = random.nextInt(0, 16);
            if (!isValid[position]) {
                blackPosition[i] = position;
                i++;
                isValid[position] = true;
            }
        }
        int i_red = 0, i_black = 0;
        for (int i=0;i<8;i++){
            for (int j=0;j<4;j++){
                if (chessboard[i][j] == 10) {
                    chessboard[i][j] += eatenChessValue(redPosition[i_red++]);
                }
                if (chessboard[i][j] == 20) {
                    chessboard[i][j] += eatenChessValue(blackPosition[i_black++]);
                }
            }
        }
            return chessboard;
    }
    public static int eatenChessValue(int i) {
        if(i==0) return 6;
        if(i>=1 && i<=2) return 5;
        if(i>=3 && i<=4) return 4;
        if(i>=5 && i<=6) return 3;
        if(i>=7 && i<=8) return 2;
        if(i>=9 && i<=13) return 1;
        if(i>=14 && i<=15) return 0;
        return 0;
    }

    public void ScoreRecorder(SquareComponent eaten) {
        if (eaten.getChessColor() == ChessColor.BLACK) {
            redScore += eatenChessValue(eaten);
        } else {
            blackScore += eatenChessValue(eaten);
        }
    }
    public int eatenChessValue(SquareComponent eaten) {
        if (eaten.type == 6) {return 30;}
        if (eaten.type == 5) {return 10;}
        if (eaten.type == 4) {return 5;}
        if (eaten.type == 3) {return 5;}
        if (eaten.type == 2) {return 5;}
        if (eaten.type == 1) {return 5;}
        if (eaten.type == 0) {return 1;}
        return 0;
    }
     public List<String> pauseToInt (){
        List<String> lines = new ArrayList<>();
        lines.add(this.getCurrentColor()+" "+this.getRedScore()+" "+this.getBlackScore());
        for (int i=1;i<=8;i++){
            char[] string = new char[12];
            for (int j=0;j<4;j++){
                SquareComponent component = squareComponents[i-1][j];
                int kind, type;
                if (component instanceof EmptySlotComponent) {
                    type = 0; kind = 0;
                }
                else{
                    type = component.type;
                    if (component.getChessColor() == RED) kind = 1;
                    else kind = 2;
                    if (component.isReversal()) kind += 2;
                }
                string[3*j] = (char)(kind+'0'); string[3*j+1] = (char)(type+'0'); string[3*j+2] = ' ';
            }
            lines.add(String.valueOf(string));
        }
        return lines;
     }


    public class KeyboardPanel extends JPanel {
        public KeyboardPanel() {
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_SPACE) {
                    for (SquareComponent[] squareComponent : squareComponents) {
                        for (SquareComponent squareComponent1 : squareComponent) {
                            squareComponent1.setCurrentReversal(true);
                            squareComponent1.repaint();
                        }
                    }
                }
            }

                @Override
                public void keyReleased(KeyEvent e) {
                    int keyCode = e.getKeyCode();
                    if (keyCode == KeyEvent.VK_SPACE) {
                        for (SquareComponent[] squareComponent : getSquareComponents()) {
                            for (SquareComponent squareComponent1 : squareComponent) {
                                squareComponent1.setCurrentReversal(false);
                                squareComponent1.repaint();
                            }
                        }
                    }
                }
            });

        }

    }

}
