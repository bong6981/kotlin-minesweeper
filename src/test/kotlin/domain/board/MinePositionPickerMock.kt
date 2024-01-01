package domain.board

import minesweeper.domain.board.MineTotal2
import minesweeper.domain.position.MinePositionPicker
import minesweeper.domain.position.Position2

class MinePositionPickerMock(
    private val minePositions: Set<Position2>,
    override val mineCount: MineTotal2 = MineTotal2(minePositions.size)
) : MinePositionPicker {
    override fun pick(allPositions: Set<Position2>): Set<Position2> = minePositions
}
