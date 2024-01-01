package domain.board

import minesweeper.domain.board.MineTotal
import minesweeper.domain.position.MinePositionPicker
import minesweeper.domain.position.Position

class MinePositionPickerMock(
    private val minePositions: Set<Position>,
    override val mineCount: MineTotal = MineTotal(minePositions.size)
) : MinePositionPicker {
    override fun pick(allPositions: Set<Position>): Set<Position> = minePositions
}
