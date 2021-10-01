package sample;

public class EditorState {
    private boolean ctrlPressed;
    private boolean altPressed;
    private boolean escPressed;
    private boolean onTextBox;
    private boolean alwaysOnTop;

    public EditorState(boolean alwaysOnTop) {
        this.alwaysOnTop = alwaysOnTop;
    }

    public boolean getCtrlPressed() { return ctrlPressed; }

    public void setCtrlPressed(boolean ctrlPressed) { this.ctrlPressed = ctrlPressed; }

    public boolean getAltPressed() { return altPressed; }

    public void setAltPressed(boolean altPressed) { this.altPressed = altPressed; }

    public boolean getEscPressed() { return escPressed; }

    public void setEscPressed(boolean escPressed) { this.escPressed = escPressed; }

    public boolean getOnTextBox() { return onTextBox; }

    public void setOnTextBox(boolean onTextBox) { this.onTextBox = onTextBox; }

    public boolean getAlwaysOnTop() { return alwaysOnTop; }

    public void setAlwaysOnTop(boolean alwaysOnTop) { this.alwaysOnTop = alwaysOnTop; }

}
