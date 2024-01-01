package domain.board

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import minesweeper.domain.board.MineTotal2

class MineTotal2Test : StringSpec({
    "지뢰 총 개수를 입력 받으면 해당 값을 가진 MineTotal이 생성된다" {
        val count = 3

        val result = MineTotal2(3)

        result.value shouldBe count
    }

    "0이하의 수로는 높이를 생성할 수 없다" {
        val wrongCount = 0

        shouldThrowExactly<IllegalArgumentException> {
            MineTotal2(wrongCount)
        }
    }
})
