package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class TransparentTextEditor {
    public static final String HELP_PATH_NAME = "src/sample/transparent_text_editor_help.txt";
    public static final String BACKGROUND_COLOR = "white";
    public static final String TEXT_COLOR = "white";
    public static final double TEXTAREA_OPACITY  = 0.2;
    public static final double TEXTFIELD_OPACITY = 0.2;
    public static final int TEXTAREA_TEXT_SIZE = 20;
    public static final int TEXTBOX_TEXT_SIZE = 15;

    private Dimension _screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private double _width =300, _height =500;
    private javafx.scene.control.TextArea _textArea;
    private TextField _textBox;
    private StackPane _pane;
    private Scene _scene;
    private Stage _stage;

    private double _textAreaOpacity = TEXTAREA_OPACITY;
    private double _textBoxOpacity = 0;
    private String _backgroundColor = BACKGROUND_COLOR;
    private String _textColor = TEXT_COLOR;
    private int _textAreaTextSize = TEXTAREA_TEXT_SIZE;
    private int _textBoxTextSize = TEXTBOX_TEXT_SIZE;

    private CommandParser _commandParser;
    private FileManager _fileManager;
    private TransparentEditorsManager _transparentEditorManager;
    private ArrayList<TransparentTextEditor> _textEditors;
    private AtomicInteger _focusedStage;
    private EditorState editorState;

    public TransparentTextEditor(TransparentEditorsManager transparentEditorsManager){
        this(null, transparentEditorsManager);
    }

    public TransparentTextEditor(Stage stage, TransparentEditorsManager transparentEditorsManager){
        // creates a new Stage if the stage not given
        _stage = (stage == null) ? new Stage() : stage;
        _transparentEditorManager = transparentEditorsManager;
        _textEditors = _transparentEditorManager.getTextEditors();
        _focusedStage = _transparentEditorManager.getFocusedStage();
        _commandParser = new CommandParser(this);
        _fileManager = new FileManager(this);

        this.editorState = new EditorState(true);

        //text and textBox
        _textArea = new TextArea();
        _textArea.setWrapText(true);
        _textArea.getStyleClass().add("text-area-default");
        _textArea.setEditable(false);
        _textArea.setOnMouseClicked(e -> {
            _focusedStage.set(_textEditors.indexOf(this));
        });
        _textBox = new TextField();
        _textBox.getStyleClass().add("text-field-default");

        // pane
        _pane = new StackPane();
        _pane.setAlignment(_textBox, Pos.BOTTOM_CENTER);
        _pane.getChildren().add(_textArea);
        _pane.getChildren().add(_textBox);
        _pane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

        // scene
        _scene = new Scene(_pane, _width, _height);
        _scene.setFill(null);
        _scene.setOnKeyPressed(new KeyboardPressedListener(this));
        _scene.getStylesheets().add(getClass().getResource("stylesheets.css").toExternalForm());

        // stage
        _stage.setTitle("Transparent Text Editor");
        _stage.initStyle(StageStyle.TRANSPARENT);
        _stage.setScene(_scene);
        _stage.setAlwaysOnTop(true);
        _stage.show();
    }

    public void createHelpWindow(TransparentTextEditor edit){
        // create a new window that contains the help information
        TransparentTextEditor editor = (edit == null) ? createNewEditor() : edit;
        File file = new File(HELP_PATH_NAME);
        editor.getTextArea().setText(FileManager.readFileContents(file));

        // set the location and size of the help window
        editor.getStage().setX(_stage.getX() + 10);
        editor.getStage().setY(0);
        editor.getStage().setHeight(_screenSize.getHeight());
        editor.getStage().setWidth(_width + 200);
        editor.getCommandParser().setTextSize(editor.getTextArea(), 14);
    }

    public TransparentTextEditor createNewEditor(){
        // creates a new transparent text editor
        TransparentTextEditor newEditor = new TransparentTextEditor(_transparentEditorManager);
        newEditor.getStage().setX(_stage.getX() + 10);
        newEditor.getStage().setY(_stage.getY() + 10);
        _textEditors.add(newEditor);
        return newEditor;
    }

    public Dimension getScreenSize(){ return _screenSize; }

    public double getHeight(){ return _height; }

    public double getWidth(){ return _width; }

    public Stage getStage(){ return _stage; }

    public TextField getTextBox(){ return _textBox; }

    public TextArea getTextArea(){ return _textArea; }

    public double getTextAreaOpacity(){ return _textAreaOpacity; }

    public void setTextAreaOpacity(double opacity) { _textAreaOpacity = opacity; }

    public double getTextBoxOpacity(){ return _textBoxOpacity; }

    public void setTextBoxOpacity(double opacity){ _textBoxOpacity = opacity; }

    public String getBackgroundColor(){ return _backgroundColor; }

    public void setBackgroundColor(String color){ _backgroundColor = color; }

    public String getTextColor(){ return _textColor; }

    public void setTextColor(String rgb_string){ _textColor = rgb_string; }

    public int getTextAreaTextSize(){ return _textAreaTextSize; }

    public void setTextAreaTextSize(int text_size){ _textAreaTextSize = text_size; }

    public void setTextBoxTextSize(int text_size) { _textBoxTextSize = text_size;}

    public int getTextBoxTextSize(){ return _textBoxTextSize;}

    public AtomicInteger getFocusedStage(){ return _focusedStage; }

    public void setFocusedStage(Integer num){ _focusedStage.set(num); }

    public ArrayList<TransparentTextEditor> getTextEditors(){ return _textEditors; }

    public FileManager getFileManagement(){ return _fileManager; }

    public CommandParser getCommandParser(){ return _commandParser; }

    public EditorState getEditorState() { return this.editorState; }

}
