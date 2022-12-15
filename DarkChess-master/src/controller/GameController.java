package controller;


import view.Chessboard;
import view.ErrorFrame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GameController {
    public Chessboard chessboard;

    public GameController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void loadGameFromFile(String path) {
        try {
            List<String> chessData = Files.readAllLines(Path.of(path));
            if (chessData.isEmpty()){
                new ErrorFrame(2);
                return;
            }
            chessboard.initAllChessOnBoard (chessData);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public  void saveGameInFile(String path) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            List<String> lines = chessboard.pauseToInt();
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            writer.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void restartGame(){
        chessboard.initAllChessOnBoard(null);
    }


}
