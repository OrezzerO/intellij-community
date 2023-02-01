// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package org.intellij.plugins.markdown.lang.index

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StringStubIndexExtension
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.stubs.StubIndexKey
import com.intellij.util.CommonProcessors
import com.intellij.util.EventDispatcher
import org.intellij.plugins.markdown.lang.psi.impl.MarkdownInlineLink
import org.intellij.plugins.markdown.lang.stubs.impl.MarkdownInlineLinkStubElement

/**
 * Index of headers actual full text.
 */
class InlineLinkTextIndex : StringStubIndexExtension<MarkdownInlineLink>() {
  override fun getKey(): StubIndexKey<String, MarkdownInlineLink> = KEY

  companion object {
    @JvmField
    val KEY: StubIndexKey<String, MarkdownInlineLink> = StubIndexKey.createIndexKey("markdown.inlineLink")

    private val dispatcher = EventDispatcher.create<LinkIndexListener?>(LinkIndexListener::class.java)

    fun collectFileHeaders(text: String, project: Project, psiFile: PsiFile?): Collection<PsiElement> {
      val list = ArrayList<PsiElement>()
      StubIndex.getInstance().processElements(
        KEY,
        text,
        project,
        psiFile?.let { GlobalSearchScope.fileScope(it) },
        MarkdownInlineLink::class.java,
        CommonProcessors.CollectProcessor(list)
      )
      return list
    }

    fun addLinkFileListener(listener: LinkIndexListener) {
      dispatcher.addListener(listener);
    }

    fun indexChanged(ele : MarkdownInlineLinkStubElement){
      dispatcher.multicaster.indexChanged(ele)
    }

    fun removeLinkIndexListener(listener: LinkIndexListener) {
      dispatcher.removeListener(listener)
    }
  }
}
