package io.yousefessa.robotimer;

import java.util.Arrays;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TimerActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    private static final int SYSTEM_ALERT_WINDOW_CODE = 5469;

    private static TimerActivity instance;

    public static TimerActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        instance = this;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            askPermission();
        } else {
            startAppService();
        }

        final TextView versionTextView = findViewById(R.id.version);
        versionTextView.setText(AndroidApplication.getInstance().getLocalVersion());
    }

    private void startAppService() {
        System.out.println("Starting service now...");
        final Intent service = new Intent(this, AppService.class);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            startService(service);
        } else {
            startForegroundService(service);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println("onActivityResult: " + requestCode + " | " + resultCode + " | " + data);

        if (requestCode != SYSTEM_ALERT_WINDOW_CODE) {
            return;
        }

        final String toastText;
        if (Settings.canDrawOverlays(this)) {
            toastText = "Authorization request has been granted";
            startAppService();
        } else {
            toastText = "Authorization request has been denied.";
        }
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT)
                .show();
    }

    private void askPermission() {
        System.out.println("Asking for a permission!");

        final Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, SYSTEM_ALERT_WINDOW_CODE);
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode,
            @NonNull @NotNull final String[] permissions, @NonNull @NotNull final int[] grantResults) {
        System.out.println("permission: " + requestCode + " | " + Arrays.toString(permissions) + " | " +
                           Arrays.toString(grantResults));

        if (requestCode != SYSTEM_ALERT_WINDOW_CODE) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (!Arrays.asList(grantResults)
                .contains(PackageManager.PERMISSION_GRANTED)) {
            return;
        }

        startAppService();
    }
}