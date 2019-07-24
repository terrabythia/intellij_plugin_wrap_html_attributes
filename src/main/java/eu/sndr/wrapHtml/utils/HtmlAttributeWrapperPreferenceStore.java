package eu.sndr.wrapHtml.utils;

import com.intellij.ide.util.PropertiesComponent;

public class HtmlAttributeWrapperPreferenceStore {

    private static final String PLUGIN_ID = "eu_sndr_wrap_lines";

    public void setTextNodesOnNewLines(boolean textNodesOnNewLines) {
        PropertiesComponent.getInstance().setValue(PLUGIN_ID + "_text_nodes_on_new_line", textNodesOnNewLines, true);
    }

    public boolean getTextNodesOnNewLines() {
        return PropertiesComponent.getInstance().getBoolean(PLUGIN_ID + "_text_nodes_on_new_line", true);
    }

    public void setFirstAttributeOnNewLine(boolean firstAttributeOnNewLine) {
        PropertiesComponent.getInstance().setValue(PLUGIN_ID + "_first_attribute_on_new_line", firstAttributeOnNewLine, true);
    }

    public boolean getFirstAttributeOnNewLine() {
        return PropertiesComponent.getInstance().getBoolean(PLUGIN_ID + "_first_attribute_on_new_line", true);
    }

}
