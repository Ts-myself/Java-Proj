package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

import java.awt.*;

public class AdvisorChessComponent extends ChessComponent {
    public int type = 5;
    public AdvisorChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size, int type){
        super(chessboardPoint, location, chessColor, clickController, size, type);
        if (this.getChessColor() == ChessColor.RED){
            image = Toolkit.getDefaultToolkit().getImage("resources/image-chess/advisor-red.png");
            canMoveImage = Toolkit.getDefaultToolkit().getImage("resources/image-chess/advisor-red-canMove.png");
        } else {
            image = Toolkit.getDefaultToolkit().getImage("resources/image-chess/advisor-black.png");
            canMoveImage = Toolkit.getDefaultToolkit().getImage("resources/image-chess/advisor-black-canMove.png");
        }
    }
}
