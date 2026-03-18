package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppAdapter adapter;
    private List<AppInfo> appList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadInstalledApps();

        adapter = new AppAdapter(appList, this::showOptionsDialog);
        recyclerView.setAdapter(adapter);
    }

    private void loadInstalledApps() {
        PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages) {
            try {
                PackageInfo pInfo = pm.getPackageInfo(packageInfo.packageName, PackageManager.GET_PERMISSIONS);
                String appName = packageInfo.loadLabel(pm).toString();
                String packageName = packageInfo.packageName;
                boolean isSystemApp = (packageInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
                String version = pInfo.versionName;
                long size = new File(packageInfo.sourceDir).length();
                List<String> permissions = pInfo.requestedPermissions != null ? 
                        Arrays.asList(pInfo.requestedPermissions) : new ArrayList<>();

                appList.add(new AppInfo(appName, packageName, packageInfo.loadIcon(pm), isSystemApp, version, size, permissions));
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void showOptionsDialog(AppInfo app) {
        String[] options = {"App Type", "Open", "Uninstall", "View Details", "Check Permissions"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(app.getAppName());
        builder.setItems(options, (dialog, which) -> {
            switch (which) {
                case 0: // App Type
                    String type = app.isSystemApp() ? "System App" : "User-Installed App";
                    Toast.makeText(this, type, Toast.LENGTH_SHORT).show();
                    break;
                case 1: // Open
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage(app.getPackageName());
                    if (launchIntent != null) {
                        startActivity(launchIntent);
                    } else {
                        Toast.makeText(this, "Cannot open this app", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2: // Uninstall
                    confirmUninstall(app);
                    break;
                case 3: // View Details
                    Intent detailIntent = new Intent(this, DetailActivity.class);
                    detailIntent.putExtra("package_name", app.getPackageName());
                    detailIntent.putExtra("app_name", app.getAppName());
                    detailIntent.putExtra("version", app.getVersion());
                    detailIntent.putExtra("size", app.getSize());
                    detailIntent.putExtra("permissions", new ArrayList<>(app.getPermissions()));
                    startActivity(detailIntent);
                    break;
                case 4: // Check Permissions
                    checkSpecialPermissions(app);
                    break;
            }
        });
        builder.show();
    }

    private void confirmUninstall(AppInfo app) {
        new AlertDialog.Builder(this)
                .setTitle("Uninstall")
                .setMessage("Are you sure you want to uninstall " + app.getAppName() + "?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    Intent intent = new Intent(Intent.ACTION_DELETE);
                    intent.setData(Uri.parse("package:" + app.getPackageName()));
                    startActivity(intent);
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void checkSpecialPermissions(AppInfo app) {
        StringBuilder sb = new StringBuilder();
        boolean location = false;
        boolean camera = false;

        for (String p : app.getPermissions()) {
            if (p.contains("LOCATION")) location = true;
            if (p.contains("CAMERA")) camera = true;
        }

        sb.append("Location: ").append(location ? "Requested" : "No").append("\n");
        sb.append("Camera: ").append(camera ? "Requested" : "No");

        new AlertDialog.Builder(this)
                .setTitle("Special Permissions")
                .setMessage(sb.toString())
                .setPositiveButton("OK", null)
                .show();
    }
}