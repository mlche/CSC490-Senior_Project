
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

    //Text area
    private JTextArea jText;

    public textEditorGUI() {
        //  set up the JFrame for the gui
        frm = new JFrame();

        //Do nothing on close so that windows event can give a warning/save document before closing
        frm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        //Add the close listener to the frame that will listen for window closing
        frm.addWindowListener(new CloseListener());

        frm.setSize(900,760);
        frm.setTitle("Tablature Text Editor");
        frm.setLocationRelativeTo(null);  //Centers the frame on the screen
        
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
        itemOpen.addActionListener(itemListener);
        itemPrint.addActionListener(itemListener);
        itemClose.addActionListener(itemListener);

        menu1.add(itemOpen);
        menu1.add(itemPrint);
        menu1.add(itemClose);

        //Set up submenu components for Edit menu

        //Set up submenu components for Insert menu
        itemInsertTab = new JMenuItem("Insert Tab");
        itemInsertTab.setPreferredSize(dimension);
        itemInsertTab.addActionListener(itemListener);
        menu3.add(itemInsertTab);

        //Set up submenu components for Help Menu

        //  set up the gui under the menu as a text field
        jText = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(jText);
        jText.setBorder(
                BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(8,8,8,8), 
                        BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
        jText.setLineWrap(true);
        jText.setWrapStyleWord(true);
        jText.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        jText.setEditable(true);
        frm.add(scrollPane);
        
        //  make the gui visible
        frm.setJMenuBar(menubar);
        frm.setVisible(true);
        
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
            else if(item == itemInsertTab){
                //Start InsertMenuGUI
                new InsertMenuGUI(frm, jText);
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