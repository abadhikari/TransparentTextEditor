package sample;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

import java.util.HashMap;

public class CommandParser {
    public static final String INVALID_COMMAND_MESSAGE = "Invalid Command!";

    private TransparentTextEditor _editor;
    private HashMap<String, String> _colors;

    public CommandParser(TransparentTextEditor editor){
        _editor = editor;
        _colors = new HashMap<>();
        _fill_colors();
    }

    private void _fill_colors(){
        // add colors to the colors hashmap
        _colors.put("white", "255, 255, 255");
        _colors.put("black", "0, 0, 0");
        _colors.put("pink", "255, 153, 204");
        _colors.put("blue", "102, 178, 255");
        _colors.put("green", "102, 255, 178");
        _colors.put("red", "255, 153, 153");
    }

    public void runTextBoxInput(String input){
        // parse command line input and run the appropriate command
        input = input.replaceAll(" ", "");
        if(input.split("=").length < 2){
            _editor.getTextBox().setText(INVALID_COMMAND_MESSAGE);
            return;
        }
        String cmdAttribute = input.split("=")[0].toLowerCase();
        String cmdValue = input.split("=")[1].toLowerCase();

        if(cmdAttribute.equals("textsize")
                || cmdAttribute.equals("text_size") || cmdAttribute.equals("text-size")
                || cmdAttribute.equals("fontsize") || cmdAttribute.equals("font-size")
                || cmdAttribute.equals("font_size")) {
            if(isInteger(cmdValue)){
                setTextSize(_editor.getTextArea(), Integer.parseInt(cmdValue));
            }
        }
        else if(cmdAttribute.equals("textcolor") || cmdAttribute.equals("text_color")
                || cmdAttribute.equals("text-color") || cmdAttribute.equals("fontcolor")
                || cmdAttribute.equals("font_color") || cmdAttribute.equals("font-color")) {
            setTextColor(_editor.getTextArea(), cmdValue);
        }
        else if(cmdAttribute.equals("backgroundcolor")
                || cmdAttribute.equals("background_color") || cmdAttribute.equals("background-color")) {
            setBackgroundColor(_editor.getTextArea(), cmdValue);
        }
        else if(cmdAttribute.equals("opacity")){
            if (isDouble(cmdValue)) {
                setOpacity(_editor.getTextArea(), Double.parseDouble(cmdValue));
            }
        }
        else{
            _editor.getTextBox().setText(INVALID_COMMAND_MESSAGE);
        }
    }


    public void setTextColor(TextInputControl text, String color){
        // both textField and textArea will share the same text color
        _runCSSCommand(text, color, null, null, null);
        _editor.setTextColor(color);
    }

    public void setTextSize(TextInputControl text, int size){
        // the text size of the textField and the textArea can be different
        if (size > 100) size = 100;
        if (text instanceof TextArea){
            _runCSSCommand(text,null, Integer.toString(size), null, null);
            _editor.setTextAreaTextSize(size);
            if (text instanceof TextArea) _editor.setTextAreaTextSize(size);
            if (text instanceof TextField) _editor.setTextBoxTextSize(size);
        }
    }

    public void setBackgroundColor(TextInputControl text, String color){
        // both textField and textArea will share the same background color
        if (_colors.containsKey(color)) {
            _runCSSCommand(text, null, null, color, null);
            _editor.setBackgroundColor(color);
        }
    }

    public void setOpacity(TextInputControl text, double opacity){
        // the opacity of the textField and the textArea can be different
        if (opacity >= 0 && opacity <= 1){
            _runCSSCommand(text, null, null, null, Double.toString(opacity));
            if (text instanceof TextArea) _editor.setTextAreaOpacity(opacity);
            if (text instanceof TextField) _editor.setTextBoxOpacity(opacity);
        }
    }

    private void _runCSSCommand(TextInputControl text, String textColor, String textSize, String backgroundColor, String opacity){
        // run a css command on the appropriate text object
        textColor = (textColor == null) ? _editor.getTextColor() : textColor;
        backgroundColor = (backgroundColor == null) ? _editor.getBackgroundColor() : backgroundColor;
        if(text instanceof TextArea){
            textSize = (textSize == null) ? Integer.toString(_editor.getTextAreaTextSize()) : textSize;
            opacity = (opacity == null) ? Double.toString(_editor.getTextAreaOpacity()) : opacity;
        }
        else{
            textSize = (textSize == null) ? Integer.toString(_editor.getTextBoxTextSize()) : textSize;
            opacity = (opacity == null) ? Double.toString(_editor.getTextBoxOpacity()) : opacity;
        }
        String cssCmd = _textColorCmd(textColor) + "; " + _textSizeCmd(textSize) + "; " + _backgroundColorOpacityCmd(backgroundColor, opacity);
        text.setStyle(cssCmd);
    }

    private String _textColorCmd(String color){
        // returns the css command for changing the text color
        String cmdStart = "-fx-text-fill: ";
        String cmdEnd = "";
        return cmdStart + color + cmdEnd;
    }

    private String _textSizeCmd(String textSize){
        // returns the css command for changing the text size
        String cmdStart = "-fx-font-size: ";
        String cmdEnd = "px";
        return cmdStart + textSize + cmdEnd;
    }

    private String _backgroundColorOpacityCmd(String backgroundColor, String opacity){
        // returns the css command for changing the background color and opacity
        String cmdStart = "-fx-background-color: rgba(";
        String cmdEnd = ", " + opacity + ")";
        return cmdStart + _colors.get(backgroundColor) + cmdEnd;
    }

    public static boolean isInteger(String integer){
        // check if a string is a valid integer
        try {
            Integer.parseInt(integer);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static boolean isDouble(String doub){
        // check if a string is a valid double
        try{
            Double.parseDouble(doub);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}

