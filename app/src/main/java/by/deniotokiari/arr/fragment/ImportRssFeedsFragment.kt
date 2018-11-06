package by.deniotokiari.arr.fragment

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import by.deniotokiari.arr.R
import by.deniotokiari.arr.viewmodel.ImportRssFeedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ImportRssFeedsFragment : Fragment() {

    private lateinit var mAddFromFileButton: View
    private val viewModel: ImportRssFeedViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_import_rss_feeds, container, false)

        mAddFromFileButton = view.findViewById(R.id.import_from_file_button)

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getFeedsCount().observe(this, Observer {
            if (it == 0) {

            } else {

            }
        })
    }

    override fun onStart() {
        super.onStart()

        mAddFromFileButton.setOnClickListener {
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
                } catch (e: ActivityNotFoundException) {
                    // ignore
                }
            }

            showFileChooser()
        }
    }

    override fun onPause() {
        super.onPause()

        mAddFromFileButton.setOnClickListener(null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == FILE_SELECT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val uri: Uri? = data?.data

            viewModel.setFileUri(uri)
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private companion object {

        const val FILE_SELECT_REQUEST_CODE = 245

    }

}