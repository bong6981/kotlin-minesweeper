package minesweeper.controller

import minesweeper.controller.InputPosition.Companion.toPosition
import minesweeper.domain.board.EmptyBoard
import minesweeper.domain.board.Height
import minesweeper.domain.board.MineBoard
import minesweeper.domain.board.MinePickedBoard
import minesweeper.domain.board.MineTotal
import minesweeper.domain.board.Width
import minesweeper.domain.game.GameResult
import minesweeper.domain.game.MinesweeperGame
import minesweeper.domain.position.RandomMinePositionPicker

class MinesweeperController(
    private val inputProvider: InputProvider,
    private val outputProvider: OutputConsumer,
) {
    fun start() {
        val board = createBoard2()
        val game = MinesweeperGame(board)
        runGame(game)
    }

    private fun createBoard2(): MineBoard {
        val height = inputProvider.height().let(::Height)
        val width = inputProvider.width().let(::Width)
        val emptyBoard = EmptyBoard.of(height, width)

        val mineCount = inputProvider.mineCount().let(::MineTotal)
        val minePickedBoard = MinePickedBoard.of(emptyBoard, RandomMinePositionPicker(mineCount))
        return MineBoard.from(minePickedBoard)
    }

    private fun runGame(game: MinesweeperGame) {
        while (game.isEnd().not()) {
            val result = game.open(inputProvider.positionToOpen().toPosition())
            showGame(game, result)
        }
    }

    private fun showGame(game: MinesweeperGame, gameResult: GameResult?) {
        if (gameResult != null) outputProvider.showGameResult(gameResult)
        else outputProvider.showBoard(game.board)
    }
}
