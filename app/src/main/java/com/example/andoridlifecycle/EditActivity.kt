package com.example.andoridlifecycle

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.edmodo.cropper.CropImageView

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment1)

        val oldSelectedStudent :StudentInfo = (intent.getSerializableExtra("selected_student") as StudentInfo)

        val nameView: EditText = findViewById<EditText>(R.id.name)
        nameView.setText(oldSelectedStudent.name)

        val surnameView: EditText = findViewById<EditText>(R.id.surname)
        surnameView.setText(oldSelectedStudent.surname)


        val imageView: CropImageView = findViewById<CropImageView>(R.id.image)

        var image: Bitmap = if (oldSelectedStudent.imageUri != null)
            getBitmap(this, null, oldSelectedStudent.imageUri, ::UriToBitmap) else getBitmap(this, R.drawable.ic_launcher_foreground, null, ::VectorDrawableToBitmap)

        if (oldSelectedStudent.imageUri != null) {
            image = Bitmap.createBitmap(
                image,
                oldSelectedStudent.x,
                oldSelectedStudent.y,
                oldSelectedStudent.w,
                oldSelectedStudent.h
            )

            image = Bitmap.createScaledBitmap(
                image,
                (resources.displayMetrics.density * 200).toInt(),
                (resources.displayMetrics.density * 200).toInt(),
                false
            )
        }
        imageView.setImageBitmap(image)

        val submitButton: Button = findViewById<Button>(R.id.submit)
        submitButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {

                val updatedSelectedStudent: StudentInfo = oldSelectedStudent.copy()
                updatedSelectedStudent.name = nameView.text.toString()
                updatedSelectedStudent.surname = surnameView.text.toString()

                val intent: Intent = Intent()
                intent.putExtra("selected_student", updatedSelectedStudent)
                setResult(RESULT_OK, intent);
                finish()
            }
        })
    }
}