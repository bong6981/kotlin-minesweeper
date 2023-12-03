package minesweeper.domain

import minesweeper.domain.cell.Position

interface PositionPicker {
    fun pick(allPositions: Set<Position>, count: Int): Set<Position>
}