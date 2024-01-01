package minesweeper.domain.position

import minesweeper.domain.board.MineTotal2

class RandomMinePositionPicker(
    override val mineCount: MineTotal2,
) : MinePositionPicker {
    override fun pick(allPositions: Set<Position2>): Set<Position2> {
        require(allPositions.size >= mineCount.value) { "지뢰 개수는 전체 셀의 수 이하여야 합니다" }
        return allPositions.shuffled().take(mineCount.value).toSet()
    }
}
