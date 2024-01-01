package minesweeper.domain.cell

import minesweeper.domain.board.MinePickedBoard
import minesweeper.domain.position.Position

object CellsFactory {
    fun from(minePickedBoard: MinePickedBoard): Map<Position, Cell> {
        val mineCountByAllPositions = minePickedBoard.mineCountByAllPosition
        val cells = mineCountByAllPositions.map { (position, count) ->
            if (minePickedBoard.isMine(position)) Cell.Mine(position)
            else Cell.Clear(position, MineCount.from(count))
        }
        return cells.associateBy { it.position }
    }
}
