package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

import java.awt.*;

public class EatenComponent extends ChessComponent {
    int number = 0;

    protected EatenComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size, int type) {
        super(chessboardPoint, location, chessColor, clickController, size, type);
        if (chessColor == ChessColor.BLACK) {
            /*switch (type) {
                case 0 -> image = Toolkit.getDefaultToolkit().getImage("");
                case 1 -> image = Toolkit.getDefaultToolkit().getImage("");
                case 2 -> image = Toolkit.getDefaultToolkit().getImage("");
                case 3 -> image = Toolkit.getDefaultToolkit().getImage("");
                case 4 -> image = Toolkit.getDefaultToolkit().getImage("");
                case 5 -> image = Toolkit.getDefaultToolkit().getImage("");
                case 6 -> image = Toolkit.getDefaultToolkit().getImage("");
            }*/
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.drawImage(image, )
    }
}
