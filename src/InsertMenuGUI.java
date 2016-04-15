import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Pop-up window to allow user to select blank tablature settings.
 */
public class InsertMenuGUI {
    //Window title
    private final String TITLE = "Insert Tab";

    //Dialog
    JDialog dialog;

    //JPanels
    private JPanel mainPanel;

    //Label Strings
    private final String INSTRUMENT_LABEL = "Instrument:";
    private final String TUNING_LABEL = "Tuning:";
    private final String SECTIONS_LABEL = "Tab sections:";
    private final String INSERT_BUTTON_LABEL = "Insert";
    private final String CANCEL_BUTTON_LABEL = "Cancel";

    //Arrays for combo boxes
    private String[] instrumentStrings;
    private String[] tuningStrings;

    //Buttons
    JButton insertButton;
    JButton cancelButton;

    //Text area
    TabSheet textArea;

    //Combo boxes
    JComboBox instrumentComboBox;
    JComboBox tuningComboBox;

    //Spinner for number of sections
    JSpinner sectionsSpinner;
    SpinnerNumberModel model;

    /**
     * Constructor
     */
    public InsertMenuGUI(JFrame frame, TabSheet text){
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
     * Build the main panel to be added to the frame.
     */
    private void buildPanel(){
        //Create main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 100, 20, 100));

        //Panel for settings
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));

        //Create combo-box labels
        JLabel instrumentLabel = new JLabel(INSTRUMENT_LABEL);
        JLabel tuningLabel = new JLabel(TUNING_LABEL);
        JLabel sectionsLabel = new JLabel(SECTIONS_LABEL);

        instrumentLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        tuningLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sectionsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        //Create combo-box arrays
        instrumentStrings = new String[]{"4-String", "6-String"};
        tuningStrings = new String[]{"E-Standard", "E-Flat", "Drop-D"};

        //Create combo-boxes
        instrumentComboBox = new JComboBox(instrumentStrings);
        tuningComboBox = new JComboBox(tuningStrings);

        instrumentComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        tuningComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        //Create spinner for selecting the number of tab sections
        model = new SpinnerNumberModel(1, 1, 20, 1);
        sectionsSpinner = new JSpinner(model);
        //sectionsSpinner.setEditor(new JSpinner.DefaultEditor(sectionsSpinner));
        JSpinner.DefaultEditor editor = new JSpinner.DefaultEditor(sectionsSpinner);
        editor.getTextField().setHorizontalAlignment(JTextField.CENTER);
        sectionsSpinner.setEditor(editor);
        sectionsSpinner.setAlignmentX(Component.LEFT_ALIGNMENT);

        //Create insert button in new layout so it can be centered
        JPanel buttonPanel = new JPanel();

        insertButton = new JButton(INSERT_BUTTON_LABEL);
        cancelButton = new JButton(CANCEL_BUTTON_LABEL);

        insertButton.addActionListener(new InsertButtonListener());
        cancelButton.addActionListener(new CancelButtonListener());

        buttonPanel.add(insertButton);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(cancelButton);

        //Add components to settings panel
        settingsPanel.add(instrumentLabel);
        settingsPanel.add(instrumentComboBox);
        settingsPanel.add(Box.createRigidArea(new Dimension(0, 22)));
        settingsPanel.add(tuningLabel);
        settingsPanel.add(tuningComboBox);
        settingsPanel.add(Box.createRigidArea(new Dimension(0, 22)));
        settingsPanel.add(sectionsLabel);
        settingsPanel.add(sectionsSpinner);

        settingsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Add panels to main panel
        mainPanel.add(settingsPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 22)));
        mainPanel.add(buttonPanel);
    }

    /**
     * Private class for Insert button listener
     */
    private class InsertButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            //Insert the tablature
            BlankTabUtility tabUtility = new BlankTabUtility(textArea);

            //Get tuning from the menu
            String tuning;
            if(tuningComboBox.getSelectedIndex() == 0){
                tuning = "e_standard";
            }else if(tuningComboBox.getSelectedIndex() == 1){
                tuning = "e_flat";
            }else{
                tuning = "drop_d";
            }

            //Get strings
            int numStrings;
            if(instrumentComboBox.getSelectedIndex() == 0){
                numStrings = 4;
            }else{
                numStrings = 6;
            }

            //Get number of tab sections
            int numSections = model.getNumber().intValue();

            tabUtility.insertTab(tuning, numStrings, numSections);

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
