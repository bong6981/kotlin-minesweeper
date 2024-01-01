package domain.board

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import minesweeper.domain.board.EmptyBoard
import minesweeper.domain.board.Height
import minesweeper.domain.board.Width
import minesweeper.domain.position.Position

class EmptyBoardTest : DescribeSpec({
    describe("빈 보드 생성") {
        context("높이와 너비가 주어지면") {
            val height = Height(2)
            val width = Width(3)

            it("0부터 (높이-1)과 0부터 (너비-1)까지의 위치를 갖는 빈 보드를 생성한다") {
                val board = EmptyBoard.of(height, width)

                board.positions shouldBe setOf(
                    Position(0, 0),
                    Position(0, 1),
                    Position(0, 2),
                    Position(1, 0),
                    Position(1, 1),
                    Position(1, 2),
                )
            }
        }
    }
})
