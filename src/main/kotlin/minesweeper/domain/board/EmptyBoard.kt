package minesweeper.domain.board

import minesweeper.domain.position.Position2

@JvmInline
value class EmptyBoard private constructor(
    val positions: Set<Position2>
) {
    companion object {
        fun of(height: Height2, width: Width2): EmptyBoard {
            val rowRange = 0 until height.value
            val columnRange = 0 until width.value

            val positions = rowRange.flatMap { row ->
                createPositionForColumnsInRow(row, columnRange)
            }.toSet()

            return EmptyBoard(positions)
        }

        private fun createPositionForColumnsInRow(row: Int, columnRange: IntRange): List<Position2> =
            columnRange.map { column ->
                Position2(
                    row = row,
                    column = column
                )
            }
    }
}
