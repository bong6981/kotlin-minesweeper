package minesweeper.domain.game

import minesweeper.domain.board.MineBoard
import minesweeper.domain.cell.Cell
import minesweeper.domain.cell.MineCount
import minesweeper.domain.position.Position

class MinesweeperGame(
    val board: MineBoard,
    private var gameResult: GameResult? = null,
) {
    fun open(position: Position): GameResult? {
        if (board.isMine(position)) {
            gameResult = GameResult.LOSS
            return gameResult
        }
        doOpen(position)
        if (board.isAllOpened()) {
            gameResult = GameResult.WIN
            return gameResult
        }
        return null
    }

    fun isEnd(): Boolean = gameResult != null

    private fun doOpen(position: Position) {
        val cell = board.open(position)
        openNearPositionsIfZero(cell)
    }

    private fun openNearPositionsIfZero(cell: Cell.Clear) {
        if (cell.nearMineCount != MineCount.ZERO) return

        cell.nearPositions.forEach { position ->
            if (board.canOpen(position)) {
                val openedCell = board.open(position)
                openNearPositionsIfZero(openedCell)
            }
        }
    }
}
