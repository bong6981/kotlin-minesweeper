package minesweeper.domain.position

import minesweeper.domain.board.MineTotal2

interface MinePositionPicker {
    val mineCount: MineTotal2
    fun pick(allPositions: Set<Position2>): Set<Position2>
}
