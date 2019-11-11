package hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.store

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerDialogFragment(val passTicket : Boolean) : DialogFragment() {

    companion object {
        const val TAG = "DatePickerDialog"
    }

    private val calSelectedDate = Calendar.getInstance()

    private lateinit var listener: DateListener

    private val dateSetListener =
        DatePickerDialog.OnDateSetListener { datePicker, year, monthOfYear, dayOfMonth ->
            // Setting the new date
            calSelectedDate.set(Calendar.YEAR, year)
            calSelectedDate.set(Calendar.MONTH, monthOfYear)
            calSelectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            //listener.onPassDateSelected(buildDateText())
            listener.onPassDateSelected(calSelectedDate.get(Calendar.DAY_OF_MONTH),
                calSelectedDate.get(Calendar.MONTH),
                calSelectedDate.get(Calendar.YEAR))

            dismiss()
        }

    private val timeSetListener =
        TimePickerDialog.OnTimeSetListener{view: TimePicker?, hourOfDay: Int, minute: Int ->
            calSelectedDate.set(Calendar.HOUR,hourOfDay)
            calSelectedDate.set(Calendar.MINUTE,minute)

            listener.onTimeDateSelected(calSelectedDate.get(Calendar.HOUR),
                calSelectedDate.get(Calendar.MINUTE))

            dismiss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        calSelectedDate.time = Date(System.currentTimeMillis())
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (passTicket) {
            return DatePickerDialog(
                requireContext(),
                dateSetListener,
                calSelectedDate.get(Calendar.YEAR),
                calSelectedDate.get(Calendar.MONTH),
                calSelectedDate.get(Calendar.DAY_OF_MONTH)
            )
        } else {
            return TimePickerDialog(
                requireContext(),
                timeSetListener,
                calSelectedDate.get(Calendar.HOUR),
                calSelectedDate.get(Calendar.MINUTE),
                true
            )
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = if (targetFragment != null) {
                targetFragment as DateListener
            } else {
                activity as DateListener
            }
        } catch (e: ClassCastException) {
            throw RuntimeException(e)
        }
    }

    private fun buildDateText(): String {
        val dateString = StringBuilder()

        dateString.append(calSelectedDate.get(Calendar.YEAR))
        dateString.append("-")
        dateString.append(calSelectedDate.get(Calendar.MONTH) + 1)
        dateString.append("-")
        dateString.append(calSelectedDate.get(Calendar.DAY_OF_MONTH))

        return dateString.toString()
    }

    interface DateListener {
        fun onPassDateSelected(day: Int, month: Int, year: Int)
        fun onTimeDateSelected(hour: Int, minute: Int)
    }

}