package ui.anwesome.com.rotatingpointview

/**
 * Created by anweshmishra on 20/01/18.
 */
import android.view.*
import android.content.*
import android.graphics.*
class RotatingPointView(ctx:Context):View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw(canvas:Canvas) {

    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}