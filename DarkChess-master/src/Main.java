import view.ChessGameFrame;

import javax.swing.*;


public class Main {
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
