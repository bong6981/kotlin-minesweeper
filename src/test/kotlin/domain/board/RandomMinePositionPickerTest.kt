package domain.board

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldBe
import minesweeper.domain.board.MineTotal2
import minesweeper.domain.position.Position2
import minesweeper.domain.position.RandomMinePositionPicker

class RandomMinePositionPickerTest : DescribeSpec({
    describe("랜덤 위치 선택") {
        context("전체 위치와 뽑아야 할 수가 주어지면") {
            val allPositions = setOf(
                Position2(0, 0),
                Position2(0, 1),
                Position2(0, 2),
                Position2(1, 0),
                Position2(1, 1),
                Position2(1, 2),
            )
            val mineCount = MineTotal2(2)

            val pickedPositions = RandomMinePositionPicker(mineCount).pick(allPositions)

            it("뽑힌 위치의 개수는 전달한 뽑아야 할 수와 같다") {
                pickedPositions.size shouldBe mineCount.value
            }

            it("뽑힌 위치는 전체 위치 중 하나이다") {
                pickedPositions.forEach { position ->
                    position shouldBeIn allPositions
                }
            }
        }

        context("전체 위치보다 뽑아야 할 수가 많은 경우") {
            val allPositions = setOf(
                Position2(0, 0),
                Position2(0, 1),
                Position2(1, 0),
                Position2(1, 1),
            )
            val mineCount = MineTotal2(5)

            it("IllegalArgumentException이 발생한다") {
                shouldThrowExactly<IllegalArgumentException> {
                    RandomMinePositionPicker(mineCount).pick(allPositions)
                }
            }
        }
    }
})
