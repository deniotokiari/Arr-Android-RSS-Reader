package by.deniotokiari.arr.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import by.deniotokiari.arr.db.entity.Article
import by.deniotokiari.arr.fragment.ArticleDetailFragment

class ArticleDetailStateFragmentViewPagerAdapter(
    fragmentManager: FragmentManager,
    private val articles: List<Article>
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment = ArticleDetailFragment.newInstance(articles[position])

    override fun getCount(): Int = articles.size

}