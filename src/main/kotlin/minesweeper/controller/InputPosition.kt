package minesweeper.controller

import minesweeper.domain.position.Position

data class InputPosition(
    val row: Int,
    val column: Int,
) {
    companion object {
        fun InputPosition.toPosition(): Position = Position(row, column)
    }
}
