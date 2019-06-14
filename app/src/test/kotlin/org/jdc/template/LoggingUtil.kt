package org.jdc.template

object LoggingUtil {
    /**
     * Java Logger (used by JUnit and OkHttp on JVM) adds a timestamp and newline with each log... this can make it difficult to read the output logs of OkHttp
     * This function will setup the format of the Logger so that the logs are on a single line
     *
     * NOTE: For JUnit tests... This call should be made in the setup() (before the Logger is initialized)
     *
     * @param logMessageOnly Set to true to ONLY show the log message (exclude timestamp etc)
     */
    fun setupSingleLineLogging(logMessageOnly: Boolean = false) {
        if (logMessageOnly) {
            System.setProperty("java.util.logging.SimpleFormatter.format", "%5\$s %6\$s%n")
        } else {
            System.setProperty("java.util.logging.SimpleFormatter.format", "%1\$tY-%1\$tm-%1\$td %1\$tH:%1\$tM:%1\$tS.%1\$tL %4$-7s [%3\$s] %5\$s %6\$s%n")
        }
    }
}