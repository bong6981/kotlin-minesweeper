package domain.cell

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import minesweeper.domain.cell.Cell2
import minesweeper.domain.cell.MineCount2
import minesweeper.domain.position.Position2

class Cell2Test : StringSpec({
    "오픈되지 않은 셀을 연다" {
        val cell = Cell2.Clear(Position2(0, 0), MineCount2.ONE)

        shouldNotThrowAny {
            cell.open()
        }
    }

    "오픈 된 셀을 열면 IllegalStateException이 발생한다" {
        val cell = Cell2.Clear(Position2(0, 0), MineCount2.ONE)
        cell.open()

        shouldThrowExactly<IllegalStateException> {
            cell.open()
        }
    }

    "인접한 셀을 반환한다" {
        val cell = Cell2.Clear(Position2(0, 0), MineCount2.ZERO)

        val result = cell.nearPositions

        result shouldBe setOf(
            Position2(0, 1),
            Position2(1, 0),
            Position2(1, 1),
        )
    }

    "MineCount가 Zero인지 여부를 반환한다" {
        forAll(
            row(MineCount2.ZERO, true),
            row(MineCount2.ONE, false),
        ) { mineCount, expect ->
            val cell = Cell2.Clear(Position2(0, 0), mineCount)

            cell.isMineCountZero() shouldBe expect
        }
    }
})
