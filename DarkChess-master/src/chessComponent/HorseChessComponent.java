package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

import java.awt.*;

public class HorseChessComponent extends ChessComponent {
    public int type = 6;
    public HorseChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size, int type){
        super(chessboardPoint, location, chessColor, clickController, size, type);
        if (this.getChessColor() == ChessColor.RED){
            image = Toolkit.getDefaultToolkit().getImage("DarkChess-master/resources/image-chess/horse-red.png");
        } else {
            image = Toolkit.getDefaultToolkit().getImage("DarkChess-master/resources/image-chess/horse-black.png");
        }
    }
}
