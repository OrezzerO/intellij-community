// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package org.intellij.plugins.markdown.view

import com.intellij.icons.AllIcons
import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.util.treeView.AbstractTreeNode
import com.intellij.openapi.project.Project
import com.intellij.psi.tree.TokenSet
import org.intellij.plugins.markdown.lang.MarkdownTokenTypes

class EasyItLinkNode(project: Project?, value: Node) : EasyItNode<Node?>(project, value) {

  override fun getChildren(): Collection<AbstractTreeNode<*>> {
    return ArrayList()
  }

  override fun update(presentation: PresentationData) {
    presentation.setIcon(AllIcons.Nodes.Method)
    presentation.presentableText = value!!.name
  }

  override fun canNavigate(): Boolean {
    return true
  }

  override fun canNavigateToSource(): Boolean {
    return canNavigate()
  }

  override fun navigate(requestFocus: Boolean) {
    value?.also {
      it.descriptor.navigate(true)
    }
  }

  // TODO attention: 这里重载了父类的 equals 和 hashcode, 父类的方法使用 value 作为比较的对象,
  //  这里修改之后,有可能造成其他问题
  override fun equals(other: Any?): Boolean {
    return this === other
  }

  override fun hashCode(): Int {
    return System.identityHashCode(this)
  }


}