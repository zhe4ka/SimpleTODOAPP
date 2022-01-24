package com.example.simpletodo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter:TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                //remove the item from the list
                listOfTasks.removeAt(position)
                //notify the adapter that our data set has changed
                adapter.notifyDataSetChanged()
                saveItems()
            }

        }

        //1. Let's detect when the user clicks on the add button
        // findViewById<Button>(R.id.button).setOnClickListener{
        //Code here is going to be executed when the user clicks on a button
        // Log.i("Caren", "User clicked on button")
        // }
        //listOfTasks.add("Finish assignment 1")
        //listOfTasks.add("Go for a walk")
// Lookup the recyclerview in activity layout

        loadItems()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        //Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)


        val inputTextField = findViewById<EditText>(R.id.addTaskField)
        //Set up the button and input field
        findViewById<Button>(R.id.button).setOnClickListener {
            //Code here is going to be executed when the user clicks on a button
            val userInputtedTask = inputTextField.text.toString()

            //Add the string to our list of task: listOfTask
            listOfTasks.add(userInputtedTask)
            //Notify the adapter that our data has been updated
            adapter.notifyItemInserted(listOfTasks.size - 1)
            //Reset text field
            inputTextField.setText("")
            saveItems()

        }
    }

    //Save the data that the user has inputted


    //Save data by writing and reading from a file


    // get the data file we need
    fun getDataFile() : File {

        //Every line is going to represent a specific task in our list of task
        return File(filesDir, "data.txt")
    }

    //Load the item by reading every line in the data file
    fun loadItems(){
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }catch (ioException: IOException){
            ioException.printStackTrace()
        }


    }


    //Save items by writing them into our data file

    fun saveItems(){
        try{

            FileUtils.writeLines(getDataFile(), listOfTasks)
        }catch (ioException: IOException){

            ioException.printStackTrace()
        }
    }



}
