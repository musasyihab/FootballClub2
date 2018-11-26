package com.musasyihab.footballclub2.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.musasyihab.footballclub2.R
import kotlinx.android.synthetic.main.error_page_view.view.*

class ErrorPageView(private val mContext: Context, mAttributes: AttributeSet): LinearLayout(mContext, mAttributes) {

    private var onReloadClick: OnReloadClick? =null

    init {

        LayoutInflater.from(mContext).inflate(R.layout.error_page_view, this, true)

        errorPageReload.setOnClickListener {
            onReloadClick?.clickReload()
        }

    }

    fun setListener(listener: OnReloadClick) {
        onReloadClick = listener
    }

    interface OnReloadClick {
        fun clickReload()
    }

}