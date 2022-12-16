package view;

import javax.swing.*;
import java.awt.*;

public class ErrorFrame extends JFrame {
    public ErrorFrame(int num){
        setTitle("Error");
        setLayout(null);
        setLocationRelativeTo(null);
        setSize(300,200);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        Label label = new Label();
        switch (num){
            case 1: label.setText("你tm不能第一步就悔棋啊兄弟");
            case 2: label.setText("此为空存档");

        }

        label.setSize(400,60);
        label.setFont(new Font("华文行楷", Font.BOLD, 15));
        label.setLocation(30,11);
        add(label);


        this.setVisible(true);
        try  { Thread.sleep(1000); }
        catch (InterruptedException e) { throw new RuntimeException(e);  }
        dispose();
    }
}
