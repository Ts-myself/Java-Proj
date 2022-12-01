package view;


import chessComponent.*;
import model.*;
import controller.ClickController;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

/**
 * 这个类表示棋盘组建，其包含：
 * SquareComponent[][]: 4*8个方块格子组件
 */
public class Chessboard extends JComponent {


    private static final int ROW_SIZE = 8;
    private static final int COL_SIZE = 4;

    private final SquareComponent[][] squareComponents = new SquareComponent[ROW_SIZE][COL_SIZE];
    //todo: you can change the initial player
    private ChessColor currentColor = ChessColor.BLACK;

    //all chessComponents in this chessboard are shared only one model controller
    public final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;


    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width + 2, height);
        CHESS_SIZE = (height - 6) / 8;
        SquareComponent.setSpacingLength(CHESS_SIZE / 12);
        System.out.printf("chessboard [%d * %d], chess size = %d\n", width, height, CHESS_SIZE);

        initAllChessOnBoard();
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
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        squareComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        squareComponents[row2][col2] = chess2;

        //只重新绘制chess1 chess2，其他不变
        chess1.repaint();
        chess2.repaint();
    }


    //FIXME:   Initialize chessboard for testing only.
    public void initAllChessOnBoard() {
        int[][] board = Chessboard.randomIntBoard();
        for (int i = 0; i < ROW_SIZE; i++) {
            for (int j = 0; j < COL_SIZE; j++) {
                int component = board[i][j];
                ChessColor color = component/10 == 1 ? ChessColor.RED : ChessColor.BLACK;
                SquareComponent squareComponent = null;
                     if (component%10 == 6) squareComponent = new GeneralChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                else if (component%10 == 5) squareComponent = new AdvisorChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                else if (component%10 == 4) squareComponent = new MinisterChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                else if (component%10 == 3) squareComponent = new ChariotChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                else if (component%10 == 2) squareComponent = new HorseChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                else if (component%10 == 1) squareComponent = new SoldierChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                else if (component%10 == 0) squareComponent = new CannonChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                assert squareComponent != null;
                squareComponent.setVisible(true);
                putChessOnBoard(squareComponent);
            }
        }
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

    /**
     * 通过GameController调用该方法
     */

    public void loadGame(List<String> chessData) {
        chessData.forEach(System.out::println);
        /*
        for (String line : chessData){
            for (int i=0;i<line.length();i++){
                //todo: load in;
            }
        }
         */
    }

    public static int[][] randomIntBoard(){
        //十位 0:空 1:红（未翻） 2:黑（未翻） 3:红 4:黑
        //个位/炮：0 兵：1 马：2 车：3 象：4 士：5 将：6
        Random random = new Random();
        int[][] chessboard = new int[8][4];
        int red=0;
        //add same random color
        //todo: random bug here
        while (red<16){
            int site = random.nextInt(32)+1;
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
                    chessboard[i][j] +=chessValue(redPosition[i_red++]);
                }
                if (chessboard[i][j] == 20) {
                    chessboard[i][j] +=chessValue(blackPosition[i_black++]);
                }
            }
        }
            return chessboard;
    }
    public static int chessValue(int i) {
        if(i==0) return 6;
        if(i>=1 && i<=2) return 5;
        if(i>=3 && i<=4) return 4;
        if(i>=5 && i<=6) return 3;
        if(i>=7 && i<=8) return 2;
        if(i>=9 && i<=13) return 1;
        if(i>=14 && i<=15) return 0;
        return 0;
    }
}
