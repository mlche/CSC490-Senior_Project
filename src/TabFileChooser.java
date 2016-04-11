import javax.swing.*;

/**
 * JFileChooser class for file chooser window.
 */
public class TabFileChooser{

    private JFileChooser jfc;

    public TabFileChooser(){
        jfc = new JFileChooser();
    }

    public String openFile(){
        //  allow user to select a file location and set a value to check
        int val = jfc.showOpenDialog(jfc);

        if(val == JFileChooser.APPROVE_OPTION){
            return "The text retrieved from file.";
        }
        return "0";
    }

    public void saveFile(String s){
        int val = jfc.showSaveDialog(jfc);

        if(val == JFileChooser.APPROVE_OPTION){
            System.out.println(s);
        }
    }
}
