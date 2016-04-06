import javax.swing.*;

/**
 * JFileChooser class for file chooser window.
 */
public class TabFileChooser extends JFileChooser{
    public TabFileChooser(){
        //JButton jb = new JButton();
        JFileChooser jfc = new JFileChooser();
        //jfc.setDialogTitle("FIle Location");
        int returnVal = jfc.showOpenDialog(jfc);
        //if(jfc.showOpenDialog(jb) == JFileChooser.APPROVE_OPTION){}
        System.out.println(jfc.getSelectedFile());
    }
    
    public void openFile(){
        
    }
}
