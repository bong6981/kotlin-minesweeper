package minesweeper.domain.cell

import minesweeper.domain.position.Position2

sealed interface Cell2 {
    val position: Position2
    data class Mine(
        override val position: Position2
    ) : Cell2

    data class Clear(
        override val position: Position2,
        val nearMineCount: MineCount2,
    ) : Cell2 {
        var isOpened: Boolean = false
            private set

        val nearPositions: Set<Position2> = position.nearPositions

        fun open() {
            check(isOpened.not())
            isOpened = true
        }

        fun isMineCountZero(): Boolean = nearMineCount == MineCount2.ZERO
    }
}
