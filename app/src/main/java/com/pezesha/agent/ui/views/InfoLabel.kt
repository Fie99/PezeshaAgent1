package com.pezesha.agent.ui.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.pezesha.agent.R

class InfoLabel(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var tvTitle: TextView? = null
    private var tvContent: TextView? = null
    private var iconRef: Drawable? = null

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.info_label_view, this, true)


        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.InfoLabel,
            0,
            0
        )

        val contentText: String = typedArray.getString(R.styleable.InfoLabel_ilContent) ?: ""
        val title: String = typedArray.getString(R.styleable.InfoLabel_ilTitle) ?: ""
        iconRef = typedArray.getDrawable(R.styleable.InfoLabel_ilIcon)

        typedArray.recycle()

        tvContent = findViewById(R.id.tv_content)
        tvTitle = findViewById(R.id.tv_title)

        setLabelTitle(title, iconRef)
        setContent(contentText)
    }

    fun setContent(content: String) {
        tvContent?.text = content
    }

    fun setLabelTitle(title: String, icon: Drawable? = null) {
        tvTitle?.apply {
            icon?.let { setCompoundDrawablesWithIntrinsicBounds(null, it, null, null) }
            text = title
        }
    }

}