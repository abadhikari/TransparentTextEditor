# TransparentTextEditor
A keyboard-only plain text editor in which the main feature is the ability to adjust the transparency. 

**Currently only functions correctly on Windows**. Doesn't function correctly on mac because the application was created on a windows keyboard initially and windows and mac keyboards are different. Will add mac compatibility in the future.

## Things to do before running:
1) Download Javafx SDK from https://gluonhq.com/products/javafx/.
2) Follow the steps given at https://openjfx.io/openjfx-docs/ which outlines how to get javafx working.

## To run:
Run the Main.java file. Shift-H will open up a help window that contains all the relevent information on how to use the transparent editor. The same information can be found below as well.

## Help:
Press esc to go in and out of editing mode
 - WILL KNOW IF IN EDITING MODE IF THE CURSOR IS VISIBLE. IF NOT VISIBLE, THEN IN QUICK COMMAND MODE

**Can't use quick commands in editing mode**

## Quick Commands:
#### Once out of editing mode:
Shift-S : save the file to disk\
Shift-O: open a file\
Shift-N: open a new transparent editor\
Shift-Q: close the transparent editor\
Shift-F: place focus off of the editor\
Shift-H: this help window will popup

#### Can move the window (MOVING THE EDITOR):
Shift-J : moves the window to the left
Shift-K : moves the window downwards
Shift-L : moves the window upwards
Shift-; (semicolon) : moves the window to the right

#### Can resize the window (RESIZING THE EDITOR):
Alt-Left Arrow : will decrease the width of the editor
Alt-Up Arrow : will decrease the height of the editor
Alt-Right Arrow : will increase the width of the editor
Alt-Down Array : will increase the height of the editor

#### Can switch between editor windows (SHUFFLING BETWEEN WINDOWS):
Ctrl : will change the focus to other editor windows

## Text Commands
To enter commands:
First press Shift-/. This will open a textbox at the bottom of the screen

### Can change:
#### font size
 - any number from 1 - 100 

#### background color
 - can be blue, green, red, black, white, pink 

#### font color 
 - any color

#### opacity of the editor
- any number from 0 - 1
 - 0 is completely transparent and 1 is completely opaque

### Example of text commands (each line is a separate command):
font size = 15
background color = blue
font color = black
opacity = 0.3 
