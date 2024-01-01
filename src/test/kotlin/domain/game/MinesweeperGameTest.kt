package domain.game

import domain.board.MinePositionPickerMock
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import minesweeper.domain.board.EmptyBoard
import minesweeper.domain.board.Height
import minesweeper.domain.board.MineBoard
import minesweeper.domain.board.MinePickedBoard
import minesweeper.domain.board.Width
import minesweeper.domain.cell.Cell
import minesweeper.domain.game.GameResult
import minesweeper.domain.game.MinesweeperGame
import minesweeper.domain.position.Position

class MinesweeperGameTest : DescribeSpec({
    describe("셀 오픈") {
        context("지뢰를 열면") {
            val board = MineBoard.from(
                MinePickedBoard.of(
                    emptyBoard = EmptyBoard.of(Height(2), Width(2)),
                    minePicker = MinePositionPickerMock(
                        setOf(Position(0, 0))
                    )
                )
            )

            val positionToOpen = Position(0, 0)

            val gameResult = MinesweeperGame(board).open(positionToOpen)

            it("게임 결과가 LOSS가 된다") {
                gameResult shouldBe GameResult.LOSS
            }
        }

        context("인접 지뢰수가 0인 일반 셀을 열면") {
            /*
            (0) (0) (1) (1)
            (0) (1) (2) (*)
            (1) (2) (*) (2)
            (*) (2) (1) (1)
            */
            /*
            (열림) (열림) (열림) (1)
            (열림) (열림) (열림) (*)
            (열림) (열림) (*)   (2)
            (*)   (2)   (1)   (1)
             */
            val board = MineBoard.from(
                MinePickedBoard.of(
                    emptyBoard = EmptyBoard.of(Height(4), Width(4)),
                    minePicker = MinePositionPickerMock(
                        setOf(
                            Position(1, 3),
                            Position(2, 2),
                            Position(3, 0),
                        )
                    )
                )
            )
            val positionToOpen = Position(0, 0)

            val result = MinesweeperGame(board).open(positionToOpen)

            /*
            (열림) (열림) (열림) (1)
            (열림) (열림) (열림) (*)
            (열림) (열림) (*)   (2)
            (*)   (2)   (1)   (1)
             */
            it("인접한 셀들이 열린다") {
                val openedPositions = setOf(
                    Position(0, 0),
                    Position(0, 1),
                    Position(0, 2),
                    Position(1, 0),
                    Position(1, 1),
                    Position(1, 2),
                    Position(2, 0),
                    Position(2, 1),
                )

                val unopenedPositions = setOf(
                    Position(0, 3),
                    Position(2, 3),
                    Position(3, 1),
                    Position(3, 2),
                    Position(3, 3),
                )

                openedPositions.forEach { position ->
                    val cell = board.cells[position]
                    cell.shouldBeTypeOf<Cell.Clear>()
                    cell.isOpened shouldBe true
                }

                unopenedPositions.forEach { position ->
                    val cell = board.cells[position]
                    cell.shouldBeTypeOf<Cell.Clear>()
                    cell.isOpened shouldBe false
                }
            }

            it("열리지 않은 셀이 있으므로 GameResult 값은 조회되지 않는다") {
                result.shouldBeNull()
            }
        }

        context("인접 지뢰수가 0이 아닌 일반 셀을 열면") {
            /*
            (0) (0) (1) (1)
            (0) (1) (2) (*)
            (1) (2) (*) (2)
            (*) (2) (1) (1)
            */
            val board = MineBoard.from(
                MinePickedBoard.of(
                    emptyBoard = EmptyBoard.of(Height(4), Width(4)),
                    minePicker = MinePositionPickerMock(
                        setOf(
                            Position(1, 3),
                            Position(2, 2),
                            Position(3, 0),
                        )
                    )
                )
            )
            val positionToOpen = Position(0, 3)

            val result = MinesweeperGame(board).open(positionToOpen)

            it("해당 셀만 열린다") {
                val unopenedPositions = setOf(
                    Position(0, 0),
                    Position(0, 1),
                    Position(0, 2),
                    Position(1, 0),
                    Position(1, 1),
                    Position(1, 2),
                    Position(2, 0),
                    Position(2, 1),
                    Position(2, 3),
                    Position(3, 1),
                    Position(3, 2),
                    Position(3, 3),
                )

                val openedPosition = Position(0, 3)
                val cell = board.cells[openedPosition]
                cell.shouldBeTypeOf<Cell.Clear>()
                cell.isOpened shouldBe true

                unopenedPositions.forEach { position ->
                    val cell = board.cells[position]
                    cell.shouldBeTypeOf<Cell.Clear>()
                    cell.isOpened shouldBe false
                }
            }

            it("열리지 않은 셀이 있으므로 GameResult 값은 조회되지 않는다") {
                result.shouldBeNull()
            }
        }

        context("이미 열었던 셀을 열면") {
            val board = MineBoard.from(
                MinePickedBoard.of(
                    emptyBoard = EmptyBoard.of(Height(4), Width(4)),
                    minePicker = MinePositionPickerMock(
                        setOf(
                            Position(1, 3),
                            Position(2, 2),
                            Position(3, 0),
                        )
                    )
                )
            )
            val positionToOpen = Position(0, 3)
            val game = MinesweeperGame(board)
            game.open(positionToOpen)
            (board.cells[positionToOpen] as Cell.Clear).isOpened shouldBe true

            it("해당 셀만 열린다") {
                shouldThrowExactly<IllegalStateException> {
                    game.open(positionToOpen)
                }
            }
        }

        context("모든 일반 셀을 열면") {
            /*
            (1) (1)
            (1) (*)
            */
            val board = MineBoard.from(
                MinePickedBoard.of(
                    emptyBoard = EmptyBoard.of(Height(2), Width(2)),
                    minePicker = MinePositionPickerMock(
                        setOf(
                            Position(1, 1),
                        )
                    )
                )
            )
            val game = MinesweeperGame(board)
            game.open(Position(0, 0))
            game.open(Position(0, 1))

            it("승리 결과가 반환된다") {
                val result = game.open(Position(1, 0))

                result shouldBe GameResult.WIN
            }
        }
    }

    describe("게임 종료 여부 확인") {
        val board = MineBoard.from(
            MinePickedBoard.of(
                emptyBoard = EmptyBoard.of(Height(2), Width(2)),
                minePicker = MinePositionPickerMock(
                    setOf(
                        Position(1, 1),
                    )
                )
            )
        )
        context("종료된 게임이라면") {
            val game = MinesweeperGame(board, GameResult.WIN)

            it("true가 반환된다") {
                game.isEnd() shouldBe true
            }
        }

        context("종료되지 않은 게임이라면") {
            val game = MinesweeperGame(board, null)

            it("false가 반환된다") {
                game.isEnd() shouldBe false
            }
        }
    }
})
