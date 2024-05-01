package com.pezesha.agent.utils

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.pezesha.agent.ui.dialogs.ResultDialogs
import com.pezesha.agent.ui.dialogs.loader.ProgressLoader
import io.sentry.Sentry
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class GlobalMethods(private val app: Application) {

    private var progressAlertDialog: AlertDialog? = null

    fun toggleLoader(context: Context, showStatus: Int) {
        if (showStatus == 1) {
            val progressDialog = ProgressLoader(context)
            progressAlertDialog = progressDialog.create()
            progressAlertDialog?.show()
        } else {
            progressAlertDialog?.let { if (it.isShowing) it.dismiss() }
        }
    }

    fun toggleError(
        context: Context,
        message: String,
        listener: (() -> Unit)?,
    ) {
        ResultDialogs.showErrorMessage(context, message, listener)
    }

    /**
     * Takes a bitmap file and create a Multipart form
     */
    fun buildFileBodyPart(
        contentType: String,
        file: File?,
        extraName: String = ""
    ): MultipartBody.Part? {
        return try {
            val reqFile = file?.asRequestBody("$contentType/*".toMediaTypeOrNull())
            val re = Regex("[^A-Za-z0-9_.]")
            val newFileName = re.replace(
                (file?.nameWithoutExtension + extraName + "." + file?.extension), ""
            )
            MultipartBody.Part.createFormData("file", newFileName, reqFile!!)
        } catch (e: Exception) {
            Sentry.captureException(e)
            null
        }
    }

    fun isConnected(): Boolean {
        val connectivityManager =
            app.getSystemService(Context.CONNECTIVITY_SERVICE) as
                    ConnectivityManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            postAndroidMInternetCheck(connectivityManager)
        else
            preAndroidMInternetCheck(connectivityManager)
    }

    private fun preAndroidMInternetCheck(
        connectivityManager: ConnectivityManager
    ): Boolean {
        val activeNetwork = connectivityManager.activeNetworkInfo
        if (activeNetwork != null) {
            return (activeNetwork.type == ConnectivityManager.TYPE_WIFI ||
                    activeNetwork.type == ConnectivityManager.TYPE_MOBILE)
        }
        return false
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun postAndroidMInternetCheck(
        connectivityManager: ConnectivityManager
    ): Boolean {
        val network = connectivityManager.activeNetwork
        val connection =
            connectivityManager.getNetworkCapabilities(network)

        return connection != null && (
                connection.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        connection.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    }

    fun toggleSuccess(
        context: Context,
        message: String,
        listener: (() -> Unit)?,
    ) {
        ResultDialogs.showSuccessMessage(context, message, listener)
    }

    /**
     * Retrieve the ANDROID_ID of a device for use as the unique device identifier
     */
//    fun getAndroidId(): String {
//        return BuildConfig.APP_DEVICE_ID.ifEmpty {
//            Secure.getString(app.contentResolver, Secure.ANDROID_ID)
//        }
//    }

}
