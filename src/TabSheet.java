import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by ken12_000 on 4/11/2016.
 */
public class TabSheet extends JScrollPane {
    private JTextArea textArea;
    private Boolean overwriteMode;
    private TabCaret caret;
    private textEditorGUI GUI;

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

        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_INSERT){
                    toggleOverwriteMode();
                }
                else if(e.getKeyCode() == KeyEvent.VK_MINUS){
                    textArea.insert("-----", textArea.getCaretPosition());
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
}
