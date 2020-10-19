package com.example.madlevel4task2.model

import androidx.annotation.DrawableRes
import com.example.madlevel4task2.R

enum class Move(@DrawableRes val drawableId: Int) {
    ROCK(R.drawable.rock),
    PAPER(R.drawable.paper),
    SCISSORS(R.drawable.scissors)
}