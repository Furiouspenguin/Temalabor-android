package hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.home.viewpager.validticketspager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.temalabor.luciferi.ejegy.R
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.UserTicket

class UserTicketsRecycleAdapter : RecyclerView.Adapter<UserTicketsRecycleAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.usertickets_listitem,parent,false)
        return MyViewHolder(v)
    }

    private val tickets: MutableList<UserTicket> = mutableListOf()

    override fun getItemCount(): Int = tickets.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val ticket = tickets[position]
        holder.typeTextView.text = ticket.ticketType.name
        holder.dateTextView.text = ticket.validUntil
    }

    inner class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val typeTextView = itemView.findViewById<TextView>(R.id.type)
        val dateTextView = itemView.findViewById<TextView>(R.id.date)
    }


    fun addTicket(ticket: UserTicket?) {
        ticket ?: return

        tickets.add(ticket)
        notifyDataSetChanged()
    }
}