package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;
import view.Chessboard;

import java.awt.*;
import java.util.ArrayList;

public class CannonChessComponent extends ChessComponent {
    public int type = 1;
    public CannonChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size, int type){
        super(chessboardPoint, location, chessColor, clickController, size, type);
        if (this.getChessColor() == ChessColor.RED){
            name = "P";
        } else {
            name = "p";
        }
    }
    public boolean canMoveTo (SquareComponent[][] chessboard, ChessboardPoint destination, Chessboard CB) {
        int nowX = super.getChessboardPoint().getX();
        int nowY = super.getChessboardPoint().getY();
        ArrayList<ChessboardPoint> canGo = new ArrayList<>();
        int[][] dir = {{1,0},{-1,0},{0,1},{0,-1}};
        for (int i=0;i<4;i++){
            int x = nowX+dir[i][0], y = nowY+dir[i][1];
            boolean jump = false;
            while ((x>=0&&x<8) && (y>=0&&y<4)){
                if (jump && chessboard[x][y].getChessColor() == CB.getCurrentColor()&&chessboard[x][y].isReversal) break;
                if (jump && !(chessboard[x][y] instanceof EmptySlotComponent)) {
                    ChessboardPoint jumpToHere = new ChessboardPoint(x, y);
                    canGo.add(jumpToHere);
                    break;
                }
                if (! (chessboard[x][y] instanceof EmptySlotComponent)){
                    jump = true;
                }
                x+=dir[i][0]; y+=dir[i][1];
            }
        }
        for (ChessboardPoint i : canGo){
            if (i.getX() == destination.getX() && i.getY() == destination.getY()) return true;
        }
        return false;
    }
    public ArrayList<ChessboardPoint> whereCanGo (SquareComponent[][] chessboard, Chessboard CB){
        int nowX = super.getChessboardPoint().getX();
        int nowY = super.getChessboardPoint().getY();
        ArrayList<ChessboardPoint> canGo = new ArrayList<>();
        int[][] dir = {{1,0},{-1,0},{0,1},{0,-1}};
        for (int i=0;i<4;i++){
            int x = nowX+dir[i][0], y = nowY+dir[i][1];
            boolean jump = false;
            while ((x>=0&&x<8) && (y>=0&&y<4)){
                if (jump && chessboard[x][y].getChessColor() == CB.getCurrentColor() && chessboard[x][y].isReversal) break;
                if (jump && !(chessboard[x][y] instanceof EmptySlotComponent)) {
                    ChessboardPoint jumpToHere = new ChessboardPoint(x, y);
                    canGo.add(jumpToHere);
                    break;
                }
                if (! (chessboard[x][y] instanceof EmptySlotComponent)){
                    jump = true;
                }
                x+=dir[i][0]; y+=dir[i][1];
            }
        }
        return canGo;
    }
}
