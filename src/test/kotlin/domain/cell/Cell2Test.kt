package domain.cell

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.StringSpec
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
})
