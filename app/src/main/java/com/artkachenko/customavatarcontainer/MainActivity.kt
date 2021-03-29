package com.artkachenko.customavatarcontainer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<CommentAvatarContainer>(R.id.avatar_container).addAvatarUris(generateImageList())
        findViewById<CommentAvatarContainer>(R.id.avatar_container_from_images).addAvatarImages(generateImages())
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

    private fun generateImages() : List<ImageView> {
        val reversedDrawableList = generateImageList().reversed()
        val imageList = mutableListOf<ImageView>()
        reversedDrawableList.forEachIndexed { index, i ->
            imageList.add(ImageView(this).apply {
                setImageDrawable(ContextCompat.getDrawable(this@MainActivity, reversedDrawableList[index]))
            })
        }
        return imageList
    }
}