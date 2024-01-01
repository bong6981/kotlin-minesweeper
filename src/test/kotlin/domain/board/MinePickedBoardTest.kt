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
            emptyBoard = EmptyBoard.of(Height2(4), Width2(4)),
            minePicker = MinePositionPickerMock(
                minePositions = setOf(
                    Position2(2, 2),
                    Position2(3, 2),
                )
            )
        )

        val result: Map<Position2, Int> = minePickedBoard.mineCountByAllPosition

        result shouldBe mapOf(
            Position2(0, 0) to 0,
            Position2(0, 1) to 0,
            Position2(0, 2) to 0,
            Position2(0, 3) to 0,

            Position2(1, 0) to 0,
            Position2(1, 1) to 1,
            Position2(1, 2) to 1,
            Position2(1, 3) to 1,

            Position2(2, 0) to 0,
            Position2(2, 1) to 2,
            Position2(2, 2) to 1,
            Position2(2, 3) to 2,

            Position2(3, 0) to 0,
            Position2(3, 1) to 2,
            Position2(3, 2) to 1,
            Position2(3, 3) to 2,
        )
    }

    describe("지뢰 위치인지 확인") {
        val minePickedBoard = MinePickedBoard.of(
            emptyBoard = EmptyBoard.of(Height2(2), Width2(2)),
            minePicker = MinePositionPickerMock(
                minePositions = setOf(
                    Position2(0, 0),
                )
            )
        )

        context("지뢰 위치라면") {
            val position = Position2(0, 0)
            it("true가 반환된다") {
                val result = minePickedBoard.isMine(position)

                result shouldBe true
            }
        }

        context("지뢰 위치가 아니라면") {
            val position = Position2(1, 1)
            it("false 반환된다") {
                val result = minePickedBoard.isMine(position)

                result shouldBe false
            }
        }
    }
})
