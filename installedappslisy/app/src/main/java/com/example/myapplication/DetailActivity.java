package com.example.myapplication;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView iconView = findViewById(R.id.detailIcon);
        TextView nameView = findViewById(R.id.detailName);
        TextView packageView = findViewById(R.id.detailPackage);
        TextView versionView = findViewById(R.id.detailVersion);
        TextView sizeView = findViewById(R.id.detailSize);
        TextView permissionsView = findViewById(R.id.detailPermissions);

        String packageName = getIntent().getStringExtra("package_name");
        String appName = getIntent().getStringExtra("app_name");
        String version = getIntent().getStringExtra("version");
        long size = getIntent().getLongExtra("size", 0);
        List<String> permissions = (List<String>) getIntent().getSerializableExtra("permissions");

        nameView.setText(appName);
        packageView.setText(packageName);
        versionView.setText("Version: " + version);
        sizeView.setText("Size: " + (size / (1024 * 1024)) + " MB");

        try {
            iconView.setImageDrawable(getPackageManager().getApplicationIcon(packageName));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (permissions != null && !permissions.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (String p : permissions) {
                sb.append(p).append("\n");
            }
            permissionsView.setText(sb.toString());
        } else {
            permissionsView.setText("No permissions found.");
        }
    }
}