package domain.game

import domain.board.MinePositionPickerMock
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import minesweeper.domain.board.EmptyBoard
import minesweeper.domain.board.Height2
import minesweeper.domain.board.MineBoard2
import minesweeper.domain.board.MinePickedBoard
import minesweeper.domain.board.Width2
import minesweeper.domain.game.GameResult
import minesweeper.domain.game.MinesweeperGame2
import minesweeper.domain.position.Position2

class MinesweeperGame2Test : DescribeSpec({
    describe("open()") {
        context("지뢰를 열면") {
            val board = MineBoard2.from(
                MinePickedBoard.of(
                    emptyBoard = EmptyBoard.of(Height2(2), Width2(2)),
                    minePicker = MinePositionPickerMock(
                        setOf(Position2(0, 0))
                    )
                )
            )

            val positionToOpen = Position2(0, 0)

            val gameResult = MinesweeperGame2(board).open(positionToOpen)

            it("게임 결과가 LOSS가 된다") {
                gameResult shouldBe GameResult.LOSS
            }
        }
        // 인접 지뢰가 다 열리거나
        // 그것만 열리거나
        // 모든 셀을 열어서 게임을 이기거나
    }
})
