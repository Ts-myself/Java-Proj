package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;

public class MenuFrame extends JFrame {
    GameController gameController;
    Chessboard chessboard;
    public MenuFrame(GameController gameController,Chessboard chessboard){
        this.gameController = gameController;
        this.chessboard = chessboard;

        setTitle("Menu");
        setSize(310, 350);
        setLocationRelativeTo(null); // Center the window.
        setLayout(null);

        addContinueButton();
        addRegretButton();
        addCheatButton();
        addSavaButton();
        addLoadButton();
        addRestartButton();
        addQuitButton();
    }


    private void addContinueButton(){
        JButton button = new JButton("Continue");
        button.setLocation(20, 20);
        button.setSize(260, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.GRAY);
        add(button);

        button.addActionListener(e -> this.dispose());
    }
    private void addRegretButton() {
        JButton button = new JButton("Regret");
        button.setLocation(20, 60);
        button.setSize(260, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.GRAY);
        add(button);

        button.addActionListener(e -> {
            chessboard.regret();
        });
    }
    private void addCheatButton() {
        JButton button = new JButton("Cheat");
        button.setLocation(20, 100);
        button.setSize(260, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.GRAY);
        add(button);
    }

    private void addSavaButton(){
        JButton button = new JButton("Save");
        button.setLocation(20, 140);
        button.setSize(260, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.GRAY);
        add(button);

        button.addActionListener(e -> {
            System.out.println("Saving game");
            SaveAndLoadFrame saveAndLoadFrame = new SaveAndLoadFrame(gameController,true);
            saveAndLoadFrame.setVisible(true);
        });
    }
    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(20, 180);
        button.setSize(260, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.GRAY);
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            SaveAndLoadFrame saveAndLoadFrame = new SaveAndLoadFrame(gameController,false);
            saveAndLoadFrame.setVisible(true);
        });
    }
    private void addRestartButton(){
        JButton button = new JButton("Restart");
        button.setLocation(20, 220);
        button.setSize(260, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.GRAY);
        add(button);

        button.addActionListener(e -> {
            System.out.println("Restarting Game!");
            gameController.restartGame();
            dispose();
        });
    }

    private void addQuitButton() {
        JButton button = new JButton("Quit");
        button.setLocation(20, 260);
        button.setSize(260, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.GRAY);
        add(button);

        button.addActionListener(e -> {
            dispose();
        });
    }


}
