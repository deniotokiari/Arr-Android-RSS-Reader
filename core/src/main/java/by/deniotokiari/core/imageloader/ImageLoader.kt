package by.deniotokiari.core.imageloader

import android.content.Context
import android.widget.ImageView

interface IImageLoader {

    fun init(context: Context): IImageLoader

    fun display(url: String, view: ImageView)

}