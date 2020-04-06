package com.nguyen.indicatorview

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF

class Dot {

    private val paint: Paint by lazy {
        Paint()
    }

    private val center: PointF by lazy {
        PointF()
    }

    var currentRadius: Int = 0

    init {
        paint.isAntiAlias = true
    }

    fun setColor(color: Int) {
        paint.color = color
    }

    fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    fun setCenter(x: Int, y: Int) {
        center.set(x.toFloat(), y.toFloat())
    }

    fun draw(canvas: Canvas) {
        canvas.drawCircle(center.x, center.y, currentRadius.toFloat(), paint)
    }
}