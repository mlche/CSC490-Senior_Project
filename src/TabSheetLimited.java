import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

/**
 * Created by ken12_000 on 4/11/2016.
 */
public class TabSheetLimited extends TabSheet{

    public TabSheetLimited(){
        //  set up the gui under the menu as a text field
        JTextArea jText = new JTextArea();
        setViewportView(jText);

        jText.setLineWrap(true);
        jText.setWrapStyleWord(true);
        jText.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        jText.setEditable(true);
        jText.setMargin(new Insets(60, 60, 60, 60));

        setTextArea(jText);

        setPreferredSize(new Dimension(900, 1000));
    }
}
