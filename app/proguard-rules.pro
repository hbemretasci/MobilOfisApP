# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keep class com.codmine.mukellef.data.remote.dto.balance.* { *; }
-keep class com.codmine.mukellef.data.remote.dto.documents.* { *; }
-keep class com.codmine.mukellef.data.remote.dto.chat.* { *; }
-keep class com.codmine.mukellef.data.remote.dto.notifications.* { *; }
-keep class com.codmine.mukellef.data.remote.dto.post_message.* { *; }
-keep class com.codmine.mukellef.data.remote.dto.post_reading.* { *; }
-keep class com.codmine.mukellef.data.remote.dto.tax_payer.* { *; }
-keep class com.codmine.mukellef.data.remote.dto.unread.* { *; }
-keep class com.codmine.mukellef.domain.model.balance.* { *; }
-keep class com.codmine.mukellef.domain.model.chat.* { *; }
-keep class com.codmine.mukellef.domain.model.datastore.* { *; }
-keep class com.codmine.mukellef.domain.model.documents.* { *; }
-keep class com.codmine.mukellef.domain.model.notifications.* { *; }
-keep class com.codmine.mukellef.domain.model.tax_payer.* { *; }
-keep class com.codmine.mukellef.domain.model.validation.* { *; }
