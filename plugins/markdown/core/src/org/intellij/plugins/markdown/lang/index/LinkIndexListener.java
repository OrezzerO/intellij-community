// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package org.intellij.plugins.markdown.lang.index;

import java.util.EventListener;

public interface LinkIndexListener extends EventListener {

  default void indexChanged(Object object) {
  }
}
