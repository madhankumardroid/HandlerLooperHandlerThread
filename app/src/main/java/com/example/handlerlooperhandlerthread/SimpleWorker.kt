package com.example.handlerlooperhandlerthread

import android.util.Log
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.atomic.AtomicBoolean

class SimpleWorker internal constructor() : Thread(TAG) {
    companion object {
        private const val TAG = "SimpleWorker"
    }

    var isAlive: AtomicBoolean = AtomicBoolean(true)
    var taskQueue: ConcurrentLinkedQueue<Runnable> = ConcurrentLinkedQueue<Runnable>()

    init {
        start()
    }

    override fun run() {
        while (isAlive.get()) {//To keep the thread alive
            taskQueue.poll()
                ?.run() //Getting the task from queue, if the task is not null, then execute it
        }
        Log.d(TAG, "run: Simpleworker terminated")
    }

    /**
     * To stop the thread
     */
    fun quit() {
        isAlive.set(false)
    }

    /**
     * To add the new task to the task queue and return this instance to add more tasks if needed
     * @param task The task to be executed
     * @return The instance of SimpleWorker
     */
    fun execute(task: Runnable): SimpleWorker {
        taskQueue.add(task)
        return this
    }
}
