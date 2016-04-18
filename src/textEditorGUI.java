import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;


class textEditorGUI{

    //JFrame to hold all components
    JFrame frm;

    //Menu items
    private JMenuItem itemOpen,itemSave;
    private JMenuItem itemPrint;
    private JMenuItem itemClose;
    private JMenuItem itemNew;
    private JMenuItem itemInsertTab;
    private JMenuItem itemHelp;
    private JMenuItem itemAbout;
    private JMenuItem itemModify;
    private JCheckBoxMenuItem itemInsertMode;
    private JCheckBoxMenuItem itemOverwriteMode;
    private JCheckBoxMenuItem itemAutoIncreaseOn;
    private JCheckBoxMenuItem itemAutoIncreaseOff;

    private TabSheetUnlimited tabSheetUnlimted;
    private TabSheetLimited tabSheetLimited;
    private TabSheet tabSheet;

    private TabFileChooser tfc;

    //Type of tab sheet to open up
    private final String UNLIMITED = "unlimited";
    private final String LIMITED = "limited";

    //Mode of cursor
    private final String INSERT = "Cursor mode:  Insert";
    private final String OVERWRITE = "Cursor mode:  Overwrite";

    //MOde of auto-increase
    private final String ON = "Auto-increase:  ON";
    private final String OFF = "Auto-increase:  OFF";


    //Panel for bottom bar
    private JPanel statusPanel;

    //Status bar label
    private JLabel statusCursor;
    private JLabel statusAutoIncrease;

    public textEditorGUI(String type) {
        //Create menu bar for the JFrame
        JMenuBar menuBar = createMenuBar();

        //  set up the JFrame for the gui
        frm = new JFrame();
        frm.setTitle("Tablature Text Editor");

        //Do nothing on close so that windows event can give a warning/save document before closing
        frm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        //Add the close listener to the frame that will listen for window closing
        frm.addWindowListener(new CloseListener());

        //Add components to the frame
        frm.setJMenuBar(menuBar);

        //Create status bar located at bottom of frame
        statusPanel = createStatusPanel();

        //Create and add a limited width tab sheet
        if(type.equals(LIMITED)){
            tabSheet = new TabSheetLimited(this);
            //Add the panel to the frame
            Container contentPane = frm.getContentPane();
            contentPane.setLayout(new FlowLayout());

            contentPane.add(tabSheet);
        }
        else{  //Create and add an unlimited width tab sheet
            tabSheet = new TabSheetUnlimited(this);
            Container contentPane = frm.getContentPane();
            contentPane.setLayout(new BorderLayout());

            contentPane.add(tabSheet);
            contentPane.add(statusPanel, BorderLayout.SOUTH);
        }

        tfc = new TabFileChooser(tabSheet);

        //Size will go to when minimized
        frm.setPreferredSize(new Dimension(900, 700));

        //  make the gui visible and full screen
        frm.pack();
        frm.setExtendedState(Frame.MAXIMIZED_BOTH);
        frm.setVisible(true);
    }

    /**
     * Create the menu bar.
     */
    private JMenuBar createMenuBar(){
        //  set up a menu bar components for user to access
        //  different functionalities
        JMenuBar menubar = new JMenuBar();
        JMenu menu1 = new JMenu(" File ");
        JMenu menu2 = new JMenu(" Edit ");
        JMenu menu3 = new JMenu("Insert");
        JMenu menu4 = new JMenu("Help");
        menubar.add(menu1);
        menubar.add(menu2);
        menubar.add(menu2);
        menubar.add(menu3);
        menubar.add(menu4);

        //ActionListener for menu items
        ActionListener itemListener = new MenuItemListener();

        //  set up submenu components for File menu
        itemNew = new JMenuItem("New");
        itemOpen = new JMenuItem("Open...");
        itemSave = new JMenuItem("Save");
        itemClose = new JMenuItem("Close");

        //Set menu item sizes
        Dimension dimension = new Dimension(100, itemOpen.getPreferredSize().height);
        itemOpen.setPreferredSize(dimension);

        //Add action listeners to menu items
        itemOpen.addActionListener(itemListener);
        itemSave.addActionListener(itemListener);
        itemNew.addActionListener(itemListener);
        itemClose.addActionListener(itemListener);

        menu1.add(itemNew);
        menu1.add(itemOpen);
        menu1.add(itemSave);
        menu1.add(itemClose);

        //Set up submenu components for Edit menu
        itemModify = new JMenuItem("Modify Tuning");
        itemModify.addActionListener(itemListener);

        JMenu menuCursorMode = new JMenu("Cursor Mode");
        itemInsertMode = new JCheckBoxMenuItem("Insert");
        itemOverwriteMode = new JCheckBoxMenuItem("Overwrite");
        menuCursorMode.add(itemInsertMode);
        menuCursorMode.add(itemOverwriteMode);
        itemInsertMode.addActionListener(itemListener);
        itemOverwriteMode.addActionListener(itemListener);

        itemInsertMode.setState(true);
        itemOverwriteMode.setState(false);

        JMenu menuAutoIncrease = new JMenu("Auto-Increase");
        itemAutoIncreaseOn = new JCheckBoxMenuItem("On");
        itemAutoIncreaseOff = new JCheckBoxMenuItem("Off");
        itemAutoIncreaseOn.addActionListener(itemListener);
        itemAutoIncreaseOff.addActionListener(itemListener);
        menuAutoIncrease.add(itemAutoIncreaseOff);
        menuAutoIncrease.add(itemAutoIncreaseOn);

        itemAutoIncreaseOff.setState(false);
        itemAutoIncreaseOn.setState(true);

        menu2.add(itemModify);
        menu2.add(menuCursorMode);
        menu2.add(menuAutoIncrease);

        //Set up submenu components for Insert menu
        itemInsertTab = new JMenuItem("Insert Tab");
        //itemInsertTab.setPreferredSize(dimension);
        itemInsertTab.addActionListener(itemListener);
        menu3.add(itemInsertTab);

        //Set up submenu components for Help Menu
        itemHelp = new JMenuItem("Help");
        itemAbout = new JMenuItem("About");
        itemHelp.setPreferredSize(dimension);
        itemHelp.addActionListener(itemListener);
        itemAbout.addActionListener(itemListener);
        menu4.add(itemHelp);
        menu4.add(itemAbout);

        return menubar;
    }

    /**
     * Create a status bar to be located at the bottom of the frame.
     * JLabel statusLabel will be the status displayed in the bar.
     * Status bar is a JPanel object with the JLabel inside.
     */
    private JPanel createStatusPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 3));

        Border raisedbevel = BorderFactory.createRaisedBevelBorder();
        Border loweredbevel = BorderFactory.createLoweredBevelBorder();

        //Create label for panel
        statusCursor = new JLabel(INSERT);
        statusCursor.setAlignmentX(Component.LEFT_ALIGNMENT);
        //statusCursor.setPreferredSize(new Dimension(300, 0));

        statusAutoIncrease = new JLabel(ON);
        statusAutoIncrease.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(statusCursor);
        panel.add(statusAutoIncrease);
        panel.add(new JLabel());

        return panel;
    }

    /**
     * Updates the status bar cursor mode.
     */
    public void updateCursorModeStatus(){
        if(tabSheet.getOverwriteMode()){
            statusCursor.setText(OVERWRITE);
            itemInsertMode.setState(false);
            itemOverwriteMode.setState(true);
        }
        else {
            statusCursor.setText(INSERT);
            itemInsertMode.setState(true);
            itemOverwriteMode.setState(false);
        }
    }

    /**
     * Updates the status bar auto-increase mode
     */
    public void updateAutoIncreaseStatus(){
        if(tabSheet.getAutoIncrease()){
            statusAutoIncrease.setText(ON);
            itemAutoIncreaseOff.setState(false);
            itemAutoIncreaseOn.setState(true);
        }
        else{
            statusAutoIncrease.setText(OFF);
            itemAutoIncreaseOff.setState(true);
            itemAutoIncreaseOn.setState(false);
        }
    }

    /**
     * Private class for menu item listener.
     * When menu item is clicked, corresponding action will be triggered.
     */
    private class MenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JMenuItem item = (JMenuItem)e.getSource();

            //Determine the menu item selected
            if(item == itemOpen){
                //Open JFileChooser
                tfc.openFile();
            }
            else if(item == itemSave){
                //  Save file
                tfc.saveFile(tabSheet.getString());
            }
            else if(item == itemNew){
                //Create new file
                tfc.createNewFile();
            }
            else if(item == itemClose){
                //Close the program
                frm.dispatchEvent(new WindowEvent(frm, WindowEvent.WINDOW_CLOSING));
            }
            else if(item == itemModify){
                //Start modify menu GUI
                new TabModifierGUI(frm, tabSheet);
            }
            else if(item == itemInsertMode){
                tabSheet.setOverwriteMode(false);
                updateCursorModeStatus();
            }
            else if(item == itemOverwriteMode){
                tabSheet.setOverwriteMode(true);
                updateCursorModeStatus();
            }
            else if(item == itemAutoIncreaseOff){
                tabSheet.setAutoIncrease(false);
                updateAutoIncreaseStatus();
            }
            else if(item == itemAutoIncreaseOn){
                tabSheet.setAutoIncrease(true);
                updateAutoIncreaseStatus();
            }
            else if(item == itemInsertTab){
                //Start InsertMenuGUI
                new InsertMenuGUI(frm, tabSheet);
            }else if(item == itemHelp){
                //Create and display help option GUI
                new HelpGUI();
            }else if(item == itemAbout){
                //Create about project GUI
            }
        }
    }

    /**
     * Private class to listen for close operation on application.
     * This will issue a warning to the user before exiting.
     */
    private class CloseListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            JFrame frame = (JFrame)e.getSource();
            boolean close;  //Will be set to true unless user selects "Cancel"

            //  handle saving if necessary(text area is unique from file)
            int n = tfc.alteredText();

            if(n == JOptionPane.CANCEL_OPTION){
                close = false;
            }
            else{
                close = true;
            }

            //Close application if close == true
            if(close){
                System.exit(0);
            }
        }
    }
}