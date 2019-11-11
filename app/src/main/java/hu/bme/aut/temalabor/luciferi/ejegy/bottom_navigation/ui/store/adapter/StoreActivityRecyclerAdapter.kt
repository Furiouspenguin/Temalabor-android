package hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.store.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.temalabor.luciferi.ejegy.R
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.TicketTypeWithPrice
import kotlinx.android.synthetic.main.ticket_type_primarydark.view.*
import org.jetbrains.anko.backgroundColor

class StoreActivityRecyclerAdapter(val context : Context) :
/*ListAdapter<TicketTypeWithPrice, StoreRecyclerAdapter.MyViewHolder>(
    object : DiffUtil.ItemCallback<TicketTypeWithPrice>(){
        override fun areItemsTheSame(oldItem: TicketTypeWithPrice, newItem: TicketTypeWithPrice): Boolean {
            return oldItem.type == newItem.type
        }

        override fun areContentsTheSame(oldItem: TicketTypeWithPrice, newItem: TicketTypeWithPrice): Boolean {
            return oldItem.typeId == newItem.typeId &&
                    oldItem.type == newItem.type &&
                    oldItem.name == newItem.name &&
                    oldItem.line == newItem.line &&
                    oldItem.validFor == newItem.validFor &&
                    oldItem.validTimeUnit == newItem.validTimeUnit &&
                    oldItem.price == newItem.price
        }
})*/
    RecyclerView.Adapter<StoreActivityRecyclerAdapter.MyViewHolder>(){

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
    fun setOnItemCickListener(listener : OnItemClickListener){
        this.listener = listener
    }
    private var listener : OnItemClickListener? = null


    private val tickets: MutableList<TicketTypeWithPrice> = mutableListOf()

    override fun getItemCount(): Int = tickets.size


    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView.apply {
        background = ContextCompat.getDrawable(context,R.drawable.rounded_corners_gold)
    }){
        val ticketNameItemView : TextView = itemView.ticket_name
        val ticketPriceItemView : TextView = itemView.ticket_price

        init {
            itemView.setOnClickListener {
                if (listener != null && adapterPosition != RecyclerView.NO_POSITION) {
                    listener!!.onItemClick(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(/*parent.*/context).inflate(R.layout.ticket_store_activity_item,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentTicketType = tickets[position]//getItem(position)
        holder.ticketNameItemView.text =currentTicketType.name
        holder.ticketPriceItemView.text = "${currentTicketType.price} HUF"
        when(currentTicketType.type){
            "passTicket" -> holder.itemView.background = ContextCompat.getDrawable(context,R.drawable.rounded_corners_gold)
            "lineTicket" -> holder.itemView.background = ContextCompat.getDrawable(context,R.drawable.rounded_corners_turquoise)
            "timeTicket" -> holder.itemView.background = ContextCompat.getDrawable(context,R.drawable.rounded_corners_primarydark)
        }
    }

    fun addTicket(ticket : TicketTypeWithPrice?) {
        ticket ?: return

        tickets.add(ticket)

        notifyDataSetChanged()
    }
}