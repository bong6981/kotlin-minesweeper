package domain.board

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import minesweeper.domain.board.EmptyBoard
import minesweeper.domain.board.Height2
import minesweeper.domain.board.MinePickedBoard
import minesweeper.domain.board.Width2
import minesweeper.domain.position.Position2

class MinePickedBoardTest : DescribeSpec({
    describe("지뢰 위치가 뽑힌 보드 생성") {
        context("빈보드와 지뢰 위치 추출기로 보드를 생성하면") {
            val emtpyBoard = EmptyBoard.of(Height2(2), Width2(3))
            val minePositions = setOf(Position2(0, 0), Position2(0, 1))
            val minePositionPicker = RandomMinePositionPickerMock(minePositions)

            val minePickedBoard = MinePickedBoard.of(emtpyBoard, minePositionPicker)

            it("지뢰가 선별된 보드의 전체 위치는 빈 보드의 전체 위치와 같다") {
                minePickedBoard.allPositions shouldBe emtpyBoard.positions
            }
            it("지뢰가 선별된 보드의 지뢰 위치는 지뢰 추출기가 추출한 위치와 같다") {
                minePickedBoard.minePositions shouldBe minePositions
            }
        }
    }
})
