package by.deniotokiari.arr.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import by.deniotokiari.arr.R
import by.deniotokiari.arr.viewmodel.MenuStateViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FeedsFragment : Fragment() {

    private val menuStateViewModel: MenuStateViewModel by sharedViewModel()

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var menu: View

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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager
            .beginTransaction()
            .replace(R.id.menu, MenuFragment())
            .commit()
    }

}