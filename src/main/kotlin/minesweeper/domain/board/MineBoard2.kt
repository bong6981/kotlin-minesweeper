package minesweeper.domain.board

import minesweeper.domain.cell.Cell2
import minesweeper.domain.position.Position2

class MineBoard2 private constructor(
    val cells: Map<Position2, Cell2>,
) {
    fun open(position: Position2): Cell2.Clear {
        val cell = findCell(position)
        check(cell is Cell2.Clear) { "지뢰를 열수 없습니다" }
        cell.open()
        return cell
    }

    fun isMine(position: Position2): Boolean =
        findCell(position) is Cell2.Mine

    fun canOpen(position: Position2): Boolean {
        val cell = cells[position] ?: return false
        return when (cell) {
            is Cell2.Mine -> false
            is Cell2.Clear -> cell.isOpened.not()
        }
    }

    fun isAllOpened(): Boolean =
        cells.values.none { it is Cell2.Clear && it.isOpened.not() }

    private fun findCell(position: Position2): Cell2 =
        cells[position] ?: throw IllegalArgumentException("보드 내에 정의된 셀이 아닙니다")

    companion object {
        fun from(minePickedBoard: MinePickedBoard): MineBoard2 {
            val cells = CellsFactory.from(minePickedBoard)
            return MineBoard2(cells)
        }
    }
}
