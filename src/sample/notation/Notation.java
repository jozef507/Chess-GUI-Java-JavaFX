package sample.notation;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Notation
{
    List<String> gameNotationLines;

    public Notation(String path)
    {
        try{
            gameNotationLines = Files.readAllLines(Paths.get(path), Charset.forName("ISO-8859-1"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public List<String> getGameNotationLines()
    {
        return this.gameNotationLines;
    }
}
