package hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.home.viewpager.validticketspager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.temalabor.luciferi.ejegy.R
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.UserTicket

class UserTicketsRecycleAdapter(val context: Context) :
    RecyclerView.Adapter<UserTicketsRecycleAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.listitem_usertickets,parent,false)
        return MyViewHolder(v)
    }

    private val tickets: MutableList<UserTicket> = mutableListOf()

    override fun getItemCount(): Int = tickets.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val ticket = tickets[position]
        holder.typeTextView.text = ticket.ticketType.name
        if (!(ticket.validFrom.isNullOrEmpty() or ticket.validUntil.isNullOrEmpty())) {
            holder.dateTextView.text = ticket.validUntil.subSequence(0,10)
        }
        when(ticket.ticketType.type){
            "passTicket" -> holder.itemView.background = ContextCompat.getDrawable(context,R.drawable.rounded_corners_gold)
            "lineTicket" -> holder.itemView.background = ContextCompat.getDrawable(context,R.drawable.rounded_corners_turquoise)
            "timeTicket" -> holder.itemView.background = ContextCompat.getDrawable(context,R.drawable.rounded_corners_primarydark)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(ticketPosition: Int)
    }

    private var listener : OnItemClickListener? = null

    inner class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val typeTextView = itemView.findViewById<TextView>(R.id.type)
        val dateTextView = itemView.findViewById<TextView>(R.id.date)

        init {
            itemview.setOnClickListener {
                if (listener != null && (adapterPosition != RecyclerView.NO_POSITION)){
                    listener!!.onItemClick(adapterPosition)
                }
            }
        }
    }

    fun setOnItemCickListener(listener : OnItemClickListener){
        this.listener = listener
    }

    fun addTicket(ticket: UserTicket?) {
        ticket ?: return

        tickets.add(ticket)

        notifyDataSetChanged()
    }
}