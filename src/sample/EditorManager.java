package sample;

public interface EditorManager {
    public void cycleEditor();
    public TextEditor getFocusedEditor();
    public boolean removeEditor();
    public TextEditor newEditorFactory();
}
