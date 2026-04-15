package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class EditUserActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName, editEmail;
    RadioGroup radioGroupGender;
    RadioButton rbMale, rbFemale;
    CheckBox cbCoding, cbReading, cbSports;
    Spinner spinnerDept;
    DatePicker datePicker;
    Button btnUpdate;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        myDb = new DatabaseHelper(this);

        editName = findViewById(R.id.edit_et_name);
        editEmail = findViewById(R.id.edit_et_email);
        radioGroupGender = findViewById(R.id.edit_rg_gender);
        rbMale = findViewById(R.id.edit_rb_male);
        rbFemale = findViewById(R.id.edit_rb_female);
        cbCoding = findViewById(R.id.edit_cb_coding);
        cbReading = findViewById(R.id.edit_cb_reading);
        cbSports = findViewById(R.id.edit_cb_sports);
        spinnerDept = findViewById(R.id.edit_spinner_dept);
        datePicker = findViewById(R.id.edit_date_picker);
        btnUpdate = findViewById(R.id.btn_update);

        userId = getIntent().getStringExtra("USER_ID");
        loadUserData();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });
    }

    private void loadUserData() {
        Cursor res = myDb.getUser(userId);
        if (res != null && res.moveToFirst()) {
            editName.setText(res.getString(1));
            editEmail.setText(res.getString(2));
            
            String gender = res.getString(3);
            if ("Male".equals(gender)) rbMale.setChecked(true);
            else if ("Female".equals(gender)) rbFemale.setChecked(true);

            String interests = res.getString(4);
            if (interests != null) {
                if (interests.contains("Coding")) cbCoding.setChecked(true);
                if (interests.contains("Reading")) cbReading.setChecked(true);
                if (interests.contains("Sports")) cbSports.setChecked(true);
            }

            String dept = res.getString(5);
            if (dept != null) {
                String[] depts = getResources().getStringArray(R.array.departments);
                int index = Arrays.asList(depts).indexOf(dept);
                if (index >= 0) spinnerDept.setSelection(index);
            }

            String dob = res.getString(6);
            if (dob != null && dob.contains("/")) {
                String[] parts = dob.split("/");
                int day = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]) - 1;
                int year = Integer.parseInt(parts[2]);
                datePicker.init(year, month, day, null);
            }
        }
    }

    private void updateData() {
        String name = editName.getText().toString();
        String email = editEmail.getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (myDb.isEmailExistsForOther(userId, email)) {
            Toast.makeText(this, "Email already exists! Please use a different one.", Toast.LENGTH_LONG).show();
            return;
        }
        
        int selectedId = radioGroupGender.getCheckedRadioButtonId();
        String gender = "";
        if (selectedId != -1) {
            RadioButton rb = findViewById(selectedId);
            gender = rb.getText().toString();
        }

        StringBuilder interests = new StringBuilder();
        if (cbCoding.isChecked()) interests.append("Coding ");
        if (cbReading.isChecked()) interests.append("Reading ");
        if (cbSports.isChecked()) interests.append("Sports ");

        String dept = spinnerDept.getSelectedItem().toString();
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();
        String dob = day + "/" + month + "/" + year;

        boolean isUpdate = myDb.updateAllData(userId, name, email, gender, interests.toString().trim(), dept, dob);
        if (isUpdate) {
            Toast.makeText(this, "Data Updated", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Data not Updated", Toast.LENGTH_SHORT).show();
        }
    }
}
