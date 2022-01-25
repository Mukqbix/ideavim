/*
 * IdeaVim - Vim emulator for IDEs based on the IntelliJ platform
 * Copyright (C) 2003-2022 The IdeaVim authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package com.maddyhome.idea.vim.action.motion.leftright

import com.maddyhome.idea.vim.api.ExecutionContext
import com.maddyhome.idea.vim.api.VimCaret
import com.maddyhome.idea.vim.api.VimEditor
import com.maddyhome.idea.vim.command.Argument
import com.maddyhome.idea.vim.command.MotionType
import com.maddyhome.idea.vim.command.OperatorArguments
import com.maddyhome.idea.vim.handler.Motion
import com.maddyhome.idea.vim.handler.MotionActionHandler
import com.maddyhome.idea.vim.handler.toMotionOrError
import kotlin.math.max
import kotlin.math.min

class MotionLeftWrapAction : MotionActionHandler.ForEachCaret() {
  override fun getOffset(
    editor: VimEditor,
    caret: VimCaret,
    context: ExecutionContext,
    argument: Argument?,
    operatorArguments: OperatorArguments,
  ): Motion {
    val moveCaretHorizontalWrap = moveCaretHorizontalWrap(editor, caret, -operatorArguments.count1)
    return if (moveCaretHorizontalWrap < 0) Motion.Error else Motion.AbsoluteOffset(moveCaretHorizontalWrap)
  }

  override val motionType: MotionType = MotionType.EXCLUSIVE
}

class MotionRightWrapAction : MotionActionHandler.ForEachCaret() {
  override fun getOffset(
    editor: VimEditor,
    caret: VimCaret,
    context: ExecutionContext,
    argument: Argument?,
    operatorArguments: OperatorArguments,
  ): Motion {
    return moveCaretHorizontalWrap(editor, caret, operatorArguments.count1).toMotionOrError()
  }

  override val motionType: MotionType = MotionType.EXCLUSIVE
}

fun moveCaretHorizontalWrap(editor: VimEditor, caret: VimCaret, count: Int): Int {
  // FIX - allows cursor over newlines
  val oldOffset = caret.offset.point
  val offset = min(max(0, caret.offset.point + count), editor.fileSize().toInt())
  return if (offset == oldOffset) {
    -1
  } else {
    offset
  }
}