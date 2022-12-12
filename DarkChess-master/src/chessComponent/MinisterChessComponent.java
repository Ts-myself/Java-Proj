package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

import java.awt.*;

public class MinisterChessComponent extends ChessComponent {
    public int type = 4;
    public MinisterChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size, int type){
        super(chessboardPoint, location, chessColor, clickController, size, type);
        if (this.getChessColor() == ChessColor.RED){
            image = Toolkit.getDefaultToolkit().getImage("resources/image-chess/minister-red.png");
        } else {
            image = Toolkit.getDefaultToolkit().getImage("resources/image-chess/minister-black.png");
        }
    }
}
