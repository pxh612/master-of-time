package com.example.pxh612_trivia_practice

import android.util.Log
import timber.log.Timber
import java.util.regex.Pattern

open class MyTimberDebugTree : Timber.Tree() {
    /** Installation:
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
        val forewordTag = "pxh612"

        val stackTrace = Throwable().stackTrace
        var element: StackTraceElement? = null
        for (elementIterator in stackTrace) {
            if (!fqcnIgnore.contains(elementIterator.className)) {
                element = elementIterator
                break
            }
        }

        val fullPath = element!!.className
        val packageNameIndex = fullPath.indexOf(PACKAGE_NAME)
        val classNameIndex = packageNameIndex + PACKAGE_NAME.length
        val className = fullPath.substring(classNameIndex + 1)

        var functionName = element.methodName
        val lineNumber = element.lineNumber
        val fileName = element.fileName
        var lineRedirection = String.format("(%s:%d)", fileName, lineNumber)

        val mTag = forewordTag
        val mMessage = String.format("%s > %s %s:    \n    %s", className, functionName, lineRedirection, message)
        Log.println(priority, mTag, mMessage)
    }

    companion object {
        private const val PACKAGE_NAME = BuildConfig.APPLICATION_ID
        private val ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$")
    }
}