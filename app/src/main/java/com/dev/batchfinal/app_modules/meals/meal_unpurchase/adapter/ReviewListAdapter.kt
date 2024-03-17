package com.dev.batchfinal.app_modules.meals.meal_unpurchase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.batchfinal.app_modules.meals.meal_unpurchase.model.review_list.ReviewModelResponse
import com.dev.batchfinal.databinding.ItemReviewListBinding
//import com.example.bottomanimationmydemo.databinding.ItemReviewListBinding
import com.dev.batchfinal.app_modules.meals.meal_unpurchase.view.activity.ChosenMealDetailActivity

class ReviewListAdapter(val chosenMealDetailActivity: ChosenMealDetailActivity,var review_list:List<ReviewModelResponse.Internaldatum>) : RecyclerView.Adapter<ReviewListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemReviewListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(reviewList: ReviewModelResponse.Internaldatum, position: Int) {
            binding.tvCustomerName.text = reviewList.userName
            binding.txtReviewContent.text = reviewList.review
            binding.txtRating.text = reviewList.rating.toString()

            binding.root.setOnClickListener {
                // listener.onMealDishListItemPosition(mealDishList, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReviewListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return review_list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(review_list[position], position)

    }
}