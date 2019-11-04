package hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.home.viewpager

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import hu.bme.aut.temalabor.luciferi.ejegy.R
import android.graphics.Color
import com.google.zxing.WriterException
import kotlinx.android.synthetic.main.fragment_pager_id.*
import net.glxn.qrgen.android.QRCode


class IDPagerFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(hu.bme.aut.temalabor.luciferi.ejegy.R.layout.fragment_pager_id,container,false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO: QR-kód generálása userId alapján: már csak a user adatára kell cserélni és kész
        val myBitmap = QRCode.from("12121212121212").withSize(500,500).bitmap()
        qr.setImageBitmap(myBitmap)
    }
}