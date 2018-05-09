package com.youyu.liandong.widget.indicator.slidebar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.view.View

/**
 * Created by csc on 2018/5/4.
 * explain：
 */
internal class SpringBar  constructor(context: Context, springColor: Int, private val maxRadiusPercent: Float = 0.9f, private val minRadiusPercent: Float = 0.35f) : View(context), ScrollBar {
    private var tabWidth: Int = 0
    private val paint: Paint
    private val path: Path
    private val foot: Point
    private val head: Point
    private var radiusMax: Float = 0.toFloat()
    private var radiusMin: Float = 0.toFloat()
    private var radiusOffset: Float = 0.toFloat()
    private val acceleration = 0.5f
    private val headMoveOffset = 0.6f
    private val footMoveOffset = 1 - headMoveOffset

    override val slideView: View
        get() = this

    override val gravity: ScrollBar.Gravity
        get() = ScrollBar.Gravity.CENTENT_BACKGROUND

    private var positionOffset: Float = 0.toFloat()

    init {
        foot = Point()
        head = Point()
        path = Path()
        paint = Paint()
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.strokeWidth = 1f
        paint.color = springColor
    }

    override fun getHeight(tabHeight: Int): Int {
        var half:Int= tabHeight / 2
        foot.y = half.toFloat()
        head.y = half.toFloat()
        radiusMax = half * maxRadiusPercent
        radiusMin = half * minRadiusPercent
        radiusOffset = radiusMax - radiusMin
        return tabHeight
    }

    override fun getWidth(tabWidth: Int): Int {
        this.tabWidth = tabWidth
        if (positionOffset < 0.02f || positionOffset > 0.98f) {
            onPageScrolled(0, 0f, 0)
        }
        return 2 * tabWidth
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        this.positionOffset = positionOffset
        if (positionOffset < 0.02f || positionOffset > 0.98f) {
            head.x = getOffsetX(0f)
            foot.x = getOffsetX(0f)
            head.radius = radiusMax
            foot.radius = radiusMax
        } else {
            val radiusOffsetHead = 0.5f
            if (positionOffset < radiusOffsetHead) {
                head.radius = radiusMin
            } else {
                head.radius = (positionOffset - radiusOffsetHead) / (1 - radiusOffsetHead) * radiusOffset + radiusMin
            }
            val radiusOffsetFoot = 0.5f
            if (positionOffset < radiusOffsetFoot) {
                foot.radius = (1 - positionOffset / radiusOffsetFoot) * radiusOffset + radiusMin
            } else {
                foot.radius = radiusMin
            }
            var headX = 0f
            if (positionOffset > headMoveOffset) {
                val positionOffsetTemp = (positionOffset - headMoveOffset) / (1 - headMoveOffset)
                headX = ((Math.atan((positionOffsetTemp * acceleration * 2f - acceleration).toDouble()) + Math.atan(acceleration.toDouble())) / (2 * Math
                        .atan(acceleration.toDouble()))).toFloat()
            }
            // x
            head.x = getOffsetX(positionOffset) - headX * getPositionDistance(position)
            var footX = 1f
            if (positionOffset < footMoveOffset) {
                val positionOffsetTemp = positionOffset / footMoveOffset
                footX = ((Math.atan((positionOffsetTemp * acceleration * 2f - acceleration).toDouble()) + Math.atan(acceleration.toDouble())) / (2 * Math
                        .atan(acceleration.toDouble()))).toFloat()
            }
            foot.x = getOffsetX(positionOffset) - footX * getPositionDistance(position)
        }
    }

    private fun getOffsetX(positionOffset: Float): Float {
        return (2 * tabWidth).toFloat() - (tabWidth / 4).toFloat() - tabWidth * (1 - positionOffset) + tabWidth / 4.0f
    }

    private fun getPositionDistance(position: Int): Float {
        return tabWidth.toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        makePath()
        canvas.drawColor(Color.TRANSPARENT)
        // canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.OVERLAY);
        canvas.drawPath(path, paint)
        canvas.drawCircle(head.x, head.y, head.radius, paint)
        canvas.drawCircle(foot.x, foot.y, foot.radius, paint)
        super.onDraw(canvas)
    }

    private fun makePath() {
        val headOffsetX = (foot.radius * Math.sin(Math.atan(((head.y - foot.y) / (head.x - foot.x)).toDouble()))).toFloat()
        val headOffsetY = (foot.radius * Math.cos(Math.atan(((head.y - foot.y) / (head.x - foot.x)).toDouble()))).toFloat()

        val footOffsetX = (head.radius * Math.sin(Math.atan(((head.y - foot.y) / (head.x - foot.x)).toDouble()))).toFloat()
        val footOffsetY = (head.radius * Math.cos(Math.atan(((head.y - foot.y) / (head.x - foot.x)).toDouble()))).toFloat()

        val x1 = foot.x - headOffsetX
        val y1 = foot.y + headOffsetY

        val x2 = foot.x + headOffsetX
        val y2 = foot.y - headOffsetY

        val x3 = head.x - footOffsetX
        val y3 = head.y + footOffsetY

        val x4 = head.x + footOffsetX
        val y4 = head.y - footOffsetY

        val anchorX = (head.x + foot.x) / 2
        val anchorY = (head.y + foot.y) / 2

        path.reset()
        path.moveTo(x1, y1)
        path.quadTo(anchorX, anchorY, x3, y3)
        path.lineTo(x4, y4)
        path.quadTo(anchorX, anchorY, x2, y2)
        path.lineTo(x1, y1)
    }

    private inner class Point {

        var x: Float = 0.toFloat()
        var y: Float = 0.toFloat()
        var radius: Float = 0.toFloat()

    }
}
