package com.example.handlerlooperhandlerthread

import android.os.Handler
import android.os.HandlerThread

open class HandlerThreadWorker internal constructor() : HandlerThread(TAG) {
    //HandlerThread has the implementation that the SimpleWorker can do
    companion object {
        private const val TAG = "HandlerThreadWorker"
    }

    private lateinit var mHandler: Handler

    init {
        start()
        mHandler =
            Handler(looper)//getLooper() will block the thread. We need to call getLooper() after the thread's start() method is called because the looper will be created once the thread gets started
    }

    fun execute(task: Runnable): HandlerThreadWorker {
        mHandler.post(task)//Adding the task to the message queue associated with this worker thread
        return this
    }
}