package Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlAttributeWrapper {

    public String wrap(String text, HtmlAttributeWrapperOptions options) {

        String prependWhiteSpace = "";
        final String[] preWhiteSpaceArray = text.split("\\S");
        if (preWhiteSpaceArray.length > 0) {
            prependWhiteSpace = preWhiteSpaceArray[0];
        }

        // Find any HTML code in the selectedText
//        final String HtmlRe = "<\\/?\\w+((\\s+\\w+(\\s*=\\s*(?:\".*?\"|'.*?'|[\\^'\">\\s]+))?)+\\s*|\\s*)\\/?>";
//        final String HtmlRe = "(\\S+)=[\"']?((?:.(?![\"']?\\s+(?:\\S+)=|[>\"']))+.)[\"']?";
//        final String HtmlRe = "([^\\s]+=(([\"][^\"]*[\"])|(['][^']*['])))";
//        final String HtmlRe = "([^\\s]+=(([\"][^\"]+[\"])|(['][^']+['])))";
//        final String HtmlRe = "([^\\s]+=(([\"][^\"]*[\"])|(['][^']*['])))";

        // html regex WITH count of closing tags...
        final String HtmlRe = "([^\\s]+=(?:[\"][^\"]*[\"]|['][^']*[']))(?:(>)([\\w\\s]+))?";

        final Pattern pattern = Pattern.compile(HtmlRe, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(text);

        String result = text;
        boolean firstAttribute = true;
        boolean firstAttributeOnNewLine = options.isFirstAttributeOnNewLine();
        boolean textNodeOnNewLine = options.isTextNodeOnNewLine();
        while (matcher.find()) {

//            String fullMatch = matcher.group(0);
            String attributeMatch = matcher.group(1);

            String closingTagMatch = matcher.group(2) != null ? matcher.group(2).trim() : "";
            String textNodeMatch = matcher.group(3) != null ? matcher.group(3) : "";

            // if not first attribute > new line
            // else if is first attributes and in settings first attributes should be on new line
            // AND it's not the only attribute on the tag
            String prefix =
                    (!firstAttribute || (firstAttributeOnNewLine && !closingTagMatch.equals(">")))
                            ?
                            "\n" + prependWhiteSpace +  "\t"
                            :
                            " ";

            String replaceMatch = prefix + attributeMatch;

            // if match group 2 is a closing tag then parent attribute is closed, so set the 'is first attribute' flag to true
            firstAttribute = closingTagMatch.equals(">");

            // TODO: if textNodeMatch is not empty and options.textNodeOnNewLine() is true
            // put the text Node on the new line
            if (textNodeOnNewLine && !textNodeMatch.trim().isEmpty()) {
                result = result.replaceFirst("\\s*" + Pattern.quote(textNodeMatch) + "\\s*", Matcher.quoteReplacement("\n" + textNodeMatch + "\n"));
            }

            // replace with matcher
            // make sure to only append 1 new line
            String replacePattern = "\\s*" + Pattern.quote(attributeMatch);
            result = result.replaceFirst(replacePattern, Matcher.quoteReplacement(replaceMatch));

        }

        return result;

    }

    public String wrap(String text) {
        return wrap(text, new HtmlAttributeWrapperOptions());
    }

    // todo: implement unwrap method?

}
