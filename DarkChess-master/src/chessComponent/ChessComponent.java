package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

import java.awt.*;

/**
 * 表示棋盘上非空棋子的格子，是所有非空棋子的父类
 */
public class ChessComponent extends SquareComponent{
    //炮：0 兵：1 马：2 车：3 象：4 士：5 将：6
    protected int type;
    protected String name;// 棋子名字：例如 兵，卒，士等

    protected ChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size, int type) {
        super(chessboardPoint, location, chessColor, clickController, size, type);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //绘制棋子填充色
        g.setColor(Color.ORANGE);
        g.fillOval(spacingLength, spacingLength, this.getWidth() - 2 * spacingLength, this.getHeight() - 2 * spacingLength);
       //绘制棋子边框
        g.setColor(Color.DARK_GRAY);
        g.drawOval(spacingLength, spacingLength, getWidth() - 2 * spacingLength, getHeight() - 2 * spacingLength);

        if (isReversal || currentReversal) {
            //绘制棋子被选中时状态
            if (isSelected()) {
                g.setColor(Color.RED);
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(6f));
                g2.drawOval(spacingLength, spacingLength, getWidth() - 2 * spacingLength, getHeight() - 2 * spacingLength);
                //绘制棋子文字
                g.setColor(this.getChessColor().getColor());
                g.setFont(new Font("楷体", Font.BOLD, 45));
                g.drawString(this.name, this.getWidth() / 4 - 4, this.getHeight() * 2 / 3 + 4);
            }
            else{

                g.setColor(this.getChessColor().getColor());
                g.setFont(CHESS_FONT);
                g.drawString(this.name, this.getWidth() / 4 - 1, this.getHeight() * 2 / 3);
            }

        }
        if (isReachable()) {
            g.setColor(Color.BLUE);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(4f));
            g2.drawOval(spacingLength, spacingLength, getWidth() - 2 * spacingLength, getHeight() - 2 * spacingLength);
        }
    }
}
