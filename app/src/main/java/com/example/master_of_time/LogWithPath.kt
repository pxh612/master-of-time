
import android.util.Log

class LogWithPath {

    companion object{

        private const val logSearching = "LogWithPath"

        private val fqcnIgnore = listOf(
            this::class.java.name,
            LogWithPath::class.java.name,
        )
        private val nativeClassName = listOf(
            "android",
            "kotlin",
            "java",
        )

        private fun String.containsNativeClass(): Boolean {
            nativeClassName.forEach {
                if(contains(it)) return true
            }
            return false
        }


        private fun className(element: StackTraceElement): String {
            return element.className.substringAfterLast('.')
        }


        private fun shortMethodLocation(
            element: StackTraceElement,
            withoutLineNumber: Boolean = false
        ): String{
            return element.run {

                var returnResult = ""

                returnResult += "${className(this)} -> $methodName()"
                if(!withoutLineNumber) returnResult += "  ($fileName:$lineNumber)"

                returnResult
            }
        }

        private fun longMethodLocation(element: StackTraceElement): String{
            return element.run{
                "$className.$methodName($fileName:$lineNumber)"
            }
        }


        private fun log(priority: Int, m: String) {

            val elementList = mutableListOf<StackTraceElement>()
            Throwable().stackTrace.forEach {
                if(it.className !in fqcnIgnore && !it.className.containsNativeClass())
                    elementList.add(it)
            }

            var everyPath = ""
            elementList.reversed().forEach {
                everyPath += when(it){
                    elementList.first() -> longMethodLocation(it) + "\n" + shortMethodLocation(it, withoutLineNumber = true)
                    else -> longMethodLocation(it) + "\n"
                }
            }

            var logId = "log_id_"
            elementList.forEach { logId += it.lineNumber }
            logId = elementList.first().let {
                className(it) + "_" + it.lineNumber
            }

            val tabGap = "\t".repeat(50)
            val finalTag: String = logSearching
            val messageNewline = "\n\t$m" + "\n"+ tabGap + logId

            Log.println(priority, finalTag + "1", m + tabGap + logId)
            Log.println(priority, finalTag + "2", shortMethodLocation(elementList.first()) + messageNewline)
            Log.println(priority, finalTag + "3", everyPath + messageNewline)
        }

        @JvmStatic
        fun v(m: String) {log(Log.VERBOSE, m)}

        @JvmStatic
        fun d(m: String) {log(Log.DEBUG, m)}

        @JvmStatic
        fun i(m: String) {log(Log.INFO, m)}

        @JvmStatic
        fun w(m: String) {log(Log.WARN, m)}

        @JvmStatic
        fun e(m: String) {log(Log.ERROR, m)}

    }
}