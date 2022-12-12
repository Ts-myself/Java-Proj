package controller;

import chessComponent.ChessComponent;
import chessComponent.SquareComponent;
import model.ChessboardPoint;

public class RegretNode {
    public int which; //1:移动  2：吃子  3：翻棋
    public SquareComponent chessComponent = null;
    public SquareComponent eatenComponent = null;
    public RegretNode (SquareComponent chessComponent, SquareComponent eatenComponent){
        this.which = 1; //吃子
        this.chessComponent = chessComponent;
        this.eatenComponent = eatenComponent;
    }
    public RegretNode (SquareComponent chessComponent){
        this.which = 2; //翻棋
        this.chessComponent = chessComponent;
    }
}
