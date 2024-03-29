package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;
import view.Chessboard;

import java.awt.*;

/**
 * 表示棋盘上非空棋子的格子，是所有非空棋子的父类
 */
public class ChessComponent extends SquareComponent{
    //炮：0 兵：1 马：2 车：3 象：4 士：5 将：6
    protected int type;
    protected Image image;
    protected Image coverImage=Toolkit.getDefaultToolkit().getImage("resources/image-chess/coverImage.png");
    protected ChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size, int type) {
        super(chessboardPoint, location, chessColor, clickController, size, type);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!(isReversal || currentReversal)) {
            g.drawImage(coverImage, spacingLength - 70, spacingLength - 38, getWidth() + 15*spacingLength, getHeight() + 5*spacingLength, this);
            if (isReachable()) {
                if (Chessboard.getCurrentColor() == ChessColor.RED) {
                    g.drawImage(redCanMoveImage, spacingLength - 70, spacingLength - 38, getWidth() + 15 * spacingLength, getHeight() + 5 * spacingLength, this);
                } else {
                    g.drawImage(blackCanMoveImage, spacingLength - 70, spacingLength - 38, getWidth() + 15 * spacingLength, getHeight() + 5 * spacingLength, this);
                }
            }
          }

        if (isReversal || currentReversal) {
            if(!isReachable())
                g.drawImage(image, spacingLength -70, spacingLength - 38, getWidth() + 15*spacingLength, getHeight() + 5*spacingLength, this);
            else
                g.drawImage(canMoveImage, spacingLength - 70, spacingLength - 38, getWidth() + 15*spacingLength, getHeight() + 5*spacingLength, this);
        }

    }
}
