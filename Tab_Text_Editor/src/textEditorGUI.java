
import javax.swing.*;
import javax.swing.border.EtchedBorder;


class textEditorGUI{
    public textEditorGUI() {
        //  set up the JFrame for the gui
        JFrame frm = new JFrame();
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setSize(700,760);
        frm.setTitle("Tablature Text Editor");
        
        //  set up a menu bar components for user to access
        //  different functionalities
        JMenuBar menubar = new JMenuBar();
        JMenu menu1 = new JMenu(" File ");
        JMenu menu2 = new JMenu(" Edit ");
        menubar.add(menu1);
        menubar.add(menu2);
        
        //  set up submenu components
        JMenuItem jmItem1 = new JMenuItem("Open...");
        JMenuItem jmItem2 = new JMenuItem("Print");
        JMenuItem jmItem3 = new JMenuItem("Close");
        menu1.add(jmItem1);
        menu1.add(jmItem2);
        menu1.add(jmItem3);
        
        //  set up the gui under the menu as a text field
        JTextArea jtext = new JTextArea();
        jtext.setBorder(
                BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(8,8,8,8), 
                        BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
        jtext.setLineWrap(true);
        jtext.setWrapStyleWord(true);
        jtext.setEditable(true);
        frm.add(jtext);
        
        //  make the gui visible
        frm.setJMenuBar(menubar);
        frm.setVisible(true);
        
    }
    
    public static void main(String[] args) {
        new textEditorGUI();
    }
}