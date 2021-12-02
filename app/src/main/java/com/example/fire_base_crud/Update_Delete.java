package com.example.fire_base_crud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class Update_Delete extends AppCompatActivity {
    private EditText mTitle, mDesc;
    private Button mSave, mSHowAll;

    private String uId, uTitle, uDesc;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = findViewById(R.id.title);
        mDesc = findViewById(R.id.description);
        mSave = findViewById(R.id.save);
        mSHowAll = findViewById(R.id.sAll);

        mSave.setText("Update");
        mSHowAll.setText("Delete");
        db = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        uTitle = bundle.getString("uTitle");
        uId = bundle.getString("uID");
        uDesc = bundle.getString("uDesc");
        mTitle.setText(uTitle);
        mDesc.setText(uDesc);

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String title = mTitle.getText().toString();
                String description = mDesc.getText().toString();
                String id = uId;
                updateToFireStore(id, title, description);
            }
        });
        mSHowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDate(uId);

            }
        });

    }

    private void deleteDate(String uId) {
        db.collection("Documents").document(uId).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Update_Delete.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Update_Delete.this, ShowDetails.class));
                        } else {
                            Toast.makeText(Update_Delete.this, "Error in deleting", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void updateToFireStore(String id, String uTitle, String uDesc) {
        db.collection("Documents").document(id).update("title", uTitle, "description", uDesc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Update_Delete.this, "Data Updated to fireStore", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Update_Delete.this, "Error in Updating", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}