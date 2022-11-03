package com.inventech;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class FileOrganizerUIPannel extends JPanel implements ActionListener {

    private final JButton selectFolderButton;
    private final JButton saveFolderButton;
    private final JButton fileOrganizerButton;
    private JFileChooser chooser;
    private File fromPath;
    private File toPath;
    private final JFrame frame;
    FileOrganizerUIPannel() {
        frame = new JFrame("File Organizer");
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame.setSize(400, 400);
        frame.setLayout(new FlowLayout());

        selectFolderButton = new JButton("Browse");
        saveFolderButton = new JButton("Select Folder to Save");
        fileOrganizerButton = new JButton("Start File Organizing...");
    }

    public JFrame showPanel() {
        selectFolderButton.setBounds(20, 100, 95, 30);
        selectFolderButton.addActionListener(this);
        frame.add(selectFolderButton);

        JLabel jLabel = new JLabel("Team: Wizards");
        jLabel.setLabelFor(selectFolderButton);
        frame.add(jLabel);

        saveFolderButton.setBounds(20, 100, 95, 30);
        saveFolderButton.addActionListener(actionListener -> {
            chooser = new JFileChooser();
            chooser.showOpenDialog(null);
            chooser.setDialogTitle("select folder to save");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.showSaveDialog(null);
            chooser.setAcceptAllFileFilterUsed(false);
            toPath = new File(chooser.getSelectedFile().getAbsolutePath());
        });
        frame.add(saveFolderButton);


        fileOrganizerButton.setBounds(20, 200, 95, 30);
        fileOrganizerButton.addActionListener(actionListener -> {
            try {
                FileOrganizer.startFileProcessing(getFromPath(), getToPath());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        frame.add(fileOrganizerButton);
        return frame;
    }

    public File getFromPath() {
        return fromPath;
    }

    public File getToPath() {
        return toPath;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.showOpenDialog(this);
        chooser.setDialogTitle("Select source folder");
        chooser.showSaveDialog(null);
        chooser.setAcceptAllFileFilterUsed(false);
        fromPath = new File(chooser.getSelectedFile().getAbsolutePath());
    }
}
