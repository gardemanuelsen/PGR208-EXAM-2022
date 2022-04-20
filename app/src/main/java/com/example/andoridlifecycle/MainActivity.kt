package com.example.andoridlifecycle

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var fragmentManager: FragmentManager

    private var studentsInfo = ArrayList<StudentInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i(Globals.TAG, "Activity 1 onCreate")
        Toast.makeText(this, "Activity onCreate", Toast.LENGTH_SHORT).show()

        //studentsInfo = StudentInfoTester.createRandomStudents(3)

        //val result: String = StudentInfoTester.getUserData("https://fakerapi.it/api/v1/persons?_quantity=3")

        val url = URL("https://fakerapi.it/api/v1/persons?_quantity=1")
        thread{
            with(url.openConnection() as HttpURLConnection){
                requestMethod="GET"
                inputStream.bufferedReader().lines().forEach{
                    //         Log.i(Globals.TAG, it)

                    val json : JSONArray = JSONArray(it)

                    for (index in 0 until json.length()){
                        val firstName: String =  (json.get(index) as JSONObject).get("firstname").toString()
                        val lastName: String =  (json.get(index) as JSONObject).get("lastname").toString()
                        val image: String =  (json.get(index) as JSONObject).get("image").toString()

                        Log.i(Globals.TAG, firstName+" "+lastName+" "+image+" ")

                        studentsInfo.add(StudentInfo(
                            firstName,
                            lastName,
                            image,
                            -1, -1, -1, -1
                        ))

                    }
                }
            }
        }

    }

    override fun onStart() {
        super.onStart()
        Log.i(Globals.TAG, "Activity 1 onStart")
        Toast.makeText(this, "Activity onStart", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        Log.i(Globals.TAG, "Activity 1 onResume")
        Toast.makeText(this, "Activity onResume", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        Log.i(Globals.TAG, "Activity 1 onPause")
        Toast.makeText(this, "Activity onPause", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        Log.i(Globals.TAG, "Activity 1 onStop")
        Toast.makeText(this, "Activity onStop", Toast.LENGTH_SHORT).show()
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(Globals.TAG, "Activity 1 onRestart")
        Toast.makeText(this, "Activity onRestart", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(Globals.TAG, "Activity 1 onDestroy")
        Toast.makeText(this, "Activity onDestroy", Toast.LENGTH_SHORT).show()
    }

    fun switchFragment(v: View) {
        Log.i(Globals.TAG, "Activity 1 switchFragment. Tag: " + v.getTag().toString())
        Toast.makeText(
            this,
            "Activity switchFragment. Tag" + v.getTag().toString(),
            Toast.LENGTH_SHORT
        ).show()

        fragmentManager = supportFragmentManager

        if (Integer.parseInt(v.getTag().toString()) == 1) {
            fragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_main,
                    Fragment1(),
                    "Fragment1"
                )
                .commit()
        } else {
            fragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_main,
                    Fragment2(studentsInfo),
                    "Fragment2"
                )
                .commit()
        }
    }

    fun submit(view: View){
        var nameViewText = (fragmentManager.findFragmentByTag("Fragment1") as Fragment1).nameView.text.toString()
        var surnameView = (fragmentManager.findFragmentByTag("Fragment1") as Fragment1).surnameView.text.toString()
        var imageUri = (fragmentManager.findFragmentByTag("Fragment1") as Fragment1).imageUri.toString()

        var rect = (fragmentManager.findFragmentByTag("Fragment1") as Fragment1).image.actualCropRect

        val newStudent: StudentInfo = StudentInfo(nameViewText, surnameView, imageUri, rect.left.toInt(), rect.top.toInt(), rect.width().toInt(), rect.height().toInt())
        studentsInfo.add(newStudent)


        Toast.makeText(this, "Added New Person", Toast.LENGTH_SHORT).show()
    }
}