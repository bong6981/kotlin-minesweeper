import minesweeper.domain.board.Positions
import minesweeper.domain.cell.Cell
import minesweeper.domain.cell.CellMark
import minesweeper.domain.cell.Position

fun Positions(row: Int, column: Int, minePositions: Set<Position>? = null): Positions {
    val allPositions = (0 until row).flatMap { row ->
        (0 until column).map { column ->
            Position(row, column)
        }
    }.toSet()
    minePositions?.forEach { require(it in allPositions) }
    val positions = Positions(value = allPositions)
    minePositions?.run { positions.pickMines(this) }
    return positions
}

fun Cell(
    position: Position = Position(0, 0),
    mark: CellMark = CellMark.ZERO,
    isOpened: Boolean = false,
): Cell {
    val cell = Cell(position, mark)
    if (isOpened) cell.open()
    return cell
}
