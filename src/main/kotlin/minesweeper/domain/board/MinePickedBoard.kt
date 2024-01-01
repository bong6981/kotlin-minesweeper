package minesweeper.domain.board

import minesweeper.domain.position.MinePositionPicker
import minesweeper.domain.position.Position2

data class MinePickedBoard(
    val allPositions: Set<Position2>,
    val minePositions: Set<Position2>
) {
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
