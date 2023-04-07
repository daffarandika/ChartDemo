package com.example.chartdemo

import android.annotation.SuppressLint
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
//    private var equalizeXAxis: Boolean = context.obtainStyledAttributes(attributeSet, R.styleable.LineChart, defStyle, 0).getBoolean(R.styleable.LineChart.equalize)
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

        val minX = line.points.minOf{ point ->
            point.x
        }

        val maxX = line.points.maxOf { point ->
            point.x
        }

        val minY = line.points.minOf { point ->
            point.y
        }

        val maxY = line.points.maxOf { point ->
            point.y
        }

        val adjustedLine: ChartLine = ChartLine(line.points.map {oldPoint ->
            ChartPoint(
                ((maxX - minX) / canvas.width) * 2 * oldPoint.x,
                ((maxY - minY) / canvas.width) * 2 * oldPoint.y
            )
        })

//        val plotStartX = 100f
//        val plotWidth = canvas.width - 50f
        for (i in 0 until adjustedLine.points.size - 1) {
            val point = adjustedLine.points[i]
            val nextPoint = adjustedLine.points[i+1]
            canvas.drawLine(
                point.x,
                point.y,
                nextPoint.x,
                nextPoint.y,
                linePaint)
//            canvas.drawCircle((plotWidth / (line.points.size.toFloat() - 1)) * (i) + plotStartX, point.y, 5f, pointStyle)
//            if (i == line.points.size - 1) {
//                canvas.drawCircle((plotWidth / (line.points.size.toFloat() - 3)) * (i) + plotStartX, nextPoint.y, 5f, pointStyle)
//            }
        }
        canvas.drawRect(rect, pointStyle)
    }
}