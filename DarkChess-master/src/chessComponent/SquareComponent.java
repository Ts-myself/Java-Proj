package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;
import view.Chessboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * 这个类是一个抽象类，主要表示8*4棋盘上每个格子的棋子情况。
 * 有两个子类：
 * 1. EmptySlotComponent: 空棋子
 * 2. ChessComponent: 表示非空棋子
 */
public abstract class SquareComponent extends JComponent {

    protected static int spacingLength;
    /**
     * chessboardPoint: 表示8*4棋盘中，当前棋子在棋格对应的位置，如(0, 0), (1, 0)等等
     * chessColor: 表示这个棋子的颜色，有红色，黑色，无色三种
     * isReversal: 表示是否翻转
     * reachable: 表示被选中棋子是否可以到达
     * selected: 表示这个棋子是否被选中
     */
    public ChessboardPoint chessboardPoint;
    protected final ChessColor chessColor;
    protected boolean isReversal;
    protected boolean currentReversal;
    protected boolean reachable;
    private boolean selected;
    protected Image canMoveImage;
    Image redCanMoveImage = Toolkit.getDefaultToolkit().getImage("resources/image-chess/red-canMove.png");
    Image blackCanMoveImage = Toolkit.getDefaultToolkit().getImage("resources/image-chess/black-canMove.png");


    public int type;
    /**
     * handle click event
     */
    private final ClickController clickController;

    protected SquareComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size, int type) {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        setLocation(location);
        setSize(size +10, size +10);
        this.chessboardPoint = chessboardPoint;
        this.chessColor = chessColor;
        this.selected = false;
        this.clickController = clickController;
        this.isReversal = false;
        this.type = type;
    }

    public boolean isReversal() {
        return isReversal;
    }

    public void setReversal(boolean reversal) {
        isReversal = reversal;
    }

    public void setCurrentReversal(boolean currentReversal) {
        this.currentReversal = currentReversal;
    }

    public boolean isReachable() {return reachable;}

    public void setReachable(boolean reachable) {this.reachable = reachable;}

    public static void setSpacingLength(int spacingLength) {
        SquareComponent.spacingLength = spacingLength;
    }

    public ChessboardPoint getChessboardPoint() {
        return chessboardPoint;
    }

    public void setChessboardPoint(ChessboardPoint chessboardPoint) {
        this.chessboardPoint = chessboardPoint;
    }

    public ChessColor getChessColor() {
        return chessColor;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * @param another 主要用于和另外一个棋子交换位置
     *                <br>
     *                调用时机是在移动棋子的时候，将操控的棋子和对应的空位置棋子(EmptySlotComponent)做交换
     */
    public void swapLocation(SquareComponent another) {
        ChessboardPoint chessboardPoint1 = getChessboardPoint(), chessboardPoint2 = another.getChessboardPoint();
        Point point1 = getLocation(), point2 = another.getLocation();
        setChessboardPoint(chessboardPoint2);
        setLocation(point2);
        another.setChessboardPoint(chessboardPoint1);
        another.setLocation(point1);
    }

    /**
     * @param e 响应鼠标监听事件
     *          <br>
     *          当接收到鼠标动作的时候，这个方法就会自动被调用，调用监听者的onClick方法，处理棋子的选中，移动等等行为。
     */
    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            System.out.printf("Click [%d,%d]\n", chessboardPoint.getX(), chessboardPoint.getY());
            clickController.onClick(this);
        }
    }

    public ArrayList<ChessboardPoint> whereCanGo (SquareComponent[][] chessboard, Chessboard CB){
        int[][] dir = {{1,0},{-1,0},{0,1},{0,-1}};
        ArrayList<ChessboardPoint> canGo = new ArrayList<>();
        int nowX = this.chessboardPoint.getX();
        int nowY = this.chessboardPoint.getY();
        for (int i=0;i<4;i++){
            int x = nowX+dir[i][0], y = nowY+dir[i][1];
            if ((x>=0&&x<8) && (y>=0&&y<4)) {
                if (chessboard[x][y] instanceof EmptySlotComponent || (chessboard[x][y].isReversal() && chessboard[x][y].getChessColor() != CB.getCurrentColor() && (chessboard[x][y].type <= this.type || (chessboard[x][y].type == 6 && this.type == 0)))) {
                    canGo.add(new ChessboardPoint(x, y));
                }
            }
        }
        return canGo;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
    }
}
