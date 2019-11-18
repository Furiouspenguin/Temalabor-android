package hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.store

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import hu.bme.aut.temalabor.luciferi.ejegy.R
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.TicketTypeWithPrice
import kotlinx.android.synthetic.main.activity_store_category.*
import org.jetbrains.anko.toast
import com.google.gson.reflect.TypeToken
import hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.store.adapter.StoreActivityRecyclerAdapter
import org.jetbrains.anko.startActivity


class StoreCategoryActivity : AppCompatActivity() {

    private lateinit var list : ArrayList<TicketTypeWithPrice>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_category)

        try {
            title = intent.getStringExtra("title")
            val listType = object : TypeToken<ArrayList<TicketTypeWithPrice>>() {}.type
            list = Gson().fromJson(intent.getStringExtra("list"),listType)
        } catch (e : Exception) {
            e.printStackTrace()
        }


        recyclerview_store_activity_tickets.layoutManager =
            LinearLayoutManager(this).apply { orientation = RecyclerView.VERTICAL }
        recyclerview_store_activity_tickets.setHasFixedSize(true)

        val adapter = StoreActivityRecyclerAdapter(this)
        recyclerview_store_activity_tickets.adapter = adapter

        if (!list.isNullOrEmpty()) {
            list.forEach {
                adapter.addTicket(it)
            }
        }


        adapter.setOnItemCickListener(object : StoreActivityRecyclerAdapter.OnItemClickListener{
            override fun onItemClick(position : Int) {
                val ticket : String = GsonBuilder().create().toJson(list[position])
                startActivity<BuyTicketActivity>(
                    "ticket" to ticket
                )
            }
        })
    }
}
