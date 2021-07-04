package fr.ricains.gui;

import com.intellij.uiDesigner.core.GridLayoutManager;
import org.fife.rsta.ac.LanguageSupport;
import org.fife.rsta.ac.LanguageSupportFactory;
import org.fife.rsta.ac.java.JavaCompletionProvider;
import org.fife.rsta.ac.java.JavaLanguageSupport;
import org.fife.ui.autocomplete.*;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.SyntaxScheme;
import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class OpenedFileMenu {
    private JPanel panel;
    private JPanel newPanel;
    private final File file;
    public PingTabFileComponent tabComponent;

    public boolean error = false;

    public PingTabFileComponent getTabComponent() {
        return tabComponent;
    }

    public OpenedFileMenu(File file) {

        this.file = file;
        var fd = 4512051;

        LanguageSupportFactory lsf = LanguageSupportFactory.get();
        LanguageSupport support = lsf.getSupportFor(SyntaxConstants.SYNTAX_STYLE_JAVA);
        JavaLanguageSupport jls = (JavaLanguageSupport) support;
        // TODO: This API will change! It will be easier to do per-editor
        // changes to the build path.
        try {
            jls.getJarManager().addClassFileSource(new JDK9ClasspathLibraryInfo());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        JPanel cp = new JPanel(new BorderLayout());

        RSyntaxTextArea textArea = new RSyntaxTextArea(20, 60);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setCodeFoldingEnabled(true);
        RTextScrollPane sp = new RTextScrollPane(textArea);

        sp.setBorder(BorderFactory.createEmptyBorder());

        textArea.setBackground(new Color(19, 19, 19));
        textArea.setForeground(Color.white);
        textArea.setFont(new Font("SF Pro", Font.PLAIN, 13));
        /*
         * textArea.setTabLineColor(new Color(54, 54, 54));
         * textArea.setSelectionColor(new Color(54, 54, 54));
         * textArea.setSelectedTextColor(new Color(54, 54, 54));
         * textArea.setMargﬁinLineColor(new Color(54, 54, 54));
         */
        textArea.setCaretColor(Color.white);
        textArea.setCurrentLineHighlightColor(new Color(54, 54, 54));
        textArea.setSelectedTextColor(new Color(54, 54, 54));

        sp.getGutter().setBackground(new Color(22, 22, 22));

        sp.getGutter().setBorder(new Gutter.GutterBorder(0, 25, 0, 15));
        sp.getGutter().setBorderColor(new Color(22, 22, 22));

        sp.getGutter().setLineNumberColor(new Color(149, 149, 149));
        sp.getGutter().setLineNumberFont(new Font("SF Pro", Font.PLAIN, 13));

        sp.getGutter().setSpacingBetweenLineNumbersAndFoldIndicator(10);

        textArea.setCaretPosition(0);
        textArea.requestFocusInWindow();
        textArea.setTabsEmulated(true);
        ToolTipManager.sharedInstance().registerComponent(textArea);

        jls.install(textArea);

        JavaCompletionProvider provider = new JavaCompletionProvider();
        provider.setAutoActivationRules(true, null);

        AutoCompletion ac = new AutoCompletion(provider);
        ac.setAutoCompleteEnabled(true);
        ac.setShowDescWindow(true);
        ac.setAutoActivationEnabled(true);
        ac.setAutoCompleteSingleChoices(false);
        ac.setAutoActivationDelay(500); // Number of milliseconds to debounce the popup window. Must be >= 0

        sp.setFoldIndicatorEnabled(false);
        sp.setBackground(new Color(19, 19, 19));
        sp.setFont(new Font("SF Pro", Font.PLAIN, 13));

        ac.install(textArea);
        LanguageSupportFactory.get().register(textArea);

        textArea.revalidate();

        changeColorScheme(textArea);

        cp.add(sp);
        newPanel = cp;

        try {
            var r = new BufferedReader(new FileReader(file));
            textArea.read(r, null);
        } catch (Exception e) {
            e.printStackTrace();
            this.error = true;
        }

        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                getTabComponent().setEdited(true);
            }
        });

    }

    public JPanel getPanel() {
        return newPanel;
    }

    private void changeColorScheme(RSyntaxTextArea textArea) {
        // Change a few things here and there.
        SyntaxScheme scheme = textArea.getSyntaxScheme();
        scheme.getStyle(Token.RESERVED_WORD).foreground = new Color(78, 146, 207);
        scheme.getStyle(Token.RESERVED_WORD_2).foreground = new Color(190, 130, 188);
        scheme.getStyle(Token.DATA_TYPE).foreground = new Color(82, 206, 180);
        scheme.getStyle(Token.ANNOTATION).foreground = new Color(82, 206, 180);
        scheme.getStyle(Token.FUNCTION).foreground = new Color(207, 207, 156);
        scheme.getStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).foreground = new Color(180, 130, 104);
        scheme.getStyle(Token.VARIABLE).foreground = new Color(137, 193, 224);

        scheme.getStyle(Token.LITERAL_BOOLEAN).foreground = new Color(78, 146, 207);

        scheme.getStyle(Token.OPERATOR).foreground = new Color(255, 255, 255);
        scheme.getStyle(Token.SEPARATOR).foreground = new Color(255, 255, 255);

        //scheme.getStyle(Token.IDENTIFIER).foreground = Color.PINK;

        // Numbers
        scheme.getStyle(Token.LITERAL_NUMBER_DECIMAL_INT).foreground = new Color(158, 177, 143);
        scheme.getStyle(Token.LITERAL_NUMBER_FLOAT).foreground = new Color(158, 177, 143);
        scheme.getStyle(Token.LITERAL_NUMBER_HEXADECIMAL).foreground = new Color(158, 177, 143);

        // Comments
        scheme.getStyle(Token.COMMENT_EOL).foreground = new Color(93, 131, 69);
        scheme.getStyle(Token.COMMENT_DOCUMENTATION).foreground = new Color(93, 131, 69);

        scheme.getStyle(Token.COMMENT_EOL).font = new Font("SF Pro", Font.PLAIN, 13);

        textArea.revalidate();
    }

    {
        // GUI initializer generated by IntelliJ IDEA GUI Designer
        // >>> IMPORTANT!! <<<
        // DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer >>> IMPORTANT!! <<< DO NOT
     * edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel = new JPanel();
        panel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }

}
