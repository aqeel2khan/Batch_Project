package com.example.bottomanimationmydemo.adapter
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomanimationmydemo.databinding.ItemAllCourseOrderBinding
import com.example.bottomanimationmydemo.databinding.ItemMealSubscribeListBinding
import com.example.bottomanimationmydemo.`interface`.CourseOrderListItemPosition
import com.example.bottomanimationmydemo.`interface`.MealSubscribeListPosition
import com.example.bottomanimationmydemo.meals.meal_purchase.model.subscribe_list_model.MealSubscribeListResponse
import com.example.bottomanimationmydemo.model.courseorderlist.OrderList
import com.example.bottomanimationmydemo.utils.MyConstant
import com.example.bottomanimationmydemo.utils.MyUtils

class MealSubscribeListAdapter(
    val context: Context?,
    var internalDatum: List<MealSubscribeListResponse.InternalDatum>,
    var listener: MealSubscribeListPosition<Int>
) : RecyclerView.Adapter<MealSubscribeListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemMealSubscribeListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(mealSubscribeListResponse: MealSubscribeListResponse.InternalDatum, position: Int) {

           // MyUtils.loadBackgroundImage(binding.backgroundImg, MyConstant.IMAGE_BASE_URL + courseData.course_detail.course_image)


            binding.tvDuration.text = mealSubscribeListResponse.duration.toString()
            binding.tvMealName.text = mealSubscribeListResponse.name.toString()
            binding.tvDescription.text = mealSubscribeListResponse.description.toString()

//            if (courseData.course_detail.course_duration.isEmpty()) {
//                binding.tvCal.text =
//                    courseData.course_detail.course_duration[position].calorie_burn.toString()
//            } else {
//                binding.tvCal.text = "0.0"
//            }


            binding.root.setOnClickListener {
                listener.onMealSubscribeListItemPosition(mealSubscribeListResponse, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemMealSubscribeListBinding =
            ItemMealSubscribeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return internalDatum.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(internalDatum[position], position)
    }
}