package sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class UtilityFunctions {
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
