package minesweeper.domain.game

import minesweeper.domain.board.MineBoard2
import minesweeper.domain.position.Position2

class MinesweeperGame2(
    val board: MineBoard2,
    var gameResult: GameResult? = null,
) {
    fun open(position: Position2): GameResult? {
        if (board.isMine(position)) {
            gameResult = GameResult.LOSS
            return gameResult
        }
        val cell = board.open(position)
        return null
    }
}
