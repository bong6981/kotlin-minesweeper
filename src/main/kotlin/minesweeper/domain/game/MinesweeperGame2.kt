package minesweeper.domain.game

import minesweeper.domain.board.MineBoard2
import minesweeper.domain.cell.Cell2
import minesweeper.domain.cell.MineCount2
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
        openNearPositionsIfZero(cell)
        if (board.isAllOpened()) {
            gameResult = GameResult.WIN
            return gameResult
        }
        return null
    }

    private fun openNearPositionsIfZero(cell: Cell2.Clear) {
        if (cell.nearMineCount != MineCount2.ZERO) return

        cell.nearPositions.forEach { position ->
            if (board.canOpen(position)) {
                println("hello $cell $position")
                val openedCell = board.open(position)
                openNearPositionsIfZero(openedCell)
            }
        }
    }
}
