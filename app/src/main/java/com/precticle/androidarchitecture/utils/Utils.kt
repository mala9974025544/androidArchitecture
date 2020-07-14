package com.precticle.test.util

import android.R
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Environment
import android.text.TextUtils
import android.text.format.DateFormat
import android.text.format.DateUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.iid.FirebaseInstanceId
import com.precticle.androidarchitecture.utils.LogUtils
import java.io.File
import java.io.IOException
import java.text.Format
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


object Utils{

    var FULLMONTH_DATE_FORMAT = "MMMM dd, yyyy"

    fun getFullDate(date: Date): String {
        val mDateFormat = SimpleDateFormat(FULLMONTH_DATE_FORMAT, Locale.ENGLISH)
        return mDateFormat.format(date)
    }
    fun isAlphaNumeric(s: String): Boolean {
        val pattern = "[a-zA-Z]"
        return s.matches(pattern.toRegex())
    }
    //extension function
    fun isViewVisible(view: View, visible: Boolean) =
        if (visible) view.visibility = View.VISIBLE else view.visibility = View.GONE

    fun Context.openWebPage(url: String) {
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    fun showAlertDialogue(context: Context, msg: String?) {
        val builder = AlertDialog.Builder(context)
        //builder.setTitle(context.getString(R.string.app_name))
        builder.setMessage(msg)
        builder.setNeutralButton(
            R.string.ok
        ) { dialog, which -> dialog.dismiss() }
        builder.create().show()
    }
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
    fun isSameday(`when`: Date?, now: Date?): Boolean {
        val time = Calendar.getInstance()
        time.time = `when`
        val thenYear = time[Calendar.YEAR]
        val thenMonth = time[Calendar.MONTH]
        val thenMonthDay = time[Calendar.DAY_OF_MONTH]
        time.time = now
        return (thenYear == time[Calendar.YEAR]
                && thenMonth == time[Calendar.MONTH]
                && thenMonthDay == time[Calendar.DAY_OF_MONTH])
    }

    fun getPrettyTime(context: Context?,date: Date?): String? {
        if (date == null) {
            return ""
        }
        return if (DateUtils.isToday(date.time)) {
           getTime(context, date)
        } else {
            getDate(context, date)
        }
    }
    fun getDate(context: Context?, date: Date?): String? {
        if (date == null) {
            return ""
        }
        val dateFormat: Format = DateFormat.getDateFormat(context)
        val patternDate =
            (dateFormat as SimpleDateFormat).toLocalizedPattern()
        return SimpleDateFormat(patternDate).format(date)
    }

    fun getTime(context: Context?, date: Date?): String? {
        if (date == null) {
            return ""
        }
        val timeFormat: Format = DateFormat.getTimeFormat(context)
        val patternTime =
            (timeFormat as SimpleDateFormat).toLocalizedPattern()
        return SimpleDateFormat(patternTime).format(date)
    }
    fun isSameYear(`when`: Date?, now: Date?): Boolean {
        val time = Calendar.getInstance()
        time.time = `when`
        val thenYear = time[Calendar.YEAR]
        time.time = now
        return thenYear == time[Calendar.YEAR]
    }

    fun openEmail(context: Context, email: String) {
        /* Create the Intent */
        val emailIntent = Intent(Intent.ACTION_SEND)

        /* Fill it with Data */emailIntent.type = "plain/text"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))

        /* Send it off to the Activity-Chooser */context.startActivity(
            Intent.createChooser(
                emailIntent,
               "chooser_title_send_mail!"
            )
        )
    }

    fun openCall(context: Context, phone: String?) {
        val intent =
            Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
        context.startActivity(intent)
    }

    fun openSMS(context: Context, phone: String?) {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.fromParts("sms", phone, null)
            )
        )
    }

    @Throws(IOException::class)
    fun createImageFile(context: Context?): File? {
        // Create an image file name
        val timeStamp =
            SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val image = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )
        // Save a file: path for use with ACTION_VIEW intents
        val mCurrentPhotoPath = image.absolutePath
        return image
    }


    fun hideKeyboard(activity: Activity) {
        val inputManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (activity.currentFocus != null) {
            activity.currentFocus!!.clearFocus()
            if (activity.currentFocus != null) {
                val binder = activity.currentFocus!!.windowToken
                inputManager.hideSoftInputFromWindow(
                    binder,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }
    }

    fun getFirstLetter(value: String): String? {
        return if (TextUtils.isEmpty(value)) {
            ""
        } else {
            val firstChar = value.substring(0, 1).toUpperCase()
            if (!Pattern.matches("[a-zA-Z]", firstChar)) {

                ""
            } else {

                firstChar
            }
        }
    }
    fun showSnackBar(
        activity: Activity,
        message: String?,
        view: View?
    ) {
        val snackbar: Snackbar = Snackbar.make(view!!, "", Snackbar.LENGTH_LONG)
        val layout: Snackbar.SnackbarLayout = snackbar.getView() as Snackbar.SnackbarLayout
        layout.setBackgroundColor(activity.resources.getColor(R.color.black))
        setMargins(layout, 80, 0, 80, 2)
        val textView =
            layout.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.text = message
        textView.setTextColor(activity.resources.getColor(R.color.white))
        // textView.setPadding(6, 0, 6, 0);
        textView.gravity = Gravity.CENTER
        //textView.setVisibility(View.INVISIBLE);
        //CUSTOME
        //View snackView = activity.getLayoutInflater().inflate(R.layout.snack_bar, null);
        //TextView textViewTop = (TextView) snackView.findViewById(R.id.tvSnack);
        //textViewTop.setText(message);
        //textViewTop.setTextColor(Color.parseColor("#F8584C"));
        //layout.setPadding(100, 0, 100, 0);
        //setMargins(textViewTop,100,0,100,0);
        //layout.addView(snackView, 0);
        snackbar.show()
    }

    fun setMargins(
        view: View,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        if (view.layoutParams is MarginLayoutParams) {
            val p = view.layoutParams as MarginLayoutParams
            p.setMargins(left, top, right, bottom)
            view.requestLayout()
        }
    }
    fun getFireBaseToken(context: Context){
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                // 2
                if (!task.isSuccessful) {
                    Log.w("failed", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }
                // 3
                val token = task.result?.token

                // 4
                val msg = token
                LogUtils.d("tag",msg.toString())
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
            })

    }
    private fun checkGooglePlayServices(context: Context): Boolean {
        // 1
        val status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context)
        // 2
        return if (status != ConnectionResult.SUCCESS) {
            Log.e("Error", "Error")
            // ask user to update google play services and manage the error.
            false
        } else {
            // 3
            Log.i("Sucess", "Google play services updated")
            true
        }
    }
}