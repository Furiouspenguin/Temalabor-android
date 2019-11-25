package hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.settings

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.temalabor.luciferi.ejegy.R
import hu.bme.aut.temalabor.luciferi.ejegy.auth.LoginActivity
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.service.RetrofitClient
import hu.bme.aut.temalabor.luciferi.ejegy.repositories.RestApiRepository
import kotlinx.android.synthetic.main.fragment_settings.*
import okhttp3.ResponseBody
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logout.setOnClickListener {
            loading.visibility = View.VISIBLE
            MyAsyncLogout{
                if (it == 200) {
                    startActivity<LoginActivity>()
                    activity?.finish()
                }
                else {
                    toast("Unauthorized, user not logged in")
                }
            }.execute()

        }

        change_password.setOnClickListener {
            if ((password.visibility == View.GONE) or (password_confirm.visibility == View.GONE) or (old_password.visibility == View.GONE)) {
                password.visibility = View.VISIBLE
                password_confirm.visibility = View.VISIBLE
                old_password.visibility = View.VISIBLE
            }
            else{
                if (password.text.toString().isEmpty() or password_confirm.text.toString().isEmpty() or old_password.text.toString().isEmpty()) {
                    toast("Fill every bracket!")
                    old_password.requestFocus()
                    return@setOnClickListener
                }
                if (password.text.toString() != password_confirm.text.toString()) {
                    password_confirm.error = "Password doesn't match!"
                    password_confirm.requestFocus()
                }
                else {
                    loading.visibility = View.VISIBLE
                    MyAsyncChangePassword(oldPassword = old_password.text.toString(),newPassword = password.text.toString()){
                        if (it == 200) {
                            toast("Password changed")
                        }
                        else if (it == 401) {
                            toast("Unauthorized, user not logged in")
                        }
                        else if (it == 403) {
                            toast("'Wrong password")
                        }
                        loading.visibility = View.GONE
                    }.execute()
                }
            }
        }
    }
    class MyAsyncLogout(val callback: (Int) -> Unit) :AsyncTask<String,Unit,Int>(){
        override fun doInBackground(vararg params: String?): Int {
            val callSyncBuy : Call<ResponseBody> = RetrofitClient.api.logout()

            val response =callSyncBuy.execute()
            return response.code()
        }

        override fun onPostExecute(result: Int) {
            super.onPostExecute(result)
            callback.invoke(result)
        }
    }

    class MyAsyncChangePassword(val oldPassword : String, val newPassword : String, val callback: (Int) -> Unit) :AsyncTask<String,Unit,Int>(){
        override fun doInBackground(vararg params: String?): Int {
            val callSyncBuy : Call<ResponseBody> = RetrofitClient.api.postPassword(oldPassword, newPassword)

            val response =callSyncBuy.execute()
            return response.code()
        }

        override fun onPostExecute(result: Int) {
            super.onPostExecute(result)
            callback.invoke(result)
        }
    }
}