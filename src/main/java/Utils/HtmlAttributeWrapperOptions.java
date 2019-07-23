package Utils;

public class HtmlAttributeWrapperOptions {

    private boolean firstAttributeOnNewLine = true;
    private boolean textNodeOnNewLine = true;

    public HtmlAttributeWrapperOptions() {
        initWithStore(new HtmlAttributeWrapperPreferenceStore());
    }

    public HtmlAttributeWrapperOptions(HtmlAttributeWrapperPreferenceStore store) {
       initWithStore(store);
    }

    public void initWithStore(HtmlAttributeWrapperPreferenceStore store) {
        setTextNodeOnNewLine(store.getTextNodesOnNewLines());
        boolean storeVal = store.getFirstAttributeOnNewLine();
        setFirstAttributeOnNewLine(store.getFirstAttributeOnNewLine());
    }

    public boolean isTextNodeOnNewLine() {
        return textNodeOnNewLine;
    }

    public void setTextNodeOnNewLine(boolean textNodeOnNewLine) {
        this.textNodeOnNewLine = textNodeOnNewLine;
    }

    public boolean isFirstAttributeOnNewLine() {
        return firstAttributeOnNewLine;
    }

    public void setFirstAttributeOnNewLine(boolean firstAttributeOnNewLine) {
        this.firstAttributeOnNewLine = firstAttributeOnNewLine;
    }
}
