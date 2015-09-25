# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/naghtarr/dev/android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Android support
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.** { *; }
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }

# Retrofit, OkHttp, Gson, Picasso
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes Exceptions
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**
-dontwarn rx.**
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}
-keep class sun.misc.Unsafe { *; }
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement


#Glide
-keepnames class com.ozm.rocks.data.glide.OzomeGlideModule
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

# Required for Google Play Services (see http://developer.android.com/google/play-services/setup.html)
-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}

-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}

-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

-keep class com.google.android.gms.ads.** { *; }
-keep class com.google.android.gms.gcm.**{ *; }

# Flurry
-keep class com.flurry.** { *; }
-dontwarn com.flurry.**

# Pushwoosh
-dontwarn com.arellomobile.android.push.**

# ButterKnife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewInjector { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

# For Guava:
-dontwarn javax.annotation.**
-dontwarn javax.inject.**
-dontwarn sun.misc.Unsafe

# For RxJava:
-dontwarn org.mockito.**
-dontwarn org.junit.**
-dontwarn org.robolectric.**

# Realm
-keepnames public class * extends io.realm.RealmObject
-keep class io.realm.** { *; }
-keep @io.realm.annotations.RealmClass public class *
-dontwarn javax.**
-dontwarn io.realm.**

# Twitter4j
-dontwarn twitter4j.**
-keep class twitter4j.** { *; }

# Joda
-dontwarn org.joda.convert.**
-dontwarn org.joda.time.tz.ZoneInfoCompiler

# GifImageView
-keep public class pl.droidsonroids.gif.GifIOException{<init>(int);}
-keep class pl.droidsonroids.gif.GifInfoHandle{<init>(long,int,int,int);}

# Api classes
-keep class ru.groupon.merchant.data.api.** { *; }
-keep interface ru.groupon.merchant.data.api.** { *; }

# Crashlitics (https://github.com/krschultz/android-proguard-snippets/blob/master/libraries/proguard-crashlytics.pro)
-keep class com.crashlytics.** { *; }
-keepattributes SourceFile,LineNumberTable

#Localitics
-keep class com.localytics.android.** { *; }

