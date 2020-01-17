package com.example.kotlinmultithreading

// Example of Thread class
class SimpleThread: Thread() {
    public override fun run() {
        println("SimpleThread: ${Thread.currentThread()} has run.")
    }
}