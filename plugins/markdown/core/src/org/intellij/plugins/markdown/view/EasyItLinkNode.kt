// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package org.intellij.plugins.markdown.view

import com.intellij.icons.AllIcons
import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.util.treeView.AbstractTreeNode
import com.intellij.openapi.project.Project
import com.intellij.psi.tree.TokenSet
import org.intellij.plugins.markdown.lang.MarkdownTokenTypes

class EasyItLinkNode(project: Project?, value: Node) : AbstractTreeNode<Node?>(project, value) {
  override fun getChildren(): Collection<AbstractTreeNode<*>> {
    return ArrayList()
  }

  override fun update(presentation: PresentationData) {
    presentation.setIcon(AllIcons.Nodes.Method)

    val empty = "EMPTY"
    val children = value?.text?.node?.getChildren(TokenSet.create(MarkdownTokenTypes.TEXT)).orEmpty()
    if (children.isEmpty()) {
      presentation.presentableText = empty
    }
    else {
      presentation.presentableText = children[0].text
    }
  }

  override fun canNavigate(): Boolean {
    return true
  }

  override fun canNavigateToSource(): Boolean {
    return canNavigate()
  }

  override fun navigate(requestFocus: Boolean) {
    val value = value
    value?.destination?.navigate(true)
  }
}