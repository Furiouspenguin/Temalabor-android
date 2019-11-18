package hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.home.viewpager.idpager

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_pager_id.*
import net.glxn.qrgen.android.QRCode
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast


class IDPagerFragment : Fragment(){

    private val permission = arrayOf(Manifest.permission.CAMERA)

    private lateinit var idPagerViewModel: IDPagerViewModel

    companion object{
        private const val PERMISSION_REQUEST = 10
    }

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
        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        start_qr_scan.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkPermission(permission)) {
                    startActivity<QRScannerActivity>()
                } else {
                    requestPermissions(permission,
                        PERMISSION_REQUEST
                    )
                }
            } else {
                startActivity<QRScannerActivity>()
            }
        }
    }

    private fun checkPermission(permissionArray: Array<String>): Boolean {
        var allSuccess = true
        for (i in permissionArray.indices) {
            if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
                allSuccess = false
        }
        return allSuccess
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST) {
            var allSuccess = true
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    allSuccess = false
                    val requestAgain = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(permissions[i])
                    if (requestAgain) {
                        toast("Permission denied")
                    } else {
                        toast("Go to settings and enable the permission")
                    }
                }
            }
            if (allSuccess)
                startActivity<QRScannerActivity>()
        }
    }
}