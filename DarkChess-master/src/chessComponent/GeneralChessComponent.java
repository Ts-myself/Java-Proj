package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessImage;
import model.ChessboardPoint;

import java.awt.*;

public class GeneralChessComponent extends ChessComponent {
    public int type = 6;
    public GeneralChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size, int type){
        super(chessboardPoint, location, chessColor, clickController, size, type);
        if (this.getChessColor() == ChessColor.RED){
            image = ChessImage.getImage(0,0);
            canMoveImage = Toolkit.getDefaultToolkit().getImage("resources/image-chess/general-red-canMove.png");
        } else {
            image = Toolkit.getDefaultToolkit().getImage("resources/image-chess/general-black.png");
            canMoveImage = Toolkit.getDefaultToolkit().getImage("resources/image-chess/general-black-canMove.png");
        }
    }
}
