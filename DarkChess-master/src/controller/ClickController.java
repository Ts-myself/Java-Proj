package controller;


import chessComponent.SquareComponent;
import chessComponent.EmptySlotComponent;
import model.ChessColor;
import model.ChessboardPoint;
import view.ChessGameFrame;
import view.Chessboard;

import java.util.ArrayList;

public class ClickController {
    private final Chessboard chessboard;
    public SquareComponent first;
    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void onClick(SquareComponent squareComponent) {
        //判断第一次点击
        if (first == null) {
            // 初始化棋手方
            if (chessboard.getCurrentColor() == ChessColor.NONE) {
                chessboard.setCurrentColor(squareComponent.getChessColor());
                squareComponent.setReversal(true);
                ChessGameFrame.getStatusLabel().setText(String.format("%s's Turn",squareComponent.getChessColor()));
                squareComponent.repaint();
            } else if (handleFirst(squareComponent)) {
                squareComponent.setSelected(true);
                first = squareComponent;

                //get canGo
                ArrayList<ChessboardPoint> canGo = first.whereCanGo(chessboard.getChessComponents(), chessboard);
                System.out.print("this point can go to:");
                for (ChessboardPoint go : canGo) {System.out.printf("(%d,%d) ", go.getX() + 1, go.getY() + 1);}
                System.out.print("\n");
                //paint
                for (ChessboardPoint point : canGo) {
                    chessboard.getSquareComponents()[point.getX()][point.getY()].setReachable(true);
                    chessboard.getSquareComponents()[point.getX()][point.getY()].repaint();
                }

                first.repaint();
            }
        }
        else {
            ArrayList<ChessboardPoint> canGo = first.whereCanGo(chessboard.getChessComponents(), chessboard);
            System.out.print("this point can go to:");
            for (ChessboardPoint go : canGo) {System.out.printf("(%d,%d) ", go.getX() + 1, go.getY() + 1);}
            System.out.print("\n");
            //paint
            for (ChessboardPoint point : canGo) {
                chessboard.getSquareComponents()[point.getX()][point.getY()].setReachable(false);
                chessboard.getSquareComponents()[point.getX()][point.getY()].repaint();
            }

            if (handleSecond(squareComponent)) {
                //repaint in swap chess method.
                chessboard.swapChessComponents(first, squareComponent);
                chessboard.clickController.swapPlayer();
                ScoreChange(squareComponent);

                first.setSelected(false);
                first = null;
            } else {
                first.setSelected(false);
                SquareComponent recordFirst = first;
                first = null;
                recordFirst.repaint();
            }
            //get canGo
        }
    }


    /**
     * @param squareComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */

    private boolean handleFirst(SquareComponent squareComponent) {
        if (!squareComponent.isReversal()) {
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

    private boolean handleSecond(SquareComponent squareComponent) {

        //没翻开或空棋子，进入if
        if (!squareComponent.isReversal() && first.type!=1) {
            //没翻开且非空棋子不能走
            if (!(squareComponent instanceof EmptySlotComponent)) {
                return false;
            }
        }

        return (squareComponent.getChessColor() != chessboard.getCurrentColor() || first.type==1) &&
                first.canMoveTo(chessboard.getChessComponents(), squareComponent.getChessboardPoint(), chessboard);
    }

    public void swapPlayer() {
        chessboard.setCurrentColor(chessboard.getCurrentColor() == ChessColor.BLACK ? ChessColor.RED : ChessColor.BLACK);
        ChessGameFrame.getStatusLabel().setText(String.format("%s's TURN", chessboard.getCurrentColor().getName()));
    }

    public void ScoreChange(SquareComponent first) {
        chessboard.ScoreRecorder(first);
        if (first.getChessColor() == ChessColor.BLACK) {
            ChessGameFrame.getRedScore().setText(String.format("Red's Score: %d", chessboard.getRedScore()));
        } else {
            ChessGameFrame.getBlackScore().setText(String.format("Black's Score: %d", chessboard.getBlackScore()));
        }
    }
}
