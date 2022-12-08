package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Date;

public class SaveAndLoadFrame extends JFrame {
    GameController gameController;
    boolean SaveOrLoad; //true:save false:load
    public SaveAndLoadFrame (GameController gameController, boolean SaveOrLoad) throws HeadlessException {
        this.SaveOrLoad = SaveOrLoad;
        this.gameController = gameController;

        setSize(510, 410);
        setLocationRelativeTo(null); // Center the window.
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        addSaveAndLoadChoice("resources/saveFiles/save1.txt",30,10,1);
        addSaveAndLoadChoice("resources/saveFiles/save2.txt",30,50,2);
        addSaveAndLoadChoice("resources/saveFiles/save3.txt",30,90,3);
        addSaveAndLoadChoice("resources/saveFiles/save4.txt",30,130,4);
        addSaveAndLoadChoice("resources/saveFiles/save5.txt",30,170,5);
        addSaveAndLoadChoice("resources/saveFiles/save6.txt",30,210,6);
        addSaveAndLoadChoice("resources/saveFiles/save7.txt",30,250,7);
        addSaveAndLoadChoice("resources/saveFiles/save8.txt",30,290,8);
        addSaveAndLoadChoice("resources/saveFiles/save9.txt",30,330,9);
    }
    public void addSaveAndLoadChoice(String path, int x, int y, int n){
        File file = new File(path);
        JButton button = new JButton("File "+n+" "+new Date(file.lastModified()));
        button.setLocation(x,y);
        button.setSize(450, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.LIGHT_GRAY);
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click File "+n);
            if (SaveOrLoad) gameController.saveGameInFile(path);
            else gameController.loadGameFromFile(path);
            this.dispose();
        });
    }

}
