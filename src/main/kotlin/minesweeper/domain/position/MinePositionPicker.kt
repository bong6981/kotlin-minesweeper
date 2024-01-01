package minesweeper.domain.position

import minesweeper.domain.board.MineTotal

interface MinePositionPicker {
    val mineCount: MineTotal
    fun pick(allPositions: Set<Position>): Set<Position>
}
