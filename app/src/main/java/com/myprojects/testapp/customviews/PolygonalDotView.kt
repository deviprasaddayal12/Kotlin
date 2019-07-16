package com.myprojects.testapp.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.CornerPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.myprojects.testapp.R
import java.lang.Exception

class PolygonalDotView : View {
    val TAG = PolygonalDotView::class.java.canonicalName

    private lateinit var paint: Paint

    val DEFAULT_STROKE_COLOR = 0xAD1457
    val DEFAULT_STROKE_WIDTH = 5f
    val DEFAULT_CIRCLE_COUNT = 1
    val DEFAULT_CIRCLE_SPACING = 5
    val DEFAULT_CIRCLE_RADIUS = 50

    var strokeWidth: Float = DEFAULT_STROKE_WIDTH
    var strokeColor: Int = DEFAULT_STROKE_COLOR
    var circleCount: Int = DEFAULT_CIRCLE_COUNT
    var circleRadius: Float = DEFAULT_CIRCLE_RADIUS.toFloat()
    var circleSpacing: Float = DEFAULT_CIRCLE_SPACING.toFloat()

    var viewWidth: Int = 0
    var viewHeight: Int = 0
    var sidesOffset: Int = 3

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PolygonalDotView)

        strokeColor = context.resources.getColor(R.color.colorAccent)
        strokeWidth = typedArray.getFloat(typedArray.getIndex(R.styleable.PolygonalDotView_pdv_strokeWidth), DEFAULT_STROKE_WIDTH)
        circleCount = typedArray.getInt(typedArray.getIndex(R.styleable.PolygonalDotView_pdv_circleCount), DEFAULT_CIRCLE_COUNT)
        circleRadius = typedArray.getInt(typedArray.getIndex(R.styleable.PolygonalDotView_pdv_circleRadius), DEFAULT_CIRCLE_RADIUS).toFloat()
        circleSpacing = typedArray.getInt(typedArray.getIndex(R.styleable.PolygonalDotView_pdv_circleSpacing), DEFAULT_CIRCLE_SPACING).toFloat()

        Log.i("$TAG.init", "Radius is: $circleRadius & Spacing is: $circleSpacing")

        typedArray.recycle()

        initPaint()
    }

    private fun initPaint() {
        paint = Paint()
        paint.isAntiAlias = true
        paint.strokeWidth = strokeWidth
        paint.color = strokeColor
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.pathEffect = CornerPathEffect(strokeWidth / 2)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        viewWidth = w
        viewHeight = h
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas?) {
        if (isInEditMode)
            return

        try {
            for (i in 1 until circleCount) {
                val circleCountOffset = i - 1

                val centreX = (viewWidth / 2).toFloat()
                val centreY = (viewHeight / 2).toFloat()

                val noOfSides = circleCountOffset + sidesOffset
                val radius = circleRadius + circleCountOffset * circleSpacing

                canvas?.drawPath(createPolygonalPath(noOfSides, radius, centreX, centreY), paint)
            }
        } catch (e: Exception) {
            Log.e("$TAG.onDraw.catch", e.message)
        }
    }

    private fun createPolygonalPath(noOfSides: Int, radius: Float, centreX: Float, centreY: Float): Path {
        val path = Path()
        val angle = getAngleSubtendedByEachPathArc(noOfSides)

        path.moveTo(centreX + radius * Math.cos(0.0).toFloat(), centreY + radius * Math.sin(0.0).toFloat())
        for (i in noOfSides downTo 1) {
            path.lineTo(centreX + radius * Math.cos(angle * i).toFloat(), centreY + radius * Math.sin(angle * i).toFloat())
        }

        path.close()
        return path
    }

    private fun getAngleSubtendedByEachPathArc(noOfSides: Int): Double {
        return (2.0 * Math.PI) / noOfSides
    }
}