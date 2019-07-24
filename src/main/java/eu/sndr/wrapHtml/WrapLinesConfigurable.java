package eu.sndr.wrapHtml;

import eu.sndr.wrapHtml.utils.HtmlAttributeWrapperPreferenceStore;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ItemListener;

public class WrapLinesConfigurable implements SearchableConfigurable {

    private WrapLinesConfigurableGUI wrapLinesConfigurableGUI;
    private ItemListener somethingChangedListener;
    private boolean someThingModified = false;

    WrapLinesConfigurable() {

        somethingChangedListener = e -> {
            someThingModified = true;
        };

    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Wrap Lines Plugin";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        wrapLinesConfigurableGUI = new WrapLinesConfigurableGUI();
        HtmlAttributeWrapperPreferenceStore store = new HtmlAttributeWrapperPreferenceStore();

        wrapLinesConfigurableGUI.getWrapTextNodesOnCheckBox().setSelected(
            store.getTextNodesOnNewLines()
        );
        wrapLinesConfigurableGUI.getWrapTextNodesOnCheckBox().addItemListener(
            somethingChangedListener
        );

        wrapLinesConfigurableGUI.getFirstAttributeOnNewCheckBox().setSelected(
            store.getFirstAttributeOnNewLine()
        );
        wrapLinesConfigurableGUI.getFirstAttributeOnNewCheckBox().addItemListener(
            somethingChangedListener
        );

        return wrapLinesConfigurableGUI.getRootPanel();
    }

    @Override
    public void disposeUIResources() {
        wrapLinesConfigurableGUI = null;
    }

    @Override
    public boolean isModified() {
        return someThingModified;
    }

    @Override
    public void apply() throws ConfigurationException {

        HtmlAttributeWrapperPreferenceStore store = new HtmlAttributeWrapperPreferenceStore();
        store.setTextNodesOnNewLines(
            wrapLinesConfigurableGUI.getWrapTextNodesOnCheckBox().isSelected()
        );
        store.setFirstAttributeOnNewLine(
            wrapLinesConfigurableGUI.getFirstAttributeOnNewCheckBox().isSelected()
        );
        someThingModified = false;

    }

    @NotNull
    @Override
    public String getId() {
        return "preference.WrapLinesConfigurable";
    }

}
