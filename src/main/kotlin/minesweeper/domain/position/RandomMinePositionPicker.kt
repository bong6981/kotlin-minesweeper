package minesweeper.domain.position

import minesweeper.domain.board.MineTotal

class RandomMinePositionPicker(
    override val mineCount: MineTotal,
) : MinePositionPicker {
    override fun pick(allPositions: Set<Position>): Set<Position> {
        require(allPositions.size >= mineCount.value) { "지뢰 개수는 전체 셀의 수 이하여야 합니다" }
        return allPositions.shuffled().take(mineCount.value).toSet()
    }
}
