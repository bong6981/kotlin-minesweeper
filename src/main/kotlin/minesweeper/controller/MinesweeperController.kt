package minesweeper.controller

import minesweeper.controller.InputPosition.Companion.toPosition
import minesweeper.domain.board.EmptyBoard
import minesweeper.domain.board.Height
import minesweeper.domain.board.Height2
import minesweeper.domain.board.MineBoard
import minesweeper.domain.board.MineBoard2
import minesweeper.domain.board.MinePickedBoard
import minesweeper.domain.board.MineTotal
import minesweeper.domain.board.MineTotal2
import minesweeper.domain.board.Width
import minesweeper.domain.board.Width2
import minesweeper.domain.board.mineBoard
import minesweeper.domain.game.MinesweeperGame
import minesweeper.domain.game.MinesweeperGame2
import minesweeper.domain.position.Position
import minesweeper.domain.position.RandomMinePositionPicker
import minesweeper.domain.position.RandomPositionPicker

class MinesweeperController(
    private val inputProvider: InputProvider,
    private val outputProvider: OutputConsumer,
) {
    fun start() {
        val board = createBoard2()
        val game = MinesweeperGame2(board)
        game.open(inputProvider.positionToOpen().toPosition())
    }

    private fun createBoard(): MineBoard {
        val height = inputProvider.height().let(::Height)
        val width = inputProvider.width().let(::Width)
        val mineCount = inputProvider.mineCount().let(::MineTotal)

        return mineBoard(RandomPositionPicker()) {
            size(width * height)
            mineCount(mineCount)
        }
    }

    private fun createBoard2(): MineBoard2 {
        val height = inputProvider.height().let(::Height2)
        val width = inputProvider.width().let(::Width2)
        val emptyBoard = EmptyBoard.of(height, width)

        val mineCount = inputProvider.mineCount().let(::MineTotal2)
        val minePickedBoard = MinePickedBoard.of(emptyBoard, RandomMinePositionPicker(mineCount))
        return MineBoard2.from(minePickedBoard)
    }

    private fun createGame(board: MineBoard): MinesweeperGame =
        MinesweeperGame(board) {
            inputProvider.positionToOpen().let {
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
