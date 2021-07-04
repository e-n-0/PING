package fr.ricains.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;

public class RightClickMenuFilesTree extends JPopupMenu {

    public RightClickMenuFilesTree(MyFile file, JTree tree)
    {
        // New file / folder
        JMenuItem newFile = new JMenuItem(new AbstractAction("New file") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Create new file");
            }
        });

        add(newFile);

        JMenuItem newFolder = new JMenuItem(new AbstractAction("New folder") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Create new folder");
            }
        });

        add(newFolder);


        addSeparator();

        // Copy
        JMenuItem copyAction = new JMenuItem(new AbstractAction("Copy") {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringSelection selection = new StringSelection(file.getFile().getName());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, null);
            }
        });

        add(copyAction);

        // Rename
        JMenuItem renameAction = new JMenuItem(new AbstractAction("Rename") {
            @Override
            public void actionPerformed(ActionEvent e) {
                tree.startEditingAtPath(tree.getSelectionPath());
            }
        });

        add(renameAction);

        // Delete
        JMenuItem deleteAction = new JMenuItem(new AbstractAction("Delete") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Remove file");
            }
        });

        add(deleteAction);
    }
}
