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

        //  execute if user approves opening a file
        if(val == JFileChooser.APPROVE_OPTION){
            File inFile = jfc.getSelectedFile();

            //  auxillary function completeing checks before opening new file
            alteredText();

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

            curr_proj = inFile;

            tabSheet.setText(s);

        }
    }

    public void saveFile(String s){
        int val = 1;

        //  if the file was not saved, create dialog to save file
        if(curr_proj == null){
            val = jfc.showSaveDialog(jfc);
            curr_proj = jfc.getSelectedFile();
        }

        //  handle writing the project contents to file.
        if(val == JFileChooser.APPROVE_OPTION || curr_proj != null){
            try (BufferedWriter writer = Files.newBufferedWriter(curr_proj.toPath(), Charset.forName("US-ASCII"))) {
                writer.write(s, 0, s.length());
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
            }
        }
    }

    public int alteredText(){
        String temp = "";
        int i = 0;

        //  if project exists in a location on disk
        if(curr_proj != null) {
            //  read in original file for comparison to text area
            try (BufferedReader reader = Files.newBufferedReader(curr_proj.toPath(), Charset.forName("US-ASCII"))) {
                String line = null;
                while ((line = reader.readLine()) != null) {
                    temp += line+"\n";
                }
            } catch (IOException x) {
                temp = "";
            }
        }

        //  if changes were made, ask user if they want to save
        if(!temp.equalsIgnoreCase(tabSheet.getString())){
            i = JOptionPane.showOptionDialog(null, "Do you want to save your current work?","Save",
                    YES_NO_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE,null,null,null);
            if(i == 0) {
                //  get text from text area and write to file
                saveFile(tabSheet.getString());
            }
        }

        return i;
    }
}
