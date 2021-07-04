package fr.ricains.gui;

import org.apache.commons.io.FileUtils;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleContext;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.EventObject;
import java.util.Locale;

public class mainForm {

    private JPanel panel1;
    private JButton button1;
    private JTree projectFiles;
    private JSplitPane splitNameSplitButton;
    private JSplitPane splitFilesTree;
    private JSplitPane splitMainView;
    private JLabel projectFilesText;
    private JTabbedPane filesTabs;
    private JScrollPane scrollFilesProject;
    private JPanel testpanelUntitled;

    public mainForm() {
    }

    public JTree getProjectFiles() {
        return projectFiles;
    }

    public JTabbedPane getFilesTabs() {
        return filesTabs;
    }

    public static void setSystemUIConfiguration() {
        // Set Menu outside of the app for macOS
        System.setProperty("apple.laf.useScreenMenuBar", "true");

        // Use darkmode for macOS (black window bar)
        System.setProperty("apple.awt.application.appearance", "NSAppearanceNameDarkAqua");


        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Remove border for TabbedPane inside the whole app
        UIManager.getDefaults().put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
        UIManager.getDefaults().put("TabbedPane.tabAreaInsets", new Insets(0, 0, 0, 0));
        UIManager.getDefaults().put("TabbedPane.tabsOverlapBorder", true);

    }

    private static WindowAdapter configWindowCloseEvent(mainForm form) {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {

                // Check if there is some unsaved opened files
                var tabCount = form.filesTabs.getTabCount();
                var someEditedFiles = false;
                for (int i = 1; i < tabCount; i++) {
                    PingTabFileComponent tab = (PingTabFileComponent) form.filesTabs.getTabComponentAt(i);
                    if (tab.getEdited()) {
                        someEditedFiles = true;
                        break;
                    }
                }

                if (someEditedFiles) {
                    String ObjButtons[] = {"Yes", "No"};
                    int PromptResult = JOptionPane.showOptionDialog(null, "You have some unsaved files in the project.\nAre you sure you want to exit?", "Exit", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
                    if (PromptResult == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                } else
                    System.exit(0);
            }
        };
    }

    public static String chooseProjectFolder()
    {
        System.setProperty("apple.awt.fileDialogForDirectories", "true");

        JFrame frame = new JFrame();

        FileDialog fd = new FileDialog(frame);
        fd.setDirectory(System.getProperty("user.home"));
        fd.setLocation(50, 50);
        fd.setVisible(true);

        String selectedFile = "";
        try {
            selectedFile = fd.getDirectory() + fd.getFile();
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(frame,
                    "You did not select a valid folder.\nThe application will close.",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);

        }
        System.setProperty("apple.awt.fileDialogForDirectories", "false");

        return selectedFile;
    }

    public static void constructMainForm(String projectPath) {

        // Config Main Form
        JFrame frame = new JFrame("Les Ricains Editor -- " + new File(projectPath).getName());
        var form = new mainForm();
        frame.setContentPane(form.panel1);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(configWindowCloseEvent(form));
        frame.pack();

        // Set the Window in the center of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

        // Remove useless borders
        form.splitNameSplitButton.setBorder(BorderFactory.createEmptyBorder());
        form.splitFilesTree.setBorder(BorderFactory.createEmptyBorder());
        form.splitMainView.setBorder(BorderFactory.createEmptyBorder());
        form.splitMainView.setContinuousLayout(true);

        // Config Scroll for JTree (Project files)
        JPanel blackCorner = new JPanel();
        blackCorner.setBackground(new Color(36, 36, 36));
        form.scrollFilesProject.setCorner(JScrollPane.LOWER_RIGHT_CORNER, blackCorner);
        form.scrollFilesProject.getVerticalScrollBar().setUI(new PingProjectFilesScrollBar());
        form.scrollFilesProject.getHorizontalScrollBar().setUI(new PingProjectFilesScrollBar());
        form.scrollFilesProject.setBorder(BorderFactory.createEmptyBorder());

        // Config file tabs + Add a text in the center of the app if no files is opened
        form.filesTabs.setUI(new PingTabbedPane());
        JLabel test = new JLabel("No file opened", SwingConstants.CENTER);
        test.setFont(new Font("SF Pro", Font.BOLD, 13));
        test.setForeground(Color.WHITE);
        form.filesTabs.add(test);

        // Load project from project Path
        final File file = new File(projectPath);
        System.out.println(file);
        final MyFile mf = new MyFile(file);
        form.projectFiles.setCellRenderer(new TreeCellRenderer());
        form.projectFiles.setModel(new FileTreeModel(mf));
        form.projectFiles.setEditable(true);
        form.projectFiles.addMouseListener(new LeftClickMenuFilesTree(form));

        // Deny the possibility to edit a cell with a triple click (only with right click -> 'rename')
        DefaultTreeCellEditor editor = new DefaultTreeCellEditor(form.projectFiles, (DefaultTreeCellRenderer) form.projectFiles.getCellRenderer()) {
            @Override
            public boolean isCellEditable(EventObject event) {
                if (event instanceof MouseEvent) {
                    return false;
                }
                return super.isCellEditable(event);
            }
        };
        form.projectFiles.setCellEditor(editor);


        // Create Menu
        frame.setJMenuBar(PingMenuFactory.createAppMenuBar(frame));

        // Set Frame visible
        frame.setVisible(true);
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        panel1.setBackground(new Color(-15198184));
        panel1.setForeground(new Color(-1));
        panel1.setPreferredSize(new Dimension(1200, 600));
        splitMainView = new JSplitPane();
        splitMainView.setBackground(new Color(-15132391));
        splitMainView.setDividerSize(1);
        splitMainView.setForeground(new Color(-1));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(splitMainView, gbc);
        splitFilesTree = new JSplitPane();
        splitFilesTree.setDividerSize(0);
        splitFilesTree.setEnabled(true);
        splitFilesTree.setOrientation(0);
        splitMainView.setLeftComponent(splitFilesTree);
        splitNameSplitButton = new JSplitPane();
        splitNameSplitButton.setBackground(new Color(-14408668));
        splitNameSplitButton.setContinuousLayout(true);
        splitNameSplitButton.setDividerSize(0);
        splitNameSplitButton.setFocusable(true);
        splitNameSplitButton.setForeground(new Color(-1));
        splitNameSplitButton.setMinimumSize(new Dimension(250, 30));
        splitNameSplitButton.setOneTouchExpandable(false);
        splitNameSplitButton.setPreferredSize(new Dimension(250, 30));
        splitNameSplitButton.setRequestFocusEnabled(true);
        splitFilesTree.setLeftComponent(splitNameSplitButton);
        projectFilesText = new JLabel();
        projectFilesText.setBackground(new Color(-12510208));
        projectFilesText.setEnabled(true);
        Font projectFilesTextFont = this.$$$getFont$$$("SF Pro", -1, 13, projectFilesText.getFont());
        if (projectFilesTextFont != null) projectFilesText.setFont(projectFilesTextFont);
        projectFilesText.setForeground(new Color(-1));
        projectFilesText.setHorizontalAlignment(0);
        projectFilesText.setHorizontalTextPosition(0);
        projectFilesText.setMaximumSize(new Dimension(200, 30));
        projectFilesText.setMinimumSize(new Dimension(200, 30));
        projectFilesText.setPreferredSize(new Dimension(200, 30));
        projectFilesText.setRequestFocusEnabled(true);
        projectFilesText.setText("PROJECT FILES");
        splitNameSplitButton.setLeftComponent(projectFilesText);
        button1 = new JButton();
        button1.setBorderPainted(false);
        button1.setContentAreaFilled(false);
        button1.setDoubleBuffered(false);
        button1.setEnabled(true);
        button1.setFocusable(true);
        button1.setForeground(new Color(-13027653));
        button1.setHideActionText(true);
        button1.setHorizontalAlignment(4);
        button1.setHorizontalTextPosition(0);
        button1.setIcon(new ImageIcon(getClass().getResource("/Double view.png")));
        button1.setIconTextGap(0);
        button1.setMaximumSize(new Dimension(30, 30));
        button1.setMinimumSize(new Dimension(30, 30));
        button1.setName("Split view");
        button1.setOpaque(false);
        button1.setPreferredSize(new Dimension(30, 30));
        button1.setRolloverEnabled(false);
        button1.setSelected(false);
        button1.setText("");
        button1.setVerticalAlignment(0);
        button1.putClientProperty("html.disable", Boolean.FALSE);
        splitNameSplitButton.setRightComponent(button1);
        scrollFilesProject = new JScrollPane();
        splitFilesTree.setRightComponent(scrollFilesProject);
        projectFiles = new JTree();
        projectFiles.setAutoscrolls(false);
        projectFiles.setBackground(new Color(-14408668));
        Font projectFilesFont = this.$$$getFont$$$("SF Pro", Font.PLAIN, 13, projectFiles.getFont());
        if (projectFilesFont != null) projectFiles.setFont(projectFilesFont);
        projectFiles.setForeground(new Color(-14408668));
        projectFiles.setInheritsPopupMenu(false);
        projectFiles.setRootVisible(false);
        projectFiles.setShowsRootHandles(true);
        projectFiles.putClientProperty("JTree.lineStyle", "");
        projectFiles.putClientProperty("html.disable", Boolean.FALSE);
        scrollFilesProject.setViewportView(projectFiles);
        filesTabs = new JTabbedPane();
        filesTabs.setBackground(new Color(-15329770));
        filesTabs.setFocusTraversalPolicyProvider(false);
        Font filesTabsFont = this.$$$getFont$$$("SF Pro Text", Font.PLAIN, 13, filesTabs.getFont());
        if (filesTabsFont != null) filesTabs.setFont(filesTabsFont);
        filesTabs.setForeground(new Color(-1));
        filesTabs.setOpaque(true);
        filesTabs.setTabLayoutPolicy(1);
        filesTabs.setTabPlacement(1);
        filesTabs.setVisible(true);
        splitMainView.setRightComponent(filesTabs);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}
