package ui.anwesome.com.kotlinrotatingpointview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import ui.anwesome.com.rotatingpointview.RotatingPointView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var view = RotatingPointView.create(this)
        view.addRotationCompleteListener {
            Toast.makeText(this,"Rotation Complete",Toast.LENGTH_SHORT).show()
        }
        fullScreen()
    }
}
fun AppCompatActivity.fullScreen() {
    supportActionBar?.hide()
    window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
}
