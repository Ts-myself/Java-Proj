package controller;


import chessComponent.SquareComponent;
import chessComponent.EmptySlotComponent;
import model.ChessColor;
import model.ChessboardPoint;
import view.ChessGameFrame;
import view.Chessboard;

import java.util.ArrayList;

public class ClickController {
    public Chessboard chessboard;
    public SquareComponent first;

    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void onClick(SquareComponent squareComponent) {

        if (first == null) {
            // 第一次翻棋
            if (chessboard.getCurrentColor() == ChessColor.NONE) {
                chessboard.setCurrentColor(squareComponent.getChessColor());
                squareComponent.setReversal(true);
                ChessGameFrame.getStatusLabel().setText(String.format("%s's Turn", squareComponent.getChessColor()));
                squareComponent.repaint();
                chessboard.regretStack.add(new RegretNode(squareComponent));
            }
            // 翻棋 或 选棋
            else if (handleFirst(squareComponent)) {
                squareComponent.setSelected(true);
                first = squareComponent;
                chessboard.paintReachable(first.whereCanGo(chessboard.getSquareComponents(), chessboard));
                first.repaint();
            }
        }
        else {
            ArrayList<ChessboardPoint> canGo = first.whereCanGo(chessboard.getSquareComponents(), chessboard);
            chessboard.paintReachable(canGo);
            // 移动或吃子
            if (handleSecond(squareComponent, canGo)) {
                chessboard.swapChessComponents(first, squareComponent);
                chessboard.clickController.swapPlayer();
                chessboard.ScoreRecorder(squareComponent,true);
                if (squareComponent instanceof EmptySlotComponent) chessboard.regretStack.add(new RegretNode(first, squareComponent.getChessboardPoint()));
                else chessboard.regretStack.add(new RegretNode(first, squareComponent));
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
            chessboard.regretStack.add(new RegretNode(squareComponent));
            squareComponent.setReversal(true);
            System.out.printf("onClick to reverse a chess [%d,%d]\n", squareComponent.getChessboardPoint().getX(), squareComponent.getChessboardPoint().getY());
            squareComponent.repaint();
            chessboard.clickController.swapPlayer();
            return false;
        }
        return squareComponent.getChessColor() == chessboard.getCurrentColor();
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
                return (squareComponent.getChessColor() != chessboard.getCurrentColor() || first.type == 1);
            }
        }
        return false;
    }

    public void swapPlayer() {
        chessboard.setCurrentColor(chessboard.getCurrentColor() == ChessColor.BLACK ? ChessColor.RED : ChessColor.BLACK);
        ChessGameFrame.getStatusLabel().setText(String.format("%s's TURN", chessboard.getCurrentColor().getName()));
    }

}
