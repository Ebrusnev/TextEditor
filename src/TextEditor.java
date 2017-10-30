import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Scanner;


public class TextEditor extends JFrame implements ActionListener {

    private JTextArea jTextArea = new JTextArea();
    private JMenu menu;
    private JMenuItem menuItem;
    private JFileChooser fileChooser;
    private Object[] choices = {"Yes", "No", "Cancel"};

    private int currentAction = 1;

    public static void main(String[] args) {

        new TextEditor();

    }

    public TextEditor() {

        createMenuBar();
        createButtonBar();

        jTextArea.setFont(new Font("Times New Roman", Font.BOLD, 14));
        jTextArea.setDragEnabled(true);
        this.add(jTextArea);

        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setPreferredSize(new Dimension(250, 250));
        this.add(jScrollPane);

        this.setSize(800, 600);
        this.setTitle("TextEditor");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    private void createMenuBar() {

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(createEditMenu());
        menuBar.add(createHelpMenu());

        this.setJMenuBar(menuBar);

    }

    private void createButtonBar() {

        JPanel buttonPanel = new JPanel();

        Box box = Box.createHorizontalBox();

        JButton textHighlighting = createAButton("./src/img/highlighter.png", 1);
        JButton textColorChange = createAButton("./src/img/textColor.png", 2);
        JButton clearAll = createAButton("./src/img/clearText.png", 3);

        box.add(textHighlighting);
        box.add(textColorChange);
        box.add(clearAll);

        buttonPanel.add(box);
        this.add(buttonPanel, BorderLayout.NORTH);

    }

    private JMenu createFileMenu() {

        menu = new JMenu("File");
        menu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        menu.setMnemonic(KeyEvent.VK_F);

        //ADDING OPEN MENU ITEM
        menu.add(createAMenuItem("Open", "./src/img/open.png", KeyEvent.VK_O,
                ActionEvent.CTRL_MASK));

        //ADDING CREATE MENU ITEM
        menu.add(createAMenuItem("Create", "./src/img/create.png", KeyEvent.VK_N,
                ActionEvent.CTRL_MASK));

        //ADDING SAVE MENU ITEM
        menu.add(createAMenuItem("Save", "./src/img/save.png", KeyEvent.VK_S,
                ActionEvent.CTRL_MASK));

        //ADDING SAVE AS MENU ITEM
        menu.add(createAMenuItem("Save As...", "./src/img/saveAs.png", KeyEvent.VK_S,
                ActionEvent.CTRL_MASK + ActionEvent.SHIFT_MASK));
        menu.addSeparator();

        //ADDING EXIT MENU ITEM
        menu.add(createAMenuItem("Exit", "./src/img/exit.png", KeyEvent.VK_Q,
                ActionEvent.CTRL_MASK));

        return menu;

    }

    private JMenu createEditMenu() {

        menu = new JMenu("Edit");
        menu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        menu.setMnemonic(KeyEvent.VK_E);

        //ADDING UNDO MENU ITEM
        menu.add(createAMenuItem("Undo", "./src/img/undo.png", KeyEvent.VK_Z,
                ActionEvent.CTRL_MASK));

        //ADDING REDO MENU ITEM
        menu.add(createAMenuItem("Redo", "./src/img/redo.png", KeyEvent.VK_Z,
                ActionEvent.CTRL_MASK + ActionEvent.SHIFT_MASK));

        menu.addSeparator();

        //CUT MENU ITEM
        menuItem = new JMenuItem(new DefaultEditorKit.CutAction());
        menuItem.setText("Cut");
        menuItem.setIcon(new ImageIcon("./src/img/cut.png"));
        menuItem.setMnemonic(KeyEvent.VK_X);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_MASK));
        menu.add(menuItem);

        //COPY MENU ITEM
        menuItem = new JMenuItem(new DefaultEditorKit.CopyAction());
        menuItem.setText("Copy");
        menuItem.setIcon(new ImageIcon("./src/img/copy.png"));
        menuItem.setMnemonic(KeyEvent.VK_C);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK));
        menu.add(menuItem);

        //PASTE MENU ITEM
        menuItem = new JMenuItem(new DefaultEditorKit.PasteAction());
        menuItem.setText("Paste");
        menuItem.setIcon(new ImageIcon("./src/img/paste.png"));
        menuItem.setMnemonic(KeyEvent.VK_V);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK));
        menu.add(menuItem);

        return menu;
    }

    private JMenu createHelpMenu() {

        menu = new JMenu("Help");
        menu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        menu.setMnemonic(KeyEvent.VK_H);

        //ADDING ABOUT MENU ITEM
        menu.add(createAMenuItem("About", "./src/img/about.png", KeyEvent.VK_I,
                ActionEvent.CTRL_MASK));

        return menu;
    }

    private JButton createAButton(String iconFile, final int actionNumber) {

        JButton button = new JButton();

        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);

        Icon buttonIcon = new ImageIcon(iconFile);
        button.setIcon(buttonIcon);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentAction = actionNumber;
            }
        });

        return button;
    }

    private JMenuItem createAMenuItem(String name, String iconFile, int keyEvent, int actionEvent) {

        menuItem = new JMenuItem(name, new ImageIcon(iconFile));
        menuItem.setMnemonic(keyEvent);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(keyEvent, actionEvent));
        menuItem.addActionListener(this);

        return menuItem;
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if ("open".equalsIgnoreCase(e.getActionCommand())) {

            fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                this.jTextArea.setText(null);

                try {
                    Scanner scanner = new Scanner(new FileReader(fileChooser.getSelectedFile().getPath()));

                    while (scanner.hasNext()) {
                        this.jTextArea.append(scanner.nextLine() + "\n");
                    }
                } catch (FileNotFoundException e1) {
                    System.out.println(e1.getMessage());
                }
            }
        } else if ("create".equalsIgnoreCase(e.getActionCommand())) {

            int button = JOptionPane.showOptionDialog(this,
                    "Do you want to save changes?", "Text Editor",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                    null, choices, choices[0]);
            if (button == 0){
                fileChooser = new JFileChooser();

                if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){

                    try {
                        BufferedWriter out = new BufferedWriter(new FileWriter(fileChooser.getSelectedFile().getPath()));

                        out.write(this.jTextArea.getText());
                        out.close();

                    } catch (IOException e1) {
                        System.out.println(e1.getMessage());
                    }
                }
            } else if (button == 1){

                jTextArea.setText(null);

            }

        } else if ("save".equalsIgnoreCase(e.getActionCommand())) {

        } else if ("Save As...".equalsIgnoreCase(e.getActionCommand())) {

            fileChooser = new JFileChooser();

            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){

                try {
                    BufferedWriter out = new BufferedWriter(new FileWriter(fileChooser.getSelectedFile().getPath()));

                    out.write(this.jTextArea.getText());
                    out.close();

                } catch (IOException e1) {
                    System.out.println(e1.getMessage());
                }

            }

        } else if ("exit".equalsIgnoreCase(e.getActionCommand())) {

            int button = JOptionPane.showOptionDialog(this,
                    "Do you want to save changes?", "Text Editor",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                    null, choices, choices[0]);
            if (button == 0) {

                fileChooser = new JFileChooser();

                if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){

                    try {
                        BufferedWriter out = new BufferedWriter(new FileWriter(fileChooser.getSelectedFile().getPath()));

                        out.write(this.jTextArea.getText());
                        out.close();

                    } catch (IOException e1) {
                        System.out.println(e1.getMessage());
                    }
                }

            } else if (button == 1) { System.exit(0); }

        } else if ("undo".equalsIgnoreCase(e.getActionCommand())) {

        } else if ("redo".equalsIgnoreCase(e.getActionCommand())) {

        } else if ("about".equalsIgnoreCase(e.getActionCommand())) {

            JOptionPane.showMessageDialog(this,
                    "TextEditor\n\nVersion 1.0\n\n" +
                            "(c) Copyright Evgenii Brusnev 2017\nAll rights not reserved\n\n" +
                            "Visit /\nSome Web Page",
                    "About Text Editor",
                    JOptionPane.INFORMATION_MESSAGE
            );

        }

    }

}


