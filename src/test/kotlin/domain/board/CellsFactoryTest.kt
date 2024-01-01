package domain.board

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import minesweeper.domain.board.CellsFactory
import minesweeper.domain.board.EmptyBoard
import minesweeper.domain.board.Height2
import minesweeper.domain.board.MinePickedBoard
import minesweeper.domain.board.Width2
import minesweeper.domain.cell.Cell2
import minesweeper.domain.position.Position2

class CellsFactoryTest : DescribeSpec({
    describe("모든 셀 생성") {
        /*
        (지뢰) (1) (0)
        (1)   (1) (0)
        (0)   (0) (0)
       */
        context("MinePickedBoard에 대한 셀을 생성하면") {
            val minePickedBoard = MinePickedBoard.of(
                emptyBoard = EmptyBoard.of(Height2(3), Width2(3)),
                minePicker = MinePositionPickerMock(
                    minePositions = setOf(
                        Position2(0, 0),
                    )
                )
            )
            val mineCountByAllPosition = minePickedBoard.mineCountByAllPosition
            mineCountByAllPosition shouldBe mapOf(
                Position2(0, 0) to 0,
                Position2(0, 1) to 1,
                Position2(0, 2) to 0,

                Position2(1, 0) to 1,
                Position2(1, 1) to 1,
                Position2(1, 2) to 0,

                Position2(2, 0) to 0,
                Position2(2, 1) to 0,
                Position2(2, 2) to 0,
            )

            val cells = CellsFactory.from(minePickedBoard)

            it("MinePickedBoard에 정의된 지뢰 위치는 지뢰 셀로 생성된다") {
                cells.values
                    .filterIsInstance<Cell2.Mine>() shouldBe setOf(Cell2.Mine(Position2(0, 0)))
            }

            it("MinePickedBoard에 정의된 지뢰가 아닌 위치는 일반 셀로 생성된다") {
                cells.values
                    .filterNot { it.position == Position2(0, 0) }
                    .forEach { cell -> cell.shouldBeTypeOf<Cell2.Clear>() }
            }
            it("일반 셀은 MinePickedBoard에서 반환한 인접 지뢰 수를 가진다") {
                val mineCountByClearPosition = mineCountByAllPosition.filterNot { it.key == Position2(0, 0) }

                mineCountByClearPosition.forEach { (position, count) ->
                    val clearCell = cells.values.firstOrNull { it.position == position }
                    clearCell.shouldBeTypeOf<Cell2.Clear>()
                    clearCell.nearMineCount.value shouldBe count
                }
            }
        }
    }
})
