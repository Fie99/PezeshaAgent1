package com.pezesha.agent.ui.views

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.pezesha.agent.R

class AddDetail(private val context: Context, attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {

    private var ivEndIcon: ImageView? = null
    private var tvState: TextView? = null
    private var tvTitle: TextView? = null

    private var endIcon: Drawable? = null

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.add_detail_view, this, true)

        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.AddDetail,
            0,
            0
        )

        val titleText: String = typedArray.getString(R.styleable.AddDetail_adTitle) ?: ""
        val widgetState: String =
            typedArray.getString(R.styleable.AddDetail_adState) ?: "INACTIVE"
        val startIcon = typedArray.getDrawable(R.styleable.AddDetail_adStartIcon)
        endIcon = typedArray.getDrawable(R.styleable.AddDetail_adEndIcon)

        typedArray.recycle()

        background = ContextCompat.getDrawable(context, R.drawable.bg_clickable_rect)

        ivEndIcon = findViewById(R.id.iv_end_icon)
        tvState = findViewById(R.id.tv_state)
        findViewById<ImageView>(R.id.iv_start_icon).setImageDrawable(startIcon)
        tvTitle = findViewById(R.id.tv_title)
        tvTitle?.text = titleText

        changeState(widgetState)
    }

    fun changeState(state: String) {
        var bgColorStr = "#000000"
        var stateColorStr = "#252525"
        var stateStr = "0% Done"
        var titleColorStr = "#252525"

        when (state) {
            "DONE" -> {
                bgColorStr = "#63A438"
                stateColorStr = "#63A438"
                stateStr = "Done"
                titleColorStr = "#BCBBC0"
                this.endIcon = ContextCompat.getDrawable(context, R.drawable.ic_done)
                background.setTint(Color.parseColor(bgColorStr))
            }

            "ACTIVE" -> {
                stateColorStr = "#FFB703"
                stateStr = "Fill In"
                this.endIcon = ContextCompat.getDrawable(context, R.drawable.arrow_forward)
            }

            else -> {
                this.endIcon = null
            }
        }

        tvTitle?.setTextColor(Color.parseColor(titleColorStr))
        tvState?.apply {
            setTextColor(Color.parseColor(stateColorStr))
            text = stateStr
        }
        ivEndIcon?.apply {
            setImageDrawable(endIcon)
            setColorFilter(Color.parseColor(bgColorStr))
        }

    }

}