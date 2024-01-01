package minesweeper.controller

import minesweeper.domain.board.EmptyBoard
import minesweeper.domain.board.Height
import minesweeper.domain.board.Height2
import minesweeper.domain.board.MineBoard
import minesweeper.domain.board.MinePickedBoard
import minesweeper.domain.board.MineTotal
import minesweeper.domain.board.MineTotal2
import minesweeper.domain.board.Width
import minesweeper.domain.board.Width2
import minesweeper.domain.board.mineBoard
import minesweeper.domain.game.MinesweeperGame
import minesweeper.domain.position.Position
import minesweeper.domain.position.RandomMinePositionPicker
import minesweeper.domain.position.RandomPositionPicker

class MinesweeperController(
    private val inputProvider: InputProvider,
    private val outputProvider: OutputConsumer,
) {
    fun start() {
        val board = createBoard()
        val game = createGame(board)
        runGame(game)
    }

    private fun createBoard(): MineBoard {
        val height = inputProvider.height().let(::Height)
        val width = inputProvider.width().let(::Width)
        val mineCount = inputProvider.mineCount().let(::MineTotal)

        val height2 = inputProvider.height().let(::Height2)
        val width2 = inputProvider.width().let(::Width2)
        val emptyBoard = EmptyBoard.of(height2, width2)

        val mineCount2 = inputProvider.mineCount().let(::MineTotal2)
        val minePickedBoard = MinePickedBoard.of(emptyBoard, RandomMinePositionPicker(mineCount2))

        return mineBoard(RandomPositionPicker()) {
            size(width * height)
            mineCount(mineCount)
        }
    }

    private fun createGame(board: MineBoard): MinesweeperGame =
        MinesweeperGame(board) {
            inputProvider.openPosition().let {
                Position(
                    row = it.row,
                    column = it.column
                )
            }
        }

    private fun runGame(game: MinesweeperGame) {
        while (game.isEnd().not()) {
            game.run()
            showGame(game)
        }
    }

    private fun showGame(game: MinesweeperGame) {
        val result = game.result
        if (result != null) outputProvider.showGameResult(result)
        else outputProvider.showBoard(game.board)
    }
}
