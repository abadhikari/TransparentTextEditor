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
            fileContents = readFileContents(file);
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

    public static String readFileContents(File file){
        // reads the contents of the file including the newlines and carriage returns
        InputStream is;
        StringBuilder sb = new StringBuilder();
        try{
            is = new FileInputStream(file);
        }catch (Exception e){
            System.out.println("Can't read from the file.");
            return "";
        }

        int i = 0;
        try{
            i = is.read();
        }catch(IOException e){
            System.out.println("Can't read line");
        }
        while(i != -1){
            sb.append((char) i);
            try{
                i = is.read();
            }catch(IOException e){

            }
        }
        return sb.toString();
    }

}
