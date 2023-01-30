// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package org.intellij.plugins.markdown.easy;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

import static com.intellij.openapi.command.WriteCommandAction.runWriteCommandAction;

public class EasyItInsertAction extends AnAction {
  @Override
  public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
    OneTimeRecorder.Record pop = OneTimeRecorder.pop();
    if (pop == null) {
      return;
    }


    Editor editor = anActionEvent.getData(CommonDataKeys.EDITOR);
    PsiFile psiFile = anActionEvent.getData(CommonDataKeys.PSI_FILE);
    VirtualFile currentVirtualFile = psiFile.getVirtualFile();

    String path = VfsUtilCore.findRelativePath(pop.virtualFile, currentVirtualFile, VfsUtilCore.VFS_SEPARATOR_CHAR);
    int lineNumber = editor.getCaretModel().getLogicalPosition().line + 1;


    Document doc = pop.doc;
    runWriteCommandAction(anActionEvent.getProject(), () ->
      doc.replaceString(pop.selectedStart, pop.selectedEnd, "[" + pop.text + "](" + path + "#L" + lineNumber + ")")
    );
  }
}
