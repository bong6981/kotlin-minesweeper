package minesweeper.domain.cell

import minesweeper.domain.position.Position2

interface Cell2 {
    val position: Position2
    data class Mine(
        override val position: Position2
    ) : Cell2

    data class Clear(
        override val position: Position2,
        val nearMineCount: MineCount2,
    ) : Cell2
}
