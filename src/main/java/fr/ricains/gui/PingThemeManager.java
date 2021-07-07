package fr.ricains.gui;

import java.awt.*;

public abstract class PingThemeManager {
    public enum Theme
    {
        LIGHT,
        DARK
    }

    public static Theme theme = Theme.DARK;

    public static Color getFontColor()
    {
        if(theme.equals(Theme.DARK))
            return Color.white;
        else
            return Color.black;
    }

    // #242424
    public static Color projectFileBackground()
    {
        if(theme.equals(Theme.DARK))
            return new Color(36, 36, 36);
        else
            return new Color(233, 233, 233);
    }

    // #222222
    public static Color tabBackgroundSelected()
    {
        if(theme.equals(Theme.DARK))
            return new Color(54, 54, 54);
        else
            return Color.white;
    }

    // #363636
    public static Color tabBackground()
    {
        if(theme.equals(Theme.DARK))
            return new Color(34, 34, 34);
        else
            return new Color(220, 220, 220);
    }

    // #161616
    public static Color tabHeaderBackground()
    {
        if(theme.equals(Theme.DARK))
            return new Color(22, 22, 22);
        else
            return new Color(240, 240, 240); // #e9e9e9
    }

    // #424242
    public static Color tabCloseButtonSelected()
    {
        if(theme.equals(Theme.DARK))
            return new Color(42,42,42);
        else
            return new Color(227, 227, 227);
    }

    // #
    public static Color tabBorderBetween()
    {
        if(theme.equals(Theme.DARK))
            return new Color(19,19,19);
        else
            return Color.white;
    }

    // #161616
    public static Color gutterBackground()
    {
        if(theme.equals(Theme.DARK))
            return new Color(22, 22, 22);
        else
            return new Color(220, 220, 220);
    }

    // #959595
    public static Color gutterFontColor()
    {
        if(theme.equals(Theme.DARK))
            return new Color(149, 149, 149);
        else
            return new Color(149, 149, 149);
    }

    // #191919
    public static Color textAreaBackground()
    {
        if(theme.equals(Theme.DARK))
            return new Color(25, 25, 25);
        else
            return Color.white;
    }

    // orange
    public static Color circleFileChange()
    {
        if(theme.equals(Theme.DARK))
            return Color.orange;
        else
            return new Color(199, 156, 0);
    }

    public static Color fontColorGitUntracked()
    {
        if(theme.equals(Theme.DARK))
            return new Color(78, 146, 207);
        else
            return Color.blue;
    }

    public static Color fontColorGitChange()
    {
        return circleFileChange();
    }

}
