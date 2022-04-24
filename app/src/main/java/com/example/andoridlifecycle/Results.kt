package com.example.andoridlifecycle

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.edmodo.cropper.CropImageView


class Results() : Fragment() {

    var itemAdapter: ItemAdapter? = null

    public lateinit var imageResult: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i(Globals.TAG, "Fragment 3 onCreate")
        Toast.makeText(activity, "Fragment 3 onCreate", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(Globals.TAG, "Fragment 3 onCreateView")
        Toast.makeText(activity, "Fragment 3 onCreateView", Toast.LENGTH_SHORT).show()

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.results, container, false)

        imageResult = view.findViewById<ImageView>(R.id.image_result)

        return view
    }




}