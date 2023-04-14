package com.example.chartdemo

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

class LineChart @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet,
    defStyle: Int = 0
): View(context, attributeSet, defStyle) {
    private lateinit var line: ChartLine
    private var touchX: Float? = -1f
    private var listener: OnClickListener? = null
    private var xmlArgs = context.obtainStyledAttributes(
        attributeSet,
        R.styleable.LineChart,
        defStyle,
        0)
    private val equalizeXAxis: Boolean = xmlArgs.getBoolean(R.styleable.LineChart_equalizeXAxis, false)
    fun submitLine(line: ChartLine) {
        this.line = line
    }
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {

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

        val axisPaint = Paint().apply {
            color = Color.BLACK
            textAlign = Paint.Align.CENTER
            strokeWidth = 8f
        }

        val textPaint = Paint().apply {
            color = Color.RED
            textAlign = Paint.Align.CENTER
        }

        // draw axis
        val xUnit = width / (line.points.size + 2)
        val yUnit = height / (line.points.size + 2)

        // y axis
        canvas.drawLine(
            10f,
            10f,
            10f,
            (height - yUnit).toFloat(),
            axisPaint
        )

        // x axis
        canvas.drawLine(
            10f,
            (height - yUnit).toFloat(),
            (width - xUnit).toFloat(),
            (height - yUnit).toFloat(),
            axisPaint
        )

        // draw graph
        val adjustedLine = ChartLine(line.points.map { oldPoint ->
            ChartPoint(
                (oldPoint.x / maxX) * (width - yUnit),
                kotlin.math.abs((oldPoint.y - maxY) / maxY) * (width - yUnit),
            )
        })
        val linePaint = Paint().apply {
            color = Color.RED
            strokeWidth = 5f
        }
        if (equalizeXAxis) {
            for (i in 0 until adjustedLine.points.size - 1) {
                val point = adjustedLine.points[i]
                val nextPoint = adjustedLine.points[i+1]
                Log.e(TAG, "val $i / ${adjustedLine.points.size} * $width")
                Log.e(TAG, "onDraw start: ${((i * width))})")
                canvas.drawLine(
                    ((i * width).toFloat()/(adjustedLine.points.size - 10)),
                    point.y,
                    (((i + 1)* width).toFloat()/(adjustedLine.points.size - 10)),
                    nextPoint.y,
                    linePaint
                )
            }
        } else {
            for (i in 0 until adjustedLine.points.size - 1) {
                val point = adjustedLine.points[i]
                val nextPoint = adjustedLine.points[i+1]
                canvas.drawLine(
                    point.x,
                    point.y,
                    nextPoint.x,
                    nextPoint.y,
                    linePaint
                )
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        touchX = event.x
        if (event.action == MotionEvent.ACTION_UP) {
            touchX = null
        }
        invalidate()
        return true
    }

    companion object {
        private const val TAG = "LineChart"
    }
}