package by.deniotokiari.arr.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import by.deniotokiari.arr.R
import by.deniotokiari.arr.fragment.FeedsFragment
import by.deniotokiari.arr.fragment.ImportRssFeedsFragment
import by.deniotokiari.arr.viewmodel.MainActivityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        viewModel.hasRssFeeds().observe(this, Observer { hasRssFeeds ->
            if (hasRssFeeds) {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.content, FeedsFragment())
                    .commit()
            } else {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.content, ImportRssFeedsFragment())
                    .commit()
            }
        })
    }

}
