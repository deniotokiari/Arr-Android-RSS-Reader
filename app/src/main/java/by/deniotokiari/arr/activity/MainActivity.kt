package by.deniotokiari.arr.activity

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import by.deniotokiari.arr.R
import by.deniotokiari.arr.fragment.ContentFragment
import by.deniotokiari.arr.fragment.ImportRssFeedsFragment
import by.deniotokiari.arr.viewmodel.MainActivityViewModel
import by.deniotokiari.arr.viewmodel.MenuStateViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModel()
    private val menuStateViewModel: MenuStateViewModel by viewModel()

    private var menuOpenState: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        viewModel.hasRssFeeds().observe(this, Observer { hasRssFeeds ->
            if (hasRssFeeds) {
                viewModel.loadArticles()

                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.content, ContentFragment())
                    .commit()
            } else {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.content, ImportRssFeedsFragment())
                    .commit()
            }
        })
        menuStateViewModel.isMenuOpen().observe(this, Observer { isOpen ->
            menuOpenState = isOpen
        })
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (supportFragmentManager.backStackEntryCount == 0) {
                val contentFragment: ContentFragment? =
                    supportFragmentManager.findFragmentById(R.id.content) as? ContentFragment

                contentFragment?.also {
                    if (!menuOpenState) {
                        menuStateViewModel.shouldOpenMenu(true)

                        return true
                    }
                }
            }
        }

        return super.onKeyUp(keyCode, event)
    }

}
