package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    ListView listView;
    ArrayList<String> userIds;
    ArrayAdapter<String> adapter;
    Button btnSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        myDb = new DatabaseHelper(this);
        listView = findViewById(R.id.list_view_users);
        btnSummary = findViewById(R.id.btn_summary);

        loadUserIds();

        registerForContextMenu(listView);

        btnSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserListActivity.this, SummaryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadUserIds() {
        userIds = new ArrayList<>();
        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            Toast.makeText(this, "No users found", Toast.LENGTH_SHORT).show();
        } else {
            while (res.moveToNext()) {
                userIds.add(res.getString(0));
            }
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userIds);
        listView.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select Action");
        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "Delete");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String selectedId = userIds.get(info.position);

        if (item.getTitle().equals("Edit")) {
            Intent intent = new Intent(UserListActivity.this, EditUserActivity.class);
            intent.putExtra("USER_ID", selectedId);
            startActivity(intent);
        } else if (item.getTitle().equals("Delete")) {
            Integer deletedRows = myDb.deleteData(selectedId);
            if (deletedRows > 0) {
                Toast.makeText(this, "Data Deleted", Toast.LENGTH_SHORT).show();
                loadUserIds();
                Intent intent = new Intent(UserListActivity.this, SummaryActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Data not Deleted", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }
}
