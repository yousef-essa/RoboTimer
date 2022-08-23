package io.yousefessa.robotimer.update.adapter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.content.FileProvider;
import io.yousefessa.applicationupdater.adapter.ApplicationAdapter;
import io.yousefessa.robotimer.AndroidApplication;
import org.jetbrains.annotations.NotNull;

public class UpdaterAdapter implements ApplicationAdapter {

    public static final String APPLICATION_VND_ANDROID_PACKAGE_ARCHIVE = "application/vnd.android.package-archive";
    public static final int DEFAULT_BUFFER_SIZE = 8 * 1024;

    @SuppressLint("SetWorldReadable")
    @Override
    public void onDownload(@NotNull final InputStream input) {
        try {
            final AndroidApplication applicationContext = AndroidApplication.getInstance();

            final File apkFile = downloadFile(applicationContext, input);
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
            final Uri apkUri = FileProvider.getUriForFile(context,
                    context.getPackageName() + ".provider", apkFile);
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
    private File downloadFile(final Context context, final InputStream input) {
        System.out.println("Starting downloading process now!");
        final File downloadDirectory = new File(context.getExternalFilesDir(null), "update");

        if (!downloadDirectory.exists()) {
            if (downloadDirectory.mkdirs()) {
                System.out.println("The update folder has been created successfully.");
            } else {
                System.out.println("The update folder has been created unsuccessfully.");
            }
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

        // todo: unsupported due to the connection not being exposed here... fix this!
        final long fileLength = -1;

        final byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        int bytes;
        int bytesCopied = 0;
        try (final BufferedInputStream bufferedInput = new BufferedInputStream(input); final BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(apkFile))) {
            while ((bytes = bufferedInput.read(buffer, 0, buffer.length)) != -1) {
                bytesCopied += bytes;
                updateProgress(Math.round(bytesCopied * 100.0f / fileLength));
                output.write(buffer, 0, bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            apkFile.setReadable(true, false);
        }
        return apkFile;
    }

    private void updateProgress(final int progress) {
        System.out.println("progress: " + progress);
        // todo: implement this after fileLength variable is ready to be used
    }
}
