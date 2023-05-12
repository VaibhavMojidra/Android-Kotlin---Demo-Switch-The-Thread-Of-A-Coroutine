package com.vaibhavmojidra.androidkotlinswitchthethreadofacoroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.vaibhavmojidra.androidkotlinswitchthethreadofacoroutine.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var count:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.startLongTaskButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                runLongRunningTask()
            }
        }

        binding.incrementButton.setOnClickListener {
            binding.countTextView.text=(++count).toString()
        }
    }

    private suspend fun runLongRunningTask(){
        for (i in 1..600){
            if(i>300){
                withContext(Dispatchers.IO){
                   Log.i( "MyTag","Task $i : Running in ${Thread.currentThread().name} Thread")
                    delay(100)
                }
            }else{
                Log.i( "MyTag","Task $i : Running in ${Thread.currentThread().name} Thread")
                delay(100)
            }
        }
    }
}