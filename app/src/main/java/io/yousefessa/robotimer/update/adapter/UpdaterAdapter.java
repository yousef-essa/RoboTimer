package io.yousefessa.robotimer.update.adapter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.FileProvider;
import io.yousefessa.applicationupdater.adapter.ApplicationAdapter;
import io.yousefessa.applicationupdater.adapter.ApplicationAdapterContext;
import io.yousefessa.robotimer.AndroidApplication;
import io.yousefessa.robotimer.R;
import org.jetbrains.annotations.NotNull;

public class UpdaterAdapter implements ApplicationAdapter {

    private static final String APPLICATION_VND_ANDROID_PACKAGE_ARCHIVE = "application/vnd.android.package-archive";
    private static final int DEFAULT_BUFFER_SIZE = 8 * 1024;
    private static final String NOTIFICATION_CHANNEL_ID = "downloading_an_update";
    private static final int NOTIFICATION_ID = 100;
    public static final String NOTIFICATION_TITLE = "Downloading an update";

    private final NotificationCompat.Builder notificationBuilder;
    private final NotificationManagerCompat notificationManager;

    public UpdaterAdapter() {
        final AndroidApplication context = AndroidApplication.getInstance();

        this.notificationManager = NotificationManagerCompat.from(context);

        this.notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setContentTitle(NOTIFICATION_TITLE)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.ic_launcher);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_TITLE,
                    NotificationManager.IMPORTANCE_LOW);
            this.notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    @SuppressLint("SetWorldReadable")
    @Override
    public void onDownload(@NotNull final ApplicationAdapterContext context) {
        final AndroidApplication applicationContext = AndroidApplication.getInstance();
        final InputStream input = context.getInputStream();
        final HttpURLConnection connection = (HttpURLConnection) context.getConnection();
        try {

            final File apkFile = downloadFile(applicationContext, input, connection);
            if (apkFile == null) {
                System.out.println("The downloading process has failed.");
                return;
            }

            installPackage(applicationContext, apkFile);
        } catch (final Exception exception) {
            exception.printStackTrace();
        }
    }

    private void installPackage(final Context context, final File apkFile) {
        System.out.println("Starting the installation process now!");
        final Intent installPackageIntent;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            final Uri apkUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", apkFile);
            installPackageIntent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
            installPackageIntent.setData(apkUri);
            installPackageIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            final Uri apkUri = Uri.fromFile(apkFile);
            installPackageIntent = new Intent(Intent.ACTION_VIEW);
            installPackageIntent.setDataAndType(apkUri, APPLICATION_VND_ANDROID_PACKAGE_ARCHIVE);
        }
        installPackageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        final PackageManager packageManager = context.getPackageManager();
        final UpdateController updateController = UpdateController.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !packageManager.canRequestPackageInstalls()) {
            updateController.requestForPackageInstall(context, installPackageIntent);
        } else {
            updateController.launchUpdateActivity(context, installPackageIntent);
        }

        // todo: create a new activity that handles the
        //  installation process of the newly updated
        //  package, that way we would be able to delete
        //  the apk file once the installation process
        //  is complete

        //        if (apkFile.delete()) {
        //            System.out.println("The apk file has been deleted successfully.");
        //        } else {
        //            System.out.println("The apk file has been deleted unsuccessfully.");
        //        }
    }

    @SuppressLint("SetWorldReadable")
    private File downloadFile(final Context context, final InputStream input, final HttpURLConnection connection) {
        System.out.println("Starting downloading process now!");
        final File downloadDirectory = new File(context.getExternalFilesDir(null), "update");

        if (downloadDirectory.exists()) {
            if (downloadDirectory.delete()) {
                System.out.println("The update folder has been deleted successfully.");
            } else {
                System.out.println("The update folder has been deleted unsuccessfully.");
            }
        }

        if (!downloadDirectory.exists() && downloadDirectory.mkdirs()) {
            System.out.println("The update folder has been created successfully.");
        } else if (!downloadDirectory.exists()) {
            System.out.println("The update folder has been created unsuccessfully.");
            return null;
        }

        final String apkName = UUID.randomUUID() + ".apk";
        final File apkFile = new File(downloadDirectory, apkName);

        if (apkFile.exists()) {
            if (apkFile.delete()) {
                System.out.println("The apkFile file has been deleted successfully.");
            } else {
                System.out.println("The apkFile file has been deleted unsuccessfully.");
            }
        }

        final long fileLength = connection.getContentLength();

        final byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        int bytes;
        int bytesCopied = 0;
        try (final BufferedInputStream bufferedInput = new BufferedInputStream(input); final BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(apkFile))) {
            while ((bytes = bufferedInput.read(buffer, 0, buffer.length)) != -1) {
                bytesCopied += bytes;
                updateProgress(Math.round(bytesCopied * 100.0f / fileLength));
                output.write(buffer, 0, bytes);
            }
        } catch (final IOException exception) {
            exception.printStackTrace();
            removeNotification();
            return null;
        } finally {
            apkFile.setReadable(true, false);
        }
        return apkFile;
    }

    private void updateProgress(final int progress) {
        System.out.println("progress: " + progress);

        if (progress == 100) {
            removeNotification();
            return;
        }

        notificationBuilder
                .setOngoing(true)
                .setProgress(100, progress, false)
                .setContentInfo(progress + "%");

        final Notification notification = notificationBuilder.build();
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    private void removeNotification() {
        notificationManager.cancel(NOTIFICATION_ID);
    }
}
