package minesweeper.domain.board

import minesweeper.domain.cell.Cell2
import minesweeper.domain.cell.MineCount2
import minesweeper.domain.position.Position2

object CellsFactory {
    fun from(minePickedBoard: MinePickedBoard): Map<Position2, Cell2> {
        val mineCountByAllPositions = minePickedBoard.mineCountByAllPosition
        val cells = mineCountByAllPositions.map { (position, count) ->
            if (minePickedBoard.isMine(position)) Cell2.Mine(position)
            else Cell2.Clear(position, MineCount2.from(count))
        }
        return cells.associateBy { it.position }
    }
}
