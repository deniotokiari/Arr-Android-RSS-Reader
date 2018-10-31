package by.deniotokiari.arr.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import by.deniotokiari.arr.R
import by.deniotokiari.arr.viewmodel.MainActivityViewModel
import by.deniotokiari.core.extensions.hide
import by.deniotokiari.core.extensions.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModel()

    private lateinit var addFeedsContainer: View
    private lateinit var addFeedView: View
    private lateinit var importFeedsView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        addFeedsContainer = findViewById(R.id.add_feeds_container)
        addFeedView = findViewById(R.id.add_feed)
        importFeedsView = findViewById(R.id.import_feeds)
    }

    override fun onStart() {
        super.onStart()

        viewModel.hasRssFeeds().observe(this, Observer { hasRssFeeds ->
            if (hasRssFeeds) {
                // init menu fragment and content fragment
                addFeedsContainer.hide()
            } else {
                addFeedsContainer.show()
            }
        })
    }

}
