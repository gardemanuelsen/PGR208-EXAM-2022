package com.example.andoridlifecycle

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.VectorDrawable
import android.net.Uri
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import java.io.Serializable
import kotlin.random.Random

object Globals {
    val TAG = "AndroidLifeCycle"
}

data class StudentInfo(var name: String, var surname: String, var imageUri: String?, var x: Int, var y: Int, var w: Int, var h: Int, var position: Int=-1): Serializable {
}

object StudentInfoTester{

    fun createRandomStudents(amount: Int): ArrayList<StudentInfo>{

        val generatedList: ArrayList<StudentInfo> = ArrayList<StudentInfo>()

        repeat(amount){
            var randomName: String = Random.generateRandomString(5..10)
            randomName = ""+randomName[0].toUpperCase() + randomName.subSequence(1,randomName.length)
            var randomSurname: String = Random.generateRandomString(8..15)
            randomSurname = ""+randomSurname[0].toUpperCase() + randomSurname.subSequence(1,randomSurname.length)

            generatedList.add(
                StudentInfo(
                    randomName,
                    randomSurname,
                    null,
                    -1, -1, -1, -1
                )
            )
        }

        return generatedList
    }
}

fun Random.generateRandomString(intRange: IntRange): String {
    var randomString: String = ""
    repeat(intRange.random()){ randomString += ('a'..'z').random().toString() }
    return randomString
}

fun VectorDrawableToBitmap(context: Context, id: Int?, uri: String?) : Bitmap {
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
    val image: Bitmap = MediaStore.Images.Media.getBitmap(context!!.contentResolver, Uri.parse(uri))
    return image
}

fun getBitmap(context: Context, id: Int?, uri: String?, decoder: (Context, Int?, String?) -> Bitmap): Bitmap {
    return decoder(context, id, uri)
}