package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

import java.awt.*;

public class SoldierChessComponent extends ChessComponent {
    public int type = 0;
    public SoldierChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size, int type) {
        super(chessboardPoint, location, chessColor, clickController, size, type);
        if (this.getChessColor() == ChessColor.RED){
            image = Toolkit.getDefaultToolkit().getImage("resources/image-chess/soldier-red.png");
            canMoveImage = Toolkit.getDefaultToolkit().getImage("resources/image-chess/soldier-red-canMove.png");
        } else {
            image = Toolkit.getDefaultToolkit().getImage("resources/image-chess/soldier-black.png");
            canMoveImage = Toolkit.getDefaultToolkit().getImage("resources/image-chess/soldier-black-canMove.png");
        }
    }

}
