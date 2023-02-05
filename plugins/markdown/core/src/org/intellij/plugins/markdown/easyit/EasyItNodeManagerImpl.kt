// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package org.intellij.plugins.markdown.easyit

import com.intellij.openapi.project.Project
import org.intellij.plugins.markdown.view.EasyItLinkNode
import org.intellij.plugins.markdown.view.EasyItNode
import org.intellij.plugins.markdown.view.Node

class EasyItNodeManagerImpl(val project: Project) : EasyItNodeManager {

  private val nodeMap: MutableMap<EasyItLinkNode, Info> = mutableMapOf()

  //private val infoMap: MutableMap<Info, MutableList<EasyItLinkNode>> = mutableMapOf()
  private val value2info: MutableMap<Node, Info> = mutableMapOf()


  override fun onNodeAdded(node: EasyItNode<*>?) {
    when (node) {
      is EasyItLinkNode -> onEasyItLinkNodeAdded(node)
    }
    nodeMap[node]?.refreshRender()
  }


  private fun onEasyItLinkNodeAdded(treeNode: EasyItLinkNode) {
    treeNode.value?.let {
      val inMapInfo = value2info.computeIfAbsent(it) { i -> Info(i) }
      nodeMap[treeNode] = inMapInfo
      inMapInfo.addNode(treeNode)
    }
  }

  override fun onNodeRemoved(easyItNode: EasyItNode<*>) {
    nodeMap[easyItNode]?.removeRender(easyItNode)
    nodeMap.remove(easyItNode)
  }

  inner class Info constructor(node: Node) {
    private val myRenderer: GutterLineEasyItRenderer
    private val nodes = mutableSetOf<EasyItLinkNode>()

    init {
      myRenderer = GutterLineEasyItRenderer(node)
    }

    fun refreshRender() {
      if (nodes.isNotEmpty()) {
        myRenderer.refreshHighlighter()
      }
    }

    fun removeRender(easyItNode: EasyItNode<*>) {
      if (nodes.isEmpty()) {
        myRenderer.removeHighlighter()
      }
      else {
        nodes.remove(easyItNode)
      }
    }

    fun addNode(node: EasyItLinkNode) {
      nodes.add(node)
    }
  }
}