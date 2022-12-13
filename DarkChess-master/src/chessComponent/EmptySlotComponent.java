package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;
import view.Chessboard;

import java.awt.*;

/**
 * 这个类表示棋盘上的空棋子的格子
 */
public class EmptySlotComponent extends SquareComponent {
    public EmptySlotComponent(ChessboardPoint chessboardPoint, Point location, ClickController listener, int size, int type) {
        super(chessboardPoint, location, ChessColor.NONE, listener, size, 0);
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.reachable) {
            if (Chessboard.getCurrentColor() == ChessColor.RED) {
                g.drawImage(redCanMoveImage, spacingLength - 70, spacingLength - 38, getWidth() + 15 * spacingLength, getHeight() + 5 * spacingLength, this);
            } else {
                g.drawImage(blackCanMoveImage, spacingLength - 70, spacingLength - 38, getWidth() + 15 * spacingLength, getHeight() + 5 * spacingLength, this);
            }
        }
    }
}
