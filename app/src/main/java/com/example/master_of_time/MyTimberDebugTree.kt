package com.example.master_of_time

import android.util.Log
import timber.log.Timber
import java.util.regex.Pattern

open class MyTimberDebugTree : Timber.DebugTree() {
    /** Installation:
    repositories {
        mavenCentral()
    }
    dependencies {
        implementation 'com.jakewharton.timber:timber:5.0.1'
    }
    */

    /** Log searching:
    package:mine ( message:logged_by_pxh612 | FATAL )
     */


    private val fqcnIgnore = listOf(
        Timber::class.java.name,
        Timber.Forest::class.java.name,
        Timber.Tree::class.java.name,
        MyTimberDebugTree::class.java.name
    )
    private val logSearching = "\t".repeat(50) + "logged_by_pxh612"


    fun methodLocation(element: StackTraceElement): String{
        return element.run{
            "$methodName ($fileName:$lineNumber)"
        }
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val element: StackTraceElement = Throwable().stackTrace
            .first { it.className !in fqcnIgnore }

        val finalTag: String? = createStackElementTag(element)
        val finalMessage = String.format("%s %s\n    %s ", methodLocation(element), logSearching, message)
        Log.println(priority, finalTag, finalMessage)
    }

}