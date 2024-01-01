package domain.position

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import minesweeper.domain.position.Position2

class Position2Test : StringSpec({
    "0이상인 row, column 값으로 Position 생성" {
        val row = 0
        val column = 1

        val position = Position2(row, column)

        position.row shouldBe row
        position.column shouldBe column
    }

    "0미만인 row 값으로 Position 생성 불가" {
        val row = -1
        val column = 1

        shouldThrowExactly<IllegalArgumentException> {
            Position2(row, column)
        }
    }

    "0미만인 column 값으로 Position 생성 불가" {
        val row = 1
        val column = -1

        shouldThrowExactly<IllegalArgumentException> {
            Position2(row, column)
        }
    }

    "인접한 위치 반환" {
        val position = Position2(1, 1)

        val result = position.nearPositions

        result shouldBe setOf(
            Position2(0, 0),
            Position2(0, 1),
            Position2(0, 2),
            Position2(1, 0),
            Position2(1, 2),
            Position2(2, 0),
            Position2(2, 1),
            Position2(2, 2),
        )
    }

    "인접한 위치 조회시 row, column 범위를 벗어나는 것은 필터링 후 반환" {
        val position = Position2(0, 0)

        val result = position.nearPositions

        result shouldBe setOf(
            Position2(0, 1),
            Position2(1, 0),
            Position2(1, 1),
        )
    }
})
