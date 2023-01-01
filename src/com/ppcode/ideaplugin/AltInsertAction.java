package com.ppcode.ideaplugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.SequenceInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;

/**
 * @author styra
 * @date 2022/5/1022:08
 */
public class AltInsertAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        PsiFile psiFile = event.getData(LangDataKeys.PSI_FILE);
        if (!(psiFile instanceof PsiJavaFile)) {
            Messages.showMessageDialog("Is not java file.", "Error", Messages.getErrorIcon());
            return;
        }

        exec();

        Project project = event.getData(PlatformDataKeys.PROJECT);
        String text = Messages.showInputDialog(project, "What is your name, dear?", "Question",
                Messages.getQuestionIcon());
        Messages.showMessageDialog(project, "Hello, " + text + "!\nI am glad to see you.", "Information",
                Messages.getInformationIcon());
    }


    public static List executeCommand() throws IOException {
        String command = "java -cp D:\\fdcode\\code-checkstyle\\target\\lib\\*;D:\\fdcode\\code-checkstyle\\target\\checkstyle-10.3.jar com.puppycrawl.tools.checkstyle.Main checkstyle -c=D:\\fdcode\\codeStyle.xml D:\\fdcode\\checkstyle-checkstyle-10.3\\src\\test\\resources\\com\\puppycrawl\\tools\\checkstyle\\checks\\imports\\redundantimport\\";
        InputStreamReader isr = null;
        BufferedReader br = null;
        List results = new ArrayList<>();
        try {
            Process process = Runtime.getRuntime().exec(command);
            // int exitVal = process.waitFor();
            SequenceInputStream sis = new SequenceInputStream(process.getErrorStream(), process.getInputStream());
            isr = new InputStreamReader(sis, StandardCharsets.UTF_8);
            br = new BufferedReader(isr);
            String line;
            boolean interrupt = false;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                results.add(line);
                if (!interrupt) {
                    Thread.sleep(500);
                    interrupt = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                br.close();
            }

            if (isr != null) {
                isr.close();
            }
        }
        return results;
    }

    public static void exec() {
        BufferedReader br = null;
        String command = "java -cp D:\\fdcode\\code-checkstyle\\target\\lib\\*;D:\\fdcode\\code-checkstyle\\target\\checkstyle-10.3.jar com.puppycrawl.tools.checkstyle.Main checkstyle -c=D:\\fdcode\\codeStyle.xml D:\\fdcode\\checkstyle-checkstyle-10.3\\src\\test\\resources\\com\\puppycrawl\\tools\\checkstyle\\checks\\imports\\redundantimport\\";
        try {
            Process p = Runtime.getRuntime().exec(command);
            br = new BufferedReader(new InputStreamReader(p.getInputStream(), "gbk"));
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
