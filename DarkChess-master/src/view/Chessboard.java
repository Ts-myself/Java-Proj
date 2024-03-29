package view;


import chessComponent.*;
import controller.RegretNode;
import model.*;
import controller.ClickController;
import static view.ChessGameFrame.*;
import static user.UserFrame.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import static model.ChessColor.*;

/**
 * 这个类表示棋盘组建，其包含：
 * SquareComponent[][]: 4*8个方块格子组件
 */
public class Chessboard extends JComponent {

    private static final int ROW_SIZE = 8;
    private static final int COL_SIZE = 4;

    private final SquareComponent[][] squareComponents = new SquareComponent[ROW_SIZE][COL_SIZE];

    static private ChessColor currentColor = ChessColor.NONE;

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
    public Stack<RegretNode> regretStack = new Stack<>();

    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize((width + 2)*2, (height + 6)*2);
        CHESS_SIZE = (height - 6) / 8 -8;
        SquareComponent.setSpacingLength((CHESS_SIZE +19)/ 12 );

        ChessImage.init();

        initAllChessOnBoard(null);
        Cheat cheat=new Cheat();
        add(cheat);
        cheat.setFocusable(true);
        cheat.requestFocusInWindow();
    }

    static public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(ChessColor currentColor) {
        Chessboard.currentColor = currentColor;
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
    public void swapChessComponents(SquareComponent chess1, SquareComponent chess2) throws IOException {
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

        if (blackScore >= 60 || redScore >= 60) {
            JButton end = new JButton(String.format("%s方胜", blackScore >= 60 ? "黑" : "红"));
            end.setLocation(this.getX()/10-20, this.getY()/10-80);
            end.setSize(this.getWidth()/2, this.getHeight()/2);
            end.setFont(new Font("华文行楷", Font.BOLD,50));
            end.setForeground(blackScore >= 60 ? new Color(0,0,0) : new Color(159, 24, 24));
            end.setBorderPainted(false);
            end.setFocusPainted(false);
            end.setContentAreaFilled(false);
            removeAll();
            add(end);
            repaint();
            end.addActionListener(e -> {
                System.out.println("Restarting Game!");
                initAllChessOnBoard(null);
                remove(end);
            });
            gameEndUser(blackScore);
        }
    }

    public void initAllChessOnBoard(List<String> chessData) {
        while (!regretStack.isEmpty()) regretStack.pop();
        int[][] board = new int[8][4];
        if (chessData == null) {
            board = Chessboard.randomIntBoard();
            blackScore = 0;
            redScore = 0;
            currentColor = ChessColor.NONE;
            for (int i=0;i<2;i++) for (int j=0;j<7;j++) eatenChessNumber[i][j] = 0;
        }
        else{
            boolean r = false;
            for (int i=0;i<chessData.size();i++){
                String[] info = chessData.get(i).split(" ");
                if (i == 0) { //读取分数与执子方
                    if (!info[0].equals("RED") && !info[0].equals("BLACK") && !info[0].equals("NONE"))
                    {new ErrorFrame("4"); return;}
                    setCurrentColor(ChessColor.valueOf(info[0]));
                    setRedScore(Integer.parseInt(info[1]));
                    setBlackScore(Integer.parseInt(info[2]));
                }
                else if (i<=2) { //读取被吃棋子
                    for (int j=0;j<7;j++){
                        eatenChessNumber[i-1][j] = Integer.parseInt(info[j]);
                    }
                } else if (info.length == 1) r = true;
                else if (!r) { //读取棋盘
                    if (i >= 11 || info.length > 4) {new ErrorFrame("2"); return;}
                    for (int j=0;j<4;j++)  board[i-3][j] = Integer.parseInt(info[j]);
                } else { //读取行棋历史
                    if (Integer.parseInt(info[1])== 2){
                        if (Integer.parseInt(info[2])>7 || Integer.parseInt(info[3])>3) {
                            new ErrorFrame("5"); return;
                        }
                    }
                    if (info[1].equals("1")) regretStack.add(new RegretNode(info[2],info[3]));
                    else regretStack.add(new RegretNode(Integer.parseInt(info[2]),Integer.parseInt(info[3])));
                }
            }
        }
        for (int i = 0; i < ROW_SIZE; i++) {
            for (int j = 0; j < COL_SIZE; j++) {
                if (!intToComponent(board[i][j], i, j)) {return;}
            }
        }
        ChessGameFrame.restartLabels(getCurrentColor(),getRedScore(),getBlackScore());
        repaint();
    }
    /*
    public void showOnChessboard (RegretNode regretNode){
        if (regretNode.which == 1){

        }else{
            squareComponents[regretNode.x][regretNode.y].setReversal(true);
            squareComponents[regretNode.x][regretNode.y].repaint();
        }

        try  { Thread.sleep(500); }
        catch (InterruptedException e) { throw new RuntimeException(e);  }

    }
     */

    public boolean intToComponent(int component, int i, int j){
        SquareComponent squareComponent = null;
        if (component/10 > 4 || component%10 >= 7 ) {new ErrorFrame("3"); return false;}
        if (component / 10 == 0){
            squareComponent = new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE, 0);
            putChessOnBoard (squareComponent);
        }
        else {
            ChessColor color = (component/10==1||component/10==3) ? ChessColor.RED : ChessColor.BLACK;

            if (component % 10 == 6) squareComponent = new GeneralChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE, 6);
            else if (component % 10 == 5) squareComponent = new AdvisorChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE, 5);
            else if (component % 10 == 4) squareComponent = new MinisterChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE, 4);
            else if (component % 10 == 3) squareComponent = new ChariotChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE, 3);
            else if (component % 10 == 2) squareComponent = new HorseChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE, 2);
            else if (component % 10 == 1) squareComponent = new CannonChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE, 1);
            else if (component % 10 == 0) squareComponent = new SoldierChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE, 0);

            if (component/10 == 3 || component/10 == 4) {
                assert squareComponent != null;
                squareComponent.setReversal(true);
            }
            assert squareComponent != null;
            //todo:  Error chess
            putChessOnBoard (squareComponent);
        }
        //load cheat
        Cheat cheat=new Cheat();
        add(cheat);
        cheat.setFocusable(true);
        return true;
    }

     /**
     * 绘制棋盘格子
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    /**
     * 将棋盘上行列坐标映射成Swing组件的Point
     */
    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE-5, row * CHESS_SIZE-2);
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
    public void paintReachable (ArrayList<ChessboardPoint> canGo,boolean turn){
        for (ChessboardPoint point : canGo) {
            this.getSquareComponents()[point.getX()][point.getY()].setReachable(turn);
            this.getSquareComponents()[point.getX()][point.getY()].repaint();
        }
    }
    public static int eatenChessValue(int i) {
        if(i==0) return 6;
        if(i>=1 && i<=2) return 5;
        if(i>=3 && i<=4) return 4;
        if(i>=5 && i<=6) return 3;
        if(i>=7 && i<=8) return 2;
        if(i>=9 && i<=10) return 1;
        if(i>=11 && i<=15) return 0;
        return 0;
    }

    public void ScoreRecorder(SquareComponent eaten , boolean forward) {
        if (eaten.getChessColor() == ChessColor.BLACK) {
            redScore = forward ? redScore + eatenChessValue(eaten) : redScore - eatenChessValue(eaten);
            ChessGameFrame.getRedScoreLabel().setText(String.format("%d / 60", getRedScore()));
        } else {
            blackScore = forward ? blackScore + eatenChessValue(eaten) : blackScore - eatenChessValue(eaten);
            ChessGameFrame.getBlackScoreLabel().setText(String.format("%d / 60", getBlackScore()));
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
        lines.add(getCurrentColor()+" "+this.getRedScore()+" "+this.getBlackScore());
        lines.add(eatenChessNumber[0][0]+" "+eatenChessNumber[0][1]+" "+eatenChessNumber[0][2]+" "+eatenChessNumber[0][3]+" "+eatenChessNumber[0][4]+" "+eatenChessNumber[0][5]+" "+eatenChessNumber[0][6]);
        lines.add(eatenChessNumber[1][0]+" "+eatenChessNumber[1][1]+" "+eatenChessNumber[1][2]+" "+eatenChessNumber[1][3]+" "+eatenChessNumber[1][4]+" "+eatenChessNumber[1][5]+" "+eatenChessNumber[1][6]);
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
        lines.add("");
        //处理regretStack
         Stack<RegretNode> clone = regretStack;
         Stack<RegretNode> upSideDown = new Stack<>();
        while (!clone.isEmpty()){
            upSideDown.add(clone.peek()); clone.pop();
        }
        int tot = 0;
        while (!upSideDown.isEmpty()){
            tot ++;
            RegretNode regretNode = upSideDown.peek(); upSideDown.pop();
            if (regretNode.which == 1) lines.add(regretNode.toString(tot));
            else lines.add(regretNode.toString(tot));
        }
        return lines;
     }


    public class Cheat extends JPanel {
        public Cheat() {
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    boolean ctrl,ms=false;
                    boolean r0, r1, r2, r3, r4, r5, r6, b0, b1, b2, b3, b4, b5, b6;
                    ctrl = e.getKeyCode() == KeyEvent.VK_CONTROL;
                    r0 = eatenComponents[0][0].mouseOn;
                    r1 = eatenComponents[0][1].mouseOn;
                    r2 = eatenComponents[0][2].mouseOn;
                    r3 = eatenComponents[0][3].mouseOn;
                    r4 = eatenComponents[0][4].mouseOn;
                    r5 = eatenComponents[0][5].mouseOn;
                    r6 = eatenComponents[0][6].mouseOn;
                    b0 = eatenComponents[1][0].mouseOn;
                    b1 = eatenComponents[1][1].mouseOn;
                    b2 = eatenComponents[1][2].mouseOn;
                    b3 = eatenComponents[1][3].mouseOn;
                    b4 = eatenComponents[1][4].mouseOn;
                    b5 = eatenComponents[1][5].mouseOn;
                    b6 = eatenComponents[1][6].mouseOn;

                    if (ctrl) {
                        SquareComponent cheatChess = null;
                        for (SquareComponent[] squareComponent : squareComponents) {
                            for (SquareComponent squareComponent1 : squareComponent) {
                                if (squareComponent1.mouseOn) {
                                    cheatChess=squareComponent1;
                                    ms=true;
                                    break;
                                }
                            }
                            if(ms) break;
                        }
                        if (ms) {
                            cheatChess.setCurrentReversal(true);
                            cheatChess.repaint();
                        } else if (!(r0 | r1 | r2 | r3 | r4 | r5 | r6 | b0 | b1 | b2 | b3 | b4 | b5 | b6)) {
                            for (SquareComponent[] squareComponent : squareComponents) {
                                for (SquareComponent squareComponent1 : squareComponent) {
                                    squareComponent1.setCurrentReversal(true);
                                    squareComponent1.repaint();
                                }
                            }
                        }
                        for (SquareComponent[] squareComponent : squareComponents) {
                            for (SquareComponent squareComponent1 : squareComponent) {
                                if (squareComponent1.getChessColor() == RED) {
                                    if (squareComponent1.type == 0 && r0) {
                                        squareComponent1.setCurrentReversal(true);
                                        squareComponent1.repaint();
                                    }
                                    if (squareComponent1.type == 1 && r1) {
                                        squareComponent1.setCurrentReversal(true);
                                        squareComponent1.repaint();
                                    }
                                    if (squareComponent1.type == 2 && r2) {
                                        squareComponent1.setCurrentReversal(true);
                                        squareComponent1.repaint();
                                    }
                                    if (squareComponent1.type == 3 && r3) {
                                        squareComponent1.setCurrentReversal(true);
                                        squareComponent1.repaint();
                                    }
                                    if (squareComponent1.type == 4 && r4) {
                                        squareComponent1.setCurrentReversal(true);
                                        squareComponent1.repaint();
                                    }
                                    if (squareComponent1.type == 5 && r5) {
                                        squareComponent1.setCurrentReversal(true);
                                        squareComponent1.repaint();
                                    }
                                    if (squareComponent1.type == 6 && r6) {
                                        squareComponent1.setCurrentReversal(true);
                                        squareComponent1.repaint();
                                    }
                                } else {
                                    if (squareComponent1.type == 0 && b0) {
                                        squareComponent1.setCurrentReversal(true);
                                        squareComponent1.repaint();
                                    }
                                    if (squareComponent1.type == 1 && b1) {
                                        squareComponent1.setCurrentReversal(true);
                                        squareComponent1.repaint();
                                    }
                                    if (squareComponent1.type == 2 && b2) {
                                        squareComponent1.setCurrentReversal(true);
                                        squareComponent1.repaint();
                                    }
                                    if (squareComponent1.type == 3 && b3) {
                                        squareComponent1.setCurrentReversal(true);
                                        squareComponent1.repaint();
                                    }
                                    if (squareComponent1.type == 4 && b4) {
                                        squareComponent1.setCurrentReversal(true);
                                        squareComponent1.repaint();
                                    }
                                    if (squareComponent1.type == 5 && b5) {
                                        squareComponent1.setCurrentReversal(true);
                                        squareComponent1.repaint();
                                    }
                                    if (squareComponent1.type == 6 && b6) {
                                        squareComponent1.setCurrentReversal(true);
                                        squareComponent1.repaint();
                                    }
                                }
                            }
                        }
                    }
                }
                @Override
                public void keyReleased(KeyEvent e) {
                    int keyCode = e.getKeyCode();
                    if (keyCode == KeyEvent.VK_CONTROL) {
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
    public void regret() {
        if (regretStack.isEmpty()){
            new ErrorFrame("6");
        }
        else{
            RegretNode regretNode = regretStack.peek();
            regretStack.pop();

            if (regretNode.which == 1){
                String[] infoC = regretNode.chessComponent.split(",");
                String[] infoE = regretNode.eatenComponent.split(",");
                intToComponent(Integer.parseInt(infoC[0])*10+Integer.parseInt(infoC[1]),Integer.parseInt(infoC[2]),Integer.parseInt(infoC[3]));
                intToComponent(Integer.parseInt(infoE[0])*10+Integer.parseInt(infoE[1]),Integer.parseInt(infoE[2]),Integer.parseInt(infoE[3]));
                this.squareComponents[Integer.parseInt(infoC[2])][Integer.parseInt(infoC[3])].repaint();
                this.squareComponents[Integer.parseInt(infoE[2])][Integer.parseInt(infoE[3])].repaint();
                this.ScoreRecorder(this.squareComponents[Integer.parseInt(infoE[2])][Integer.parseInt(infoE[3])],false);
                if (!infoE[0].equals("0")) {
                    changeEatenNumber(Integer.parseInt(infoE[1]), Integer.parseInt(infoE[0])%2 == 1 ? ChessColor.RED : BLACK, false);
                }}
            if (regretNode.which == 2){
                this.getSquareComponents()[regretNode.x][regretNode.y].setReversal(false);
                this.getSquareComponents()[regretNode.x][regretNode.y].repaint();
            }

            if (regretStack.isEmpty()) ChessGameFrame.changeStatusLabel(NONE,0,0);
            else this.clickController.swapPlayer();
        }
    }

}
