## Add project specific ProGuard rules here.

# Don't obfuscate so that Crashlytics reports and logcat errors can be read
# https://firebase.google.com/docs/crashlytics/get-deobfuscated-reports?platform=android
-dontobfuscate
-keepattributes SourceFile,LineNumberTable        # Keep file names and line numbers.
-keep public class * extends java.lang.Exception  # Optional: Keep custom exceptions.

#https://r8.googlesource.com/r8/+/refs/heads/master/compatibility-faq.md

##---------------Begin: proguard configuration for Ktor Client  ----------
# Issue Tracker (Initial issue/Work-around): https://youtrack.jetbrains.com/issue/KTOR-5528
# Issue Tracker (Fix): https://youtrack.jetbrains.com/issue/KTOR-3484
-dontwarn org.slf4j.impl.StaticLoggerBinder
##---------------End: proguard configuration for Ktor Client  ----------
