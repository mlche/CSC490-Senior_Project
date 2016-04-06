import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The help screen GUI.
 */
public class HelpGUI {

    //Frame to hold panels
    private JFrame frame;

    //Title for frame
    private String TITLE = "Help";

    //JPanels
    private JPanel mainPanel;

    //Text pane to display help information
    private JTextPane textPane;
    StyledDocument doc;

    //Strings for files
    private final String TAB_SYMBOLS = "tab symbols";

    /**
     * Constructor.
     * Set up the JFrame.
     */
    public HelpGUI(){
        frame = new JFrame();

        //Get frame ready
        frame.setTitle(TITLE);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create the menu bar
        JMenuBar menuBar = createMenuBar();

        //Build the panel and add it to the frame
        buildPanel();

        Container contentPane = frame.getContentPane();
        contentPane.setBackground(Color.WHITE);
        contentPane.add(menuBar, BorderLayout.LINE_START);
        contentPane.add(mainPanel);

        //Display the window
        frame.pack();
        //frame.setSize(new Dimension(800, 500));
        frame.setLocationRelativeTo(null); // Center frame on screen
        frame.setVisible(true);
    }

    public JMenuBar createMenuBar() {

        JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.PAGE_AXIS));
        menuBar.add(Box.createRigidArea(new Dimension(0, 100)));
        menuBar.add(createMenu("Reading Tabs"));
        menuBar.add(Box.createRigidArea(new Dimension(0, 100)));

        JMenu tabMenu = createMenu("Tab Symbols");
        tabMenu.addMenuListener(new TabSymbolMenuListener());
        menuBar.add(tabMenu);

        menuBar.add(Box.createRigidArea(new Dimension(0, 100)));
        menuBar.add(createMenu("Tunings"));
        menuBar.add(Box.createRigidArea(new Dimension(0, 100)));

        menuBar.setBorder(BorderFactory.createMatteBorder(0,0,0,1, Color.BLACK));

        return menuBar;
    }

    // used by createMenuBar
    public JMenu createMenu(String title) {
        JMenu m = new JMenu(title);
        m.setLayout(new FlowLayout());

        return m;
    }

    /**
     * Build the main panel to hold other panels.
     */
    private void buildPanel(){
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);

        textPane = new JTextPane();
        textPane.setPreferredSize(new Dimension(800, 500));
        doc = textPane.getStyledDocument();
        textPane.setEditable(false);

        mainPanel.add(textPane);
    }

    /**
     * Retrieve and display tab symbols in the text pane.
     */
    private void displayTabSymbols(){
        //Get tab symbol text
        Path path = getFilePath(TAB_SYMBOLS);

        //Array to hold text
        String[] textArray = new String[1];

        Charset charset = Charset.forName("US-ASCII");
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String line = "";
            int i = 0;
            while ((line = reader.readLine()) != null){
                textArray[0] += line + "\n";
                i++;
            }

            doc.insertString(doc.getLength(), textArray[0], null);

        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the file path
     */
    private Path getFilePath(String type){
        Path pth;
        //Get tab symbols file path
        if(type.equals(TAB_SYMBOLS)){
            pth = Paths.get("src", "resources", "help_text", "tab_symbols.txt");
        }else{
            pth = null;
        }

        return pth;
    }

    /**
     * Listener classes for menu options.
     */
    private class TabSymbolMenuListener implements MenuListener {
        @Override
        public void menuSelected(MenuEvent e) {
            displayTabSymbols();
        }

        @Override
        public void menuDeselected(MenuEvent e) {

        }

        @Override
        public void menuCanceled(MenuEvent e) {

        }
    }
}
