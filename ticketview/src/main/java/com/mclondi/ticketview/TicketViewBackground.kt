package com.mclondi.ticketview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.Log

class TicketViewBackground(
    typedArray: TypedArray,
    context: Context,
    var paddingLeft: Int,
    var paddingRight: Int,
    var paddingTop: Int,
    var paddingBottom: Int
) : Drawable() {

    private val mBackgroundPaint = Paint()
    private val mBorderPaint = Paint()
    private val mDividerPaint = Paint()
    private val mPath = Path()
    private var mDividerStartX: Float = 0f
    private var mDividerStartY: Float = 0f
    private var mDividerStopX: Float = 0f
    private var mDividerStopY: Float = 0f
    private val mRect = RectF()
    private val mRoundedCornerArc = RectF()
    private val mScallopCornerArc = RectF()
    private var mScallopHeight: Int = 0
    private var mScallopPosition: Float = 0f
    private var mBackgroundDrawablePaint: Paint

    var orientation: Int = 0
        set(value) {
            field = value
            initElements()
        }
    var scallopPositionPercent: Float = 0f
        set(value) {
            field = value
            initElements()
        }
    var scallopHide: Boolean = false
        set(value) {
            field = value
            initElements()
        }
    var ticketBackgroundColor: Int = 0
        set(value) {
            field = value
            initElements()
        }
    var ticketBackgroundDrawable: Drawable? = null
        set(value) {
            field = value
            initElements()
        }
    var showBorder: Boolean = false
        set(value) {
            field = value
            initElements()
        }
    var borderWidth: Int = 0
        set(value) {
            field = value
            initElements()
        }
    var borderColor: Int = 0
        set(value) {
            field = value
            initElements()
        }
    var showDivider: Boolean = false
        set(value) {
            field = value
            initElements()
        }
    var scallopRadius: Int = 0
        set(value) {
            field = value
            initElements()
        }
    var dividerDashLength: Int = 0
        set(value) {
            field = value
            initElements()
        }
    var dividerDashGap: Int = 0
        set(value) {
            field = value
            initElements()
        }
    var dividerType: Int = 0
        set(value) {
            field = value
            initElements()
        }
    var dividerWidth: Int = 0
        set(value) {
            field = value
            initElements()
        }
    var dividerColor: Int = 0
        set(value) {
            field = value
            initElements()
        }
    var cornerType: Int = 0
        set(value) {
            field = value
            initElements()
        }
    var cornerTopType: Int = 0
        set(value) {
            field = value
            initElements()
        }
    var cornerBottomType: Int = 0
        set(value) {
            field = value
            initElements()
        }
    var cornerRadius: Int = 0
        set(value) {
            field = value
            initElements()
        }
    var dividerPadding: Int = 0
        set(value) {
            field = value
            initElements()
        }

    init {

        orientation = typedArray.getInt(R.styleable.TicketView_ticketOrientation, TicketView.Orientation.HORIZONTAL)
        ticketBackgroundColor = typedArray.getColor(
            R.styleable.TicketView_ticketBackgroundColor,
            context.resources.getColor(android.R.color.white)
        )
        ticketBackgroundDrawable = typedArray.getDrawable(R.styleable.TicketView_ticketBackgroundDrawable)
        scallopRadius =
            typedArray.getDimensionPixelSize(R.styleable.TicketView_ticketScallopRadius, context.dpToPx(20f))
        scallopPositionPercent = typedArray.getFloat(R.styleable.TicketView_ticketScallopPositionPercent, 50f)
        scallopHide = typedArray.getBoolean(R.styleable.TicketView_ticketScallopHide, false)
        showBorder = typedArray.getBoolean(R.styleable.TicketView_ticketShowBorder, false)
        borderWidth = typedArray.getDimensionPixelSize(R.styleable.TicketView_ticketBorderWidth, context.dpToPx(2f))
        borderColor = typedArray.getColor(
            R.styleable.TicketView_ticketBorderColor,
            context.resources.getColor(android.R.color.black)
        )
        showDivider = typedArray.getBoolean(R.styleable.TicketView_ticketShowDivider, false)
        dividerType = typedArray.getInt(R.styleable.TicketView_ticketDividerType, TicketView.DividerType.NORMAL)
        dividerWidth = typedArray.getDimensionPixelSize(R.styleable.TicketView_ticketDividerWidth, context.dpToPx(2f))
        dividerColor = typedArray.getColor(
            R.styleable.TicketView_ticketDividerColor,
            context.resources.getColor(android.R.color.darker_gray)
        )
        dividerDashLength =
            typedArray.getDimensionPixelSize(R.styleable.TicketView_ticketDividerDashLength, context.dpToPx(8f))
        dividerDashGap =
            typedArray.getDimensionPixelSize(R.styleable.TicketView_ticketDividerDashGap, context.dpToPx(4f))
        cornerType = typedArray.getInt(R.styleable.TicketView_ticketCornerType, TicketView.CornerType.NORMAL)
        cornerTopType = typedArray.getInt(R.styleable.TicketView_ticketTopCornerType, -1)
        cornerBottomType = typedArray.getInt(R.styleable.TicketView_ticketBottomCornerType, -1)
        cornerRadius = typedArray.getDimensionPixelSize(R.styleable.TicketView_ticketCornerRadius, context.dpToPx(4f))
        dividerPadding =
            typedArray.getDimensionPixelSize(R.styleable.TicketView_ticketDividerPadding, context.dpToPx(10f))

        mBackgroundDrawablePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mBackgroundDrawablePaint.color = -0x1000000
        mBackgroundDrawablePaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

        initElements()

    }


    override fun draw(canvas: Canvas) {

        doLayout(bounds.width().toFloat(), bounds.height().toFloat())
        canvas.drawPath(mPath, mBackgroundPaint)
        ticketBackgroundDrawable?.let {
            val bmp = Bitmap.createBitmap(bounds.width(), bounds.height(), Bitmap.Config.ARGB_8888)
            val cnv = Canvas(bmp)
            it.setBounds(0, 0, bounds.width(), bounds.height())
            it.draw(cnv)
            canvas.drawBitmap(bmp, 0f, 0f, mBackgroundDrawablePaint)
            bmp.recycle()

        }
        if (showBorder) {
            canvas.drawPath(mPath, mBorderPaint)
        }
        if (showDivider) {
            canvas.drawLine(mDividerStartX, mDividerStartY, mDividerStopX, mDividerStopY, mDividerPaint)
        }

    }

    override fun setAlpha(alpha: Int) {
        mBackgroundPaint.alpha = alpha
        mBackgroundDrawablePaint.alpha = alpha
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mBackgroundPaint.colorFilter = colorFilter
        mBackgroundDrawablePaint.colorFilter = colorFilter
    }

    override fun getOutline(outline: Outline) {
        outline.setRoundRect(bounds, cornerRadius.toFloat())
    }

    private fun initElements() {

        if (dividerWidth > scallopRadius) {
            dividerWidth = scallopRadius
            Log.w(
                TicketView.TAG,
                "You cannot apply divider width greater than scallop radius. Applying divider width to scallop radius."
            )
        }

        mScallopPosition = 100 / scallopPositionPercent
        mScallopHeight = scallopRadius * 2

        setBackgroundPaint()
        setBorderPaint()
        setDividerPaint()

    }


    private fun setBackgroundPaint() {
        mBackgroundPaint.alpha = 0
        mBackgroundPaint.isAntiAlias = true
        mBackgroundPaint.color = ticketBackgroundColor
        mBackgroundPaint.style = Paint.Style.FILL
    }

    private fun setBorderPaint() {
        mBorderPaint.alpha = 0
        mBorderPaint.isAntiAlias = true
        mBorderPaint.color = borderColor
        mBorderPaint.strokeWidth = borderWidth.toFloat()
        mBorderPaint.style = Paint.Style.STROKE
    }

    private fun setDividerPaint() {
        mDividerPaint.alpha = 0
        mDividerPaint.isAntiAlias = true
        mDividerPaint.color = dividerColor
        mDividerPaint.strokeWidth = dividerWidth.toFloat()

        if (dividerType == TicketView.DividerType.DASH)
            mDividerPaint.pathEffect =
                DashPathEffect(floatArrayOf(dividerDashLength.toFloat(), dividerDashGap.toFloat()), 0.0f)
        else
            mDividerPaint.pathEffect = PathEffect()
    }

    private fun doLayout(
        width: Float,
        height: Float
    ) {
        val offset: Float
        val left = paddingLeft.toFloat()
        val right = (width - paddingRight)
        val top = paddingTop.toFloat()
        val bottom = (height - paddingBottom)
        mPath.reset()

        val defaultCornerType = cornerType


        if (orientation == TicketView.Orientation.HORIZONTAL) {
            offset = (top + bottom) / mScallopPosition - scallopRadius

            if (cornerTopType >= 0) {
                cornerType = cornerTopType
            }

            when (cornerType) {
                TicketView.CornerType.ROUNDED -> {
                    mPath.arcTo(getTopLeftCornerRoundedArc(top, left), 180.0f, 90.0f, false)
                    mPath.lineTo(left + cornerRadius, top)

                    mPath.lineTo(right - cornerRadius, top)
                    mPath.arcTo(getTopRightCornerRoundedArc(top, right), -90.0f, 90.0f, false)
                }
                TicketView.CornerType.SCALLOP -> {
                    mPath.arcTo(getTopLeftCornerScallopArc(top, left), 90.0f, -90.0f, false)
                    mPath.lineTo(left + cornerRadius, top)

                    mPath.lineTo(right - cornerRadius, top)
                    mPath.arcTo(getTopRightCornerScallopArc(top, right), 180.0f, -90.0f, false)
                }
                else -> {
                    mPath.moveTo(left, top)
                    mPath.lineTo(right, top)
                }
            }

            if (!scallopHide) {
                mRect.set(
                    right - scallopRadius,
                    top + offset,
                    right + scallopRadius,
                    mScallopHeight.toFloat() + offset + top
                )
                mPath.arcTo(mRect, 270f, -180.0f, false)
            }


            cornerType = if (cornerBottomType >= 0) {
                cornerBottomType
            } else {
                defaultCornerType
            }

            when (cornerType) {
                TicketView.CornerType.ROUNDED -> {

                    mPath.arcTo(getBottomRightCornerRoundedArc(bottom, right), 0.0f, 90.0f, false)
                    mPath.lineTo(right - cornerRadius, bottom)

                    mPath.lineTo(left + cornerRadius, bottom)
                    mPath.arcTo(getBottomLeftCornerRoundedArc(left, bottom), 90.0f, 90.0f, false)

                }
                TicketView.CornerType.SCALLOP -> {

                    mPath.arcTo(getBottomRightCornerScallopArc(bottom, right), 270.0f, -90.0f, false)
                    mPath.lineTo(right - cornerRadius, bottom)

                    mPath.lineTo(left + cornerRadius, bottom)
                    mPath.arcTo(getBottomLeftCornerScallopArc(left, bottom), 0.0f, -90.0f, false)

                }
                else -> {
                    mPath.lineTo(right, bottom)
                    mPath.lineTo(left, bottom)
                }
            }

            if (!scallopHide) {
                mRect.set(
                    left - scallopRadius,
                    top + offset,
                    left + scallopRadius,
                    mScallopHeight.toFloat() + offset + top
                )
                mPath.arcTo(mRect, 90.0f, -180.0f, false)
            }
            mPath.close()

        } else {
            offset = (right + left) / mScallopPosition - scallopRadius

            cornerType = if (cornerTopType >= 0) {
                cornerTopType
            } else {
                defaultCornerType
            }

            when (cornerType) {
                TicketView.CornerType.ROUNDED -> {
                    mPath.arcTo(getTopLeftCornerRoundedArc(top, left), 180.0f, 90.0f, false)
                    mPath.lineTo(left + cornerRadius, top)

                }
                TicketView.CornerType.SCALLOP -> {

                    mPath.arcTo(getTopLeftCornerScallopArc(top, left), 90.0f, -90.0f, false)
                    mPath.lineTo(left + cornerRadius, top)

                }
                else -> mPath.moveTo(left, top)
            }

            if (!scallopHide) {
                mRect.set(
                    left + offset,
                    top - scallopRadius,
                    mScallopHeight.toFloat() + offset + left,
                    top + scallopRadius
                )
                mPath.arcTo(mRect, 180.0f, -180.0f, false)
            }

            cornerType = if (cornerTopType >= 0) {
                cornerTopType
            } else {
                defaultCornerType
            }


            when (cornerType) {
                TicketView.CornerType.ROUNDED -> {

                    mPath.lineTo(right - cornerRadius, top)
                    mPath.arcTo(getTopRightCornerRoundedArc(top, right), -90.0f, 90.0f, false)

                }
                TicketView.CornerType.SCALLOP -> {

                    mPath.lineTo(right - cornerRadius, top)
                    mPath.arcTo(getTopRightCornerScallopArc(top, right), 180.0f, -90.0f, false)

                }
                else -> mPath.lineTo(right, top)
            }

            cornerType = if (cornerBottomType >= 0) {
                cornerBottomType
            } else {
                defaultCornerType
            }

            when (cornerType) {
                TicketView.CornerType.ROUNDED -> {

                    mPath.arcTo(getBottomRightCornerRoundedArc(bottom, right), 0.0f, 90.0f, false)
                    mPath.lineTo(right - cornerRadius, bottom)

                }
                TicketView.CornerType.SCALLOP -> {

                    mPath.arcTo(getBottomRightCornerScallopArc(bottom, right), 270.0f, -90.0f, false)
                    mPath.lineTo(right - cornerRadius, bottom)

                }
                else -> mPath.lineTo(right, bottom)
            }

            if (!scallopHide) {
                mRect.set(
                    left + offset,
                    bottom - scallopRadius,
                    mScallopHeight.toFloat() + offset + left,
                    bottom + scallopRadius
                )
                mPath.arcTo(mRect, 0.0f, -180.0f, false)
            }

            cornerType = if (cornerBottomType >= 0) {
                cornerBottomType
            } else {
                defaultCornerType
            }

            when (cornerType) {
                TicketView.CornerType.ROUNDED -> {

                    mPath.arcTo(getBottomLeftCornerRoundedArc(left, bottom), 90.0f, 90.0f, false)
                    mPath.lineTo(left, bottom - cornerRadius)

                }
                TicketView.CornerType.SCALLOP -> {

                    mPath.arcTo(getBottomLeftCornerScallopArc(left, bottom), 0.0f, -90.0f, false)
                    mPath.lineTo(left, bottom - cornerRadius)

                }
                else -> mPath.lineTo(left, bottom)
            }
            mPath.close()
        }

        // divider
        if (orientation == TicketView.Orientation.HORIZONTAL) {
            mDividerStartX = left + scallopRadius.toFloat() + dividerPadding.toFloat()
            mDividerStartY = scallopRadius.toFloat() + top + offset
            mDividerStopX = right - scallopRadius.toFloat() - dividerPadding.toFloat()
            mDividerStopY = scallopRadius.toFloat() + top + offset
        } else {
            mDividerStartX = scallopRadius.toFloat() + left + offset
            mDividerStartY = top + scallopRadius.toFloat() + dividerPadding.toFloat()
            mDividerStopX = scallopRadius.toFloat() + left + offset
            mDividerStopY = bottom - scallopRadius.toFloat() - dividerPadding.toFloat()
        }

    }

    private fun getTopLeftCornerRoundedArc(top: Float, left: Float): RectF {
        mRoundedCornerArc.set(left, top, left + cornerRadius * 2, top + cornerRadius * 2)
        return mRoundedCornerArc
    }

    private fun getTopRightCornerRoundedArc(top: Float, right: Float): RectF {
        mRoundedCornerArc.set(right - cornerRadius * 2, top, right, top + cornerRadius * 2)
        return mRoundedCornerArc
    }

    private fun getBottomLeftCornerRoundedArc(left: Float, bottom: Float): RectF {
        mRoundedCornerArc.set(left, bottom - cornerRadius * 2, left + cornerRadius * 2, bottom)
        return mRoundedCornerArc
    }

    private fun getBottomRightCornerRoundedArc(bottom: Float, right: Float): RectF {
        mRoundedCornerArc.set(right - cornerRadius * 2, bottom - cornerRadius * 2, right, bottom)
        return mRoundedCornerArc
    }

    private fun getTopLeftCornerScallopArc(top: Float, left: Float): RectF {
        mScallopCornerArc.set(left - cornerRadius, top - cornerRadius, left + cornerRadius, top + cornerRadius)
        return mScallopCornerArc
    }

    private fun getTopRightCornerScallopArc(top: Float, right: Float): RectF {
        mScallopCornerArc.set(right - cornerRadius, top - cornerRadius, right + cornerRadius, top + cornerRadius)
        return mScallopCornerArc
    }

    private fun getBottomLeftCornerScallopArc(left: Float, bottom: Float): RectF {
        mScallopCornerArc.set(left - cornerRadius, bottom - cornerRadius, left + cornerRadius, bottom + cornerRadius)
        return mScallopCornerArc
    }

    private fun getBottomRightCornerScallopArc(bottom: Float, right: Float): RectF {
        mScallopCornerArc.set(right - cornerRadius, bottom - cornerRadius, right + cornerRadius, bottom + cornerRadius)
        return mScallopCornerArc
    }

}