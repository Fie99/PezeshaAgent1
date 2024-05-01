package com.pezesha.agent.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.pezesha.agent.R

class BusinessStats(context: Context, attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {

    private var tvNumber: TextView? = null

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.business_stats_view, this, true)

        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.BusinessStats,
            0,
            0
        )

        val contentText: String = typedArray.getString(R.styleable.BusinessStats_bsContent) ?: ""
        val number: String = typedArray.getString(R.styleable.BusinessStats_bsNumber) ?: "0"
        val iconRef = typedArray.getDrawable(R.styleable.BusinessStats_bsIcon)

        typedArray.recycle()

        findViewById<ImageView>(R.id.iv_icon).setImageDrawable(iconRef)
        tvNumber = findViewById(R.id.tv_number)
        findViewById<TextView>(R.id.tv_content).text = contentText

        setNumber(number)
    }

    fun setNumber(number: String) {
        tvNumber?.text = number
    }

}