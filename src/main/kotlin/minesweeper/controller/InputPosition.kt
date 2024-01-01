package minesweeper.controller

import minesweeper.domain.position.Position2

data class InputPosition(
    val row: Int,
    val column: Int,
) {
    companion object {
        fun InputPosition.toPosition(): Position2 = Position2(row, column)
    }
}
