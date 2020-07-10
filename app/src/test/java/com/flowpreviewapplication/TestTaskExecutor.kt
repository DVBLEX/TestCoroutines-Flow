package com.flowpreviewapplication

import androidx.arch.core.executor.TaskExecutor

class TestTaskExecutor : TaskExecutor() {

    override fun executeOnDiskIO(runnable: Runnable) {
        runnable.run()
    }

    override fun isMainThread(): Boolean = true

    override fun postToMainThread(runnable: Runnable) {
        runnable.run()
    }

}