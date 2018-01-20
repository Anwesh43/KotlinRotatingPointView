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
    data class RotatingPointState(var mode:Int = 0,var deg:Float = 0f,var deg2:Float = 0f) {
        fun update() {
            deg += 5
            if(mode == 1) {
                deg2+=5
                if(deg2 == 180f) {
                    mode = 0
                }
            }
        }
        fun draw(canvas:Canvas,paint:Paint,x:Float,y:Float,r:Float) {
            canvas.save()
            canvas.translate(x,y)
            canvas.save()
            canvas.rotate(deg)
            paint.style = Paint.Style.FILL
            canvas.drawCircle(r,0f,r/20,paint)
            canvas.restore()
            paint.style = Paint.Style.STROKE
            canvas.save()
            canvas.drawArc(RectF(-r,-r,r,r),deg-deg2,2*deg2,false,paint)
            canvas.restore()
            canvas.restore()
        }
        fun toggleMode() {
            if(mode == 0) {
                mode = 1
            }
        }
    }
}