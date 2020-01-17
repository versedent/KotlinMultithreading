package com.example.kotlinmultithreading

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlin.concurrent.thread;
import kotlinx.coroutines.*;
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    suspend fun doWorkFor1Seconds(): String {
        delay(1000)
        return "doWorkFor1Seconds"
    }

    suspend fun doWorkFor2Seconds(): String {
        delay(2000)
        return "doWorkFor2Seconds"
    }

    // Serial execution
    private fun doWorkInSeries() {
        GlobalScope.launch {
            val one = doWorkFor1Seconds()
            val two = doWorkFor2Seconds()
            println("Kotlin One : " + one)
            println("Kotlin Two : " + two)
        }
    }

    // Parallel execution
    private fun doWorkInParallel() {
        val one = GlobalScope.async {
            doWorkFor1Seconds()
        }
        val two = GlobalScope.async {
            doWorkFor2Seconds()
        }
        GlobalScope.launch {
            val combined = one.await() + "_" + two.await()
            println("Kotlin combined : " + combined)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Example of kotlin.concurrent.thread function usage
        thread {
            println("${Thread.currentThread()} has run.")
            // Will output: Thread[Thread-2,5,main] has run.
        }

        // Usage example of Thread class
        val thread = SimpleThread()
        thread.start()
        // Will output: Thread[Thread-3,5,main] has run.

        // Usage example of Runnable class
        val threadWithRunnable = Thread(SimpleRunnable())
        threadWithRunnable.start()
        // Will output: Thread[Thread-4,5,main] has run.


        // Performance difference between simple threads and coroutines
        var counter = 0
        val numberOfThreadsOrCoroutines = 10_000
        var time = measureTimeMillis {
            for (i in 1..numberOfThreadsOrCoroutines) {
                thread() {
                    counter += 1
                }
            }
        }
        println("Created ${numberOfThreadsOrCoroutines} threads in ${time}ms.")

        time = measureTimeMillis {
            for (i in 1..numberOfThreadsOrCoroutines) {
                GlobalScope.launch{
                    counter += 1
                }

            }
        }
        println("Created ${numberOfThreadsOrCoroutines} coroutines in ${time}ms.")


        println("Start of coroutine")
        GlobalScope.launch {
            delay(2000)
            println("Inside of coroutine")
        }
        println("End of coroutine")
        // The output is
        // Kotlin Start
        // Kotlin End
        // Kotlin Inside


        doWorkInSeries()
        // The output is
        // Kotlin One : doWorkFor1Seconds
        // Kotlin Two : doWorkFor2Seconds

        doWorkInParallel()
        // The output is
        // Kotlin combined : doWorkFor1Seconds_doWorkFor2Seconds
    }
}
