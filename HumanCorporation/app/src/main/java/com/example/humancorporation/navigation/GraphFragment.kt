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
import com.example.humancorporation.MainActivity
import com.example.humancorporation.R
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import kotlinx.android.synthetic.main.fragment_graph.*

class GraphFragment : Fragment(){
    val entries = ArrayList<CandleEntry>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_graph, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        drawGraph()

        if(entries.isNotEmpty()) {
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

        txt_price1.text = (activity as MainActivity).todayPrice().toString()
        txt_price2.text = (activity as MainActivity).maxPrice().toString()
        txt_price3.text = (activity as MainActivity).minPrice().toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun drawGraph(){
        val stock = (activity as MainActivity).getAllStock()
        var cnt = 0f
        if(!stock.isNullOrEmpty()) {
            for (item in stock) {
                val shadowHigh = item.shadowHigh
                val shadowLow = item.shadowLow
                val open = item.open
                val close = item.close
                entries.add(CandleEntry(cnt, shadowHigh, shadowLow, open, close))
                cnt++
            }
        }
    }
}