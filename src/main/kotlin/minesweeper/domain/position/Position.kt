package minesweeper.domain.position

data class Position(
    val row: Int,
    val column: Int,
) {
    init {
        require(row >= MIN_ROW) { "위치의 행 값은 ${MIN_ROW}이상이어야 합니다" }
        require(column >= MIN_COLUMN) { "위치의 열 값은 ${MIN_COLUMN}이상이어야 합니다" }
    }

    val nearPositions: Set<Position>
        get() = setOfNotNull(
            createIfValid(row = row - 1, column = column - 1),
            createIfValid(row = row - 1, column = column),
            createIfValid(row = row - 1, column = column + 1),
            createIfValid(row = row, column = column - 1),
            createIfValid(row = row, column = column + 1),
            createIfValid(row = row + 1, column = column - 1),
            createIfValid(row = row + 1, column = column),
            createIfValid(row = row + 1, column = column + 1),
        )

    companion object {
        private const val MIN_ROW = 0
        private const val MIN_COLUMN = 0
        fun createIfValid(row: Int, column: Int): Position? =
            if (isValid(row, column)) Position(row, column)
            else null

        private fun isValid(row: Int, column: Int): Boolean {
            return row >= MIN_ROW && column >= MIN_COLUMN
        }
    }
}
