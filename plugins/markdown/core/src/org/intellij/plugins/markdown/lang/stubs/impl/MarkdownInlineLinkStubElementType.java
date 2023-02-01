package org.intellij.plugins.markdown.lang.stubs.impl;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.stubs.IndexSink;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import org.intellij.plugins.markdown.lang.index.InlineLinkTextIndex;
import org.intellij.plugins.markdown.lang.psi.impl.MarkdownInlineLink;
import org.intellij.plugins.markdown.lang.stubs.MarkdownStubElementType;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class MarkdownInlineLinkStubElementType extends MarkdownStubElementType<MarkdownInlineLinkStubElement, MarkdownInlineLink> {
  private static final Logger LOG = Logger.getInstance(MarkdownInlineLinkStubElementType.class);

  public MarkdownInlineLinkStubElementType(@NotNull String debugName) {
    super(debugName);
  }

  @NotNull
  @Override
  public PsiElement createElement(@NotNull ASTNode node) {
    return new MarkdownInlineLink(node);
  }

  @Override
  public MarkdownInlineLink createPsi(@NotNull MarkdownInlineLinkStubElement stub) {
    return new MarkdownInlineLink(stub, this);
  }

  @NotNull
  @Override
  public MarkdownInlineLinkStubElement createStub(@NotNull MarkdownInlineLink psi, StubElement parentStub) {
    return new MarkdownInlineLinkStubElement(parentStub, this, psi.getText());
  }

  @Override
  public void serialize(@NotNull MarkdownInlineLinkStubElement stub, @NotNull StubOutputStream dataStream) throws IOException {
    writeUTFFast(dataStream, stub.getIndexedName());
  }

  @NotNull
  @Override
  public MarkdownInlineLinkStubElement deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) {
    String indexedName = null;
    try {
      indexedName = dataStream.readUTFFast();
    }
    catch (IOException e) {
      LOG.error("Cannot read data stream; ", e.getMessage());
    }

    final var actualName = StringUtil.isEmpty(indexedName) ? null : indexedName;
    return new MarkdownInlineLinkStubElement(
      parentStub,
      this,
      actualName
    );
  }

  @Override
  public void indexStub(@NotNull MarkdownInlineLinkStubElement stub, @NotNull IndexSink sink) {
    final var indexedName = stub.getIndexedName();
    if (indexedName != null) {
      sink.occurrence(InlineLinkTextIndex.KEY, indexedName);
      InlineLinkTextIndex.Companion.indexChanged(stub);
    }
  }

  private static void writeUTFFast(@NotNull StubOutputStream dataStream, String text) throws IOException {
    if (text == null) text = "";
    dataStream.writeUTFFast(text);
  }
}
