// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package org.intellij.plugins.markdown.view

import com.intellij.icons.AllIcons
import com.intellij.ide.SelectInTarget
import com.intellij.ide.impl.ProjectViewSelectInTarget
import com.intellij.ide.projectView.ViewSettings
import com.intellij.ide.projectView.impl.AbstractProjectViewPaneWithAsyncSupport
import com.intellij.ide.projectView.impl.ProjectAbstractTreeStructureBase
import com.intellij.ide.projectView.impl.ProjectTreeStructure
import com.intellij.ide.projectView.impl.ProjectViewTree
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import org.jetbrains.annotations.Nls
import javax.swing.Icon
import javax.swing.tree.DefaultTreeModel

class EasyItProjectView(project: Project) : AbstractProjectViewPaneWithAsyncSupport(project) {
  override fun getTitle(): @Nls(capitalization = Nls.Capitalization.Title) String {
    return "Easy-IT"
  }

  override fun getIcon(): Icon {
    return AllIcons.Ide.Link
  }

  override fun getId(): String {
    return ID
  }

  override fun getWeight(): Int {
    return 10
  }

  override fun createSelectInTarget(): SelectInTarget {
    return object : ProjectViewSelectInTarget(myProject) {
      override fun toString(): String {
        return ID
      }

      override fun getMinorViewId(): String? {
        return ID
      }

      override fun getWeight(): Float {
        return 10F
      }
    }
  }

  override fun createStructure(): ProjectAbstractTreeStructureBase {
    return object : ProjectTreeStructure(myProject, ID) {
      override fun createRoot(project: Project, settings: ViewSettings): EasyItFileNode {
        val baseDir = project.guessProjectDir()!!
        val readmeFile = baseDir.children?.let {
          it.firstOrNull { v -> !v.isDirectory && v.name == "README.md" }
        }
        return EasyItFileNode(project, readmeFile ?: baseDir, true)
      }

      // Children will be searched in async mode
      override fun isToBuildChildrenInBackground(element: Any): Boolean {
        return true
      }
    }
  }

  override fun createTree(treeModel: DefaultTreeModel): ProjectViewTree {
    return object : ProjectViewTree(treeModel) {
      override fun isRootVisible(): Boolean {
        return true
      }
    }
  }

  companion object {
    const val ID = "EasyIT"
  }
}