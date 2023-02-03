// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package org.intellij.plugins.markdown.easyit;

import com.intellij.openapi.project.Project;
import org.intellij.plugins.markdown.view.EasyItNode;
import org.jetbrains.annotations.Nullable;

public interface EasyItNodeManager {
  static @Nullable EasyItNodeManager getInstance(@Nullable Project project) {
    return project == null || project.isDisposed() ? null : project.getService(EasyItNodeManager.class);
  }

  void onNodeAdded(EasyItNode node);

  void onNodeRemoved(EasyItNode node);
}
