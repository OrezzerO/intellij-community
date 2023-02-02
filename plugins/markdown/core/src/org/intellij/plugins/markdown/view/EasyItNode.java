// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package org.intellij.plugins.markdown.view;

import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public abstract class EasyItNode<T> extends AbstractTreeNode<T> {
  protected EasyItNode(Project project, @NotNull T value) {
    super(project, value);
  }
}
