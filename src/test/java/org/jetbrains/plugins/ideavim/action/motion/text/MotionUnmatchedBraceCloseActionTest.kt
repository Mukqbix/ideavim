/*
 * Copyright 2003-2023 The IdeaVim authors
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE.txt file or at
 * https://opensource.org/licenses/MIT.
 */

package org.jetbrains.plugins.ideavim.action.motion.text

import com.maddyhome.idea.vim.command.VimStateMachine
import org.jetbrains.plugins.ideavim.VimTestCase
import org.junit.jupiter.api.Test

class MotionUnmatchedBraceCloseActionTest : VimTestCase() {
  @Test
  fun `test go to bracket`() {
    doTest(
      "]}",
      """
      int main() {
        $c
      }
      """.trimIndent(),
      """
      int main() {
        
      $c}
      """.trimIndent(),
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
    )
  }

  @Test
  fun `test go to next bracket`() {
    doTest(
      "]}",
      """
      class Xxx {
        int main() {
          $c
        }
      }
      """.trimIndent(),
      """
      class Xxx {
        int main() {
          
        $c}
      }
      """.trimIndent(),
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
    )
  }

  @Test
  fun `test go to next bracket with quotes`() {
    doTest(
      "]}",
      """
      class Xxx {
        int main() {
          $c
          String  x = "}"
        }
      }
      """.trimIndent(),
      """
      class Xxx {
        int main() {
          
          String  x = "}"
        $c}
      }
      """.trimIndent(),
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
    )
  }

  @Test
  fun `test go to next next bracket`() {
    doTest(
      "]}]}",
      """
      class Xxx {
        int main() {
          $c
        }
      }
      """.trimIndent(),
      """
      class Xxx {
        int main() {
          
        }
      $c}
      """.trimIndent(),
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
    )
  }

  @Test
  fun `test go to next next bracket with count`() {
    doTest(
      "2]}",
      """
      class Xxx {
        int main() {
          $c
        }
      }
      """.trimIndent(),
      """
      class Xxx {
        int main() {
          
        }
      $c}
      """.trimIndent(),
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
    )
  }

  @Test
  fun `test go to next next bracket with great count`() {
    doTest(
      "5]}",
      """
      class Xxx {
        int main() {
          $c
        }
      }
      """.trimIndent(),
      """
      class Xxx {
        int main() {
          
        }
      $c}
      """.trimIndent(),
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
    )
  }

  @Test
  fun `test go to next bracket multiple brackets`() {
    doTest(
      "]}",
      """ {$c {}} """,
      """ { {}$c} """,
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
    )
  }
}
