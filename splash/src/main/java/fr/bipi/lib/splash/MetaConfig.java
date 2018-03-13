package fr.bipi.lib.splash;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * <p>Created on 19/06/17
 *
 * @author Bastien PAUL
 */
class MetaConfig {

    private static final String TAG = "MetaConfig";
    private static final String KEY_ACTIVITY = "activity";
    private static final String KEY_TIMING = "timing";
    private static final int DEFAULT_TIMING = 0;

    private int timing = DEFAULT_TIMING;
    private Intent targetIntent;

    MetaConfig(@NonNull Activity activity) {
        try {
            final ActivityInfo info = activity.getPackageManager()
                .getActivityInfo(activity.getComponentName(), PackageManager.GET_META_DATA);

            String targetActivity = info.metaData.getString(KEY_ACTIVITY);
            if (targetActivity == null) {
                throw new NullPointerException("Target activity is not defined in manifest's meta data");
            }
            timing = info.metaData.getInt(KEY_TIMING, DEFAULT_TIMING);
            ComponentName componentName = new ComponentName(activity.getPackageName(), targetActivity);

            targetIntent = new Intent(activity.getIntent());
            targetIntent.setComponent(componentName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    int getTiming() {
        return timing;
    }

    @Nullable
    Intent getTargetIntent() {
        return targetIntent;
    }
}
