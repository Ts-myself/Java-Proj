import view.ChessGameFrame;

import javax.swing.*;


public class Main {
    /* todo list
        1.游戏界面设计 （开始与结束UI）
        2.被吃棋子摆在一旁
        3.ai模式
        4.音效与剩余界面背景
        5.悔棋栈的load and save （读入是现实进程
        6.测试用例准备
        8.包装与联网
        9.读档报错类型
        10.图像的预载入 ！！
        fix
        1.按按钮后 cheat 没能 focus
        2.restart后部分功能失效 如作弊
        3.点击作弊按钮以开关作弊mode
        4.作弊：偷看任意一个棋子？？？
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessGameFrame mainFrame = new ChessGameFrame(800, 930);
            mainFrame.setVisible(true);
        });
    }
}
