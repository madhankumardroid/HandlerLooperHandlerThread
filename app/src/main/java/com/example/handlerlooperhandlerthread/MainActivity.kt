package com.example.handlerlooperhandlerthread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.example.handlerlooperhandlerthread.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
//    private lateinit var worker: SimpleWorker
    private lateinit var worker: HandlerThreadWorker
    private var handler = object : Handler(Looper.getMainLooper()) {
        //To receive the message from worker thread and show it in UI
        override fun handleMessage(msg: Message) {
            binding.tvMessage.text = msg.obj as CharSequence?
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        worker = HandlerThreadWorker()

        worker.execute {// When the execute method is called, the SimpleWorker thread will execute the code inside this runnable
            Log.d("Woker", "1st Runnable: Thread name: ${Thread.currentThread().name}")
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            val msg = Message.obtain()
            msg.obj = "Task 1 completed"
            handler.sendMessage(msg)//Posting message from worker thread to main thread via handler
        }.execute {
            Log.d("Woker", "2nd Runnable: Thread name: ${Thread.currentThread().name}")
            try {
                Thread.sleep(500)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            val msg = Message.obtain()
            msg.obj = "Task 2 completed"
            handler.sendMessage(msg)
        }.execute {
            Log.d("Woker", "3rd Runnable: Thread name: ${Thread.currentThread().name}")
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            val msg = Message.obtain()
            msg.obj = "Task 3 completed"
            handler.sendMessage(msg)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::worker.isInitialized) {
            worker.quit() //To stop the thread when the activity is destroyed
        }
    }
}