package com.example.kotlinmultithreading

// Example of Runnable class
class SimpleRunnable: Runnable {
    public override fun run() {
        println("SimpleRunnable: ${Thread.currentThread()} has run.")
    }
}