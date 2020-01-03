package sample;

import javafx.stage.FileChooser;
import java.io.*;

public class FileManager {
    private TransparentTextEditor _editor;
    private FileChooser _fileChooser;

    public FileManager(TransparentTextEditor editor){
        _editor = editor;
        _fileChooser = new FileChooser();
    }

    public void saveAs(){
        // open a save dialog and then save the file to disk
        File file = _fileChooser.showSaveDialog(_editor.getStage());
        if(file != null)
            _saveFile(_editor.getTextArea().getText(), file);
    }

    public void openFile(){
        // open the file system and then select the file to load to the editor
        File file = _fileChooser.showOpenDialog(_editor.getStage());
        String fileContents = "";
        if(file != null) {
            fileContents = UtilityFunctions.readFileContents(file);
            fileContents = (fileContents.equals("")) ? _editor.getTextArea().getText() : fileContents;
            _editor.getTextArea().setText(fileContents);
        }
    }

    private void _saveFile(String contents, File file){
        // write the given contents to the file
        FileWriter fileWriter;
        try{
            fileWriter = new FileWriter(file);
            fileWriter.write(contents);
            fileWriter.close();
        }catch (Exception e){
            System.out.println("Can't write file.");
        }
    }

}
