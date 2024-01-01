package domain.board

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import minesweeper.domain.board.EmptyBoard
import minesweeper.domain.board.Height2
import minesweeper.domain.board.MineBoard2
import minesweeper.domain.board.MinePickedBoard
import minesweeper.domain.board.Width2
import minesweeper.domain.position.Position2

class MineBoard2Test : DescribeSpec({
    describe("셀 오픈") {
        val mineBoard = MineBoard2.from(
            MinePickedBoard.of(
                emptyBoard = EmptyBoard.of(Height2(3), Width2(3)),
                minePicker = MinePositionPickerMock(
                    minePositions = setOf(Position2(0, 0))
                )
            )
        )

        context("지뢰가 아닌 셀을 열면") {
            val positionToOpen = Position2(1, 0)

            val result = mineBoard.open(positionToOpen)

            it("해당 셀이 반환된다") {
                result.position shouldBe positionToOpen
            }
        }

        context("이미 연 셀을 열면") {
            val positionToOpen = Position2(2, 0)
            mineBoard.open(positionToOpen)

            it("IllegalStateException이 발생한다") {
                shouldThrowExactly<IllegalStateException> {
                    mineBoard.open(positionToOpen)
                }
            }
        }

        context("지뢰를 열면") {
            val positionToOpen = Position2(0, 0)

            it("IllegalStateException이 발생한다") {
                shouldThrowExactly<IllegalStateException> {
                    mineBoard.open(positionToOpen)
                }
            }
        }
    }

    describe("지뢰 여부 반환") {
        val mineBoard = MineBoard2.from(
            MinePickedBoard.of(
                emptyBoard = EmptyBoard.of(Height2(3), Width2(3)),
                minePicker = MinePositionPickerMock(
                    minePositions = setOf(Position2(0, 0))
                )
            )
        )

        context("지뢰 위치로 조회시") {
            val position = Position2(0, 0)

            it("true가 반환된다") {
                mineBoard.isMine(position) shouldBe true
            }
        }

        context("지뢰가 아닌 위치로 조회시") {
            val position = Position2(1, 0)

            it("false가 반환된다") {
                mineBoard.isMine(position) shouldBe false
            }
        }

        context("보드 내 위치가 아닌 경우") {
            val position = Position2(3, 3)

            it("IllegalArgumentException가 발생한다") {
                shouldThrowExactly<IllegalArgumentException> {
                    mineBoard.isMine(position)
                }
            }
        }
    }
})
