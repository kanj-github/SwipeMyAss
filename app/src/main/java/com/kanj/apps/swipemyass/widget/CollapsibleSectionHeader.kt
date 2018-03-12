package com.kanj.apps.swipemyass.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.kanj.apps.swipemyass.R

/**
 * Created by kanj on 12/03/18.
 */
class CollapsibleSectionHeader(val mContext: Context, val mAttributeSet: AttributeSet?, val mDefStyleAttr : Int)
    : FrameLayout(mContext, mAttributeSet, mDefStyleAttr), View.OnClickListener {

    var collapsibleSection : View? = null
    private var arrowIcon: ImageView? = null
    var isCollapsed : Boolean = false
        set (value) {
            field = value
            setArrowIcon()
        }

    constructor(mContext: Context, mAttributeSet: AttributeSet) : this(mContext, mAttributeSet, 0)

    constructor(mContext: Context) : this(mContext, null, 0)

    init {
        val typedArray : TypedArray? = mContext.obtainStyledAttributes(mAttributeSet, R.styleable.CollapsibleSectionHeader,
                mDefStyleAttr, 0)
        val view = View.inflate(mContext, R.layout.widget_collapsible_section_header, this)

        typedArray?.let {
            view.findViewById<TextView>(R.id.section_title).text = it.getString(R.styleable.CollapsibleSectionHeader_sectionTitle)
            isCollapsed = it.getBoolean(R.styleable.CollapsibleSectionHeader_isCollapsed, false)
            it.recycle()
        }

        arrowIcon = view.findViewById(R.id.arrow_icon)
        arrowIcon?.setOnClickListener(this)
        setArrowIcon()
    }

    override fun onClick(view: View?) {
        if (view?.id == R.id.arrow_icon) {
            collapsibleSection?.let {
                if (isCollapsed) {
                    expandSection(it)
                } else {
                    collapseSection(it)
                }
                isCollapsed = !isCollapsed
                setArrowIcon()
            }
        }
    }

    private fun expandSection(section: View) {
        if (section.visibility == View.VISIBLE) return

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            section.visibility = View.VISIBLE
        } else {
            this.elevation = section.elevation + 1
            section.translationY = -section.height.toFloat()
            section.visibility = View.VISIBLE
            section.animate()
                    .translationY(0.0f)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            section.translationY = 0.0f
                        }
                    })
        }
    }

    private fun collapseSection(section: View) {
        if (section.visibility == View.GONE) return

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            section.visibility = View.GONE
        } else {
            this.elevation = section.elevation + 1
            section.animate()
                    .translationY(-section.height.toFloat())
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            section.visibility = View.GONE
                        }
                    })}
    }

    private fun setArrowIcon() {
        if (isCollapsed) {
            arrowIcon?.setImageResource(R.drawable.ic_pointer_down)
        } else {
            arrowIcon?.setImageResource(R.drawable.ic_pointer_up)
        }
    }
}