package by.deniotokiari.arr.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.deniotokiari.arr.R
import by.deniotokiari.arr.db.entity.RssFeed
import by.deniotokiari.arr.viewmodel.OpmlImportRssFeedViewModel
import by.deniotokiari.core.extensions.gone
import by.deniotokiari.core.extensions.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class ImportRssFeedsFragment : Fragment() {

    private lateinit var addFromFileButton: View
    private lateinit var importFeedsButton: Button
    private lateinit var selectAllFeedsButton: Button
    private lateinit var rssFeedsRecyclerView: RecyclerView

    private val viewModelOpml: OpmlImportRssFeedViewModel by viewModel()

    private fun initRecyclerView(recyclerView: RecyclerView, rssFeeds: List<RssFeed>) {
        class RssFeedItemViewHolder(root: ViewGroup): RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_rss_feed_item, root, false)) {

            val feedImportCheckBox: CheckBox = itemView.findViewById(R.id.feed_import_check_box)
            val feedTitleTextView: TextView = itemView.findViewById(R.id.feed_title_text_view)

        }
        class RssFeedsRecyclerViewAdapter(private val feeds: List<RssFeed>): RecyclerView.Adapter<RssFeedItemViewHolder>() {
            private val onCheckedChangeListener: CompoundButton.OnCheckedChangeListener = CompoundButton.OnCheckedChangeListener { button, isChecked ->
                val item: RssFeed = button.tag as RssFeed

                if (isChecked) {
                    viewModelOpml.addFeedToImport(item)
                } else {
                    viewModelOpml.removeFeedToImport(item)
                }
            }

            private val onClickListener: View.OnClickListener = View.OnClickListener {
                (it.tag as CheckBox).performClick()
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RssFeedItemViewHolder = RssFeedItemViewHolder(parent)

            override fun getItemCount(): Int = feeds.size

            override fun onBindViewHolder(holder: RssFeedItemViewHolder, position: Int) {
                val item: RssFeed = feeds[position]

                holder.feedTitleTextView.tag = holder.feedImportCheckBox
                holder.feedTitleTextView.text = item.title
                holder.feedTitleTextView.setOnClickListener(onClickListener)

                holder.feedImportCheckBox.tag = item
                holder.feedImportCheckBox.setOnCheckedChangeListener(null)
                holder.feedImportCheckBox.isChecked = viewModelOpml.containsFeedToImport(item)
                holder.feedImportCheckBox.setOnCheckedChangeListener(onCheckedChangeListener)
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = RssFeedsRecyclerViewAdapter(rssFeeds)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_import_rss_feeds, container, false)

        addFromFileButton = view.findViewById(R.id.import_from_file_button)
        importFeedsButton = view.findViewById(R.id.import_feeds_button)
        selectAllFeedsButton = view.findViewById(R.id.select_all_button)
        rssFeedsRecyclerView = view.findViewById(R.id.rss_feeds_recycler)

        importFeedsButton.text = getString(R.string.IMPORT_FEEDS_WITH_COUNT, 0)

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModelOpml.getRssFeeds().observe(this, Observer {
            if (it == null) {
                // show error
            } else {
                addFromFileButton.gone()
                rssFeedsRecyclerView.visible()

                importFeedsButton.visible()
                selectAllFeedsButton.visible()

                initRecyclerView(rssFeedsRecyclerView, it)
            }
        })
        viewModelOpml.getFeedsToImport().observe(this, Observer {
            it?.also { feeds ->
                importFeedsButton.text = getString(R.string.IMPORT_FEEDS_WITH_COUNT, feeds.size)

                val isEmpty: Boolean = feeds.isEmpty()

                importFeedsButton.isEnabled = !isEmpty

                if (isEmpty) {
                    selectAllFeedsButton.text = getString(R.string.SELECT_ALL_FEEDS)
                } else {
                    selectAllFeedsButton.text = getString(R.string.CLEAR_SELECTION)
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addFromFileButton.visible()
        addFromFileButton.setOnClickListener {
            fun showFileChooser() {
                val intent: Intent = Intent(Intent.ACTION_GET_CONTENT)
                    .apply {
                        type = "*/*"
                        addCategory(Intent.CATEGORY_OPENABLE)
                    }

                try {
                    startActivityForResult(
                        Intent.createChooser(intent, getString(R.string.SELECT_A_FILE_TO_IMPORT)),
                        FILE_SELECT_REQUEST_CODE
                    )
                } catch (e: Throwable) {
                    // ignore
                }
            }

            showFileChooser()
        }

        selectAllFeedsButton.setOnClickListener {
            viewModelOpml.addOrRemoveAllFeeds()

            rssFeedsRecyclerView.adapter?.notifyDataSetChanged()
        }

        importFeedsButton.setOnClickListener {
            viewModelOpml.importSelectedFeeds()
        }
    }

    override fun onPause() {
        super.onPause()

        addFromFileButton.setOnClickListener(null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == FILE_SELECT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val uri: Uri? = data?.data

            if (uri == null) {

            } else {
                viewModelOpml.setUri(uri)
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private companion object {

        const val FILE_SELECT_REQUEST_CODE = 245

    }

}