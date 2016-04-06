import javax.swing.*;
import javax.swing.text.StyledDocument;
import java.awt.*;

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
        menuBar.add(createMenu("Tab Symbols"));
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

        JTextPane textPane = new JTextPane();
        textPane.setPreferredSize(new Dimension(800, 500));
        StyledDocument doc = textPane.getStyledDocument();
        textPane.setEditable(false);

        mainPanel.add(textPane);
    }
}
