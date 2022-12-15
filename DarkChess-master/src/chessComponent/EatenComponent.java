package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EatenComponent extends SquareComponent{
    int number;
    Image image;
    BufferedImage[][] images=new BufferedImage[2][7];

    public EatenComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size, int type) {
        super(chessboardPoint, location, chessColor, clickController, size, type);
        if (chessColor == ChessColor.RED) {
            switch (type) {
                case 0 -> image = Toolkit.getDefaultToolkit().getImage("resources/image-chess/soldier-red.png");
                case 1 -> image = Toolkit.getDefaultToolkit().getImage("resources/image-chess/cannon-red.png");
                case 2 -> image = Toolkit.getDefaultToolkit().getImage("resources/image-chess/horse-red.png");
                case 3 -> image = Toolkit.getDefaultToolkit().getImage("resources/image-chess/chariot-red.png");
                case 4 -> image = Toolkit.getDefaultToolkit().getImage("resources/image-chess/minister-red.png");
                case 5 -> image = Toolkit.getDefaultToolkit().getImage("resources/image-chess/advisor-red.png");
                case 6 -> image = Toolkit.getDefaultToolkit().getImage("resources/image-chess/general-red.png");
            }
        } else {
            switch (type) {
                case 0 -> image = Toolkit.getDefaultToolkit().getImage("resources/image-chess/soldier-black.png");
                case 1 -> image = Toolkit.getDefaultToolkit().getImage("resources/image-chess/cannon-black.png");
                case 2 -> image = Toolkit.getDefaultToolkit().getImage("resources/image-chess/horse-black.png");
                case 3 -> image = Toolkit.getDefaultToolkit().getImage("resources/image-chess/chariot-black.png");
                case 4 -> image = Toolkit.getDefaultToolkit().getImage("resources/image-chess/minister-black.png");
                case 5 -> image = Toolkit.getDefaultToolkit().getImage("resources/image-chess/advisor-black.png");
                case 6 -> image = Toolkit.getDefaultToolkit().getImage("resources/image-chess/general-black.png");
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, -30, 0,150,90, this);
    }
}
