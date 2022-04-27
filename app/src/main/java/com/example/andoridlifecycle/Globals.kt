package com.example.andoridlifecycle

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.VectorDrawable
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.ContextCompat
import org.json.JSONArray
import org.json.JSONObject
import java.io.Serializable
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread
import kotlin.random.Random

object Globals {
    val TAG = "AndroidLifeCycle"
}

data class StudentInfo(var info: String, var imageUri: String?, var imageH: Int, var imageW: Int, var position: Int=-1): Serializable {
}

object StudentInfoTester {


    fun VectorDrawableToBitmap(context: Context, id: Int?, uri: String?): Bitmap {
        val drawable = (ContextCompat.getDrawable(context!!, id!!) as VectorDrawable)
        val image = Bitmap.createBitmap(
            drawable.getIntrinsicWidth(),
            drawable.getIntrinsicHeight(),
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(image)
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
        drawable.draw(canvas)

        return image
    }

    fun UriToBitmap(context: Context, id: Int?, uri: String?): Bitmap {
        val image: Bitmap =
            MediaStore.Images.Media.getBitmap(context!!.contentResolver, Uri.parse(uri))
        return image
    }

    fun getBitmap(
        context: Context,
        id: Int?,
        uri: String?,
        decoder: (Context, Int?, String?) -> Bitmap
    ):
            Bitmap {
        return decoder(context, id, uri)
    }
}
