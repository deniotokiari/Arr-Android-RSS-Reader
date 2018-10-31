package by.deniotokiari.arr.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

class LaunchActivity : AppCompatActivity() {

    override fun onStart() {
        super.onStart()

        startActivity(Intent(this, MainActivity::class.java))

        finish()
    }

}