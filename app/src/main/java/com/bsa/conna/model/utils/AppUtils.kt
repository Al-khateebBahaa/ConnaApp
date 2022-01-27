package com.bsa.tars_android.model.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.bsa.conna.model.utils.USER_DATA
import com.google.android.material.snackbar.Snackbar
import conna.R
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

private const val PERMISSION_REQUEST = 10

@Singleton
class AppUtils @Inject constructor(
    @ApplicationContext val context: Context
) {

    var loading = MutableLiveData(false)



    @Singleton
    private fun getShared(): SharedPreferences {

        return EncryptedSharedPreferences.create(
            USER_DATA,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    }

    fun clearAllData() = getShared().edit().clear().apply()


    fun savePreferencesString(key: String, value: String?) =
        getShared().edit().putString(key, value).apply()


    fun savePreferencesNumber(key: String, value: Int) =
        getShared().edit().putInt(key, value).apply()


    fun savePreferencesBoolean(key: String, value: Boolean) {
        getShared().edit().putBoolean(key, value).apply()
    }




    fun getPreferencesString(key: String): String =
        getShared().getString(key, "") ?: ""


    fun getPreferencesStringIfNull(key: String): String? =
        getShared().getString(key, null)


    fun getPreferencesBoolean(key: String?): Boolean = getShared().getBoolean(key, false)



    fun getPreferencesInt(key: String?): Int =
        getShared().getInt(key, 0)


    fun checkPermission(context: Context, activity: Activity, permission: String): Boolean {
        if (ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        if (activity.shouldShowRequestPermissionRationale(permission)) {
            AlertDialog.Builder(context)
                .setTitle("")//context.getString(R.string.permission_request))
                .setMessage("")//context.getString(R.string.give_app_permission))
                .setPositiveButton(
                    android.R.string.ok
                ) { dialog: DialogInterface?, which: Int ->
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(permission),
                        PERMISSION_REQUEST
                    )
                }.setNegativeButton(
                    android.R.string.cancel
                ) { dialog: DialogInterface, which: Int -> dialog.dismiss() }.create()
                .show()
            return false
        }

        ActivityCompat.requestPermissions(activity, arrayOf(permission), PERMISSION_REQUEST)

        return false
    }



    fun checkViews(vararg views: EditText): Boolean {
        for (ed in views) {
            if (ed.text.isNullOrBlank()) {
                ed.error = context.getString(R.string.empty_field)
                return false
            }
        }
        return true
    }


    fun showSnack(activity: Activity, message: String?) {
        val snackbar = Snackbar.make(
            activity.findViewById(android.R.id.content),
            message!!, Snackbar.LENGTH_LONG
        )
        snackbar.setActionTextColor(ContextCompat.getColor(activity, R.color.main_color))
        snackbar.setAction(
            activity.getString(android.R.string.ok)
        ) { v: View? -> snackbar.dismiss() }
        snackbar.show()
    }


    fun hideKeyboard(view: View) {

        val keyboardManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        keyboardManager.hideSoftInputFromWindow(view.windowToken, 0)

    }


    @SuppressLint("MissingPermission")
    fun isInternetAvailable(context: Context): Boolean {

        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        if (!result)
            Toast.makeText(
                context,
                "No internet connection, please check your connection",
                Toast.LENGTH_LONG
            ).show()


        return result
    }

    fun setLocale(activity: Activity, languageCode: String) {

        val resources = activity.resources
        val config = resources.configuration

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            config.setLocale(Locale(languageCode.lowercase(Locale.getDefault())))
        } else {

            val locale = Locale(languageCode)
            Locale.setDefault(locale)
            config.setLocale(locale)


            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N)
                context.createConfigurationContext(config)
        }

        resources.updateConfiguration(config, resources.displayMetrics)


    }


    fun setLocale(config: Configuration?) {

        if (config == null)
            return

        val resources = context.resources

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N)
            context.createConfigurationContext(config)
        else
            resources.updateConfiguration(config, resources.displayMetrics)


    }


    fun calcDifferentTime(firstDate: String, lastDate: String): Boolean {

        val date1: Date?
        val date2: Date?


        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        try {
            date1 = simpleDateFormat.parse(firstDate)
            date2 = simpleDateFormat.parse(lastDate)
        } catch (e: ParseException) {
            e.printStackTrace()

            return false
        }

        val difference = date2!!.time - date1!!.time

        if (difference > 0)
            return true

        return false

    }


    fun createPartFromText(text: String): RequestBody {
        return text.toRequestBody(MultipartBody.FORM)
    }



}