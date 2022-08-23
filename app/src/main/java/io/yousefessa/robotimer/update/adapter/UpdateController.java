package io.yousefessa.robotimer.update.adapter;

import android.content.Context;
import android.content.Intent;

public interface UpdateController {
    void requestForPackageInstall(final Context context, final Intent packageIntent);

    void launchUpdateActivity(final Context context);
    void launchUpdateActivity(final Context context, final Intent packageIntent);

    void packageIntent(final Intent intent);
    Intent packageIntent();

    class SingletonHolder {
        private static final DefaultUpdateController INSTANCE = new DefaultUpdateController();
    }

    static UpdateController getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
