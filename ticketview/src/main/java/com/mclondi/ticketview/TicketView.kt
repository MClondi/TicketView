package com.mclondi.ticketview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.IntDef

class TicketView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr)  {

    val background : TicketViewBackground?



    init {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TicketView)
            background = TicketViewBackground(typedArray, context, paddingLeft, paddingRight, paddingTop, paddingBottom)
            val stateList = ColorStateList(
                arrayOf(intArrayOf()),
                intArrayOf(context.resources.getColor(android.R.color.white))
            )
            setBackground( RippleDrawable( stateList, background,null ))
            typedArray.recycle()
        } else {
            background = null
        }

        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }


    var ticketBackgroundDrawable: Drawable? = null
        set(value) {
            field = value
            background?.ticketBackgroundDrawable = value
            invalidate()
        }


    companion object {
        const val TAG = "TICKET_VIEW"
    }

    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    @IntDef(Orientation.HORIZONTAL, Orientation.VERTICAL)
    annotation class Orientation {
        companion object {
            const val HORIZONTAL = 0
            const val VERTICAL = 1
        }
    }

    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    @IntDef(DividerType.NORMAL, DividerType.DASH)
    annotation class DividerType {
        companion object {
            const val NORMAL = 0
            const val DASH = 1
        }
    }

    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    @IntDef(CornerType.NORMAL, CornerType.ROUNDED)
    annotation class CornerType {
        companion object {
            const val NORMAL = 0
            const val ROUNDED = 1
            const val SCALLOP = 2
        }
    }

}
