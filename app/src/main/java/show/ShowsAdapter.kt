package show

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shows_saratedd.databinding.ViewShowItemBinding

class ShowsAdapter (
    private var items: List<Show>
//    adapter treba viewholder
    ) : RecyclerView.Adapter<ShowsAdapter.ShowViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int ): ShowViewHolder {
//        layout inflation = xml -> kotlin/java code
//        every view has a context
//        ??? parent.context
            val binding = ViewShowItemBinding.inflate(LayoutInflater.from(parent.context))
            return ShowViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
//        retrieve the correct superhero from the list and send it to the viewholder
//        which knows how to connect the view with the data
            holder.bind(items[position])
        }

        //        the adapter needs to know how many items are there in the list (starting and ending indices)
        override fun getItemCount(): Int = items.count()
//    item.size

        inner class ShowViewHolder(private var binding: ViewShowItemBinding)
            : RecyclerView.ViewHolder(binding.root) {
            fun bind(item: Show) {
                binding.showTitle.text = item.name
                binding.showDesc.text = item.description
                binding.showImage.setImageResource(item.imageResourceID)

//            binding.cardConatiner.setOnClickListener {
////                treba prikazati podatke o showu
//            }
            }
        }
    }