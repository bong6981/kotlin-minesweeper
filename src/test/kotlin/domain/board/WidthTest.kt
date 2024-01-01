package domain.board

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import minesweeper.domain.board.Width

class WidthTest : StringSpec({
    "너비를 입력 받으면 해당 값을 가진 Width가 생성된다" {
        val width = 3

        val result = Width(3)

        result.value shouldBe width
    }

    "0이하의 수로는 높이를 생성할 수 없다" {
        val wrongWidth2 = 0

        shouldThrowExactly<IllegalArgumentException> {
            Width(wrongWidth2)
        }
    }
})
