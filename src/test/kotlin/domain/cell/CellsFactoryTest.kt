package domain.cell

import domain.board.MinePositionPickerMock
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import minesweeper.domain.board.EmptyBoard
import minesweeper.domain.board.Height
import minesweeper.domain.board.MinePickedBoard
import minesweeper.domain.board.Width
import minesweeper.domain.cell.Cell
import minesweeper.domain.cell.CellsFactory
import minesweeper.domain.position.Position

class CellsFactoryTest : DescribeSpec({
    describe("모든 셀 생성") {
        /*
        (지뢰) (1) (0)
        (1)   (1) (0)
        (0)   (0) (0)
       */
        context("MinePickedBoard에 대한 셀을 생성하면") {
            val minePickedBoard = MinePickedBoard.of(
                emptyBoard = EmptyBoard.of(Height(3), Width(3)),
                minePicker = MinePositionPickerMock(
                    minePositions = setOf(
                        Position(0, 0),
                    )
                )
            )
            val mineCountByAllPosition = minePickedBoard.mineCountByAllPosition
            mineCountByAllPosition shouldBe mapOf(
                Position(0, 0) to 0,
                Position(0, 1) to 1,
                Position(0, 2) to 0,

                Position(1, 0) to 1,
                Position(1, 1) to 1,
                Position(1, 2) to 0,

                Position(2, 0) to 0,
                Position(2, 1) to 0,
                Position(2, 2) to 0,
            )

            val cells = CellsFactory.from(minePickedBoard)

            it("MinePickedBoard에 정의된 지뢰 위치는 지뢰 셀로 생성된다") {
                cells.values
                    .filterIsInstance<Cell.Mine>() shouldBe setOf(Cell.Mine(Position(0, 0)))
            }

            it("MinePickedBoard에 정의된 지뢰가 아닌 위치는 일반 셀로 생성된다") {
                cells.values
                    .filterNot { it.position == Position(0, 0) }
                    .forEach { cell -> cell.shouldBeTypeOf<Cell.Clear>() }
            }
            it("일반 셀은 MinePickedBoard에서 반환한 인접 지뢰 수를 가진다") {
                val mineCountByClearPosition = mineCountByAllPosition.filterNot { it.key == Position(0, 0) }

                mineCountByClearPosition.forEach { (position, count) ->
                    val clearCell = cells.values.firstOrNull { it.position == position }
                    clearCell.shouldBeTypeOf<Cell.Clear>()
                    clearCell.nearMineCount.value shouldBe count
                }
            }
        }
    }
})
