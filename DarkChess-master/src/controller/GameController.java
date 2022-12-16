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
            //101：后缀名错误
            if (!path.substring(path.lastIndexOf(".")).equals(".txt")) {new ErrorFrame("1"); return;}

            List<String> chessData = Files.readAllLines(Path.of(path));
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
