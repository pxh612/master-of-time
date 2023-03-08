package com.example.pxh612_trivia_practice

import android.util.Log
import timber.log.Timber
import java.util.regex.Pattern

open class MyTimberDebugTree : Timber.Tree() {
    /* Installation:
    repositories {
        mavenCentral()
    }
    dependencies {
        implementation 'com.jakewharton.timber:timber:5.0.1'
    }
    */
    private val fqcnIgnore = listOf(
        Timber::class.java.name,
        Timber.Forest::class.java.name,
        Timber.Tree::class.java.name,
        MyTimberDebugTree::class.java.name
    )

    fun todo(TODO: String) : Boolean{
        e(TODO)
        return false
    }
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val tagForeword = "pxh612"

        val stackTrace = Throwable().stackTrace
        var element: StackTraceElement? = null
        for (elementIterator in stackTrace) {
            if (!fqcnIgnore.contains(elementIterator.className)) {
                element = elementIterator
                break
            }
        }


        val fullClassName = element!!.className
        val lastIndex = fullClassName.lastIndexOf('.')
        val secondLastIndex = fullClassName.lastIndexOf('.', lastIndex - 1)
        var className = fullClassName.substring(lastIndex + 1)
        val folderName = fullClassName.substring(secondLastIndex + 1, lastIndex)

        var functionName = element.methodName
        val lineNumber = element.lineNumber
        val fileName = element.fileName
        var lineRedirection = String.format("(%s:%d)", fileName, lineNumber)

//        lineRedirection = String.format("%1$" + LINE_REDIRECTION_FINAL_LENGTH + "s", lineRedirection)//.replace(' ','_')
//        className = String.format("%1$" + CLASS_NAME_FINAL_LENGTH + "s", className)//.replace(' ', '_')
//        functionName = String.format("%1$" + FUNCTION_NAME_FINAL_LENGTH + "s", functionName)//.replace(' ', '_')
        val mTag = when(folderName){
            PACKAGE_NAME -> tagForeword + " - " + folderName
            else -> tagForeword
        }
        val mMessage = String.format("%s > %s %s:  %s", className, functionName, lineRedirection, message)
        Log.println(priority, mTag, mMessage)
    }

    companion object {
        private const val CLASS_NAME_FINAL_LENGTH = 50
        private const val FUNCTION_NAME_FINAL_LENGTH = 35
        private const val LINE_REDIRECTION_FINAL_LENGTH = 40
        private const val PACKAGE_NAME = BuildConfig.APPLICATION_ID
        private val ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$")
    }


}