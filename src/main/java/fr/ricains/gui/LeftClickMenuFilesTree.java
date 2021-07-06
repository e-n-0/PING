package fr.ricains.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LeftClickMenuFilesTree implements MouseListener {

    private final mainForm form;

    public LeftClickMenuFilesTree(mainForm form)
    {
        this.form = form;
    }

    public static void openFileTab(OpenedFileMenu newMenu, mainForm form, JTabbedPane pane)
    {
        if(newMenu.error)
        {
            // An error occured when opening the file
            JOptionPane.showMessageDialog(null,
                    "Failed to open the file \"" + newMenu.getFile().getName() + "\"",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        pane.addTab(newMenu.getFile().getName(), newMenu.getPanel());
        var indexLastTab = pane.getTabCount() - 1;
        pane.setTabComponentAt(indexLastTab, new PingTabFileComponent(pane, form));
        pane.setSelectedIndex(indexLastTab);

        PingTabFileComponent pingTab = (PingTabFileComponent) pane.getTabComponentAt(indexLastTab);
        pingTab.setMenu(newMenu);
        pingTab.setFilePath(newMenu.getFile().getAbsolutePath());
        newMenu.tabComponent = pingTab;
        newMenu.form = form;
    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (SwingUtilities.isRightMouseButton(e)) {

            int row = form.getProjectFiles().getClosestRowForLocation(e.getX(), e.getY());
            form.getProjectFiles().setSelectionRow(row);
            MyFile fileSelected = (MyFile) form.getProjectFiles().getLastSelectedPathComponent();
            System.out.println(fileSelected.getFile().getAbsolutePath());

            if (e.isPopupTrigger()) {
                RightClickMenuFilesTree menu = new RightClickMenuFilesTree(fileSelected, form.getProjectFiles());
                menu.show(e.getComponent(), e.getX(), e.getY());
                //form.projectFiles.removeRow

                FileTreeModel model = (FileTreeModel) form.getProjectFiles().getModel();
                //model.valueForPathChanged();
            }
        } else {
            int row = form.getProjectFiles().getClosestRowForLocation(e.getX(), e.getY());
            form.getProjectFiles().setSelectionRow(row);
            MyFile fileSelected = (MyFile) form.getProjectFiles().getLastSelectedPathComponent();

            if (fileSelected.isDirectory())
                return;

            // Search in opened files if this isn't already opened
            var focusedFilesTabs = form.getFocusedFilesTab();
            var tabCount = focusedFilesTabs.getTabCount();
            for (int i = 1; i < tabCount; i++) {
                PingTabFileComponent tab = (PingTabFileComponent) focusedFilesTabs.getTabComponentAt(i);
                if (tab.getFilePath().equals(fileSelected.getFile().getAbsolutePath())) {
                    // Already opened
                    focusedFilesTabs.setSelectedIndex(i);
                    return;
                }
            }

            System.out.println(fileSelected.getFile().getAbsolutePath());

            var newMenu = new OpenedFileMenu(fileSelected.getFile());
            openFileTab(newMenu, form, focusedFilesTabs);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
