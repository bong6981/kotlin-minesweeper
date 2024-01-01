package minesweeper.domain.board

import minesweeper.domain.cell.Cell2
import minesweeper.domain.position.Position2

class MineBoard2 private constructor(
    val cells: Map<Position2, Cell2>,
) {
    companion object {

        fun from(minePickedBoard: MinePickedBoard): MineBoard2 {
            val cells = CellsFactory.from(minePickedBoard)
            return MineBoard2(cells)
        }
    }
}
