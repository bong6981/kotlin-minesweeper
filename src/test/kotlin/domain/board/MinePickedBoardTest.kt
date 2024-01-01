package domain.board

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import minesweeper.domain.board.EmptyBoard
import minesweeper.domain.board.Height
import minesweeper.domain.board.MinePickedBoard
import minesweeper.domain.board.Width
import minesweeper.domain.position.Position

class MinePickedBoardTest : DescribeSpec({
    describe("지뢰 위치가 뽑힌 보드 생성") {
        context("빈보드와 지뢰 위치 추출기로 보드를 생성하면") {
            val emtpyBoard = EmptyBoard.of(Height(2), Width(3))
            val minePositions = setOf(Position(0, 0), Position(0, 1))
            val minePositionPicker = MinePositionPickerMock(minePositions)

            val minePickedBoard = MinePickedBoard.of(emtpyBoard, minePositionPicker)

            it("지뢰가 선별된 보드의 전체 위치는 빈 보드의 전체 위치와 같다") {
                minePickedBoard.allPositions shouldBe emtpyBoard.positions
            }
            it("지뢰가 선별된 보드의 지뢰 위치는 지뢰 추출기가 추출한 위치와 같다") {
                minePickedBoard.minePositions shouldBe minePositions
            }
        }
    }

    describe("위치별 인접 지뢰 수 반환") {
        /*
          (0) (0) (0)     (0)
          (0) (1) (1)     (1)
          (0) (2) (지뢰,1) (2)
          (0) (2) (지뢰,1) (2)
         */
        val minePickedBoard = MinePickedBoard.of(
            emptyBoard = EmptyBoard.of(Height(4), Width(4)),
            minePicker = MinePositionPickerMock(
                minePositions = setOf(
                    Position(2, 2),
                    Position(3, 2),
                )
            )
        )

        val result: Map<Position, Int> = minePickedBoard.mineCountByAllPosition

        result shouldBe mapOf(
            Position(0, 0) to 0,
            Position(0, 1) to 0,
            Position(0, 2) to 0,
            Position(0, 3) to 0,

            Position(1, 0) to 0,
            Position(1, 1) to 1,
            Position(1, 2) to 1,
            Position(1, 3) to 1,

            Position(2, 0) to 0,
            Position(2, 1) to 2,
            Position(2, 2) to 1,
            Position(2, 3) to 2,

            Position(3, 0) to 0,
            Position(3, 1) to 2,
            Position(3, 2) to 1,
            Position(3, 3) to 2,
        )
    }

    describe("지뢰 위치인지 확인") {
        val minePickedBoard = MinePickedBoard.of(
            emptyBoard = EmptyBoard.of(Height(2), Width(2)),
            minePicker = MinePositionPickerMock(
                minePositions = setOf(
                    Position(0, 0),
                )
            )
        )

        context("지뢰 위치라면") {
            val position = Position(0, 0)
            it("true가 반환된다") {
                val result = minePickedBoard.isMine(position)

                result shouldBe true
            }
        }

        context("지뢰 위치가 아니라면") {
            val position = Position(1, 1)
            it("false 반환된다") {
                val result = minePickedBoard.isMine(position)

                result shouldBe false
            }
        }
    }
})
