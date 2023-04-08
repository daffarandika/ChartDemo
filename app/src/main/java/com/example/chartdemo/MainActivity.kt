package com.example.chartdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chartdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val line = ChartLine(listOf(
            ChartPoint(0f,120f),
            ChartPoint(100f,150f),
            ChartPoint(160f,300f),
            ChartPoint(200f,400f),
            ChartPoint(210f,390f),
            ChartPoint(220f,290f),
            ChartPoint(270f,580f),
            ChartPoint(220f,290f),
            ChartPoint(100f,150f),
            ChartPoint(160f,300f),
            ChartPoint(100f,150f),
            ChartPoint(160f,300f),
            ChartPoint(270f,580f),
            ChartPoint(270f,80f),
            ChartPoint(0f,120f),
            ChartPoint(0f,120f),
            ChartPoint(0f,120f),
            ChartPoint(100f,150f),
            ChartPoint(160f,300f),
        ))
        binding.cust.submitLine(line)
    }
}