package com.paisalo.newinternalsourcingapp.Activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.paisalo.newinternalsourcingapp.Adapters.CollectionAdapter;
import com.paisalo.newinternalsourcingapp.Fragments.collection.CollectionViewModel;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.databinding.ActivityCollectionBinding;

public class CollectionActivity extends AppCompatActivity {

    private CollectionAdapter collectionAdapter;
    private RecyclerView recyclerView;
    ActivityCollectionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

                binding = ActivityCollectionBinding.inflate(getLayoutInflater());
                setContentView(binding.getRoot());

                CollectionViewModel collectionViewModel = new ViewModelProvider(this).get(CollectionViewModel.class);

                recyclerView = findViewById(R.id.applicationFormRecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));

                String key = "YourKey";
                collectionAdapter = new CollectionAdapter(this, "David", "Sam", "001", "4545454545", "Mumbai", "NewDelhi");
                recyclerView.setAdapter(collectionAdapter);
            }
        }