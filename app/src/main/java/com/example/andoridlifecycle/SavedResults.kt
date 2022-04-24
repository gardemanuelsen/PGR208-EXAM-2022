package com.example.andoridlifecycle

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView


class SavedResults(val studentsInfo: ArrayList<StudentInfo>) : Fragment() {

    var itemAdapter: ItemAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i(Globals.TAG, "Fragment 2 onCreate")
        Toast.makeText(activity, "Fragment 2 onCreate", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(Globals.TAG, "Fragment 2 onCreateView")
        Toast.makeText(activity, "Fragment 2 onCreateView", Toast.LENGTH_SHORT).show()

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.saved_results, container, false)

        val recyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        //Alternatively to defining manager in XML: recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)

        var onItemClickListener = object: View.OnClickListener {
            override fun onClick(view: View?) {

                val position: Int = view?.tag.toString().toInt()
                studentsInfo.removeAt(position)

                itemAdapter?.notifyDataSetChanged()
            }
        }

        var onItemEditListener = object: View.OnClickListener {
            override fun onClick(view: View?) {



            }
        }

        itemAdapter = ItemAdapter(studentsInfo, onItemClickListener, onItemEditListener)
        recyclerView.setAdapter(itemAdapter)

        return view
    }

    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            val updatedStudentInfo: StudentInfo = (intent?.getSerializableExtra("selected_student") as StudentInfo)

        }
    }

}