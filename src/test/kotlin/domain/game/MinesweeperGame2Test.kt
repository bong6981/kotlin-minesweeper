package domain.game

import domain.board.MinePositionPickerMock
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import minesweeper.domain.board.EmptyBoard
import minesweeper.domain.board.Height2
import minesweeper.domain.board.MineBoard2
import minesweeper.domain.board.MinePickedBoard
import minesweeper.domain.board.Width2
import minesweeper.domain.cell.Cell2
import minesweeper.domain.game.GameResult
import minesweeper.domain.game.MinesweeperGame2
import minesweeper.domain.position.Position2

class MinesweeperGame2Test : DescribeSpec({
    describe("open()") {
        context("지뢰를 열면") {
            val board = MineBoard2.from(
                MinePickedBoard.of(
                    emptyBoard = EmptyBoard.of(Height2(2), Width2(2)),
                    minePicker = MinePositionPickerMock(
                        setOf(Position2(0, 0))
                    )
                )
            )

            val positionToOpen = Position2(0, 0)

            val gameResult = MinesweeperGame2(board).open(positionToOpen)

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
            val board = MineBoard2.from(
                MinePickedBoard.of(
                    emptyBoard = EmptyBoard.of(Height2(4), Width2(4)),
                    minePicker = MinePositionPickerMock(
                        setOf(
                            Position2(1, 3),
                            Position2(2, 2),
                            Position2(3, 0),
                        )
                    )
                )
            )
            val positionToOpen = Position2(0, 0)

            val result = MinesweeperGame2(board).open(positionToOpen)

            /*
            (열림) (열림) (열림) (1)
            (열림) (열림) (열림) (*)
            (열림) (열림) (*)   (2)
            (*)   (2)   (1)   (1)
             */
            it("인접한 셀들이 열린다") {
                val openedPositions = setOf(
                    Position2(0, 0),
                    Position2(0, 1),
                    Position2(0, 2),
                    Position2(1, 0),
                    Position2(1, 1),
                    Position2(1, 2),
                    Position2(2, 0),
                    Position2(2, 1),
                )

                val unopenedPositions = setOf(
                    Position2(0, 3),
                    Position2(2, 3),
                    Position2(3, 1),
                    Position2(3, 2),
                    Position2(3, 3),
                )

                openedPositions.forEach { position ->
                    val cell = board.cells[position]
                    cell.shouldBeTypeOf<Cell2.Clear>()
                    cell.isOpened shouldBe true
                }

                unopenedPositions.forEach { position ->
                    val cell = board.cells[position]
                    cell.shouldBeTypeOf<Cell2.Clear>()
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
            val board = MineBoard2.from(
                MinePickedBoard.of(
                    emptyBoard = EmptyBoard.of(Height2(4), Width2(4)),
                    minePicker = MinePositionPickerMock(
                        setOf(
                            Position2(1, 3),
                            Position2(2, 2),
                            Position2(3, 0),
                        )
                    )
                )
            )
            val positionToOpen = Position2(0, 3)

            val result = MinesweeperGame2(board).open(positionToOpen)

            it("해당 셀만 열린다") {
                val unopenedPositions = setOf(
                    Position2(0, 0),
                    Position2(0, 1),
                    Position2(0, 2),
                    Position2(1, 0),
                    Position2(1, 1),
                    Position2(1, 2),
                    Position2(2, 0),
                    Position2(2, 1),
                    Position2(2, 3),
                    Position2(3, 1),
                    Position2(3, 2),
                    Position2(3, 3),
                )

                val openedPosition = Position2(0, 3)
                val cell = board.cells[openedPosition]
                cell.shouldBeTypeOf<Cell2.Clear>()
                cell.isOpened shouldBe true

                unopenedPositions.forEach { position ->
                    val cell = board.cells[position]
                    cell.shouldBeTypeOf<Cell2.Clear>()
                    cell.isOpened shouldBe false
                }
            }

            it("열리지 않은 셀이 있으므로 GameResult 값은 조회되지 않는다") {
                result.shouldBeNull()
            }
        }

        context("이미 열었던 셀을 열면") {
            val board = MineBoard2.from(
                MinePickedBoard.of(
                    emptyBoard = EmptyBoard.of(Height2(4), Width2(4)),
                    minePicker = MinePositionPickerMock(
                        setOf(
                            Position2(1, 3),
                            Position2(2, 2),
                            Position2(3, 0),
                        )
                    )
                )
            )
            val positionToOpen = Position2(0, 3)
            val game = MinesweeperGame2(board)
            game.open(positionToOpen)
            (board.cells[positionToOpen] as Cell2.Clear).isOpened shouldBe true

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
            val board = MineBoard2.from(
                MinePickedBoard.of(
                    emptyBoard = EmptyBoard.of(Height2(2), Width2(2)),
                    minePicker = MinePositionPickerMock(
                        setOf(
                            Position2(1, 1),
                        )
                    )
                )
            )
            val game = MinesweeperGame2(board)
            game.open(Position2(0, 0))
            game.open(Position2(0, 1))

            it("승리 결과가 반환된다") {
                val result = game.open(Position2(1, 0))

                result shouldBe GameResult.WIN
            }
        }
    }
})
