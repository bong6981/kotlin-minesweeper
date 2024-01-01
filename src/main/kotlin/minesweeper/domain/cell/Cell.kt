package minesweeper.domain.cell

import minesweeper.domain.position.Position

sealed interface Cell {
    val position: Position
    data class Mine(
        override val position: Position
    ) : Cell

    data class Clear(
        override val position: Position,
        val nearMineCount: MineCount,
    ) : Cell {
        var isOpened: Boolean = false
            private set

        val nearPositions: Set<Position> = position.nearPositions

        fun open() {
            check(isOpened.not())
            isOpened = true
        }

        fun isMineCountZero(): Boolean = nearMineCount == MineCount.ZERO
    }
}
