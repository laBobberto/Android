package com.blobblob.empty

import androidx.annotation.StringRes

enum class AppCategory(@StringRes val titleRes: Int) {
    GAME(R.string.category_game),
    SOCIAL(R.string.category_social),
    TOOLS(R.string.category_tools),
    PRODUCTIVITY(R.string.category_productivity),
    ENTERTAINMENT(R.string.category_entertainment),
    APP(R.string.category_app)
}
