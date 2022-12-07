import view.ChessGameFrame;

import javax.swing.*;


public class Main {
    /* todo list
        1.save and load function ！！！
        2.游戏界面设计
        3.棋子贴图
        4.偷看棋子功能
        5.开始时翻出的第一个颜色为先手玩家的阵营 ！！！
        6.ai模式
        7.被吃棋子摆在一旁
     */
    public static void main(String[] args) {

        /*
        int[][] board = Chessboard.randomIntBoard();
        for (int i=0;i<8;i++){
            for (int j=0;j<4;j++){
                System.out.printf("%d ",board[i][j]);
            }
            System.out.printf("\n");
        }
        */

        SwingUtilities.invokeLater(() -> {
            ChessGameFrame mainFrame = new ChessGameFrame(720, 720);
            mainFrame.setVisible(true);
        });
    }
}
