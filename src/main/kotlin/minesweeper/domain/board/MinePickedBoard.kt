package minesweeper.domain.board

import minesweeper.domain.position.MinePositionPicker
import minesweeper.domain.position.Position2

class MinePickedBoard private constructor(
    val allPositions: Set<Position2>,
    val minePositions: Set<Position2>
) {

    val mineCountByAllPosition: Map<Position2, Int> by lazy {
        val nearMineCountByPosition = minePositions
            .flatMap { it.nearPositions }
            .filter { it in allPositions }
            .groupBy { it }
            .mapValues { it.value.size }

        allPositions.associateWith { nearMineCountByPosition[it] ?: 0 }
    }

    fun isMine(position2: Position2) = position2 in minePositions

    companion object {
        fun of(emptyBoard: EmptyBoard, minePicker: MinePositionPicker): MinePickedBoard {
            val allPositions = emptyBoard.positions
            val minePositions = minePicker.pick(allPositions)
            return MinePickedBoard(
                allPositions = allPositions,
                minePositions = minePositions,
            )
        }
    }
}
