// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package org.intellij.plugins.markdown.view

import com.intellij.icons.AllIcons
import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.util.treeView.AbstractTreeNode
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiManager
import com.intellij.psi.tree.TokenSet
import org.intellij.plugins.markdown.lang.MarkdownTokenTypes

class EasyItLinkNode(project: Project?, value: Node) : EasyItNode<Node?>(project, value) {
  val anchorAttributes = mutableMapOf<String, String>()

  init {
    value.destination?.text?.substringAfter("#")?.let { parseAnchor(it) }

  }

  private fun parseAnchor(anchor: String) {
    val split = anchor.split("&")
    for (s in split) {
      val equalPairs = s.split("=")
      when (equalPairs.size) {
        1 -> {
          anchorAttributes[s] = s
        }
        2 -> {
          anchorAttributes[equalPairs[0]] = equalPairs[1]
        }
        else -> {}
      }
    }
  }

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
    value?.file?.let { PsiManager.getInstance(myProject).findFile(it)?.navigate(requestFocus) }

  }
}