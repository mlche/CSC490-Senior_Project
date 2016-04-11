import javax.swing.*;

/**
 * Created by ken12_000 on 4/11/2016.
 */
public class TabModifierGUI {
    //Window title
    private final String TITLE = "Tab Modification";

    //Dialog
    JDialog dialog;

    //JPanels
    private JPanel mainPanel;

    //Text area
    JTextArea textArea;

    public TabModifierGUI(JFrame frame, JTextArea text){
        //Get text area
        textArea = text;

        //Build the panel and add it to the frame
        buildPanel();

        dialog = new JDialog(new JFrame(), TITLE);

        dialog.setDefaultCloseOperation(dialog.DISPOSE_ON_CLOSE);

        dialog.setResizable(false);

        //Add panel to dialog
        dialog.add(mainPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    /**
     * Build the main panel to go in the frame
     */
    private void buildPanel(){

    }
}
