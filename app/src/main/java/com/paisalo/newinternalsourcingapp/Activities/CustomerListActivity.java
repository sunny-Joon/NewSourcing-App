package com.paisalo.newinternalsourcingapp.Activities;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.paisalo.newinternalsourcingapp.Adapters.CustomerListAdapter;
import com.paisalo.newinternalsourcingapp.Adapters.EMI_RecyclerviewAdapter;
import com.paisalo.newinternalsourcingapp.Modelclasses.CustomerModel;
import com.paisalo.newinternalsourcingapp.Modelclasses.EmiModel;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Utils.GlobalClass;

import java.util.ArrayList;
import java.util.List;

public class CustomerListActivity extends AppCompatActivity {

    private RecyclerView recyclerView,emi_recyclerview;
    private CustomerListAdapter adapter;
    private EMI_RecyclerviewAdapter emi_adapter;
    ImageView backgroundImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        View view = findViewById(android.R.id.content).getRootView();
        backgroundImageView = findViewById(R.id.customerlistBackgroundImageview);
        recyclerView = findViewById(R.id.customerListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<CustomerModel> dataList = getDataList();

        adapter = new CustomerListAdapter(this, dataList, new CustomerListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View itemView) {
                showPopup(position, itemView,view);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    private List<CustomerModel> getDataList() {
        List<CustomerModel> dataList = new ArrayList<>();
        dataList.add(new CustomerModel("Baldev", "Suresh", "9999999999","2545","UCAR007739","78 Ganj Khair Aligarh"));
        dataList.add(new CustomerModel("John", "Doe", "1234567890", "5678", "UCAR012345", "123 Main St, Cityville"));
        dataList.add(new CustomerModel("Alice", "Smith", "9876543210", "9876", "UCAR056789", "456 Oak St, Townsville"));
        dataList.add(new CustomerModel("Bob", "Johnson", "1112233444", "1234", "UCAR098765", "789 Pine St, Villagetown"));
        dataList.add(new CustomerModel("Eva", "Williams", "5556667777", "4321", "UCAR987654", "321 Elm St, Hamletville"));
        dataList.add(new CustomerModel("Carlos", "Martinez", "8889990000", "5678", "UCAR543210", "654 Birch St, Boroughville"));
        dataList.add(new CustomerModel("Sophia", "Brown", "4445556666", "8765", "UCAR111222", "987 Cedar St, Municipalitytown"));

        return dataList;
    }

    private void showPopup(int position, View itemView, View view) {
        Dialog dialog = GlobalClass.showBlurredPopup(itemView.getContext(), view, backgroundImageView, R.layout.collection_popup);

        dialog.show();
        backgroundImageView.setVisibility(View.VISIBLE);

        Button cancel = dialog.findViewById(R.id.cancelPopup);
        emi_recyclerview = dialog.findViewById(R.id.emiRecyclerView);
        CheckBox checkBox = dialog.findViewById(R.id.lumpsumamount);
        CardView cardView = dialog.findViewById(R.id.lumpsumAmountCardView);
        cardView.setVisibility(View.GONE);

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            cardView.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });

        emi_recyclerview.setLayoutManager(new LinearLayoutManager(itemView.getContext()));

        List<EmiModel> emiList = getEMIDataList();
        emi_adapter = new EMI_RecyclerviewAdapter(itemView.getContext(), emiList);

        emi_recyclerview.setAdapter(emi_adapter);


        cancel.setOnClickListener(v -> {
                    dialog.dismiss();
                    dialog.dismiss();
                });
        }



    private List<EmiModel> getEMIDataList() {
        List<EmiModel> emiDataList = new ArrayList<>();
        emiDataList.add(new EmiModel("08-07-2023", "2585"));
        emiDataList.add(new EmiModel("08-08-2023", "2585"));
        emiDataList.add(new EmiModel("08-09-2023", "2585"));

        return emiDataList;
    }

}
