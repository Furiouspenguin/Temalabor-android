package hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.home.viewpager.idpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_pager_id.*
import net.glxn.qrgen.android.QRCode
import org.jetbrains.anko.support.v4.longToast


class IDPagerFragment : Fragment(){

    private lateinit var idPagerViewModel: IDPagerViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        idPagerViewModel = ViewModelProviders.of(this).get(IDPagerViewModel::class.java)

        val root = inflater.inflate(hu.bme.aut.temalabor.luciferi.ejegy.R.layout.fragment_pager_id,container,false)

        idPagerViewModel.userData?.observe(this, Observer {
            name.text = it.name
            idNumber.text = it.idCard
            val myBitmap = QRCode.from(it.id).withColor(0xFFFFFFFF.toInt(),0xE612B4AA.toInt()).withSize(500,500).bitmap()
            qr.setImageBitmap(myBitmap)
            //longToast(it.toString())
        })

        return root
    }
}