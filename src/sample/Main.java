package sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        TransparentEditorsManager transparentEditorsManager = new TransparentEditorsManager();
        TransparentTextEditor textEditor = new TransparentTextEditor(stage, transparentEditorsManager);
        textEditor.createHelpWindow(textEditor);

        transparentEditorsManager.getTextEditors().add(textEditor);
    }

    public static void main(String[] args) {
        launch(args);
    }

}

