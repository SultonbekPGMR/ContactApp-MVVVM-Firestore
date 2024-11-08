package uz.gita.contactappfirestore.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter
import uz.gita.contactappfirestore.R
import uz.gita.contactappfirestore.data.model.ContactData
import uz.gita.contactappfirestore.databinding.ItemRvBinding

class RvAdapter() : RecyclerSwipeAdapter<RvAdapter.Holder>() {

    private var funEdit: ((contactData: ContactData) -> Unit)? = null
    private var funDelete: ((contactData: ContactData) -> Unit)? = null
    private var funCall: ((contactData: ContactData) -> Unit)? = null

    private val list = ArrayList<ContactData>()

    fun setFunEdit(block: (contactData: ContactData) -> Unit) {
        funEdit = block
    }

    fun setFunCall(block: (contactData: ContactData) -> Unit) {
        funCall = block
    }

    fun setFunDelete(block: (contactData: ContactData) -> Unit) {
        funDelete = block
    }

    fun submitList(newList: List<ContactData>) {
        val diffCallback = ContactDiffCallback(list, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        list.clear()
        list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class Holder(private val binding: ItemRvBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(contactData: ContactData) {
            binding.txtName.text = contactData.name
            binding.txtNumber.text = contactData.phoneNumber
        }

        // btn in swipe listener
        init {
            binding.btnEdit.setOnClickListener {
                funEdit?.invoke(list[adapterPosition])
                binding.swipe.close()
            }
            binding.btnDelete.setOnClickListener {
                funDelete?.invoke(list[adapterPosition])
                binding.swipe.close()
            }
        }


        // btn call
        init {
            binding.btnCall.setOnClickListener { funCall?.invoke(list[adapterPosition]) }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder = Holder(
        ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = list.size
    override fun getSwipeLayoutResourceId(position: Int): Int {
        return R.id.swipe
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.onBind(list[position])
        mItemManger.bindView(holder.itemView, position)

    }



    class ContactDiffCallback(
        private val oldList: List<ContactData>,
        private val newList: List<ContactData>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }


}

