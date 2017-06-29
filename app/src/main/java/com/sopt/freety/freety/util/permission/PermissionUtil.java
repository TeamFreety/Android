package com.sopt.freety.freety.util.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;


/**
 * Created by cmslab on 6/29/17.
 */

public class PermissionUtil {

    public static final int REQUEST_LOCATION = 2002;
    public static int checkSelfPermission(@NonNull Context context, @NonNull String permission) {
        if (permission == null) {
            throw new IllegalArgumentException("permission is null");
        }

        return context.checkPermission(permission, android.os.Process.myPid(), android.os.Process.myUid());
    }

    public static boolean checkPermissions(Activity activity, String permission) {
        int permissionResult = ActivityCompat.checkSelfPermission(activity, permission);
        if (permissionResult == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    public static void requestPermissions(final @NonNull Activity activity,
                                          final @NonNull String[] permissions,
                                          final int requestCode) {
        if (activity instanceof ActivityCompat.OnRequestPermissionsResultCallback) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    final int[] grantResults = new int[permissions.length];
                    PackageManager packageManager = activity.getPackageManager();
                    String packageName = activity.getPackageName();

                    final int permissionCount = permissions.length;
                    for (int i = 0; i < permissionCount; i++) {
                        grantResults[i] = packageManager.checkPermission(permissions[i], packageName);
                    }

                    ((ActivityCompat.OnRequestPermissionsResultCallback) activity).onRequestPermissionsResult(
                            requestCode, permissions, grantResults);
                }
            });
        }
    }

    public static boolean verifyPermission(int[] grantResults) {
        if (grantResults.length < 1) {
            return false;
        }
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static void requestFineLocationPermissions(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
    }
}
