package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TransparentEditorsManager implements EditorManager {
    private ArrayList<TransparentTextEditor> _textEditors;
    private AtomicInteger _focusedStage;

    //private List<TextEditor> textEditors;
    //private int focusedStage;

    public TransparentEditorsManager(){
        _textEditors = new ArrayList<>();
        _focusedStage = new AtomicInteger(0);
    }

    public ArrayList<TransparentTextEditor> getTextEditors(){
        return _textEditors;
    }

    public AtomicInteger getFocusedStage(){
        return _focusedStage;
    }

    public void cycleEditor() {

    }

    public TextEditor getFocusedEditor() {
        //return textEditors.get(focusedStage);
        return new TextEditor();
    }

    public boolean removeEditor() {
        try {
            //textEditors.remove(focusedStage);
            return true;
        }
        catch(IndexOutOfBoundsException e) {
            System.out.println(e);
        }
        return false;
    }

    public TextEditor newEditorFactory() {
        // This method will be a factory

        // creates a new transparent text editor
        /*TransparentTextEditor newEditor = new TransparentTextEditor(_transparentEditorManager);
        newEditor.getStage().setX(_stage.getX() + 10);
        newEditor.getStage().setY(_stage.getY() + 10);
        _textEditors.add(newEditor);
        return newEditor;
         */
        return new TextEditor();
    }
}
