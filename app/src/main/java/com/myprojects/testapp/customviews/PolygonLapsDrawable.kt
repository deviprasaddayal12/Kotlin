package com.myprojects.testapp.customviews

import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.Log
import java.lang.Exception

class PolygonLapsDrawable : Drawable() {
    companion object {
        val TAG = PolygonLapsDrawable::class.java.canonicalName
    }

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

    private fun createPaint() {
        paint = Paint()
        paint.isAntiAlias = true
        paint.strokeWidth = strokeWidth
        paint.color = strokeColor
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.pathEffect = CornerPathEffect(strokeWidth / 2)
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

    override fun onBoundsChange(bounds: Rect?) {
        viewWidth = if (bounds != null) bounds.width() else 0
        viewHeight = if (bounds != null) bounds.height() else 0

        super.onBoundsChange(bounds)
    }

    override fun draw(canvas: Canvas) {
        try {
            for (i in 1 until circleCount) {
                val circleCountOffset = i - 1

                val centreX = (viewWidth / 2).toFloat()
                val centreY = (viewHeight / 2).toFloat()

                val noOfSides = circleCountOffset + sidesOffset
                val radius = circleRadius + circleCountOffset * circleSpacing

                canvas.drawPath(createPolygonalPath(noOfSides, radius, centreX, centreY), paint)
            }
        } catch (e: Exception) {
            Log.e("$TAG.onDraw.catch", e.message)
        }
    }

    override fun setAlpha(alpha: Int) {

    }

    override fun setColorFilter(colorFilter: ColorFilter?) {

    }

    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }
}
