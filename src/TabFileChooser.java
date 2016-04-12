import javax.swing.*;

import static javax.swing.JOptionPane.YES_NO_CANCEL_OPTION;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;

/**
 * JFileChooser class for file chooser window.
 */
public class TabFileChooser{

    private JFileChooser jfc;
    private TabSheet tabSheet;
    private File curr_proj;

    public TabFileChooser(TabSheet ts){
        jfc = new JFileChooser();
        tabSheet = ts;
        curr_proj = null;
    }

    public void openFile(){

        //  allow user to select a file location and set a value to check
        int val = jfc.showOpenDialog(jfc);

        if(val == JFileChooser.APPROVE_OPTION){
            File inFile = jfc.getSelectedFile();

            String temp = "";
            try (BufferedReader reader = Files.newBufferedReader(curr_proj.toPath(), Charset.forName("US-ASCII"))) {
                String line = null;
                while ((line = reader.readLine()) != null) {
                    temp += line;
                }
            } catch (IOException x) {
                temp = "";
            }

                if(!tabSheet.getString().equals(temp)){
                    if(JOptionPane.showOptionDialog(null, "Do you want to save?","Save",
                            YES_NO_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE,null,null,null) == 0) {

                        //  get text from text area and write to file
                        saveFile(tabSheet.getString());
                    }
                }

            String s = "";

            //  open file location and read it in to text area
            try (BufferedReader reader = Files.newBufferedReader(inFile.toPath(), Charset.forName("US-ASCII"))) {
                String line = null;
                while ((line = reader.readLine()) != null) {
                    s += line + "\n";
                }
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
            }

            curr_proj = jfc.getSelectedFile();

            tabSheet.setText(s);

        }
    }

    public void saveFile(String s){
        int val = 21;
        if(curr_proj == null){
            val = jfc.showSaveDialog(jfc);
            curr_proj = jfc.getSelectedFile();
        }

        if(val == JFileChooser.APPROVE_OPTION || val == 21){

            try (BufferedWriter writer = Files.newBufferedWriter(curr_proj.toPath(), Charset.forName("US-ASCII"))) {
                writer.write(s, 0, s.length());
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
            }
        }
    }
}
