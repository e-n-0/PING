package fr.ricains.gui;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.prefs.Preferences;

public abstract class PingMenuFactory {

    private static JMenu createFileMenu(JFrame frame)
    {
        JMenu menuFile = new JMenu("File");
        JMenuItem openProject = new JMenuItem("Open new project folder");

        // Open new instance of mainForm on another Project Folder
        openProject.addActionListener(e ->
        {
            System.setProperty("apple.awt.fileDialogForDirectories", "true");
            FileDialog fd = new FileDialog(frame);
            fd.setDirectory(System.getProperty("user.home"));
            fd.setLocation(50, 50);
            fd.setVisible(true);

            String selectedFile = "";
            try {
                selectedFile = fd.getFile() != null ? fd.getDirectory() + fd.getFile() : "";
                if(!selectedFile.equals(""))
                    mainForm.constructMainForm(selectedFile);

            } catch (Exception e2) {}

            System.setProperty("apple.awt.fileDialogForDirectories", "false");
        });

        JMenuItem saveCurrentFile = new JMenuItem("Save file");
        saveCurrentFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.META_DOWN_MASK));
        saveCurrentFile.getAccessibleContext().setAccessibleDescription("Save the current opened file in the editor.");
        saveCurrentFile.addActionListener(e -> SaveFile.save());

        menuFile.add(openProject);
        menuFile.addSeparator();
        menuFile.add(saveCurrentFile);

        return menuFile;
    }

    private static JMenu createWindowMenu(JFrame frame)
    {
        JMenu menuWindow = new JMenu("Window");
        JMenuItem switchTheme = new JMenuItem("Switch to Dark/Light Theme");

        switchTheme.addActionListener(e ->
        {
            Preferences prefs = Preferences.userNodeForPackage(PingThemeManager.class);

            if(PingThemeManager.theme.equals(PingThemeManager.Theme.DARK))
            {
                PingThemeManager.theme = PingThemeManager.Theme.LIGHT;
                prefs.put("THEME", "l");
            }
            else
            {
                PingThemeManager.theme = PingThemeManager.Theme.DARK;
                prefs.put("THEME", "d");
            }

            // Get all Frame visible
            System.out.println(Frame.getFrames().length);
            for(var elem : Frame.getFrames())
            {
                try
                {
                    PingJFrame pingFrame = (PingJFrame) elem;
                    mainForm mainForm = pingFrame.getForm();
                    mainForm.setFormColors();

                    var tabCount = mainForm.getFilesTabs().getTabCount();
                    for (int i = 1; i < tabCount; i++) {
                        PingTabFileComponent tab = (PingTabFileComponent) mainForm.getFilesTabs().getTabComponentAt(i);
                        tab.getMenu().setFormColors();
                    }
                }
                catch(Exception ee){}

            }

            frame.invalidate();
            frame.repaint();
        });

        menuWindow.add(switchTheme);
        return menuWindow;
    }

    public static JMenuBar createAppMenuBar(JFrame frame)
    {
        JMenuBar menuBar = new JMenuBar();

        menuBar.add(createFileMenu(frame));
        menuBar.add(createWindowMenu(frame));

        return menuBar;
    }
}
