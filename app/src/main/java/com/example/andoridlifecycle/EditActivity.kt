package com.example.andoridlifecycle

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.edmodo.cropper.CropImageView
import com.example.andoridlifecycle.StudentInfoTester.UriToBitmap
import com.example.andoridlifecycle.StudentInfoTester.VectorDrawableToBitmap
import com.example.andoridlifecycle.StudentInfoTester.getBitmap
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment1)

        val oldSelectedStudent: StudentInfo =
            (intent.getSerializableExtra("selected_student") as StudentInfo)

        val infoView: EditText = findViewById<EditText>(R.id.name)
        infoView.setText(oldSelectedStudent.info)

        val imageView: CropImageView = findViewById<CropImageView>(R.id.image)

        thread {
            var image: Bitmap? = null

            if (oldSelectedStudent.imageUri.toString().startsWith("http")) {
                with(URL(oldSelectedStudent.imageUri).openConnection() as HttpURLConnection) {
                    requestMethod = "GET"

                    setRequestProperty(
                        "User-Agent",
                        "Mozilla/5.0"
                    )

                    val bm: Bitmap = BitmapFactory.decodeStream(inputStream)

                    imageView.post { imageView.setImageBitmap(bm) }
                }
            } else {
                image = if (oldSelectedStudent.imageUri != null)
                    getBitmap(
                        this,
                        null,
                        oldSelectedStudent.imageUri,
                        ::UriToBitmap
                    ) else getBitmap(
                    this,
                    R.drawable.ic_launcher_foreground,
                    null,
                    ::VectorDrawableToBitmap
                )
                image = Bitmap.createScaledBitmap(
                    image,
                    oldSelectedStudent.imageH,
                    oldSelectedStudent.imageW,
                    false
                )
                if (oldSelectedStudent.imageUri != null) {
                    image = Bitmap.createBitmap(
                        image
                    )

                    image = Bitmap.createScaledBitmap(
                        image,
                        (resources.displayMetrics.density * 200).toInt(),
                        (resources.displayMetrics.density * 200).toInt(),
                        false
                    )
                }
                imageView.setImageBitmap(image)
            }
        }

        val submitButton: Button = findViewById<Button>(R.id.submit)
        submitButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {

                val updatedSelectedStudent: StudentInfo = oldSelectedStudent.copy()
                updatedSelectedStudent.info = infoView.text.toString()

                val intent: Intent = Intent()
                intent.putExtra("selected_student", updatedSelectedStudent)
                setResult(RESULT_OK, intent);
                finish()
            }
        })
    }
}