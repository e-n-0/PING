package fr.ricains.gui;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class TreeCellRenderer extends DefaultTreeCellRenderer {
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean sel, boolean exp, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, exp, leaf, row, hasFocus);
        //String node = (String) ((DefaultMutableTreeNode) value).getUserObject();

        setForeground(PingThemeManager.getFontColor());
        setBackground(PingThemeManager.projectFileBackground());
        setBackgroundSelectionColor(PingThemeManager.tabBackground());
        setBackgroundNonSelectionColor(PingThemeManager.projectFileBackground());
        setFont(new Font("SF Pro Text", Font.PLAIN, 13));

        return this;
    }

}
