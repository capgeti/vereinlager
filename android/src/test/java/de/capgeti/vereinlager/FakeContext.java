package de.capgeti.vereinlager;

import android.content.*;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import java.io.*;

/**
* Author: capgeti
* Date:   06.10.13 00:23
*/
class FakeContext extends Context {
    @Override public AssetManager getAssets() {
        return null;
    }

    @Override public Resources getResources() {
        return null;
    }

    @Override public PackageManager getPackageManager() {
        return null;
    }

    @Override public ContentResolver getContentResolver() {
        return null;
    }

    @Override public Looper getMainLooper() {
        return null;
    }

    @Override public Context getApplicationContext() {
        return null;
    }

    @Override public void setTheme(int i) {
    }

    @Override public Resources.Theme getTheme() {
        return null;
    }

    @Override public ClassLoader getClassLoader() {
        return null;
    }

    @Override public String getPackageName() {
        return null;
    }

    @Override public ApplicationInfo getApplicationInfo() {
        return null;
    }

    @Override public String getPackageResourcePath() {
        return null;
    }

    @Override public String getPackageCodePath() {
        return null;
    }

    @Override public SharedPreferences getSharedPreferences(String s, int i) {
        return null;
    }

    @Override public FileInputStream openFileInput(String s) throws FileNotFoundException {
        return null;
    }

    @Override public FileOutputStream openFileOutput(String s, int i) throws FileNotFoundException {
        return null;
    }

    @Override public boolean deleteFile(String s) {
        return false;
    }

    @Override public File getFileStreamPath(String s) {
        return null;
    }

    @Override public File getFilesDir() {
        return null;
    }

    @Override public File getExternalFilesDir(String s) {
        return null;
    }

    @Override public File getObbDir() {
        return null;
    }

    @Override public File getCacheDir() {
        return null;
    }

    @Override public File getExternalCacheDir() {
        return null;
    }

    @Override public String[] fileList() {
        return new String[0];
    }

    @Override public File getDir(String s, int i) {
        return null;
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String s, int i, SQLiteDatabase.CursorFactory cursorFactory) {
        return null;
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String s, int i, SQLiteDatabase.CursorFactory cursorFactory, DatabaseErrorHandler databaseErrorHandler) {
        return null;
    }

    @Override public boolean deleteDatabase(String s) {
        return false;
    }

    @Override public File getDatabasePath(String s) {
        return null;
    }

    @Override public String[] databaseList() {
        return new String[0];
    }

    @Override public Drawable getWallpaper() {
        return null;
    }

    @Override public Drawable peekWallpaper() {
        return null;
    }

    @Override public int getWallpaperDesiredMinimumWidth() {
        return 0;
    }

    @Override public int getWallpaperDesiredMinimumHeight() {
        return 0;
    }

    @Override public void setWallpaper(Bitmap bitmap) throws IOException {
    }

    @Override public void setWallpaper(InputStream inputStream) throws IOException {
    }

    @Override public void clearWallpaper() throws IOException {
    }

    @Override public void startActivity(Intent intent) {
    }

    @Override public void startActivity(Intent intent, Bundle bundle) {
    }

    @Override public void startActivities(Intent[] intents) {
    }

    @Override public void startActivities(Intent[] intents, Bundle bundle) {
    }

    @Override
    public void startIntentSender(IntentSender intentSender, Intent intent, int i, int i2, int i3) throws IntentSender.SendIntentException {
    }

    @Override
    public void startIntentSender(IntentSender intentSender, Intent intent, int i, int i2, int i3, Bundle bundle) throws IntentSender.SendIntentException {
    }

    @Override public void sendBroadcast(Intent intent) {
    }

    @Override public void sendBroadcast(Intent intent, String s) {
    }

    @Override public void sendOrderedBroadcast(Intent intent, String s) {
    }

    @Override
    public void sendOrderedBroadcast(Intent intent, String s, BroadcastReceiver broadcastReceiver, Handler handler, int i, String s2, Bundle bundle) {
    }

    @Override public void sendStickyBroadcast(Intent intent) {
    }

    @Override
    public void sendStickyOrderedBroadcast(Intent intent, BroadcastReceiver broadcastReceiver, Handler handler, int i, String s, Bundle bundle) {
    }

    @Override public void removeStickyBroadcast(Intent intent) {
    }

    @Override public Intent registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        return null;
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, String s, Handler handler) {
        return null;
    }

    @Override public void unregisterReceiver(BroadcastReceiver broadcastReceiver) {
    }

    @Override public ComponentName startService(Intent intent) {
        return null;
    }

    @Override public boolean stopService(Intent intent) {
        return false;
    }

    @Override public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        return false;
    }

    @Override public void unbindService(ServiceConnection serviceConnection) {
    }

    @Override public boolean startInstrumentation(ComponentName componentName, String s, Bundle bundle) {
        return false;
    }

    @Override public Object getSystemService(String s) {
        return null;
    }

    @Override public int checkPermission(String s, int i, int i2) {
        return 0;
    }

    @Override public int checkCallingPermission(String s) {
        return 0;
    }

    @Override public int checkCallingOrSelfPermission(String s) {
        return 0;
    }

    @Override public void enforcePermission(String s, int i, int i2, String s2) {
    }

    @Override public void enforceCallingPermission(String s, String s2) {
    }

    @Override public void enforceCallingOrSelfPermission(String s, String s2) {
    }

    @Override public void grantUriPermission(String s, Uri uri, int i) {
    }

    @Override public void revokeUriPermission(Uri uri, int i) {
    }

    @Override public int checkUriPermission(Uri uri, int i, int i2, int i3) {
        return 0;
    }

    @Override public int checkCallingUriPermission(Uri uri, int i) {
        return 0;
    }

    @Override public int checkCallingOrSelfUriPermission(Uri uri, int i) {
        return 0;
    }

    @Override public int checkUriPermission(Uri uri, String s, String s2, int i, int i2, int i3) {
        return 0;
    }

    @Override public void enforceUriPermission(Uri uri, int i, int i2, int i3, String s) {
    }

    @Override public void enforceCallingUriPermission(Uri uri, int i, String s) {
    }

    @Override public void enforceCallingOrSelfUriPermission(Uri uri, int i, String s) {
    }

    @Override public void enforceUriPermission(Uri uri, String s, String s2, int i, int i2, int i3, String s3) {
    }

    @Override public Context createPackageContext(String s, int i) throws PackageManager.NameNotFoundException {
        return null;
    }
}
