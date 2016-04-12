import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;

/**
 * Class to hold information about current tab opened in tabEditorGUI.
 */
public class TabSheetUnlimited extends TabSheet{

    public TabSheetUnlimited(textEditorGUI gui){
        //  set up the gui under the menu as a text field
        JTextArea jText = new JTextArea();
        setViewportView(jText);

        //jText.setBorder(
        //        BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(8,8,8,8),
         //               BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));

        //jText.setBorder(new BevelBorder(BevelBorder.LOWERED));

        jText.setLineWrap(false);
        jText.setWrapStyleWord(true);
        jText.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        jText.setEditable(true);

       // jText.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0));

        Border raisedbevel = BorderFactory.createRaisedBevelBorder();
        Border loweredbevel = BorderFactory.createLoweredBevelBorder();

        setBorder(BorderFactory.createCompoundBorder(raisedbevel, loweredbevel));

        //super.textArea = jText;
        setTextArea(jText);
        setGUI(gui);

    }
}
