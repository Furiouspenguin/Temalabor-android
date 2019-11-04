package hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import hu.bme.aut.temalabor.luciferi.ejegy.R

class StoreFragment : Fragment() {

    private lateinit var storeViewModel: StoreViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        storeViewModel =
            ViewModelProviders.of(this).get(StoreViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_store, container, false)
        val textView: TextView = root.findViewById(R.id.text_store)
        storeViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }

    //TODO: a tickets, a tickets/buy és a tickets/validate kell itt
}