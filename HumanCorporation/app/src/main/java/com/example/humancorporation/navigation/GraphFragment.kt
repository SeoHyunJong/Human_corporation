package com.example.humancorporation.navigation

import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.humancorporation.DataUtil
import com.example.humancorporation.MainActivity
import com.example.humancorporation.R
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_graph.*
import java.time.LocalDate

class GraphFragment : Fragment(){
    val entries = ArrayList<CandleEntry>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_graph, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drawGraph()

        val dataSet = CandleDataSet(entries, "").apply {
            // 심지 부분
            shadowColor = Color.LTGRAY
            shadowWidth = 1F

            // 음봄
            decreasingColor = Color.BLUE
            decreasingPaintStyle = Paint.Style.FILL
            // 양봉
            increasingColor = Color.RED
            increasingPaintStyle = Paint.Style.FILL

            neutralColor = Color.DKGRAY
            setDrawValues(false)
            // 터치시 노란 선 제거
            highLightColor = Color.TRANSPARENT
        }
        /*
        candle_chart.axisLeft.run {
            setDrawAxisLine(false)
            setDrawGridLines(false)
            textColor = Color.TRANSPARENT
        }

        candle_chart.axisRight.run {
            isEnabled = false
        }

        // X 축
        candle_chart.xAxis.run {
            textColor = Color.TRANSPARENT
            setDrawAxisLine(false)
            setDrawGridLines(false)
            setAvoidFirstLastClipping(true)
        }

        // 범례
        candle_chart.legend.run {
            isEnabled = false
        }
        */
        candle_chart.apply {
            this.data = CandleData(dataSet)
            description.isEnabled = false
            isHighlightPerDragEnabled = true
            requestDisallowInterceptTouchEvent(true)
            invalidate()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun drawGraph(){
        val date = (activity as MainActivity).getDate()
        var cnt = 0f

        if(date.isNotEmpty()) {
            for (d in date) {
                val shadowHigh = (activity as MainActivity).maxPrice(d).toFloat()
                val shadowLow = (activity as MainActivity).minPrice(d).toFloat()
                val open = (activity as MainActivity).openPrice(d).toFloat()
                val close = (activity as MainActivity).closedPrice(d).toFloat()
                entries.add(CandleEntry(cnt, shadowHigh, shadowLow, open, close))
                cnt++
            }
        }
    }
}