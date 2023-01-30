// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package org.intellij.plugins.markdown.easy;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class EasyItRecordAction extends AnAction {
  @Override
  public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
    Editor editor = anActionEvent.getData(CommonDataKeys.EDITOR);
    PsiFile psiFile = anActionEvent.getData(CommonDataKeys.PSI_FILE);
    if (editor == null || psiFile == null) {
      return;
    }
    CaretModel caretModel = editor.getCaretModel();

    OneTimeRecorder.Record record = new OneTimeRecorder.Record();
    record.offset = caretModel.getOffset();
    record.text = caretModel.getPrimaryCaret().getSelectedText();
    record.selectedStart = caretModel.getPrimaryCaret().getSelectionStart();
    record.selectedEnd = caretModel.getPrimaryCaret().getSelectionEnd();
    record.doc = editor.getDocument();
    record.virtualFile = psiFile.getVirtualFile();
    // todo record something
    OneTimeRecorder.push(record);
  }
}
