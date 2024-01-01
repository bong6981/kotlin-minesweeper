package minesweeper.domain.board

@JvmInline
value class MineTotal2(
    val value: Int
) {
    init {
        require(value > 0) { "지뢰 개수는 0보다 큰 정수여야 합니다" }
    }
}
