import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ken12_000 on 4/11/2016.
 */
public class TabModifierGUI {
    //Window title
    private final String TITLE = "Tab Modification";

    //Dialog
    private JDialog dialog;

    //JPanels
    private JPanel mainPanel;

    //Text area
    private JTextArea textArea;

    //Combo Boxes
    private JComboBox currentTuningBox;
    private JComboBox newTuningBox;

    //Labels
    private final String CURRENT_TUNING = "Current Tuning:";
    private final String NEW_TUNING = "New Tuning:";

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
        //Create main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 100, 20, 100));

        //Panel for settings
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));

        //Create combo box labels
        JLabel curTuningLabel = new JLabel(CURRENT_TUNING);
        JLabel newTuningLabel = new JLabel(NEW_TUNING);

        curTuningLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        newTuningLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        //Create array for combo boxes
        String[] tunings = new String[]{"E Standard", "E Flat", "Drop D"};

        //Create the combo boxes
        currentTuningBox = new JComboBox(tunings);
        newTuningBox = new JComboBox(tunings);

        currentTuningBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        newTuningBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        //Create modify button in new layout
        JPanel buttonPanel = new JPanel();

        JButton modifyButton = new JButton("Modify");
        JButton cancelButton = new JButton("Cancel");

        modifyButton.addActionListener(new ModifyButtonListener());
        cancelButton.addActionListener(new CancelButtonListener());

        buttonPanel.add(modifyButton);
        buttonPanel.add(Box.createHorizontalStrut(40));
        buttonPanel.add(cancelButton);

        //Add components to settings panel
        settingsPanel.add(curTuningLabel);
        settingsPanel.add(currentTuningBox);
        settingsPanel.add(Box.createRigidArea(new Dimension(0, 22)));
        settingsPanel.add(newTuningLabel);
        settingsPanel.add(newTuningBox);

        settingsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Add panels to main panel
        mainPanel.add(settingsPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 22)));
        mainPanel.add(buttonPanel);
    }

    /**
     * Private class for Insert button listener
     */
    private class ModifyButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Modify Tablature

            //Close dialog
            dialog.dispose();
        }
    }

    /**
     * Private class for Cancel button listener
     */
    private class CancelButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            //Close the dialog screen
            dialog.dispose();
        }
    }
}
