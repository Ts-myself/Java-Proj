package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

import java.awt.*;

public class CannonChessComponent extends ChessComponent {
    //public int type = 0;
    public CannonChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size){
        super(chessboardPoint, location, chessColor, clickController, size);
        if (this.getChessColor() == ChessColor.RED){
            name = "P";
        } else {
            name = "p";
        }
    }
    public boolean canMoveTo (SquareComponent[][] chessboard, ChessboardPoint destination) {
        //todo: Override this;
        return true;
    }
}
