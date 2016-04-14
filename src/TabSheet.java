import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by ken12_000 on 4/11/2016.
 */
public class TabSheet extends JScrollPane {
    private JTextArea textArea;
    private Boolean overwriteMode;
    private TabCaret caret;
    private textEditorGUI GUI;
    private Boolean autoIncrease;

    public void append(String str){
        textArea.append(str);
    }

    public void setText(String s){
        textArea.setText(s);
    }

    public String getString(){
        return textArea.getText();
    }

    public int isEmpty(){
        if(textArea.getText().length() > 0){
            return 1;
        }
        return 0;
    }

    public int numRows(){
        return textArea.getLineCount();
    }

    /**
     * Set text editor GUI so that insert button can update status of GUI (menu and bar)
     */
    public void setGUI(textEditorGUI gui){
        GUI = gui;
    }

    /**
     * Set the text area for the tab sheet.
     * Assigning the area an overwritable document.
     */
    public void setTextArea(JTextArea area){
        textArea = area;
        textArea.setDocument(new OverwritableDocument(textArea));

        //Add custom caret
        caret = new TabCaret();
        caret.setBlinkRate(500);
        textArea.setCaret(caret);

        //Have overwrite mode initially off
        setOverwriteMode(false);
        setAutoIncrease(true);

        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_INSERT){
                    toggleOverwriteMode();
                }
                else if(e.getKeyCode() == KeyEvent.VK_MINUS){
                    if(autoIncrease) {
                        increaseTabLength();
                    }
                }
            }
        });
    }

    /**
     * Allow user to turn overwrite mode on/off
     */
    public void setOverwriteMode(Boolean mode){
        overwriteMode = mode;
        caret.setOverwriteMode(mode);
    }

    /**
     * ALlow user to have auto-increase mode on or off.
     * Increases all tab lines together
     */
    public void setAutoIncrease(Boolean state){
        autoIncrease = state;
    }

    /**
     * Toggle the overwrite mode
     */
    private void toggleOverwriteMode(){
        if(overwriteMode) {
            setOverwriteMode(false);
            GUI.updateCursorModeStatus();
        }
        else {
            setOverwriteMode(true);
            GUI.updateCursorModeStatus();
        }
    }

    private void toggleAutoIncrease(){
        if(autoIncrease){
            setAutoIncrease(false);
            //GUI.updateAutoIncreaseStatus();
        }
        else{
            setAutoIncrease(true);
            //GUI.updateAutoIncreaseStatus();
        }
    }

    /**
     * Private class to allow overwriting.
     */
    private class OverwritableDocument extends DefaultStyledDocument{
        private JTextArea textArea;

        public OverwritableDocument(JTextArea area){
            textArea = area;
        }

        public void insertString(int offset, String str,
                                 AttributeSet as) throws BadLocationException {
            if(overwriteMode && str.length() == 1 && offset != getLength()) {
                remove(offset, 1);
            }
            super.insertString(offset, str, as);
        }
    }

    /**
     * Get the overwrite mode
     */
    public Boolean getOverwriteMode(){
        return overwriteMode;
    }

    /**
     * Get auto-incrase mode
     */
    public Boolean getAutoIncrease() {
        return autoIncrease;
    }

    /**
     * Find all lines previous and after the line that the cursor is on.
     * Only keep lines up until blank lines "" are hit.
     * This will result in a set of lines that are in the same section of tablature that the cursor is in.
     * Now increase each of these lines by one '-' symbol.
     */
    private void increaseTabLength(){
        //Create an array with enough indexes for each line in the text area.
        //String[] lines = new String[textArea.getLineCount()];
        ArrayList<String> lines = new ArrayList<>();

        try {
            //Get the line that the offset is on
            int pos = textArea.getLineOfOffset(textArea.getCaretPosition());
            System.out.println(pos);

            //Fill an array with all lines in the text area
            int i = 0;
            for (String line : textArea.getText().split("\\n")) {
                lines.add(line);
                i++;
            }

            //Check to see if line of cursor is blank, if don't increase any tabs
            if (!lines.get(pos).equals("")) {
                //Fill an array with all lines previous to the offset line UP TO A BLANK LINE
                //Add elements to the front of the array list since they are being read backwards
                //ArrayList<String> prev = new ArrayList<String>();
                ArrayList<Integer> prev = new ArrayList<Integer>();
                for (int j = pos - 1; j >= 0; j--) {
                    if (!lines.get(j).equals("")) {
                        prev.add(0, j);
                    } else
                        break;
                }

                //Fill an array with all lines INCLUDING and after the offset line UP TO A BLANK LINE
                //ArrayList<String> next = new ArrayList<String>();
                ArrayList<Integer> next = new ArrayList<Integer>();
                for (int k = pos + 1; k < lines.size(); k++) {
                    if (!lines.get(k).equals("")) {
                        next.add(k);
                    } else
                        break;
                }

                //Combine the two lists to get the whole section of tablature
                ArrayList<Integer> section = new ArrayList<>(prev);
                section.addAll(next);

                //Add a dash to each line in section
                int end;
                for(int j = 0; j < section.size(); j++){
                    end = textArea.getLineEndOffset(section.get(j));
                    textArea.insert("-", end - 2);
                }

            }
        }catch(BadLocationException e1){
            e1.printStackTrace();
        }
    }
}
