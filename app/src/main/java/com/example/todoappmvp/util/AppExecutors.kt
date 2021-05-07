package com.example.todoappmvp.util

import android.os.Handler
import android.os.Looper
import androidx.annotation.VisibleForTesting
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors @VisibleForTesting internal constructor(
    val diskIO: Executor = DiskIOThreadExecutor(),
    val networkIO: Executor = Executors.newFixedThreadPool(THREAD_COUNT),
    val mainThread: Executor = MainThreadExecutor()
) {

    private companion object {
        const val THREAD_COUNT = 3

        class MainThreadExecutor(
            val mainThreadHandler: Handler = Handler(Looper.getMainLooper())
        ) : Executor {
            override fun execute(command: Runnable) {
                mainThreadHandler.post(command)
            }
        }
    }
}
