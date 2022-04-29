package el.ka.soundspec.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import el.ka.soundspec.helpers.mapAmplitudeToLevel

class SoundSpector(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var soundState = mutableListOf<Int>()
    fun setSoundState(newSoundState: MutableList<Int>) {
        soundState = newSoundState
        invalidate()
    }

    private var minLevel: Int = 0
    private var maxLevel: Int = 0
    private var countOfColumns: Int = 0

    private var w: Int = 0
    private var h: Int = 0

    fun settingSize(minLevel: Int, maxLevel: Int, countOfColumns: Int) {
        this.minLevel = minLevel
        this.maxLevel = maxLevel
        this.countOfColumns = countOfColumns
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        w = MeasureSpec.getSize(widthMeasureSpec)
        h = MeasureSpec.getSize(heightMeasureSpec)

        updateSizeOfElement()
        generateColumns()
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }

    private var elementWidth = 0
    private var elementHeight = 0

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private var color = Color.TRANSPARENT
    private var color1 = Color.rgb(230, 217, 37)
    private var color2 = Color.rgb(230, 117, 37)
    private var color3 = Color.rgb(230, 37, 37)


    private fun updateSizeOfElement() {
        elementWidth = w / countOfColumns
        elementHeight = h / maxLevel
    }


    override fun onDraw(canvas: Canvas) {
        paint.color = color
        Log.d("OnDraw", "$width | $height")
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        drawColumns(canvas)
        super.onDraw(canvas)
    }

    private val columns = mutableListOf<MutableList<Rect>>()
    private fun generateColumns() {
        columns.clear()
        for (i in 0 until countOfColumns) {
            val column = mutableListOf<Rect>()
            for (j in 0 until maxLevel) {
                val rect = Rect()
                with(rect) {
                    top = j * elementHeight
                    bottom = top + elementHeight
                    left = i * elementWidth
                    right = left + elementWidth
                }
                column.add(j, rect)
            }
            columns.add(i, column)
        }
    }


    private fun drawColumns(canvas: Canvas) {
        if (soundState.size > 0) {
            for (i in 0 until countOfColumns) {
                val columnsValue = soundState[i]
                if (columnsValue != 0) {
                    val middle: Int = maxLevel / 2 + 1
                    val c: Int = (columnsValue - 1) / 2
                    for (j in 1 until c) {
                        val colorLevel = mapAmplitudeToLevel(middle + j, 0, c, 1, 3)
                        paint.color = color1

                        val topEl = columns[i][middle + j]
                        val bottomEl = columns[i][middle - j]
                        var r = (elementHeight * 0.6).toFloat()

                        canvas.drawCircle(topEl.exactCenterX(), topEl.exactCenterY(), r, paint)
                        canvas.drawCircle(bottomEl.exactCenterX(), bottomEl.exactCenterY(), r, paint)
                        /*canvas.drawCircle(
                            columns[i][middle - j].exactCenterX(),
                            columns[i][middle - j].exactCenterY(),
                            (elementHeight * 0.6).toFloat(),
                            paint
                        )

                         */2
//                        canvas.drawRect(columns[i][middle-j], paint)
                    }
                }

                /* for (j in 0 until maxLevel) {
                     // отрисовка по низу
                     *//*
                if (j < maxLevel - columnsValue) {
                    paint.color = color
                } else {
                    paint.color = Color.BLACK
                }
                canvas.drawRect(columns[i][j], paint)
                     *//*

                    // отрисовка по середине


                }*/
            }
        }
    }
    // value = 2
    // size - value = кол-во незакрашенных
//    3 - 2 = 1

    // [0, 1, 2]

}