package by.deniotokiari.arr.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import by.deniotokiari.arr.R
import by.deniotokiari.arr.viewmodel.CurrentRssFeedViewModel
import by.deniotokiari.arr.viewmodel.MenuStateViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ContentFragment : Fragment() {

    private val menuStateViewModel: MenuStateViewModel by sharedViewModel()
    private val currentRssFeedViewModel: CurrentRssFeedViewModel by sharedViewModel()

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var menu: View
    private lateinit var toolbar: Toolbar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_feeds, container, false)

        drawerLayout = view.findViewById(R.id.left_menu_drawer_layout)
        menu = view.findViewById(R.id.menu)

        drawerLayout.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {
            override fun onDrawerOpened(drawerView: View) {
                menuStateViewModel.isMenuOpen(true)
            }

            override fun onDrawerClosed(drawerView: View) {
                menuStateViewModel.isMenuOpen(false)
            }
        })

        toolbar = view.findViewById(R.id.toolbar)

        toolbar.setTitle(R.string.LAST_ARTICLES)

        setHasOptionsMenu(true)

        (activity as AppCompatActivity).apply {
            setSupportActionBar(toolbar)

            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setHomeAsUpIndicator(R.drawable.ic_menu)
            }
        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        menuStateViewModel.shouldOpenMenu().observe(this, Observer {
            if (it) {
                drawerLayout.openDrawer(menu)
            } else {
                drawerLayout.closeDrawer(menu)
            }
        })

        currentRssFeedViewModel.getCurrentItem().observe(this, Observer {
            toolbar.title = it.title

            childFragmentManager
                .beginTransaction()
                .replace(R.id.content, ArticlesFragment.newInstance(it.id))
                .commit()

            menuStateViewModel.shouldOpenMenu(false)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager
            .beginTransaction()
            .replace(R.id.menu, MenuFragment())
            .replace(R.id.content, ArticlesFragment.newInstance())
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                menuStateViewModel.shouldOpenMenu(true)

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}