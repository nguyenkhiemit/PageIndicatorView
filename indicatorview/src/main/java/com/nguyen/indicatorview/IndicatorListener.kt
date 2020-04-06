package com.nguyen.indicatorview

import androidx.viewpager.widget.ViewPager

interface IndicatorListener {

    @Throws(PagesLessException::class)
    fun setViewPager(viewPager: ViewPager)

    fun setAnimateDuration(duration: Long)

    fun setRadiusSelected(radius: Int)

    fun setRadiusUnselected(radius: Int)

    fun setDistanceDot(distance: Int)
}