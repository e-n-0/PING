package fr.ricains.gui;

import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

public class PingTabbedPane extends BasicTabbedPaneUI {

    @Override
    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        super.paintTabBackground(g, tabPlacement, tabIndex, x, y, w, h, isSelected);

        if(isSelected)
            g.setColor(new Color(54, 54, 54));
        else
            g.setColor(new Color(34, 34, 34));

        g.fillRect(x, y, w, h);
    }

    @Override
    protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title, Rectangle textRect, boolean isSelected) {
        super.paintText(g, tabPlacement, font, metrics, tabIndex, title, textRect, isSelected);
    }

    @Override
    protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
        return 40;
    }

    @Override
    protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {

        if(tabIndex == 0)
            return 0;

        var width = super.calculateTabWidth(tabPlacement, tabIndex, metrics);
        return width-5;
    }

    @Override
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        if(tabIndex == 1)
            return;

        g.setColor(new Color(19,19,19));
        g.drawRect(x, y-1, w, h+1);
        if(isSelected)
            g.setColor(new Color(121,134,133));
    }

    @Override
    protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) { }

    @Override
    protected void paintContentBorder(Graphics graphics, int i, int i1) { }


    /*@Override
    protected void paintTab(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect) {
        super.paintTab(g, tabPlacement, rects, tabIndex, iconRect, textRect);
        g.setColor(Color.pink);
        ((Graphics2D)g).setBackground(Color.red);

    }*/

    // Full Screen Tab content (file opened)
    private final Insets borderInsets = new Insets(0, 0, 0, 0);
    @Override
    protected Insets getContentBorderInsets(int tabPlacement) {
        return borderInsets;
    }
}
