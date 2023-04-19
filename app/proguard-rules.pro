## Add project specific ProGuard rules here.

# Don't obfuscate so that Crashlytics reports and logcat errors can be read
-dontobfuscate

#https://r8.googlesource.com/r8/+/refs/heads/master/compatibility-faq.md

##---------------Begin: proguard configuration for OkHttp  ----------
# PR on OKHttp: https://github.com/square/okhttp/commit/9da841c24c3b3dabc1d9230ab2f1e71105768771
-dontwarn okhttp3.internal.platform.**
-dontwarn org.conscrypt.**
-dontwarn org.bouncycastle.**
-dontwarn org.openjsse.**
##---------------End: proguard configuration for OkHttp  ----------#

#---------------Begin: proguard configuration for Retrofit  ----------
# https://github.com/square/retrofit/issues/3751
 # Keep generic signature of Call, Response (R8 full mode strips signatures from non-kept items).
 -keep,allowobfuscation,allowshrinking interface retrofit2.Call
 -keep,allowobfuscation,allowshrinking class retrofit2.Response

 # With R8 full mode generic signatures are stripped for classes that are not
 # kept. Suspend functions are wrapped in continuations where the type argument
 # is used.
 -keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
 ##---------------End: proguard configuration for Retrofit  ----------
