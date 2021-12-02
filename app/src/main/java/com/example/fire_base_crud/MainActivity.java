package com.example.fire_base_crud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private EditText mTitle, mDesc;
    private Button mSave, mSHowAll;
    private FirebaseFirestore db;
    private String uId, uTitle, uDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = findViewById(R.id.title);
        mDesc = findViewById(R.id.description);
        mSave = findViewById(R.id.save);
        mSHowAll = findViewById(R.id.sAll);

        db = FirebaseFirestore.getInstance();
        mSave.setText("Save");


        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String title = mTitle.getText().toString();
                String description = mDesc.getText().toString();
                String id = UUID.randomUUID().toString();
                saveToFireStroe(id, title, description);
            }
        });

        mSHowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ShowDetails.class));
            }
        });
    }


    private void saveToFireStroe(String id, String title, String description) {
        if (title.isEmpty() || description.isEmpty()){
            Toast.makeText(MainActivity.this, "Please Enter in both the field", Toast.LENGTH_SHORT).show();
        }else{
            HashMap<String, Object> map = new HashMap<>();

            map.put("id", id);
            map.put("title", title);
            map.put("description", description);

            db.collection("Documents").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){

                                Toast.makeText(MainActivity.this, "Data Saved to fireStore", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Error in Saving", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}