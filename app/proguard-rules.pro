## Add project specific ProGuard rules here.

# Don't obfuscate so that Crashlytics reports and logcat errors can be read
# https://firebase.google.com/docs/crashlytics/get-deobfuscated-reports?platform=android
-dontobfuscate
-keepattributes SourceFile,LineNumberTable        # Keep file names and line numbers.
-keep public class * extends java.lang.Exception  # Optional: Keep custom exceptions.

#https://r8.googlesource.com/r8/+/refs/heads/master/compatibility-faq.md

##---------------Begin: proguard configuration for OkHttp  ----------
# PR on OKHttp: https://github.com/square/okhttp/commit/9da841c24c3b3dabc1d9230ab2f1e71105768771
-dontwarn okhttp3.internal.platform.**
-dontwarn org.conscrypt.**
-dontwarn org.bouncycastle.**
-dontwarn org.openjsse.**
##---------------End: proguard configuration for OkHttp  ----------#
