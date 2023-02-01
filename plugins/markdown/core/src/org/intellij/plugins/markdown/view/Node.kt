// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package org.intellij.plugins.markdown.view

import org.intellij.plugins.markdown.lang.psi.impl.MarkdownLinkDestination
import org.intellij.plugins.markdown.lang.psi.impl.MarkdownLinkText

class Node {
  var text: MarkdownLinkText? = null
    private set
  var destination: MarkdownLinkDestination? = null
    private set

  constructor()
  constructor(text: MarkdownLinkText?, destination: MarkdownLinkDestination?) {
    this.text = text
    this.destination = destination
  }
}