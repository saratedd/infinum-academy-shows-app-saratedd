package show

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shows_saratedd.databinding.ViewShowItemBinding

class ShowsAdapter(
    private var items: List<Show>,
    private val onItemClickCallback: (Show) -> Unit
//    adapter treba viewholder
) : RecyclerView.Adapter<ShowsAdapter.ShowViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
//        layout inflation = xml -> kotlin/java code
//        every view has a context
//        ??? parent.context
        val binding = ViewShowItemBinding.inflate(LayoutInflater.from(parent.context))
//            zasto predajemo binding
        return ShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
//        retrieve the correct show from the list and send it to the viewholder
//        which knows how to connect the view with the data
        holder.bind(items[position])
    }

    //      the adapter needs to know how many items are there in the list (starting and ending indices)
//    item.size
    override fun getItemCount(): Int = items.count()

    fun addAllShows(shows: List<Show>) {
        items = shows
        notifyDataSetChanged()
    }

    fun addShow(show: Show) {
        items = items + show
        notifyItemInserted(items.lastIndex)
    }

    inner class ShowViewHolder(private var binding: ViewShowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(show: Show) {
            binding.showTitle.text = show.name
            binding.showDesc.text = show.description
            binding.showImage.setImageResource(show.imageResourceID)

            binding.cardConatiner.setOnClickListener {
//                treba prikazati podatke o showu
                onItemClickCallback(show)
            }
        }
    }
}