import Utils.HtmlAttributeWrapper;
import Utils.HtmlAttributeWrapperOptions;
import Utils.HtmlAttributeWrapperPreferenceStore;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.codeStyle.CodeStyleManager;
import org.jetbrains.annotations.NotNull;

public class WrapLinesAction extends AnAction {

    private static final Logger LOGGER = Logger.getInstance(WrapLinesAction.class);

    @Override
    public void update(@NotNull AnActionEvent e) {

        final Project project = e.getProject();
        final Editor editor = e.getData(CommonDataKeys.EDITOR);

        // only show the menu item when the project and editor are ready
        e.getPresentation().setVisible(project != null && editor != null);

    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        final Project project = e.getProject();
        //Access document, caret, and selection
        final Document document = editor.getDocument();
        final SelectionModel selectionModel = editor.getSelectionModel();

        String selectedText;
        int start;
        int end;

        if (selectionModel.hasSelection()) {

            // if there is a selection, use this selection as the html tag to put attributes on a new line

            start = selectionModel.getSelectionStart();
            end = selectionModel.getSelectionEnd();

            selectedText = document.getText(new TextRange(start, end));

        }
        else {

            // fetch the current line (caret position) as the 'selected text'
            start = document.getLineStartOffset(
                    document.getLineNumber(
                            selectionModel.getSelectionStart()
                    )
            );
            end = document.getLineEndOffset(
                    document.getLineNumber(start)
            );

            selectedText = document.getText(new TextRange(start, end));

        }

        // exit when the selected text is empty
        if (selectedText.trim().isEmpty()) {
            return;
        }

        final HtmlAttributeWrapper htmlAttributeWrapper = new HtmlAttributeWrapper();
        final HtmlAttributeWrapperOptions options =
            new HtmlAttributeWrapperOptions(
                new HtmlAttributeWrapperPreferenceStore()
            );
        final String result = htmlAttributeWrapper.wrap(selectedText, options);

        if (! result.isEmpty()) {

            final int startReplacementPos = start;
            final int endReplacementPos = end;
            WriteCommandAction.runWriteCommandAction(project, () -> {

                document.replaceString(startReplacementPos, endReplacementPos, result);

                selectionModel.removeSelection();

                selectionModel.setSelection(startReplacementPos, startReplacementPos + result.length());

                PsiDocumentManager documentManager =
                        PsiDocumentManager.getInstance(project);
                documentManager.commitDocument(document);

                /* Format the generated code so that it will be pretty */
                /* also, formatting in the style the user's preference is most likely the best formatting */
                CodeStyleManager styleManager =
                        CodeStyleManager.getInstance(project);
                PsiElement psiFile = e.getData(LangDataKeys.PSI_FILE);

                if (psiFile != null) {

                    PsiElement psiElement = psiFile.findElementAt(startReplacementPos);
                    if (psiElement != null) {
                        styleManager.reformat(psiElement.getParent());
                    }

                }
            });

        }

    }
}
