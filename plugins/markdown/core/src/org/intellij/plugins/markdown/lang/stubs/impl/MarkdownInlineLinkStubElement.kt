package org.intellij.plugins.markdown.lang.stubs.impl

import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.StubElement
import org.intellij.plugins.markdown.lang.psi.impl.MarkdownInlineLink
import org.intellij.plugins.markdown.lang.stubs.MarkdownStubElementBase

class MarkdownInlineLinkStubElement(
  parent: StubElement<*>,
  elementType: IStubElementType<*, *>,
  val indexedName: String?
) : MarkdownStubElementBase<MarkdownInlineLink?>(parent, elementType)
