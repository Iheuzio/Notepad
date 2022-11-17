package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.File;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.ScrollBarUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.ScrollPaneUI;
import javax.swing.plaf.basic.BasicScrollPaneUI;
import javax.swing.KeyStroke;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;
import javax.swing.Scrollable;
import javax.swing.AbstractAction;

import javax.print.*;

public class App extends JFrame implements ActionListener {
    JTextArea t;
    JFrame f;

    private String path = "";
    private String default_title = "Untitled.txt";
    private JFileChooser j;
    private FileNameExtensionFilter filter;

    App() {

        f = new JFrame("notepad - Untitled.txt");
        t = new JTextArea();

        JMenuBar mb = new JMenuBar();

        JScrollPane scroll = new JScrollPane(t);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JMenu m1 = new JMenu("File");
        JMenu m2 = new JMenu("Edit");

        JMenuItem mi1 = new JMenuItem("New");
        JMenuItem mi2 = new JMenuItem("Open");
        JMenuItem mi3 = new JMenuItem("Save");
        JMenuItem mi8 = new JMenuItem("Save as");
        JMenuItem mi9 = new JMenuItem("Print");

        JMenuItem mi4 = new JMenuItem("cut");
        JMenuItem mi5 = new JMenuItem("copy");
        JMenuItem mi6 = new JMenuItem("paste");
        JMenuItem mc = new JMenuItem("close");

        for (JMenuItem mi : new JMenuItem[] { mi1, mi2, mi3, mi8, mi9, mi4, mi5, mi6, mc }) {
            mi.addActionListener(this);
        }
        for (JMenuItem mi : new JMenuItem[] { mi1, mi2, mi3, mi8, mi9, mc }) {
            m1.add(mi);
        }
        for (JMenuItem mi : new JMenuItem[] { mi4, mi5, mi6 }) {
            m2.add(mi);
        }

        mb.add(m1);
        mb.add(m2);

        t.setLineWrap(true);
        t.setWrapStyleWord(true);

        t.getInputMap().put(KeyStroke.getKeyStroke("control S"), "save");
        t.getActionMap().put("save", new AbstractAction() {
            /**
             * @description: saves the file
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                mi3.doClick();
            }
        });

        t.getInputMap().put(KeyStroke.getKeyStroke("control O"), "open");
        t.getActionMap().put("open", new AbstractAction() {
            /**
             * @description: opens the file
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                mi2.doClick();
            }
        });

        t.getInputMap().put(KeyStroke.getKeyStroke("control N"), "new");
        t.getActionMap().put("new", new AbstractAction() {
            /**
             * @description: creates a new file
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                mi1.doClick();
            }
        });

        f.add(scroll);
        f.setJMenuBar(mb);
        f.setSize(500, 500);
        f.setLocationRelativeTo(null);
        f.setResizable(true);
        f.setVisible(true);

        f.addWindowListener(new WindowAdapter() {
            /**
             * @description: calls the kill() method if windows x button is clicked and
             *               performs the same action as close().
             */
            @Override
            public void windowClosing(WindowEvent e) {
                if (kill() == 1) {
                    f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });

        t.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                f.setTitle("notepad - " + default_title + " *");
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                f.setTitle("notepad - " + default_title + " *");

            }

            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                f.setTitle("notepad - " + default_title + " *");
            }
        });

        t.addMouseWheelListener(new MouseWheelListener() {
            /**
             * @description: scroll up and down using mouse wheel
             */
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() < 0) {
                    scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getValue() - 20);
                } else {
                    scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getValue() + 20);
                }
            }
        });

        t.addMouseWheelListener(new MouseWheelListener() {
            /**
             * @description: zoom in and out using mouse wheel
             */
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.isAltDown()) {
                    if (e.getWheelRotation() < 0) {
                        t.setFont(new Font("Arial", Font.PLAIN, t.getFont().getSize() + 1));
                    } else {
                        t.setFont(new Font("Arial", Font.PLAIN, t.getFont().getSize() - 1));
                    }
                }
            }
        });
        final String description[] = { "*.txt", "*.docx", "*.doc", "*.java", "*.py", "*.c", "*.cpp", "*.html", "*.css",
                "*.js", "*.json", "*.xml", "*.yml", "*.yaml", "*.md", "*.markdown", "*.sql" };
        final String extensions[] = { "txt", "docx", "doc", "java", "py", "c", "cpp", "html", "css", "js", "json",
                "xml", "yml", "yaml", "md", "markdown", "sql" };
        j = new JFileChooser("C:");
        for (int i = 0; i < description.length; i++) {
            filter = new FileNameExtensionFilter(description[i], extensions[i]);
            j.addChoosableFileFilter(filter);
        }
        j.setAcceptAllFileFilterUsed(false);
    }

    /**
     * @param ActionEvent Action Event listner for menu items
     * @description Listens to the menu items and performs an action based on the
     *              menu item selected
     */
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        switch (s) {
            case "New":
                newFile();
                break;
            case "Open":
                open();
                break;
            case "Save":
                saveToPath();
                break;
            case "Save as":
                save();
                break;
            case "Print":
                try {
                    if (path == "") {
                        JOptionPane.showMessageDialog(f, "Please save or open a file first");
                        return;
                    }
                    boolean printed = t.print();
                    if (printed) {
                        JOptionPane.showMessageDialog(f, "Printing completed");
                    } else {
                        JOptionPane.showMessageDialog(f, "Printing cancelled");
                    }
                }  catch (Exception evt) {
                        JOptionPane.showMessageDialog(f, "File name cannot be the same" + evt.getStackTrace());
                    break; 
                } 
                break;
            case "cut":
                t.cut();
                break;
            case "copy":
                t.copy();
                break;
            case "paste":
                t.paste();
                break;
            case "close":
                kill();
                break;
        }

    }

    public void newFile() {
        // check if file contains a * which means it has been edited
        if (f.getTitle().contains("*")) {
            int a = JOptionPane.showConfirmDialog(f, "Do you want to save changes to " + default_title + "?");
            if (a == JOptionPane.YES_OPTION) {
                saveToPath();
                t.setText("");
                default_title = "Untitled.txt";
                f.setTitle("notepad - " + default_title);
                path = "";
                return;

            }
            if (a == JOptionPane.NO_OPTION) {
                path = "";
                t.setText("");
                default_title = "Untitled.txt";
                f.setTitle("notepad - " + default_title);
                return;
            } else {
                return;
            }
        // if not saved, means file was just opened
        } else {
            t.setText("");
            default_title = "Untitled.txt";
            f.setTitle("notepad - " + default_title);
            path = "";
        }

    }
    /**
     * @description saves a file relative to the path or calls save() if path is not defined 
     */
    public void saveToPath() {
        if (path == "") {
            save();
            return;
        }
        try {
            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);
            t.write(bw);
            bw.close();
            f.setTitle("notepad - " + default_title);
        } catch (Exception evt) {
            JOptionPane.showMessageDialog(f, "Saving error" + evt.getMessage());
        }
    }

    /**
     * @description Saves the file to specified path and returns the path (acts as a
     *              saveas function basically)
     * @return String path of the file
     */
    public void save() {
        int r = j.showSaveDialog(null);

        if (r == JFileChooser.APPROVE_OPTION) {
            File fi = new File(j.getSelectedFile().getAbsolutePath());
            try {
                FileWriter wr = new FileWriter(fi, false);
                BufferedWriter w = new BufferedWriter(wr);

                w.write(t.getText());
                w.flush();
                w.close();
                default_title = fi.getName();
                f.setTitle("notepad" + " - " + default_title);
                path = fi.getAbsolutePath();
            } catch (Exception evt) {
                JOptionPane.showMessageDialog(f, "Saving error" + evt.getMessage());
            }
        } else
            JOptionPane.showMessageDialog(f, "Cancelled saving");
    }

    /**
     * @description Opens a file from the specified path and returns the path
     * @return String path of the file
     */
    private String open() {
        int r = j.showOpenDialog(null);
        File fi = null;
        if (r == JFileChooser.APPROVE_OPTION) {
            fi = new File(j.getSelectedFile().getAbsolutePath());
            try {
                String s1 = "", sl = "";
                FileReader fr = new FileReader(fi);
                BufferedReader br = new BufferedReader(fr);
                sl = br.readLine();
                while ((s1 = br.readLine()) != null) {
                    sl = sl + "\n" + s1;
                }
                t.setText(sl);
                default_title = fi.getName();
                f.setTitle("notepad" + " - " + default_title);
                path = fi.getAbsolutePath();
            } catch (Exception evt) {
                JOptionPane.showMessageDialog(f, evt.getMessage());
            }
        }
        if (fi != null) {
            return fi.getAbsolutePath();
        }
        return "";
    }

    /**
     * @description asks the user if wants to save the file before closing
     *              application
     * @return int 1 if the user wants to continue editing (cancel)
     */
    private int kill() {
        if (!f.getTitle().contains("*")) {
            System.exit(0);
        }
        int a = JOptionPane.showConfirmDialog(f, "Do you want to save the file before exiting?");
        if (a == JOptionPane.YES_OPTION) {
            save();
            System.exit(0);
        }
        if (a == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
        return 1;
    }

    public static void main(String args[]) {
        launch();
    }

    /**
     * @description Launches the application
     */
    private static void launch() {
        new App();
    }
}