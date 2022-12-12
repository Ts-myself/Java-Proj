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
    protected Image image;
    protected Image coverImage=Toolkit.getDefaultToolkit().getImage("resources/image-chess/advisor-red.png");
    protected ChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size, int type) {
        super(chessboardPoint, location, chessColor, clickController, size, type);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!(isReversal || currentReversal)) {
            g.drawImage(coverImage, spacingLength - 10, spacingLength - 10, getWidth() + spacingLength, getHeight() + spacingLength, this);
        }

        if (isReversal || currentReversal) {
            g.drawImage(image, spacingLength - 10, spacingLength - 10, getWidth() + spacingLength, getHeight() + spacingLength, this);

            //绘制棋子被选中时状态
            if (isSelected()) {
                g.drawImage(image, spacingLength - 10, spacingLength - 10, getWidth() + spacingLength, getHeight() + spacingLength, this);

            }
            /*else{

            }*/

        }
        if (isReachable()) {
            g.setColor(Color.BLUE);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(4f));
            g2.drawOval(spacingLength, spacingLength, getWidth() - 2 * spacingLength, getHeight() - 2 * spacingLength);
        }
    }
}
