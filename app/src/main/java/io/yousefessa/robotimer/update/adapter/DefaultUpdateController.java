package io.yousefessa.robotimer.update.adapter;

import android.content.Context;
import android.content.Intent;
import io.yousefessa.robotimer.util.ApplicationMainLooper;

public class DefaultUpdateController implements UpdateController {
    private Intent installIntent;

    @Override
    public void requestForPackageInstall(final Context context, final Intent packageIntent) {
        packageIntent(packageIntent);

        ApplicationMainLooper.instance()
                .post(() -> {
                    final Intent updateIntent = new Intent(context, UnknownAppSourceRequestActivity.class);
                    updateIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(updateIntent);
                    packageIntent(null);
                });
    }

    @Override
    public void launchUpdateActivity(final Context context, final Intent packageIntent) {
        if (packageIntent == null) {
            return;
        }

        ApplicationMainLooper.instance()
                .post(() -> {
                    context.startActivity(packageIntent);
                    packageIntent(null);
                });
    }

    @Override
    public void launchUpdateActivity(final Context context) {
        launchUpdateActivity(context, packageIntent());
    }

    @Override
    public void packageIntent(final Intent intent) {
        this.installIntent = intent;
    }

    @Override
    public Intent packageIntent() {
        return installIntent;
    }
}
