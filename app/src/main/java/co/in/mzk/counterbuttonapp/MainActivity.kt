package co.`in`.mzk.counterbuttonapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import co.`in`.mzk.counterbutton.CounterButton

class MainActivity : AppCompatActivity() {

    companion object{
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button : CounterButton = findViewById(R.id.button_cart);

        button.setCartListener(object : CounterButton.OnCountChangeListener {
            override fun onClick(count: Int, added: Boolean, view: View) {
                Log.d(TAG, "onClick: $count, $added")
            }
        })

        button.setCount(3)

    }
}