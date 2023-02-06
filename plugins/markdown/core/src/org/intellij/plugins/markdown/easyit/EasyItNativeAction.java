// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package org.intellij.plugins.markdown.easyit;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.intellij.plugins.markdown.lang.psi.impl.MarkdownLinkText;
import org.intellij.plugins.markdown.view.Node;
import org.jetbrains.annotations.NotNull;

public class EasyItNativeAction extends AnAction {

  private final Node node;

  public EasyItNativeAction(@NotNull Node node) {
    super(node.getName());
    this.node = node;
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    MarkdownLinkText text = this.node.getText();
    text.navigate(true);
  }
}
