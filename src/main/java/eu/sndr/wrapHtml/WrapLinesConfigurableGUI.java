package eu.sndr.wrapHtml;

import javax.swing.*;

public class WrapLinesConfigurableGUI {

    private JPanel rootPanel;
    private JCheckBox wrapTextNodesOnCheckBox;
    private JCheckBox firstAttributeOnNewCheckBox;

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public JCheckBox getWrapTextNodesOnCheckBox() {
        return wrapTextNodesOnCheckBox;
    }

    public JCheckBox getFirstAttributeOnNewCheckBox() {
        return firstAttributeOnNewCheckBox;
    }
}
