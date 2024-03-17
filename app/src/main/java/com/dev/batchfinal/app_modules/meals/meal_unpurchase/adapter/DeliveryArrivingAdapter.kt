package com.dev.batchfinal.app_modules.meals.meal_unpurchase.adapter




import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.batchfinal.app_modules.meals.meal_unpurchase.model.delivery_arriving.DeliveryArrivingResponse
import com.dev.batchfinal.app_modules.meals.meal_unpurchase.view.activity.CheckOutActivity
import com.dev.batchfinal.databinding.ItemDeliveryArrivingBinding
import com.dev.batchfinal.`interface`.CategoryListItemPosition
import com.dev.batchfinal.`interface`.DeliveryArrivingListPosition

class DeliveryArrivingAdapter(
    val checkOutActivity: CheckOutActivity,
    var delivery_arriving_list:List<DeliveryArrivingResponse.Internaldatum>,
    var listner: DeliveryArrivingListPosition<Int>
) : RecyclerView.Adapter<DeliveryArrivingAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemDeliveryArrivingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(delivery_arriving: DeliveryArrivingResponse.Internaldatum, position: Int) {
           binding.tvType.text=delivery_arriving.options.toString()

            binding.root.setOnClickListener {
                listner.onCategoryListItemPosition(delivery_arriving, position)
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
        holder.bind(delivery_arriving_list[position], position)

    }
}