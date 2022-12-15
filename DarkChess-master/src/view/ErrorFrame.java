package view;

import javax.swing.*;
import java.awt.*;

public class ErrorFrame extends JFrame {
    public ErrorFrame(String num){
        setTitle("Error");
        setLayout(null);
        setLocationRelativeTo(null);
        setSize(300,200);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        Label label = new Label();
        switch (num) {
            case "1" -> label.setText("101：wrong suffix!");
            case "2" -> label.setText("102: wrong chessboard");
            case "3" -> label.setText("103: wrong component");//todo:
            case "4" -> label.setText("104: missing color");
            case "5" -> label.setText("105: wrong move");//todo:
            default -> {
            }
        }

        label.setSize(400,60);
        label.setFont(new Font("华文行楷", Font.BOLD, 15));
        label.setLocation(30,11);
        add(label);

        this.setVisible(true);

        try  { Thread.sleep(3000); }
        catch (InterruptedException e) { throw new RuntimeException(e);  }
        dispose();
    }
}
