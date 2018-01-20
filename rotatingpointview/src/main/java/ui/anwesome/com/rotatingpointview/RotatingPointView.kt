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
    data class Animator(var view:RotatingPointView,var animated:Boolean = false) {
        fun animate(updatecb:()->Unit) {
            if(animated) {
                updatecb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                }
                catch(ex:Exception) {

                }
            }
        }
        fun startAnimating() {
            if(!animated) {
                animated = true
                view.postInvalidate()
            }
        }
    }
    data class RotatingPoint(var x:Float,var y:Float,var r:Float) {
        fun draw(canvas:Canvas,paint:Paint) {
            paint.strokeWidth = r/10

        }
        fun update() {

        }
        fun toggleMode() {

        }
    }
}