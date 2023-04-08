package com.example.chartdemo

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet.Motion
import kotlin.math.round

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
        val rectLength = (width * 0.8f).toInt()
        val rect = Rect(0, 0, rectLength, rectLength)
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
        val lineTransparentPaint = Paint().apply {
            isAntiAlias = true
            color = Color.RED
            alpha = 122
            style = Paint.Style.STROKE
            strokeWidth = 1.2f
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

        val adjustedLine = ChartLine(line.points.map { oldPoint ->
            ChartPoint(
                (oldPoint.x / maxX) * rect.width(),
                kotlin.math.abs((oldPoint.y - maxY) / maxY) * rect.width(),
            )
        })

        if (touchX != null) {
            canvas.drawLine(
                touchX!!,
                0f,
                touchX!!,
                rectLength.toFloat(),
                lineTransparentPaint
            )
            Log.e(TAG, "cok touchX: $touchX", )
            Log.e(TAG, "cok touchX: $touchX line X vals: ${
                List(line.points.size) { index ->
                    ((index * rectLength).toFloat()/(adjustedLine.points.size - 1))
                }
            }")
            if (round(touchX!!) in List(adjustedLine.points.size) { index ->
                    round(((index * rectLength).toFloat()/(adjustedLine.points.size - 1)))
            }) {
//                canvas.drawCircle(
//                    touchX!!,
////                    line.points.any {
////                        (round(it.x) == round(touchX!!))
////                    },
//                    10f,
//                    pointStyle
//                )
                //TODO: touch at index
                Log.e(TAG, "onDraw: yes", )
                canvas.drawCircle(
                    touchX!!,
                    touchX!!,
                    10f,
                    pointStyle
                )
            }
        }

        if (equalizeXAxis) {
            for (i in 0 until adjustedLine.points.size - 1) {
                val point = adjustedLine.points[i]
                val nextPoint = adjustedLine.points[i+1]
                Log.e(TAG, "val $i / ${adjustedLine.points.size} * $rectLength", )
                Log.e(TAG, "onDraw start: ${((i * rectLength)/adjustedLine.points.size).toFloat()}", )
                canvas.drawLine(
                    ((i * rectLength).toFloat()/(adjustedLine.points.size - 1)),
                    point.y,
                    (((i + 1)* rectLength).toFloat()/(adjustedLine.points.size - 1)),
                    nextPoint.y,
                    linePaint)
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
                    linePaint)
            }
        }
        canvas.drawRect(rect, pointStyle)
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