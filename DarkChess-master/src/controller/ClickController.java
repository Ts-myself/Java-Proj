package controller;


import chessComponent.SquareComponent;
import chessComponent.EmptySlotComponent;
import model.ChessColor;
import model.ChessboardPoint;
import view.Chessboard;

import java.util.ArrayList;

import static view.ChessGameFrame.*;

public class ClickController {
    public Chessboard chessboard;
    public SquareComponent first;

    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void onClick(SquareComponent squareComponent) {

        if (first == null) {
            // 第一次翻棋
            if (Chessboard.getCurrentColor() == ChessColor.NONE) {
                chessboard.setCurrentColor(squareComponent.getChessColor() == ChessColor.RED ? ChessColor.BLACK : ChessColor.RED);
                squareComponent.setReversal(true);
                changeStatusLabel(Chessboard.getCurrentColor(),0,0);
                squareComponent.repaint();
                chessboard.regretStack.add(new RegretNode(squareComponent.getChessboardPoint().getX(),squareComponent.getChessboardPoint().getY()));
            }
            // 翻棋 或 选棋
            else if (handleFirst(squareComponent)) {
                squareComponent.setSelected(true);
                first = squareComponent;
                chessboard.paintReachable(first.whereCanGo(chessboard.getSquareComponents(), chessboard),true);
                first.repaint();
            }
        }
        else {
            ArrayList<ChessboardPoint> canGo = first.whereCanGo(chessboard.getSquareComponents(), chessboard);
            chessboard.paintReachable(canGo,false);
            // 移动或吃子
            if (handleSecond(squareComponent, canGo)) {

                chessboard.regretStack.add(new RegretNode(first.toString(),squareComponent.toString()));

                chessboard.ScoreRecorder(squareComponent,true);
                chessboard.swapChessComponents(first, squareComponent);
                changeEatenNumber(squareComponent.type,squareComponent.getChessColor(),true);
                chessboard.clickController.swapPlayer();
                first.setSelected(false);
                first = null;
            }
            // 非法操作
            else {
                first.setSelected(false);
                SquareComponent recordFirst = first;
                first = null;
                recordFirst.repaint();
            }
        }


    }


    /**
     * @param squareComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */

    private boolean handleFirst(SquareComponent squareComponent) {
        if (!squareComponent.isReversal() && !(squareComponent instanceof EmptySlotComponent)) {
            chessboard.regretStack.add(new RegretNode(squareComponent.getChessboardPoint().getX(),squareComponent.getChessboardPoint().getY()));
            squareComponent.setReversal(true);
            System.out.printf("onClick to reverse a chess [%d,%d]\n", squareComponent.getChessboardPoint().getX(), squareComponent.getChessboardPoint().getY());
            squareComponent.repaint();
            chessboard.clickController.swapPlayer();
            return false;
        }
        return squareComponent.getChessColor() == Chessboard.getCurrentColor();
    }

    /**
     * @param squareComponent first棋子目标移动到的棋子second
     * @return first棋子是否能够移动到second棋子位置
     */

    private boolean handleSecond(SquareComponent squareComponent, ArrayList<ChessboardPoint> canGo) {
        //非空且未翻且不是炮不能走
        if (!squareComponent.isReversal() && first.type != 1 && !(squareComponent instanceof EmptySlotComponent)) return false;
        //在canGo里能走
        for (ChessboardPoint point : canGo) {
            if (point.getX() == squareComponent.getChessboardPoint().getX() && point.getY() == squareComponent.getChessboardPoint().getY()) {
                return (squareComponent.getChessColor() != Chessboard.getCurrentColor() || first.type == 1);
            }
        }
        return false;
    }

    public void swapPlayer() {
        chessboard.setCurrentColor(Chessboard.getCurrentColor() == ChessColor.BLACK ? ChessColor.RED : ChessColor.BLACK);
        changeStatusLabel(Chessboard.getCurrentColor(),chessboard.getBlackScore(),chessboard.getRedScore());
    }


}
