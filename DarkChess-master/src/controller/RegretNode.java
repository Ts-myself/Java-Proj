package controller;


public class RegretNode {
    public int which; //1:移动 or 吃子  2：翻棋
    public String chessComponent;
    public String eatenComponent;
    public int x , y;
    public RegretNode (String chessComponent, String eatenComponent){
        this.which = 1; //移动 or 吃子
        this.chessComponent = chessComponent;
        this.eatenComponent = eatenComponent;
    }
    public RegretNode (int x, int y){
        this.which = 2; //翻棋
        this.x = x;
        this.y = y;
    }
}
