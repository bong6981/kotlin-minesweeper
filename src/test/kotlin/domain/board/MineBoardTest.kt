package domain.board

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import minesweeper.domain.board.EmptyBoard
import minesweeper.domain.board.Height
import minesweeper.domain.board.MineBoard
import minesweeper.domain.board.MinePickedBoard
import minesweeper.domain.board.Width
import minesweeper.domain.cell.Cell
import minesweeper.domain.position.Position

class MineBoardTest : DescribeSpec({
    describe("셀 오픈") {
        val mineBoard = MineBoard.from(
            MinePickedBoard.of(
                emptyBoard = EmptyBoard.of(Height(3), Width(3)),
                minePicker = MinePositionPickerMock(
                    minePositions = setOf(Position(0, 0))
                )
            )
        )

        context("지뢰가 아닌 셀을 열면") {
            val positionToOpen = Position(1, 0)

            val result = mineBoard.open(positionToOpen)

            it("해당 셀이 반환된다") {
                result.position shouldBe positionToOpen
            }
        }

        context("이미 연 셀을 열면") {
            val positionToOpen = Position(2, 0)
            mineBoard.open(positionToOpen)

            it("IllegalStateException이 발생한다") {
                shouldThrowExactly<IllegalStateException> {
                    mineBoard.open(positionToOpen)
                }
            }
        }

        context("지뢰를 열면") {
            val positionToOpen = Position(0, 0)

            it("IllegalStateException이 발생한다") {
                shouldThrowExactly<IllegalStateException> {
                    mineBoard.open(positionToOpen)
                }
            }
        }
    }

    describe("지뢰 여부 반환") {
        val mineBoard = MineBoard.from(
            MinePickedBoard.of(
                emptyBoard = EmptyBoard.of(Height(3), Width(3)),
                minePicker = MinePositionPickerMock(
                    minePositions = setOf(Position(0, 0))
                )
            )
        )

        context("지뢰 위치로 조회시") {
            val position = Position(0, 0)

            it("true가 반환된다") {
                mineBoard.isMine(position) shouldBe true
            }
        }

        context("지뢰가 아닌 위치로 조회시") {
            val position = Position(1, 0)

            it("false가 반환된다") {
                mineBoard.isMine(position) shouldBe false
            }
        }

        context("보드 내 위치가 아닌 경우") {
            val position = Position(3, 3)

            it("IllegalArgumentException가 발생한다") {
                shouldThrowExactly<IllegalArgumentException> {
                    mineBoard.isMine(position)
                }
            }
        }
    }

    describe("열수 있는 위치인지 확인") {
        val mineBoard = MineBoard.from(
            MinePickedBoard.of(
                emptyBoard = EmptyBoard.of(Height(2), Width(2)),
                minePicker = MinePositionPickerMock(
                    minePositions = setOf(Position(0, 0))
                )
            )
        )

        context("보드 내, 열지 않은, 지뢰가 아닌 위치라면") {
            val position = Position(0, 1)
            it("true를 반환한다") {
                mineBoard.canOpen(position) shouldBe true
            }
        }

        context("보드 내 지뢰가 아닌 위치지만 이미 열었다면") {
            val position = Position(1, 0)
            val cell = mineBoard.open(position)
            cell.shouldBeTypeOf<Cell.Clear>()
            cell.isOpened shouldBe true

            it("false를 반환한다") {
                mineBoard.canOpen(position) shouldBe false
            }
        }

        context("지뢰 위치라면") {
            val position = Position(0, 0)
            it("false를 반환한다") {
                mineBoard.canOpen(position) shouldBe false
            }
        }

        context("보드 내의 위치가 아니라면") {
            val position = Position(0, 3)
            it("false를 반환한다") {
                mineBoard.canOpen(position) shouldBe false
            }
        }
    }

    describe("모든 일반 셀을 열었는지 확인") {
        context("모든 일반 셀을 열었다면") {
            val mineBoard = MineBoard.from(
                MinePickedBoard.of(
                    emptyBoard = EmptyBoard.of(Height(2), Width(2)),
                    minePicker = MinePositionPickerMock(
                        minePositions = setOf(Position(0, 0))
                    )
                )
            )
            mineBoard.open(Position(0, 1))
            mineBoard.open(Position(1, 0))
            mineBoard.open(Position(1, 1))

            it("true가 반환된다") {
                mineBoard.isAllOpened() shouldBe true
            }
        }

        context("열지 않은 일반 셀이 하나라도 있다면") {
            val mineBoard = MineBoard.from(
                MinePickedBoard.of(
                    emptyBoard = EmptyBoard.of(Height(2), Width(2)),
                    minePicker = MinePositionPickerMock(
                        minePositions = setOf(Position(0, 0))
                    )
                )
            )
            mineBoard.open(Position(0, 1))
            mineBoard.open(Position(1, 0))

            it("false 가 반환된다") {
                mineBoard.isAllOpened() shouldBe false
            }
        }
    }
})
