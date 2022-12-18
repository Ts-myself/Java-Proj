package model;

import java.awt.*;

public class ChessImage {
    static Image[][] images = new Image[3][10];

    static public void init(){
        images[0][0] = Toolkit.getDefaultToolkit().getImage("resources/image-chess/general-red.png");
    }

    static public Image getImage (int i, int j){return images[i][j];}
}
