package com.reset4.fourwork.constants;

/**
 * Created by eilkyam on 12.06.2016.
 */
public class ProjectConstants {

    /*
     * Sequence for first use;
     * 1) Set application name in AndroidManifest.xml as android:name=".manifest.FourApp" or derived.
     * 2) Set EntityLibraryPackageName
     * 3) Set BusinessObjectLibraryPackageName
     * 4) Set DatabaseName
     * 5) Set DatabaseVersion (If metadata is changed, DatabaseVersion must be increased.)
     * 6) add compile 'com.firebase:firebase-client-android:2.5.2+' to dependencies in build.gradle (Module: app)
     * 7) add below lines to build.gradle (Module: app)
     *      packagingOptions {
     *          exclude 'META-INF/LICENSE'
     *          exclude 'META-INF/LICENSE-FIREBASE.txt'
     *          exclude 'META-INF/NOTICE'
     *      }
     * 8) add below lines to proguard-rules.pro (ProGuard Rules for app)
     *      -keep class com.firebase.** { *; }
     *      -keepnames class com.fasterxml.jackson.** { *; }
     *      -dontwarn com.fasterxml.jackson.databind.**
     * 9) add <uses-permission android:name="android.permission.INTERNET" /> to AndroidManifest.xml
     */

    public final static String EntityLibraryPackageName = "com.reset4.passlock.entities";
    public final static String BusinessObjectLibraryPackageName = "com.reset4.passlock.businessobjects";
    public final static String DatabaseName = "PassLock";
    public final static int DatabaseVersion = 1;
}
