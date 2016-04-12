import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;

/**
 * Created by ken12_000 on 4/11/2016.
 */
public class TabSheet extends JScrollPane {
    JTextArea textArea;

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
     * Set the text area for the tab sheet.
     * Assigning the area an overwritable document.
     */
    public void setTextArea(JTextArea area){
        textArea = area;
        textArea.setDocument(new OverwritableDocument(textArea));
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
            if(str.length() == 1 && offset != getLength()) {
                remove(offset, 1);
            }
            super.insertString(offset, str, as);
        }
    }
}
