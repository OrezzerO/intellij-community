// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package org.intellij.plugins.markdown.easyit;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.intellij.plugins.markdown.view.EasyItLinkNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class GutterDynamicActionGroup extends ActionGroup {

  private final EasyItNodeManagerImpl.Info info;

  public GutterDynamicActionGroup(EasyItNodeManagerImpl.Info info) {
    this.info = info;
  }

  @Override
  public AnAction @NotNull [] getChildren(@Nullable AnActionEvent e) {
    Set<EasyItLinkNode> nodes = info.getNodes();
    if (nodes.isEmpty()) {
      return new AnAction[0];
    }
    else {
      return nodes.stream().map(this::toAction).toArray(AnAction[]::new);
    }
  }

  private AnAction toAction(EasyItLinkNode node) {
    return new EasyItNativeAction(node.getValue());
  }
}
