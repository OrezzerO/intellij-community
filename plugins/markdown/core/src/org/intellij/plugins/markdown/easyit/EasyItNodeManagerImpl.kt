// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package org.intellij.plugins.markdown.easyit

import com.intellij.openapi.project.Project
import org.intellij.plugins.markdown.view.EasyItLinkNode
import org.intellij.plugins.markdown.view.EasyItNode

class EasyItNodeManagerImpl(val project:Project) :EasyItNodeManager{

  private val nodeMap: MutableMap<EasyItLinkNode, Info> = mutableMapOf()

  override fun onNodeAdded(node: EasyItNode<*>?) {
    when (node) {
      is EasyItLinkNode -> nodeMap.computeIfAbsent(node) { n: EasyItLinkNode -> Info(n) }
    }
    nodeMap[node]?.refreshRender()
  }

  override fun onNodeRemoved(easyItNode: EasyItNode<*>) {
    val info = nodeMap[easyItNode]
    if (info != null) {
      info.removeRender()
    }
  }

  inner class Info constructor(node: EasyItLinkNode) {
    private val myRenderer: GutterLineEasyItRenderer

    init {
      myRenderer = GutterLineEasyItRenderer(node)
    }

    fun refreshRender() {
      myRenderer.refreshHighlighter()
    }

    fun removeRender() {
      myRenderer.removeHighlighter()
    }
  }
}