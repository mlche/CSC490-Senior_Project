import javax.swing.*;

/**
 * Created by ken12_000 on 4/11/2016.
 */
public class TabSheet extends JScrollPane{
    JTextArea textArea;

    public void append(String str){
        textArea.append(str);
    }

    public void setText(String s){
        textArea.setText(s);
    }
}
