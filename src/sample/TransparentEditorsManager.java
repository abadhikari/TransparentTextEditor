package sample;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class TransparentEditorsManager {
    private ArrayList<TransparentTextEditor> _textEditors;
    private AtomicInteger _focusedStage;

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
}
