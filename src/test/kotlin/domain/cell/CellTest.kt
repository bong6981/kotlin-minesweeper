package domain.cell

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import minesweeper.domain.cell.Cell
import minesweeper.domain.cell.MineCount
import minesweeper.domain.position.Position

class CellTest : StringSpec({
    "오픈되지 않은 셀을 연다" {
        val cell = Cell.Clear(Position(0, 0), MineCount.ONE)

        shouldNotThrowAny {
            cell.open()
        }
    }

    "오픈 된 셀을 열면 IllegalStateException이 발생한다" {
        val cell = Cell.Clear(Position(0, 0), MineCount.ONE)
        cell.open()

        shouldThrowExactly<IllegalStateException> {
            cell.open()
        }
    }

    "인접한 셀을 반환한다" {
        val cell = Cell.Clear(Position(0, 0), MineCount.ZERO)

        val result = cell.nearPositions

        result shouldBe setOf(
            Position(0, 1),
            Position(1, 0),
            Position(1, 1),
        )
    }

    "MineCount가 Zero인지 여부를 반환한다" {
        forAll(
            row(MineCount.ZERO, true),
            row(MineCount.ONE, false),
        ) { mineCount, expect ->
            val cell = Cell.Clear(Position(0, 0), mineCount)

            cell.isMineCountZero() shouldBe expect
        }
    }
})
