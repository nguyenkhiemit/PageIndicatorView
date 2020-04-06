package com.nguyen.indicatorview

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.nguyen.indicatorview.R

class IndicatorView: View, IndicatorListener, ViewPager.OnPageChangeListener {

    companion object {
        const val DEFAULT_ANIMATE_DURATION = 200L

        const val DEFAULT_RADIUS_SELECTED = 20

        const val DEFAULT_RADIUS_UNSELECTED = 15

        const val DEFAULT_DISTANCE = 40
    }

    private var viewPager: ViewPager? = null

    val dots = ArrayList<Dot>()

    private var animateDuration: Long = DEFAULT_ANIMATE_DURATION

    private var radiusSelected: Int = DEFAULT_RADIUS_SELECTED

    private var radiusUnselected: Int = DEFAULT_RADIUS_UNSELECTED

    private var distance: Int = DEFAULT_DISTANCE

    private var colorSelected: Int = 0

    private var colorUnselected: Int = 0

    private var currentPosition: Int = 0

    private var beforePosition: Int = 0

    private var animatorZoomIn: ValueAnimator? = null

    private var animatorZoomOut: ValueAnimator? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.IndicatorView)
        radiusSelected = typeArray.getDimensionPixelSize(R.styleable.IndicatorView_indi_radius_selected, DEFAULT_RADIUS_SELECTED)
        radiusUnselected = typeArray.getDimensionPixelSize(R.styleable.IndicatorView_indi_radius_unselected, DEFAULT_RADIUS_UNSELECTED)
        distance = typeArray.getInt(R.styleable.IndicatorView_indi_distance, DEFAULT_DISTANCE)
        colorSelected = typeArray.getColor(R.styleable.IndicatorView_indi_color_selected, Color.parseColor("#ffffff"))
        colorUnselected = typeArray.getColor(R.styleable.IndicatorView_indi_color_unselected, Color.parseColor("#ffffff"))
        typeArray.recycle()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        val yCenter = height / 2
        val d = distance + 2 * radiusUnselected
        val firstXCenter = (width / 2) - ((dots.size - 1) * d / 2)
        for(i in 0 .. dots.size - 1) {
            dots[i].setCenter(firstXCenter + d * i, yCenter)
            dots[i].currentRadius = if(i == currentPosition) radiusSelected else radiusUnselected
            dots[i].setColor(if(i == currentPosition) colorSelected else colorUnselected)
            dots[i].setAlpha(if(i == currentPosition) 255 else radiusUnselected * 255 / radiusSelected)
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredHeight = 2 * radiusSelected
        var width = 0
        var height = 0

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        if(widthMode == MeasureSpec.EXACTLY) {
            width = widthSize
        } else if(widthMode == MeasureSpec.AT_MOST) {
            width = widthSize
        } else {
            width = 0
        }

        if(heightMode == MeasureSpec.EXACTLY) {
            height = heightSize
        } else if(heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(desiredHeight, heightSize)
        } else {
            height = desiredHeight
        }
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for(dot in dots) {
            dot.draw(canvas)
        }
    }

    override fun setViewPager(viewPager: ViewPager) {
        this.viewPager = viewPager
        this.viewPager!!.addOnPageChangeListener(this)
        viewPager.adapter?.count?.let {
            initDot(it)
        }
        onPageSelected(0)
    }

    @Throws(PagesLessException::class)
    private fun initDot(count: Int) {
        if(count < 2) throw PagesLessException()
        for(i in 0 .. (count - 1)) {
            dots.add(Dot())
        }
    }

    override fun setAnimateDuration(duration: Long) {
        this.animateDuration = duration
    }

    override fun setRadiusSelected(radius: Int) {
        this.radiusSelected = radius
    }

    override fun setRadiusUnselected(radius: Int) {
        this.radiusUnselected = radius
    }

    override fun setDistanceDot(distance: Int) {
        this.distance = distance
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        beforePosition = currentPosition
        currentPosition = position

        if(beforePosition == currentPosition) {
            beforePosition = currentPosition + 1
        }

        val animationSet = AnimatorSet()
        animationSet.duration = animateDuration

        animatorZoomIn = ValueAnimator.ofInt(radiusUnselected, radiusSelected)
        animatorZoomIn!!.addUpdateListener {
            val newRadius = it.animatedValue
            changeNewRadius(currentPosition, newRadius as Int)
        }

        animatorZoomOut = ValueAnimator.ofInt(radiusSelected, radiusUnselected)
        animatorZoomOut!!.addUpdateListener {
            val newRadius = it.animatedValue
            changeNewRadius(beforePosition, newRadius as Int)
        }
        animationSet.play(animatorZoomIn).with(animatorZoomOut)
        animationSet.start()
    }

    private fun changeNewRadius(positionPerform: Int, newRadius: Int) {
        if(dots[positionPerform].currentRadius != newRadius) {
            dots[positionPerform].currentRadius = newRadius
            dots[positionPerform].setAlpha(newRadius * 255 / radiusSelected)
            invalidate()
        }
    }
}