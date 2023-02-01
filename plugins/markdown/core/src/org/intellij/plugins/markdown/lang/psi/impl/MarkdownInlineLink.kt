package org.intellij.plugins.markdown.lang.psi.impl

import com.intellij.lang.ASTNode
import org.intellij.plugins.markdown.lang.stubs.MarkdownStubBasedPsiElementBase
import org.intellij.plugins.markdown.lang.stubs.MarkdownStubElement
import org.intellij.plugins.markdown.lang.stubs.impl.MarkdownInlineLinkStubElement
import org.intellij.plugins.markdown.lang.stubs.impl.MarkdownInlineLinkStubElementType
import org.jetbrains.annotations.ApiStatus

@ApiStatus.Experimental
// todo Markdown header 实现了 PsiExternalReferenceHost 接口, 需要看下 InlineLink 是否需要实现这个接口
class MarkdownInlineLink : MarkdownStubBasedPsiElementBase<MarkdownStubElement<*>>,MarkdownLink {
  constructor(node: ASTNode) : super(node)
  constructor(stub: MarkdownInlineLinkStubElement, type: MarkdownInlineLinkStubElementType) : super(stub, type)

}
