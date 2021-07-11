package fr.ricains.gui;

import java.io.File;
import java.util.prefs.Preferences;


import javax.swing.*;

public class Main {

    private static String getValidProjectPathArgument(String[] args)
    {
        if(args.length > 0)
        {
            String pathStr =  args[0];
            File file = new File(pathStr);
            if(!file.isDirectory() || !file.canRead() || !file.canWrite())
                return "";

            return pathStr;
        }

        return "";
    }

    private static void loadSavedThemeSetting()
    {
        Preferences prefs = Preferences.userNodeForPackage(PingThemeManager.class);
        String themeStr = prefs.get("THEME", "d");
        if(themeStr.equals("d"))
            PingThemeManager.theme = PingThemeManager.Theme.DARK;
        else
            PingThemeManager.theme = PingThemeManager.Theme.LIGHT;
    }

    public static void main(String[] args) {

        loadSavedThemeSetting();
        mainForm.setSystemUIConfiguration();

        String pathStr = getValidProjectPathArgument(args);
        if(pathStr.equals(""))
            pathStr = mainForm.chooseProjectFolder();

        if(pathStr.equals(""))
        {
            JOptionPane.showMessageDialog(null,
                    "You did not select a valid folder.\nThe application will close.",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);

            System.exit(0);
        }

        System.out.println(pathStr);

        try {
            mainForm.constructMainForm(pathStr);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
