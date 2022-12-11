import view.ChessGameFrame;

import javax.swing.*;


public class Main {
    /* todo list
        2.游戏界面设计
        3.棋子贴图 ——>被吃棋子摆在一旁
        6.ai模式
        8.背景和音乐
        9.悔棋
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessGameFrame mainFrame = new ChessGameFrame(800, 850);
            mainFrame.setVisible(true);
        });
    }
}
