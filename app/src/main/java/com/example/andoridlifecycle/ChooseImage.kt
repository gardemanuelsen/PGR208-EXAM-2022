package com.example.andoridlifecycle

import android.content.Intent
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.edmodo.cropper.CropImageView
import com.example.andoridlifecycle.StudentInfoTester.UriToBitmap
import com.example.andoridlifecycle.StudentInfoTester.getBitmap

class ChooseImage : Fragment() {

    public lateinit var nameView: EditText
    public lateinit var surnameView: EditText
    public lateinit var image: CropImageView

    public var imageUri: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i(Globals.TAG, "Fragment 1 onCreate")
        Toast.makeText(activity, "Fragment 1 onCreate", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(Globals.TAG, "Fragment 1 onCreateView")
        Toast.makeText(activity, "Fragment 1 onCreateView", Toast.LENGTH_SHORT).show()

        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.choose_image, container, false)

        nameView = view.findViewById<EditText>(R.id.name)
        surnameView = view.findViewById<EditText>(R.id.surname)

        image = view.findViewById<CropImageView>(R.id.image)
        image.setOnClickListener{

            var i = Intent()

            i.action = Intent.ACTION_GET_CONTENT
            i.type = "*/*"

            startForResult.launch(i)
        }

        return view
    }

    var startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

        imageUri = it.data?.data.toString()

        var bitmap_image = getBitmap(requireContext(), null, imageUri, ::UriToBitmap)

        image.layoutParams = image.layoutParams.apply {

            width = bitmap_image.width
            height = bitmap_image.height
        }

        image.setImageBitmap(bitmap_image)
        image.background = BitmapDrawable(resources, bitmap_image)
    }

}