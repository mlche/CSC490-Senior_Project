
import javafx.geometry.Bounds;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;


class textEditorGUI{

    //JFrame to hold all components
    JFrame frm;

    //Menu items
    private JMenuItem itemOpen;
    private JMenuItem itemPrint;
    private JMenuItem itemClose;
    private JMenuItem itemInsertTab;
    private JMenuItem itemHelp;
    private JMenuItem itemAbout;
    private JMenuItem itemModify;

    JTextArea jText;

    public textEditorGUI() {
        //Create menu bar for the JFrame
        JMenuBar menuBar = createMenuBar();

        //Create the editable text frame (JScrollPane)
        JScrollPane scrollPane = createTextArea();

        //  set up the JFrame for the gui
        frm = new JFrame();
        frm.setTitle("Tablature Text Editor");

        //Do nothing on close so that windows event can give a warning/save document before closing
        frm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        //Add the close listener to the frame that will listen for window closing
        frm.addWindowListener(new CloseListener());

        //Add components to the frame
        frm.setJMenuBar(menuBar);

        //Add the panel to the frame
        Container contentPane = frm.getContentPane();
        contentPane.setLayout(new FlowLayout());
        contentPane.add(scrollPane);
        
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
        itemOpen = new JMenuItem("Open...");
        itemPrint = new JMenuItem("Print");
        itemClose = new JMenuItem("Close");

        //Set menu item sizes
        Dimension dimension = new Dimension(100, itemOpen.getPreferredSize().height);
        itemOpen.setPreferredSize(dimension);

        //Add action listeners to menu items
        itemOpen.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                TabFileChooser tfc = new TabFileChooser();
            }

        });
        itemPrint.addActionListener(itemListener);
        itemClose.addActionListener(itemListener);

        menu1.add(itemOpen);
        menu1.add(itemPrint);
        menu1.add(itemClose);

        //Set up submenu components for Edit menu
        itemModify = new JMenuItem("Modify Tuning");
        itemModify.setPreferredSize(dimension);
        itemModify.addActionListener(itemListener);
        menu2.add(itemModify);

        //Set up submenu components for Insert menu
        itemInsertTab = new JMenuItem("Insert Tab");
        itemInsertTab.setPreferredSize(dimension);
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
     * Create the editable text frame to go in the JFrame
     */
    private JScrollPane createTextArea(){
        //  set up the gui under the menu as a text field
        jText = new JTextArea();

        jText.setLineWrap(true);
        jText.setWrapStyleWord(true);
        jText.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        jText.setEditable(true);
        jText.setMargin(new Insets(60, 60, 60, 60));

        JScrollPane scrollPane = new JScrollPane(jText);

        scrollPane.setPreferredSize(new Dimension(900, 1000));

        return scrollPane;
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
            }
            else if(item == itemPrint){
                //Do print operation
            }
            else if(item == itemClose){
                //Close the program
                frm.dispatchEvent(new WindowEvent(frm, WindowEvent.WINDOW_CLOSING));
            }
            else if(item == itemModify){
                //Start modify menu GUI
                new TabModifierGUI(frm, jText);
            }
            else if(item == itemInsertTab){
                //Start InsertMenuGUI
                new InsertMenuGUI(frm, jText);
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

            //Prompt user to save document with "Yes" "No" or "Cancel"
            int n = JOptionPane.showConfirmDialog(frame,
                    "Would you like to save before exiting?",
                    "Exiting...",
                    JOptionPane.YES_NO_CANCEL_OPTION);

            //Get user's answer from prompt
            if(n == JOptionPane.YES_OPTION){
                //Save their document
                close = true;
            }
            else if(n == JOptionPane.NO_OPTION){
                close = true;
            }
            else {
                //"Cancel" option was selected.  Do not close application.
                close = false;
            }

            //Close application if close == true
            if(close){
                System.exit(0);
            }
        }
    }
}