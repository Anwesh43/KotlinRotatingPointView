package ui.anwesome.com.rotatingpointview

/**
 * Created by anweshmishra on 20/01/18.
 */
import android.app.Activity
import android.view.*
import android.content.*
import android.graphics.*
class RotatingPointView(ctx:Context):View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val renderer = Renderer(this)
    var rotationCompleteListener:RotatingPointCompleteListener?=null
    fun addRotationCompleteListener(completeListener: () -> Unit) {
        rotationCompleteListener = RotatingPointCompleteListener(completeListener)
    }
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
        fun update(stopcb: () -> Unit) {
            state.update(stopcb)
        }
        fun toggleMode() {
            state.toggleMode()
        }
    }
    data class RotatingPointState(var mode:Int = 0,var deg:Float = 0f,var deg2:Float = 0f) {
        fun update(stopcb:()->Unit) {
            when(mode) {
                0 -> {
                    deg += 5
                }
                1 -> {
                    deg2 += 5
                    if(deg2 == 180f) {
                        mode = 2
                        deg += 180
                    }
                }
                2 -> {
                    deg2 -= 5
                    if(deg2 < 0f) {
                        deg2 = 0f
                        mode = 0
                    }
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
            paint.strokeCap = Paint.Cap.ROUND
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
                rotatingPoint?.update{
                    view.rotationCompleteListener?.rotationCompleteListener?.invoke()
                }
            }
        }
        fun handleTap() {
            rotatingPoint?.toggleMode()
        }
    }
    companion object {
        fun create(activity:Activity):RotatingPointView {
            val view = RotatingPointView(activity)
            activity.setContentView(view)
            return view
        }
    }
    data class RotatingPointCompleteListener(var rotationCompleteListener:()->Unit)
}