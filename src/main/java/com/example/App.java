package com.example;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.*;

public class App extends JFrame implements ActionListener {
    JTextArea t;
    JFrame f;

    App() {
        f = new JFrame("notepad");
        t = new JTextArea();

        JMenuBar mb = new JMenuBar();

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

        mi1.addActionListener(this);
        mi2.addActionListener(this);
        mi3.addActionListener(this);
        mi8.addActionListener(this);
        mi9.addActionListener(this);

        mi4.addActionListener(this);
        mi5.addActionListener(this);
        mi6.addActionListener(this);

        mc.addActionListener(this);

        m1.add(mi1);
        m1.add(mi2);
        m1.add(mi3);
        m1.add(mi8);
        m1.add(mi9);

        m2.add(mi4);
        m2.add(mi5);
        m2.add(mi6);

        mb.add(m1);
        mb.add(m2);
        mb.add(mc);

        f.setJMenuBar(mb);
        f.add(t);
        f.setSize(500, 500);
        f.setLocationRelativeTo(null);
        f.setResizable(true);
        f.setVisible(true);

        f.addWindowListener(new WindowAdapter() {
            /**
             * @description: calls the kill() method if windows x button is clicked and performs the same action as close().
             */
            @Override
            public void windowClosing(WindowEvent e) {
                if (kill() == 1) {
                    f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }

    /**
     * @param ActionEvent Action Event listner for menu items
     * @description Listens to the menu items and performs an action based on the
     *              menu item selected
     */
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        JFileChooser j;
        int r;
        String path = "";

        if (s.equals("cut")) {
            t.cut();
        }

        if (s.equals("copy")) {
            t.copy();
        }
        if (s.equals("paste")) {
            t.paste();
        }
        if (s.equals("Save")) {
            if (path.equals("")) {
                path = save();
            } else {
                try {
                    System.out.println("in loop");
                    FileWriter fw = new FileWriter(path);
                    BufferedWriter bw = new BufferedWriter(fw);
                    t.write(bw);
                    bw.close();
                } catch (Exception evt) {
                    JOptionPane.showMessageDialog(f, "Saving error" + evt.getMessage());
                }
            }
        }
        if (s.equals("Save as")) {
            path = save();
        }
        if (s.equals("Print")) {
            try {
                t.print();
            } catch (Exception evt) {
                JOptionPane.showMessageDialog(f, "Printing error" + evt.getMessage());
            }
        }
        if (s.equals("New")) {
            if (t.getText().equals("")) {
                t.setText("");
            } else {
                path = save();

            }
        }
        if (s.equals("Open")) {
            j = new JFileChooser("C:");

            r = j.showOpenDialog(null);

            if (r == JFileChooser.APPROVE_OPTION) {
                j = new JFileChooser("C:");

                File fi = new File(j.getSelectedFile().getAbsolutePath());
                try {
                    String s1 = "", sl = "";
                    FileReader fr = new FileReader(fi);
                    try (
                            BufferedReader br = new BufferedReader(fr)) {
                        sl = br.readLine();
                        while ((s1 = br.readLine()) != null) {
                            sl = sl + " " + s1;
                        }
                        if (sl != null) {
                            t.setText(sl);
                        }
                    }
                    t.setText(sl);

                } catch (FileNotFoundException e1) {
                    JOptionPane.showMessageDialog(f, "File not found");
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(f, "Error in reading" + e1.getMessage());
                } finally {
                    JOptionPane.showMessageDialog(f, "File opened successfully");
                }
            }
        }
        if (s.equals("close")) {
            kill();
        }
    }

    /**
     * @description Saves the file to specified path and returns the path (acts as a saveas function basically)
     * @return String path of the file
     */
    public String save() {
        JFileChooser j = new JFileChooser("C:");

        int r = j.showSaveDialog(null);

        if (r == JFileChooser.APPROVE_OPTION) {

            File fi = new File(j.getSelectedFile().getAbsolutePath());

            try {
                FileWriter wr = new FileWriter(fi, false);
                BufferedWriter w = new BufferedWriter(wr);

                w.write(t.getText());
                w.flush();
                w.close();

                return fi.getAbsolutePath();
            } catch (Exception evt) {
                JOptionPane.showMessageDialog(f, "Saving error" + evt.getMessage());
            }
        } else
            JOptionPane.showMessageDialog(f, "Cancelled saving");
        return null;
    }

    /**
     * @description asks the user if wants to save the file before closing application
     * @return int 1 if the user wants to continue editing (cancel)
     */
    private int kill() {
        int a = JOptionPane.showConfirmDialog(f, "Do you want to save the file before exiting?");

        if (a == JOptionPane.YES_OPTION) {
            save();
            System.exit(0);
        } else if (a == JOptionPane.NO_OPTION) {
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