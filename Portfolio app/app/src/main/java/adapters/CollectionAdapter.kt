package adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.museumapp.R
import com.example.museumapp.activities.ArtworkActivity
import kotlinx.android.synthetic.main.collection_item.view.*
import model.Artwork
import toDrawable

class CollectionAdapter(private val collection: ArrayList<Artwork>): RecyclerView.Adapter<CollectionAdapter.ViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.collection_item, parent, false))
    }

    override fun getItemCount(): Int {
        return collection.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(collection[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(artwork: Artwork) {
            itemView.tvTitle.text = artwork.title
            itemView.ivCollectionImage.setImageDrawable(toDrawable(artwork.imageData, context.resources))
            itemView.setOnClickListener {
                val intent = Intent(context, ArtworkActivity::class.java)
                intent.putExtra("artwork", artwork)
                intent.putExtra("collection", collection)
                ContextCompat.startActivity(context, intent, null)
            }
        }
    }
}