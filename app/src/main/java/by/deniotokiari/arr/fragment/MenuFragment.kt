package by.deniotokiari.arr.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.deniotokiari.arr.R
import by.deniotokiari.arr.adapter.MenuGroupAdapter
import by.deniotokiari.arr.viewmodel.MenuItem
import by.deniotokiari.arr.viewmodel.MenuViewModel
import by.deniotokiari.core.imageloader.IImageLoader
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuFragment : Fragment() {

    private val menuViewModel: MenuViewModel by viewModel()
    private val imageLoader: IImageLoader by inject()

    private lateinit var menuRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val result: View = LayoutInflater.from(context).inflate(R.layout.fragment_menu, container, false)

        menuRecyclerView = result.findViewById(R.id.menu_recycler_view)

        return result
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menuRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        menuRecyclerView.adapter = MenuGroupAdapter(ArrayList(), imageLoader)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        menuViewModel.getMenuItems().observe(this, Observer { newItems ->
            val oldItems: ArrayList<MenuItem>? = (menuRecyclerView.adapter as MenuGroupAdapter?)?.items

            oldItems?.also {
                oldItems.clear()
                oldItems.addAll(newItems)
            }
        })

        menuViewModel.getMenuItemsDiff().observe(this, Observer { diff ->
            menuRecyclerView.adapter?.also { adapter ->
                diff.dispatchUpdatesTo(adapter)
            }
        })
    }

}
