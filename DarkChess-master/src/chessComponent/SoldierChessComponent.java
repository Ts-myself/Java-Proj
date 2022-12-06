package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

import java.awt.*;

public class SoldierChessComponent extends ChessComponent {
    public int type = 0;
    public SoldierChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size, int type) {
        super(chessboardPoint, location, chessColor, clickController, size, type);
        if (this.getChessColor() == ChessColor.RED) {
            name = "兵";
        } else {
            name = "卒";
        }
    }

}
