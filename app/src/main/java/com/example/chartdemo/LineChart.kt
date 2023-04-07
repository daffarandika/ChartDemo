package com.example.chartdemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View

class LineChart @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet,
    defStyle: Int = 0
): View(context, attributeSet, defStyle) {
    private lateinit var line: ChartLine
    fun submitLine(line: ChartLine) {
        this.line = line
    }
    override fun onDraw(canvas: Canvas) {
        val rect = Rect(0, 0, canvas.width, canvas.width)
        val paint = Paint().apply {
            isAntiAlias = true
            color = Color.BLACK
            style = Paint.Style.STROKE
            strokeWidth = 5f
        }
        val linePaint = Paint().apply {
            isAntiAlias = true
            color = Color.GRAY
            style = Paint.Style.STROKE
            strokeWidth = 3f
        }
        val pointStyle = Paint().apply {
            color = Color.RED
            style = Paint.Style.STROKE
        }

        val minY = line.points.map {point ->
            point.y
        }.min()

        val maxY = line.points.map {point ->
            point.y
        }.max()

        val plotStartX = 100f
        val plotWidth = canvas.width - 50f
        for (i in 0 until line.points.size - 1) {
            val point = line.points[i]
            val nextPoint = line.points[i+1]
            canvas.drawLine(
                (plotWidth / (line.points.size.toFloat() - 1)) * (i) + plotStartX,
                (minY / maxY) * point.y,
                (plotWidth / (line.points.size.toFloat() - 1)) * (i+1) + plotStartX,
                (minY / maxY) * nextPoint.y,
                linePaint)
//            canvas.drawCircle((plotWidth / (line.points.size.toFloat() - 1)) * (i) + plotStartX, point.y, 5f, pointStyle)
//            if (i == line.points.size - 1) {
//                canvas.drawCircle((plotWidth / (line.points.size.toFloat() - 3)) * (i) + plotStartX, nextPoint.y, 5f, pointStyle)
//            }
        }
        canvas.drawRect(rect, pointStyle)
    }
}