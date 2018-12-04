package by.deniotokiari.arr.thirdparty

import android.content.Context
import android.widget.ImageView
import by.deniotokiari.core.imageloader.IImageLoader
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager

class GlideImageLoader : IImageLoader {

    private lateinit var glide: RequestManager

    override fun init(context: Context): IImageLoader {
        glide = Glide.with(context)

        return this
    }

    override fun display(url: String, view: ImageView) {
        glide.load(url).into(view)
    }

}