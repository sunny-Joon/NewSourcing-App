package com.paisalo.newinternalsourcingapp.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.annotation.Nullable;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.paisalo.newinternalsourcingapp.Activities.ActivityCollection;
import com.paisalo.newinternalsourcingapp.Adapters.AdapterInstSettlementData;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.Modelclasses.PosInstRcv;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCollectionSettlement extends AbsCollectionFragment {

    private static final String ARG_DB_NAME = "param1";
    private static final String ARG_DB_DESC = "param2";
    private String mDbName;
    private String mDbDesc;
    private ListView lv;
    private SearchView searchView;
    public FragmentCollectionSettlement() {
    }

    public static FragmentCollectionSettlement newInstance(String dbName, String dbDesc) {
        FragmentCollectionSettlement fragment = new FragmentCollectionSettlement();
        Bundle args = new Bundle();
        args.putString(ARG_DB_NAME, dbName);
        args.putString(ARG_DB_DESC, dbDesc);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDbName = getArguments().getString(ARG_DB_NAME);
            mDbDesc = getArguments().getString(ARG_DB_DESC);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        lv = (ListView) view.findViewById(R.id.lvDueData);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setEnabled(false);
                view.setClickable(false);
                final PosInstRcv dueData = (PosInstRcv) parent.getAdapter().getItem(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Receipt Settlement");
                builder.setMessage("Are you sure to settle this receipt ?");
                builder.setPositiveButton("Settle Receipt", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PosInstRcv submitData = dueData;
                        submitData.setPayFlag("S");
                        saveDeposit(submitData);
                        dialog.dismiss();

                    }
                });
                builder.setNegativeButton(R.string.Cancel, null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        lv.setAdapter(new AdapterInstSettlementData(getContext(), R.layout.layout_item_receipt_settlement, ((ActivityCollection) getActivity()).getRecSettlementData()));
        searchView = (SearchView) view.findViewById(R.id.searchViewDueData);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ((AdapterInstSettlementData) lv.getAdapter()).getFilter().filter(newText);
                return false;
            }
        });
        return view;
    }

    public String getName() {
        return getArguments().getString(ARG_DB_DESC);
    }


    private void saveDeposit(PosInstRcv instRcv) {

        Gson gson =new Gson();
        JsonObject jsonObject= gson.fromJson(instRcv.toString(), JsonObject.class);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> call = apiService.saveDeposit(GlobalClass.Token,GlobalClass.dbname,jsonObject);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    ActivityCollection.refreshData(null);
                } else {
                    Toast.makeText(getContext(), "Error: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public void refreshData() {
        searchView.setQuery(null, false);
        AdapterInstSettlementData adapterDueData = (AdapterInstSettlementData) lv.getAdapter();
        adapterDueData.clear();
        adapterDueData.addAll(((ActivityCollection) getActivity()).getRecSettlementData());
        adapterDueData.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        refreshData();
        super.onResume();
    }

    @Override
    public void onStart() {
        refreshData();
        super.onStart();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ARG_DB_NAME, mDbName);
        outState.putString(ARG_DB_DESC, mDbDesc);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mDbName = savedInstanceState.getString(ARG_DB_NAME);
            mDbDesc = savedInstanceState.getString(ARG_DB_DESC);
        }
    }


}
