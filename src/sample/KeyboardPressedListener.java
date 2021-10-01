package sample;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyboardPressedListener implements EventHandler<KeyEvent> {
    private TransparentTextEditor _editor;

    public KeyboardPressedListener(TransparentTextEditor editor){
        _editor = editor;
    }

    public void handle(KeyEvent event){
        // handle keyboard events
        double xLoc = _editor.getStage().getX();
        double yLoc = _editor.getStage().getY();

        int moveSpeed = 20;
        int expansionSpeed = 20;

        // switch between editing and command mode
        if (event.getCode() == KeyCode.ESCAPE) {
            if(!alternateEditingMode())
                return;
        }

        if (event.getCode() == KeyCode.ALT) {
            boolean alt_pressed = this._editor.getEditorState().getAltPressed();
            this._editor.getEditorState().setAltPressed(!alt_pressed);
        }

        // cycle through the various tabs
        if (event.getCode() == KeyCode.CONTROL) {
            cyclingTabs();
        }

        // after escape is pressed, the document becomes uneditable and special commands can be run
        boolean escPressed = this._editor.getEditorState().getEscPressed();
        if (!escPressed){
            commandMode(event, moveSpeed, expansionSpeed, xLoc, yLoc);
        }
    }

    private void commandMode(KeyEvent event, int moveSpeed, int expansionSpeed, double xLoc, double yLoc){
        // exiting the document
        if (event.getCode() == KeyCode.Q) {
            _editor.getTextEditors().remove(_editor);
            _editor.getStage().close();
        }

        // move the editor using J,K,L, and semicolon
        moveEditor(event, moveSpeed, xLoc, yLoc);

        // resize the editor using the arrow keys
        resizeEditor(event, expansionSpeed, xLoc, yLoc);

        // make textbox appear and disappear
        if(event.getCode() == KeyCode.SLASH) {
            makeTextBoxVisible();
        }

        // enter the text box command
        boolean onTextBox = this._editor.getEditorState().getOnTextBox();
        if (onTextBox) {
            if (event.getCode() == KeyCode.ENTER) {
                runTextBoxCommand();
            }
            // deletes all contents of the textbox once command is run
            _editor.getTextBox().textProperty().addListener((obs, oldText, newText) -> {
                if(newText.length() == 0 || newText.equals(CommandParser.INVALID_COMMAND_MESSAGE) || newText.equals("?")){
                    _editor.getTextBox().setText("");
                }
            });
        }

        // saving a file
        if (event.getCode() == KeyCode.S)
            _editor.getFileManagement().saveAs();

        // printing the contents of the text area onto the console
        if (event.getCode() == KeyCode.P)
            System.out.println(_editor.getTextArea().getText());

        // loading a file
        if (event.getCode() == KeyCode.O)
            _editor.getFileManagement().openFile();

        // setting gui as focused
        if (event.getCode() == KeyCode.F){
            boolean alwaysOnTop = this._editor.getEditorState().getAlwaysOnTop();
            this._editor.getEditorState().setAlwaysOnTop(!alwaysOnTop);
            _editor.getStage().setAlwaysOnTop(!alwaysOnTop);
        }

        // add a new transparent text editor
        if (event.getCode() == KeyCode.N){
            _editor.createNewEditor();
        }

        // create a new help window
        if (event.getCode() == KeyCode.H){
            _editor.createHelpWindow(null);
        }
    }

    private boolean alternateEditingMode(){
        // alternate between editing and quick command mode
        boolean onTextBox = this._editor.getEditorState().getOnTextBox();
        if(onTextBox){
            _editor.getTextArea().requestFocus();
            _editor.getCommandParser().setOpacity(_editor.getTextBox(), 0.0);
            this._editor.getEditorState().setOnTextBox(false);
            _editor.getTextBox().clear();
            return false;
        }
        boolean esc_pressed = this._editor.getEditorState().getEscPressed();
        this._editor.getEditorState().setEscPressed(!esc_pressed);
        _editor.getTextArea().setEditable(!esc_pressed);
        return true;
    }

    private void makeTextBoxVisible(){
        // makes the textBox appear and disappear
        boolean onTextBox = this._editor.getEditorState().getOnTextBox();
        this._editor.getEditorState().setOnTextBox(!onTextBox);
        _editor.getTextBox().clear();
        _editor.getTextBox().setEditable(_editor.getTextBoxOpacity() == 0);
        if(_editor.getTextBoxOpacity() == 0.0) {
            _editor.getTextBox().requestFocus();
            _editor.getCommandParser().setOpacity(_editor.getTextBox(), TransparentTextEditor.TEXTFIELD_OPACITY);
        }
        else {
            _editor.getTextArea().requestFocus();
            _editor.getCommandParser().setOpacity(_editor.getTextBox(), 0.0);
        }
    }

    private void runTextBoxCommand(){
        // entering textBox input
        this._editor.getEditorState().setOnTextBox(false);
        _editor.getCommandParser().runTextBoxInput(_editor.getTextBox().getText());
        _editor.getTextArea().requestFocus();
        _editor.getTextBox().clear();
        _editor.getCommandParser().setOpacity(_editor.getTextBox(), 0.0);
    }

    private void cyclingTabs(){
        // tabs through the various editors
        boolean ctrlPressed = this._editor.getEditorState().getCtrlPressed();
        this._editor.getEditorState().setCtrlPressed(!ctrlPressed);
        int newFocusedStageIndex = (_editor.getFocusedStage().get() + 1) % _editor.getTextEditors().size();
        _editor.getTextEditors().get(_editor.getFocusedStage().get()).getCommandParser().setOpacity(_editor.getTextArea(), _editor.getTextAreaOpacity() - 0.1);
        _editor.setFocusedStage(newFocusedStageIndex);
        TransparentTextEditor editor = _editor.getTextEditors().get(newFocusedStageIndex);
        editor.getCommandParser().setOpacity(editor.getTextArea(), editor.getTextAreaOpacity() + 0.1);
        editor.getStage().requestFocus();
    }

    private void moveEditor(KeyEvent event, int moveSpeed, double xLoc, double yLoc){
        // move the location of the editor using J,K,L, and semicolon
        if (event.getCode() == KeyCode.J){
            if(xLoc - moveSpeed > 0) {
                _editor.getStage().setX(xLoc - moveSpeed);
            }
            else {
                _editor.getStage().setX(0);
            }
        }
        if (event.getCode() == KeyCode.K){
            if(yLoc + moveSpeed + _editor.getStage().getHeight() < _editor.getScreenSize().getHeight()) {
                _editor.getStage().setY(yLoc + moveSpeed);
            }
            else {
                _editor.getStage().setY(_editor.getScreenSize().getHeight() - _editor.getStage().getHeight());
            }
        }
        if (event.getCode() == KeyCode.L){
            if(yLoc - moveSpeed > 0) {
                _editor.getStage().setY(yLoc - moveSpeed);
            }
            else {
                _editor.getStage().setY(0);
            }
        }
        if (event.getCode() == KeyCode.SEMICOLON){
            if(xLoc + moveSpeed + _editor.getStage().getWidth() < _editor.getScreenSize().getWidth()) {
                _editor.getStage().setX(xLoc + moveSpeed);
            }
            else {
                _editor.getStage().setX(_editor.getScreenSize().getWidth() - _editor.getStage().getWidth());
            }
        }
    }

    private void resizeEditor(KeyEvent event, int expansionSpeed, double xLoc, double yLoc){
        // resize the transparent editor using arrow keys
        if (event.getCode() == KeyCode.RIGHT){
            if(expansionSpeed + _editor.getStage().getWidth() + xLoc < _editor.getScreenSize().getWidth()) {
                _editor.getStage().setWidth(expansionSpeed + _editor.getStage().getWidth());
            }
            else {
                _editor.getStage().setWidth(_editor.getScreenSize().getWidth() - xLoc);
            }
        }
        if (event.getCode() == KeyCode.DOWN){
            if(expansionSpeed + _editor.getStage().getHeight() + yLoc < _editor.getScreenSize().getHeight()) {
                _editor.getStage().setHeight(expansionSpeed + _editor.getStage().getHeight());
            }
            else {
                _editor.getStage().setHeight(_editor.getScreenSize().getHeight() - yLoc);
            }
        }
        if (event.getCode() == KeyCode.UP){
            if(-expansionSpeed + _editor.getStage().getHeight() > _editor.getHeight()) {
                _editor.getStage().setHeight(-expansionSpeed + _editor.getStage().getHeight());
            }
            else {
                _editor.getStage().setHeight(_editor.getHeight());
            }
        }
        if (event.getCode() == KeyCode.LEFT){
            if(-expansionSpeed + _editor.getStage().getWidth() > _editor.getWidth()) {
                _editor.getStage().setWidth(-expansionSpeed + _editor.getStage().getWidth());
            }
            else {
                _editor.getStage().setWidth(_editor.getWidth());
            }
        }
    }
}


