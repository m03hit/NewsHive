package mohit.newshive

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import mohit.newshive.databinding.ListNewsArticleItemBinding
import mohit.newshive.model.Article

class NewsAdapter(private var newsList: List<Article>,private val onClickListener: OnClickListener) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    class ViewHolder( val binding: ListNewsArticleItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListNewsArticleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentNews = newsList[position]
        holder.binding.tvTitle.text = currentNews.title
        holder.binding.ivArticle.load(currentNews.urlToImage)
        holder.binding.tvDescription.text=currentNews.description
        holder.binding.tvPublished.text=currentNews.publishedAt
        holder.binding.tvSource.text=currentNews.source.name
        holder.binding.root.setOnClickListener {
            onClickListener.onClick(currentNews)
        }
    }

    fun updateList(updatedList: List<Article>) {
        newsList = updatedList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    class OnClickListener(val clickListener: (article:Article) -> Unit){
        fun onClick(article: Article) = clickListener(article)
    }

}