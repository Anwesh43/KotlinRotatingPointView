package ui.anwesome.com.kotlinrotatingpointview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.rotatingpointview.RotatingPointView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RotatingPointView.create(this)
    }
}
