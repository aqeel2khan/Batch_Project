package com.example.bottomanimationmydemo.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.bottomanimationmydemo.R
import com.example.bottomanimationmydemo.`interface`.OnListFragmentInteractionListener
import com.myfatoorah.sdk.entity.initiatepayment.PaymentMethod


class MyItemRecyclerViewAdapter(
    private val mValues: java.util.ArrayList<PaymentMethod>,
    private val mListener: OnListFragmentInteractionListener?,
    private val listener: OnRadioButtonClickListener)
    : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    private val mOnClickListener: View.OnClickListener
    private var listSelected = ArrayList<Boolean>()

    init {
        for (i in 0..mValues.size) {
            listSelected.add(false)
        }

        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as PaymentMethod
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(v.id, item, )

            changeSelected(v.id)
        }
    }

    fun changeSelected(position: Int) {
        for (i in 0..mValues.size)
            listSelected[i] = i == position
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]

        Glide.with(holder.mView.context)
            .load(item.imageUrl)
            .into(holder.mImage)
        holder.mName.text = item.paymentMethodEn
        holder.mRadioButton.isChecked = position == selectedPosition
        /*if (listSelected[position])
            holder.mcbSelected.visibility = View.VISIBLE
        else
            holder.mcbSelected.visibility = View.GONE*/

        with(holder.mView) {
            tag = item
            id = position
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mImage: ImageView = mView.findViewById(R.id.image)
        val mName: TextView = mView.findViewById(R.id.tv_card_name)
        val mRadioButton: RadioButton = mView.findViewById(R.id.rb_selected)
//        val mcbSelected: CheckBox = mView.findViewById(R.id.cbSelected)

        init {
            mRadioButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    selectedPosition = position
                    notifyDataSetChanged() // or notifyItemChanged(position) for more efficiency
                    listener.onRadioButtonClick(mValues[position])
                }
            }
        }

    }
    interface OnRadioButtonClickListener {
        fun onRadioButtonClick(item: PaymentMethod)
    }
}


/*class MyItemRecyclerViewAdapter(
    val checkOutActivityCopy: CheckOutActivityCopy,
    var mValues: ArrayList<PaymentMethod>,
    var mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {
    private val mOnClickListener: View.OnClickListener
    private var listSelected = ArrayList<Boolean>()

    init {
        for (i in 0..mValues.size) {
            listSelected.add(false)
        }

        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as PaymentMethod
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(v.id, item)

            changeSelected(v.id)
        }
    }

    fun changeSelected(position: Int) {
        for (i in 0..mValues.size)
            listSelected[i] = i == position
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: FragmentItemBinding = FragmentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mValues[position])
//        val item = mValues[position]


    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(paymentMethod: PaymentMethod) {
            Glide.with(checkOutActivityCopy)
                .load(paymentMethod.imageUrl)
                .into(binding.image)

            if (listSelected[position])
                binding.cbSelected.visibility = View.VISIBLE
            else
                binding.cbSelected.visibility = View.GONE

            with(holder.mView) {
                tag = item
                id = position
                setOnClickListener(mOnClickListener)
            }
        }

        val mImage: ImageView = mView.image
        val mcbSelected: CheckBox = mView.cbSelected

    }
}*/
