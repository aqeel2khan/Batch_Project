package com.dev.batchfinal.app_modules.meals.meal_unpurchase.adapter




import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.batchfinal.app_modules.meals.meal_unpurchase.model.delivery_arriving.DeliveryArrivingResponse
import com.dev.batchfinal.app_modules.meals.meal_unpurchase.view.activity.CheckOutActivity
import com.dev.batchfinal.databinding.ItemDeliveryArrivingBinding
import com.dev.batchfinal.`interface`.DeliveryArrivingListPosition

class DeliveryArrivingAdapter(
    val checkOutActivity: CheckOutActivity,
    var delivery_arriving_list:List<DeliveryArrivingResponse.Internaldatum>,
    var listner: DeliveryArrivingListPosition<Int>
) : RecyclerView.Adapter<DeliveryArrivingAdapter.ViewHolder>() {
    private var previousPosition = 0

    inner class ViewHolder(val binding: ItemDeliveryArrivingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(delivery_arriving: List<DeliveryArrivingResponse.Internaldatum>, position: Int) {
           binding.tvType.text=delivery_arriving[position].options.toString()
            if (position==previousPosition){
                previousPosition=-1
                binding.cardCall.setCardBackgroundColor(Color.parseColor("#F1E6DA"))
            }else{
                if(delivery_arriving[position].isSelected==true){
                    binding.cardCall.setCardBackgroundColor(Color.parseColor("#F1E6DA"))
                }else{
                    binding.cardCall.setCardBackgroundColor(Color.parseColor("#EEE8E8"))
                }
            }
            binding.root.setOnClickListener {
                listner.onCategoryListItemPosition(delivery_arriving, position)
                if(delivery_arriving[position].isSelected==false || delivery_arriving[position].isSelected==null){
                    for (i in 0 until delivery_arriving_list.size)
                        if (position==i){
                            delivery_arriving[i].isSelected=true
                            notifyDataSetChanged()
                        }else{
                            delivery_arriving[i].isSelected=false
                            notifyDataSetChanged()
                        }
                }else {
                    notifyDataSetChanged()

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDeliveryArrivingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return delivery_arriving_list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(delivery_arriving_list, position)

    }
}