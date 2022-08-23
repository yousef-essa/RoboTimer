package io.yousefessa.robotimer.update.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UnknownAppSourceRequestActivity extends AppCompatActivity {
    private final ActivityResultLauncher<Intent> unknownAppSourceDialog =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            UpdateController.getInstance().launchUpdateActivity(this);
            finish();
        }
    });

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        @SuppressLint("InlinedApi")
        final Intent unknownAppSourceIntent = new Intent()
                .setAction(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
                .setData(Uri.parse(String.format("package:%s", getPackageName())));
        unknownAppSourceDialog.launch(unknownAppSourceIntent);
    }
}
