package com.example.chartdemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CustomDrawable @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet,
    defStyle: Int = 0,
): View(context, attr, defStyle) {
    private var color: Int? = null
    fun submitColor(color: Int) {
        this.color = color
    }
    override fun onDraw(canvas: Canvas) {
        val paint = Paint().apply {
            isAntiAlias = true
            this.color = this@CustomDrawable.color!!
            style = Paint.Style.STROKE
        }
        canvas.drawCircle(100f, 1000f, 90f, paint)
    }
}