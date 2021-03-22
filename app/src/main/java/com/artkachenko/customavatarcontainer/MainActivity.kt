package com.artkachenko.customavatarcontainer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<CommentAvatarContainer>(R.id.avatar_container).addAvatarPlaceholders(generateImageList())
    }

    private fun generateImageList(): List<Int> {
        return listOf<Int>(
            R.drawable.ic_circular_avatar,
            R.drawable.ic_circular_avatar_red,
            R.drawable.ic_circular_avatar_yellow,
            R.drawable.ic_circular_avatar_blue,
            R.drawable.ic_circular_avatar,
            R.drawable.ic_circular_avatar_red)
    }
}