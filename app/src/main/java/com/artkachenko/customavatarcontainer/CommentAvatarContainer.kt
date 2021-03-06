package com.artkachenko.customavatarcontainer

import android.content.Context
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.marginLeft
import androidx.core.view.marginRight

class CommentAvatarContainer @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, deffStyle: Int = 0) : FrameLayout(context, attributeSet, deffStyle) {

    private val rect = RectF()

    private var customHeight = 26
    private var avatarCount = 4
    private var overlap = customHeight.toFloat() / 2

    init {
        val array = context.obtainStyledAttributes(attributeSet, R.styleable.CommentAvatarContainer)

        kotlin.runCatching {
            customHeight = array.getInt(R.styleable.CommentAvatarContainer_avatar_size, 26)
            avatarCount = array.getInt(R.styleable.CommentAvatarContainer_avatar_count, 4)
            overlap = array.getFloat(R.styleable.CommentAvatarContainer_itemOverlap, 13F)
        }

        array.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = (avatarCount  * dp(customHeight.toFloat())) + ((avatarCount - 1) * dp(overlap)) + paddingLeft + paddingRight + marginRight + marginLeft
        val desiredHeight = dp(customHeight.toFloat()) + paddingTop + paddingBottom

        val measuredWidth = measureDimension(desiredWidth, widthMeasureSpec)
        val measuredHeight = measureDimension(desiredHeight, heightMeasureSpec)

        rect.set(0F, 0F, measuredWidth.toFloat(), measuredHeight.toFloat())

        setMeasuredDimension(
            measuredWidth,
            measuredHeight
        )

        if (childCount == 0) return
        for (i in 0.until(childCount)) {
            val child = getChildAt(i)
            measureChild(
                child,
                MeasureSpec.makeMeasureSpec(dp(customHeight.toFloat()), MeasureSpec.EXACTLY),
                MeasureSpec.getSize(heightMeasureSpec)
            )
        }
    }

    fun addAvatarUris(avatarList: List<Int>?) {
        if (avatarList.isNullOrEmpty()) {
            visibility = View.GONE
            return
        }
        visibility = View.VISIBLE
        removeAllViews()

        avatarList.take(avatarCount).forEachIndexed { index, i ->
            addViewFromUriWithOverlap(i, index)
        }
    }

    fun addAvatarImages(views: List<ImageView>?) {
        if (views.isNullOrEmpty()) {
            visibility = View.GONE
            return
        }
        visibility = View.VISIBLE
        removeAllViews()

        views.take(avatarCount).forEachIndexed { index, imageView ->
            addViewWithOverlap(imageView, index)
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {

        if (childCount == 0) return
        for (i in 0.until(childCount)) {
            val child = getChildAt(i)
            val positionX = (dp(overlap) * i)
            val positionY = 0
            child.layout(positionX, positionY, positionX + child.measuredWidth, positionY + child.measuredHeight)
        }
    }

    private fun addViewFromUriWithOverlap(@DrawableRes drawableId: Int, index: Int) {
        val imageView = ImageView(context).apply {
            setImageViewParams(this, index)
        }
        imageView.setImageDrawable(ContextCompat.getDrawable(context, drawableId))
        addView(imageView)
    }

    private fun addViewWithOverlap(imageView: ImageView, index: Int) {
        imageView.apply {
            setImageViewParams(this, index)
        }

        addView(imageView)
    }

    private fun setImageViewParams(imageView: ImageView, index: Int) {
        val elevation = dpF(4F - index)

        imageView.apply {
            this.elevation = elevation
            val layoutParams = ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            layoutParams.height = dp(customHeight.toFloat())
            layoutParams.width = dp(customHeight.toFloat())
            this.layoutParams = layoutParams
        }
    }

    private fun measureDimension(desiredSize: Int, measureSpec: Int): Int {
        var result: Int
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            result = desiredSize
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize)
            }
        }

        return result
    }

    private fun dpF(pixels: Float) : Float {
        return context.resources.displayMetrics.density * pixels
    }

    private fun dp(pixels: Float) : Int {
        return (context.resources.displayMetrics.density * pixels).toInt()
    }
}