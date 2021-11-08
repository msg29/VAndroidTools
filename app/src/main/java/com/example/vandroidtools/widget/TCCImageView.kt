package com.example.vandroidtools.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.ceil
import kotlin.math.max

/**
 * create by yangjiwei in 2021/11/8
 *
 * top center crop image view
 * repeat draw if drawable height smaller than image view
 */
class TCCImageView : AppCompatImageView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas?) {
        val nonNullDrawable = drawable ?: return
        val nonNullMatrix = imageMatrix ?: return

        val realW = (measuredWidth - paddingLeft - paddingRight).toFloat()
        val realH = (measuredHeight - paddingTop - paddingBottom).toFloat()
        if (realW == 0f || realH == 0f) return

        val drawableW = nonNullDrawable.intrinsicWidth
        val drawableH = nonNullDrawable.intrinsicHeight
        if (drawableW == 0 || drawableH == 0) return

        val scaleX = realW / drawableW
        val realDrawH = drawableH * scaleX
        val repeat = max(1, ceil(realH / realDrawH).toInt())

        repeat(repeat) { index ->
            nonNullMatrix.reset()
            nonNullMatrix.postScale(scaleX, scaleX)
            nonNullMatrix.postTranslate(0f, index * realDrawH)
            imageMatrix = nonNullMatrix
            super.onDraw(canvas)
        }
    }

}