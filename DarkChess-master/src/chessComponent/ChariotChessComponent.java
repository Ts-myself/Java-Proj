package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

import java.awt.*;

/**
 * 表示黑红车
 */
public class ChariotChessComponent extends ChessComponent {
    public int type = 3;
    public ChariotChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size, int type) {
        super(chessboardPoint, location, chessColor, clickController, size, type);
        if (this.getChessColor() == ChessColor.RED){
            image = Toolkit.getDefaultToolkit().getImage("DarkChess-master/resources/image-chess/soldier-red.png");
        } else {
            image = Toolkit.getDefaultToolkit().getImage("DarkChess-master/resources/image-chess/soldier-black.png");
        }
    }

}
