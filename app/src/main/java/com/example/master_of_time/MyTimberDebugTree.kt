package com.example.master_of_time

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

    /** Log searching:
    package:mine ( message:logged_by_pxh612 | FATAL )
     */
    private val fqcnIgnore = listOf(
        Timber::class.java.name,
        Timber.Forest::class.java.name,
        Timber.Tree::class.java.name,
        MyTimberDebugTree::class.java.name
    )
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val element: StackTraceElement? = Throwable().stackTrace
            .first { it.className !in fqcnIgnore }


        val fullPath = element!!.className
//        val packageNameIndex = fullPath.indexOf(PACKAGE_NAME)
//        val classNameIndex = packageNameIndex + PACKAGE_NAME.length
        val classNameIndex = fullPath.lastIndexOf('.')
        val className = fullPath.substring(classNameIndex + 1)
        val classNameTest = className.also {
            val m = ANONYMOUS_CLASS.matcher(it)
            if (m.find()) {
                m.replaceAll("")
            }
            "classname..."
        }

        val functionName = element.methodName
        val lineNumber = element.lineNumber
        val fileName = element.fileName
        val lineRedirection = String.format("(%s:%d)", fileName, lineNumber)

        val logSearching = "\t".repeat(50) + "logged_by_pxh612"

        val mTag = classNameTest
        val mMessage = String.format("%s %s:    %s\n    %s ", functionName, lineRedirection, logSearching, message)
        Log.println(priority, mTag, mMessage)
    }

    companion object {
        private const val PACKAGE_NAME = BuildConfig.APPLICATION_ID
        private val ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$")
    }
}