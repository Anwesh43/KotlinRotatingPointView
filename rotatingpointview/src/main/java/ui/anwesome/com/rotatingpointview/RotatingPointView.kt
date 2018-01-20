package ui.anwesome.com.rotatingpointview

/**
 * Created by anweshmishra on 20/01/18.
 */
import android.view.*
import android.content.*
import android.graphics.*
class RotatingPointView(ctx:Context):View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val renderer = Renderer(this)
    override fun onDraw(canvas:Canvas) {
        canvas.drawColor(Color.parseColor("#212121"))
        renderer.render(canvas,paint)
    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                renderer.handleTap()
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
        val state = RotatingPointState()
        fun draw(canvas:Canvas,paint:Paint) {
            paint.strokeWidth = r/10
            paint.color = Color.parseColor("#283593")
            state.draw(canvas,paint,x,y,r)
        }
        fun update() {
            state.update()
        }
        fun toggleMode() {
            state.toggleMode()
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
    data class Renderer(var view:RotatingPointView,var time:Int = 0) {
        var rotatingPoint:RotatingPoint?=null
        val animator = Animator(view)
        fun render(canvas:Canvas,paint:Paint) {
            if(time == 0) {
                val w = canvas.width.toFloat()
                val h = canvas.height.toFloat()
                rotatingPoint = RotatingPoint(w/2,h/2,Math.min(w,h)/3)
                animator.startAnimating()
            }
            rotatingPoint?.draw(canvas,paint)
            time++
            animator.animate {
                rotatingPoint?.update()
            }
        }
        fun handleTap() {
            rotatingPoint?.toggleMode()
        }
    }
}