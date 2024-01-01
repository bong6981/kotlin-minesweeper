package domain.board

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import minesweeper.domain.board.Height2

class Height2Test : StringSpec({
    "높이를 입력 받으면 해당 값을 가진 Height가 생성된다" {
        val height = 3

        val result = Height2(3)

        result.value shouldBe height
    }

    "0이하의 수로는 높이를 생성할 수 없다" {
        val wrongHeight = 0

        shouldThrowExactly<IllegalArgumentException> {
            Height2(wrongHeight)
        }
    }
})
