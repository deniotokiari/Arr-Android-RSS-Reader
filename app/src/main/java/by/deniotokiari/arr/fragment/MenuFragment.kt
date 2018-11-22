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
import by.deniotokiari.arr.db.entity.RssFeed
import by.deniotokiari.arr.viewmodel.MenuViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuFragment : Fragment() {

    private val menuViewModel: MenuViewModel by viewModel()

    private lateinit var menuRecyclerView: RecyclerView
    private var items: List<RssFeed>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val result: View = LayoutInflater.from(context).inflate(R.layout.fragment_menu, container, false)

        menuRecyclerView = result.findViewById(R.id.menu_recycler_view)

        return result
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menuRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        menuViewModel.getMenuItems().observe(this, Observer {
            items = it

            menuRecyclerView.adapter?.notifyDataSetChanged()
        })
    }

}