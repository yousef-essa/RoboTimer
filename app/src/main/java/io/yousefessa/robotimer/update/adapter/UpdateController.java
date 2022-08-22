package io.yousefessa.robotimer.update.adapter;

import android.content.Context;
import android.content.Intent;
import io.yousefessa.robotimer.util.ApplicationMainLooper;

public class UpdateController {
    private Intent installIntent;

    public void requestForPackageInstall(final Context context, final Intent installIntent) {
        installIntent(installIntent);

        ApplicationMainLooper.instance()
                .post(() -> {
                    final Intent updateIntent = new Intent(context, UnknownAppSourceRequestActivity.class);
                    updateIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(updateIntent);
                    installIntent(null);
                });
    }

    public void launchUpdateActivity(final Context context, final Intent installPackageIntent) {
        if (installPackageIntent == null) {
            return;
        }

        ApplicationMainLooper.instance()
                .post(() -> {
                    context.startActivity(installPackageIntent);
                    installIntent(null);
                });
    }

    public void launchUpdateActivity(final Context context) {
        launchUpdateActivity(context, installIntent());
    }

    public void installIntent(final Intent inputStream) {
        this.installIntent = inputStream;
    }

    public Intent installIntent() {
        return installIntent;
    }

    public static class Singleton {
        public static final UpdateController INSTANCE = new UpdateController();
    }
}
