// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package org.intellij.plugins.markdown.view

import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import org.intellij.plugins.markdown.lang.psi.impl.MarkdownLinkDestination
import org.intellij.plugins.markdown.lang.psi.impl.MarkdownLinkText

class Node(val text: MarkdownLinkText, val destination: MarkdownLinkDestination, val file: VirtualFile) {

  var project: Project = text.project

  val descriptor: OpenFileDescriptor

  val anchorAttributes = mutableMapOf<String, String>()

  var line: Int = 0

  init {
    destination.text?.substringAfter("#")?.let { parseAnchor(it) }
    this.line = anchorAttributes["L"]?.let { Integer.valueOf(it) - 1 } ?: 0
    this.descriptor = OpenFileDescriptor(text.project, file, this.line, 0)
  }

  private fun parseAnchor(anchor: String) {
    val split = anchor.split("&")
    for (s in split) {
      val equalPairs = s.split("=")
      when (equalPairs.size) {
        1 -> {
          parseSingleAnchor(s, anchorAttributes)
        }
        2 -> {
          anchorAttributes[equalPairs[0]] = equalPairs[1]
        }
        else -> {}
      }
    }
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Node

    if (file != other.file) return false
    if (project != other.project) return false
    if (line != other.line) return false

    return true
  }

  override fun hashCode(): Int {
    var result = file.hashCode()
    result = 31 * result + project.hashCode()
    result = 31 * result + line
    return result
  }

  companion object {
    fun parseSingleAnchor(s: String, anchorAttributes: MutableMap<String, String>) {
      var lastLetter = 0
      for ((index, c) in s.withIndex()) {
        if (c.isLetter()) {
          lastLetter = index
        }
      }
      if (lastLetter == s.length - 1) {
        anchorAttributes[s] = s
      }
      else {
        anchorAttributes[s.substring(0, lastLetter + 1)] = s.substring(lastLetter + 1)
      }
    }

  }



}

//fun main() {
//  var s = "L2d1"
//  val map = mutableMapOf<String, String>()
//  parseSingleAnchor(s, map)
//
//  for (entry in map) {
//    println(entry.key)
//    println(entry.value);
//  }
//}
