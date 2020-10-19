package com.example.madlevel4task2.model

import androidx.annotation.StringRes
import com.example.madlevel4task2.R

enum class GameResult(@StringRes val message: Int) {
    WIN(R.string.win_message),
    LOSE(R.string.lose_message),
    DRAW(R.string.draw_message)
}