package minesweeper.domain.board

import minesweeper.domain.cell.Cell
import minesweeper.domain.cell.CellsFactory
import minesweeper.domain.position.Position

class MineBoard private constructor(
    val cells: Map<Position, Cell>,
) {
    fun open(position: Position): Cell.Clear {
        val cell = findCell(position)
        check(cell is Cell.Clear) { "지뢰를 열수 없습니다" }
        cell.open()
        return cell
    }

    fun isMine(position: Position): Boolean =
        findCell(position) is Cell.Mine

    fun canOpen(position: Position): Boolean {
        val cell = cells[position] ?: return false
        return when (cell) {
            is Cell.Mine -> false
            is Cell.Clear -> cell.isOpened.not()
        }
    }

    fun isAllOpened(): Boolean =
        cells.values.none { it is Cell.Clear && it.isOpened.not() }

    private fun findCell(position: Position): Cell =
        cells[position] ?: throw IllegalArgumentException("보드 내에 정의된 셀이 아닙니다")

    companion object {
        fun from(minePickedBoard: MinePickedBoard): MineBoard {
            val cells = CellsFactory.from(minePickedBoard)
            return MineBoard(cells)
        }
    }
}
