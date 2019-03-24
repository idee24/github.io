package momatechsoftwares.rest_demo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import momatechsoftwares.rest_demo.R;

public class FireBaseActivity extends AppCompatActivity {

    private StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_base);

        storageReference = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FireBaseActivity.this, MovieListActivity.class);
        startActivity(intent);
        finish();
    }
}
