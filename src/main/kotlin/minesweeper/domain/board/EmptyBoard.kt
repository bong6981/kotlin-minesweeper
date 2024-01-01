package minesweeper.domain.board

import minesweeper.domain.position.Position

@JvmInline
value class EmptyBoard private constructor(
    val positions: Set<Position>
) {
    companion object {
        fun of(height: Height, width: Width): EmptyBoard {
            val rowRange = 0 until height.value
            val columnRange = 0 until width.value

            val positions = rowRange.flatMap { row ->
                createPositionForColumnsInRow(row, columnRange)
            }.toSet()

            return EmptyBoard(positions)
        }

        private fun createPositionForColumnsInRow(row: Int, columnRange: IntRange): List<Position> =
            columnRange.map { column ->
                Position(
                    row = row,
                    column = column
                )
            }
    }
}
