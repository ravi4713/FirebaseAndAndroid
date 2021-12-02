package com.example.fire_base_crud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ShowDetails extends AppCompatActivity {

    private FirebaseFirestore db;
    final ArrayList<ShowDetailsModel> items = new ArrayList<ShowDetailsModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);
        db = FirebaseFirestore.getInstance();
        showData();
    }

    private void showData(){
        items.clear();
        db.collection("Documents").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot snapshot : task.getResult()){
                            items.add(new ShowDetailsModel(snapshot.getString("id"),snapshot.getString("title"), snapshot.getString("description")));
                        }
                        ShowDetailsAadaptor adaptor = new ShowDetailsAadaptor(ShowDetails.this, items);
                        ListView listView = (ListView) findViewById(R.id.list);

                        listView.setAdapter(adaptor);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                ShowDetailsModel item = items.get(position);
                                Bundle bundle = new Bundle();
                                bundle.putString("uID", item.getMid());
                                bundle.putString("uTitle", item.getmTilte());
                                bundle.putString("uDesc", item.getmDescription());
                                Intent intent = new Intent(ShowDetails.this, Update_Delete.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });

                        Toast.makeText(ShowDetails.this, "Done!!", Toast.LENGTH_SHORT).show();
                    }


                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ShowDetails.this, "Some thing went Wrong !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
