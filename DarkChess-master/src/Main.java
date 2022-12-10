import view.ChessGameFrame;

import javax.swing.*;


public class Main {
    /* todo list
        2.游戏界面设计
        3.棋子贴图 ——>被吃棋子摆在一旁
        6.ai模式
        8.背景和音乐
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
            ChessGameFrame mainFrame = new ChessGameFrame(800, 850);
            mainFrame.setVisible(true);
        });
    }
}
