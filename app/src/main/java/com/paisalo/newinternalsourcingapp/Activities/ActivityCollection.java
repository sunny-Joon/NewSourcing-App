package com.paisalo.newinternalsourcingapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.paisalo.newinternalsourcingapp.Adapters.AdapterCollectionFragmentPager;
import com.paisalo.newinternalsourcingapp.BuildConfig;
import com.paisalo.newinternalsourcingapp.Entities.ScanAadhaar.DateUtils;
import com.paisalo.newinternalsourcingapp.Fragments.AbsCollectionFragment;
import com.paisalo.newinternalsourcingapp.Fragments.FragmentCollection;
import com.paisalo.newinternalsourcingapp.Fragments.FragmentCollectionSettlement;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.Modelclasses.DueData;
import com.paisalo.newinternalsourcingapp.Modelclasses.PosInstRcv;
import com.paisalo.newinternalsourcingapp.Modelclasses.SmCode_DateModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.Collection.CustomerListDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.Collection.CustomerListModel;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;
import com.paisalo.newinternalsourcingapp.Utils.Utils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCollection extends AppCompatActivity {

    private static AdapterCollectionFragmentPager fragmentPagerAdapter;
    private ViewPager fragContainer;
    private static ArrayList<CustomerListDataModel> dueDataList = new ArrayList<>();
    private static ArrayList<PosInstRcv> settlementDataList = new ArrayList<>();
    private static String foCode;
    private static String creator;
    private static String areaCode;
    private DueData dueData;
    private static ArrayList<AbsCollectionFragment> absFragments;
    private static AbsCollectionFragment fragSettlement = null;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        Intent intent = getIntent();
        if (intent != null) {
            foCode = intent.getStringExtra("foCode");
            creator = intent.getStringExtra("creator");
            areaCode = intent.getStringExtra("areaCode");
        }

       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);*//*
        TabLayout tabLayout  = findViewById(R.id.tablayout);

        tabLayout.setVisibility(View.GONE);*/

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        absFragments = new ArrayList<>();

        fragmentPagerAdapter = new AdapterCollectionFragmentPager(getSupportFragmentManager(), absFragments);


        List<SmCode_DateModel> list=GlobalClass.getList(this);
        if (list!=null){
            for (int i=0;i<list.size();i++) {
                if (isTwoDaysOlder(list.get(i).getTranDate())){
                    GlobalClass.removeItem(this,list.get(i).getSmcode());
                }
            }
        }

        fragContainer = (ViewPager) findViewById(R.id.fragContainer);
        fragContainer.setAdapter(fragmentPagerAdapter);
        fragContainer.setOffscreenPageLimit(1);
        refreshData(null);
    }

    public static boolean isTwoDaysOlder(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            Date parsedDate = sdf.parse(dateString);
            // Get the current date
            Calendar currentDate = Calendar.getInstance();
            // Calculate the date two days ago
            Calendar twoDaysAgo = Calendar.getInstance();
            twoDaysAgo.add(Calendar.DAY_OF_MONTH, -2);
            // Compare dates
            return parsedDate.before(twoDaysAgo.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            // Handle parsing exception
            return false;
        }
    }
    public static void refreshData(final AbsCollectionFragment fragmentCollection) {
        String gdate = DateUtils.getFormatedDate(new Date(), "yyyy-MM-dd");
        String cityCode = areaCode;

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<CustomerListModel> call = apiInterface.dueInstallments(GlobalClass.Token,GlobalClass.Imei,GlobalClass.dbname,GlobalClass.Id,gdate, cityCode);
        //Call<CustomerListModel> call = apiInterface.dueInstallments(GlobalClass.Token,"353587955003255",GlobalClass.dbname,"GRST002632","10-06-2024", "0166");
        Log.d("TAG", "refreshData: " + GlobalClass.Token + GlobalClass.Imei + GlobalClass.dbname + GlobalClass.Id + "11-07-2024" + cityCode);

        call.enqueue(new Callback<CustomerListModel>() {
            @Override
            public void onResponse(Call<CustomerListModel> call, Response<CustomerListModel> response) {
                Log.d("TAG", "refreshData1: " + "run");
                if (response.isSuccessful()) {
                    Log.d("TAG", "refreshData2: " + "success");
                    CustomerListModel dueData = response.body();
                    if (dueData != null) {
                        Log.d("TAG", "refreshData3: " + "success1");

                        List<CustomerListDataModel> dueDataList1 = dueData.getData();
                        Log.d("refreshData4", dueDataList1.size() + "");
                        Log.d("TAG","refreshData6" + dueDataList1.toString() );
                        dueDataList.addAll(getDueDataByDbName(dueDataList1, foCode, creator));



                        populateFragments();
                        refreshSettlement();
                        if (fragmentCollection != null)
                            fragmentCollection.refreshData();
                    }
                } else {
                    Log.d("TAG", "refreshData: " + "else");
                    Log.d("DueData Error", "Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<CustomerListModel> call, Throwable t) {
                Log.d("TAG", "failure: " + "run");
                Log.d("DueData Error", t.getMessage());
            }
        });
    }

    public static void refreshSettlement() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<PosInstRcv>> call = apiService.getFMSettlementData(GlobalClass.Token,GlobalClass.Imei,GlobalClass.dbname, foCode,creator);

        call.enqueue(new Callback<List<PosInstRcv>>() {
            @Override
            public void onResponse(Call<List<PosInstRcv>> call, Response<List<PosInstRcv>> response) {
                if (response.isSuccessful()) {
                    List<PosInstRcv> dueDataList = response.body();
                    if (dueDataList != null) {
                        Type listType = new TypeToken<List<PosInstRcv>>() {}.getType();
                        settlementDataList.clear();
                        String jsonString = dueDataList.toString();
                        List<PosInstRcv> dueData = GlobalClass.convertToObjectArray(jsonString, listType);
                        settlementDataList.addAll(getSettlementDataByDbName(dueData, foCode, creator));

                        if (settlementDataList.size() > 0) {
                            if (fragSettlement == null) {
                                Log.d("checking", "yha tak 2");
                                fragSettlement = FragmentCollectionSettlement.newInstance("POSDB", "Settlement");
                                absFragments.add(fragSettlement);
                                fragmentPagerAdapter.addFragment(fragSettlement, fragmentPagerAdapter.getCount());
                                Log.d("checking", fragmentPagerAdapter.getCount() + "");
                                fragmentPagerAdapter.notifyDataSetChanged();
                            }
                            fragSettlement.refreshData();
                        } else {
                            Log.d("checking", "yha tak 3");
                            if (absFragments != null) {
                                Log.d("checking", "yha tak 4");
                                absFragments.remove(absFragments.size() - 1);
                                fragmentPagerAdapter.removeFragment(fragmentPagerAdapter.getCount() - 1);
                                Log.d("checking", (fragmentPagerAdapter.getCount() - 1) + "");
                                fragmentPagerAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                } else {
                    Log.d("Settlement Error", "Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<PosInstRcv>> call, Throwable t) {
                Log.e("SettlementErrorLatest", t.getMessage());
            }
        });
    }

    public static ArrayList<PosInstRcv> getSettlementDataByDbName(List<PosInstRcv> instRcvs, String foCode, String creator) {
        ArrayList<PosInstRcv> instRcvArrayList = new ArrayList<>();
        for (PosInstRcv dueData : instRcvs) {
            if (dueData.getFoCode().equals(foCode) && dueData.getCreator().equals(creator))
                instRcvArrayList.add(dueData);
        }
        Collections.sort(instRcvArrayList, PosInstRcv.InstRcvName);
        return instRcvArrayList;
    }

    private static void populateFragments() {
        absFragments = getFragments(getDatabases());
        refreshFragments();
    }

    private static void refreshFragments() {
        fragmentPagerAdapter.clearFragments();
        fragmentPagerAdapter.setFragments(absFragments);
        fragmentPagerAdapter.notifyDataSetChanged();

    }

    private AbsCollectionFragment getFragmentByDbName(String dbName) {
        AbsCollectionFragment fragmentRet = null;
        for (AbsCollectionFragment fragment : absFragments) {
            if (fragment.getName().equals(dbName)) {
                fragmentRet = fragment;
                break;
            }
        }
        return fragmentRet;
    }

    private static ArrayList<AbsCollectionFragment> getFragments(Map<String, String> databases) {
        ArrayList<AbsCollectionFragment> fragmentArrayList = new ArrayList<>();
        for (Map.Entry<String, String> dbEntry : databases.entrySet()) {
            fragmentArrayList.add(FragmentCollection.newInstance(dbEntry.getKey(), dbEntry.getValue()));
        }
        return fragmentArrayList;
    }

    private static Map<String, String> getDatabases() {
        Map<String, String> dbs = new HashMap<>();
        for (CustomerListDataModel dueData : dueDataList) {
            dbs.put(dueData.getDb(), dueData.getDbName());
        }
        return dbs;
    }

    public List<CustomerListDataModel> getDueDataByDbName(String dbName) {
        List<CustomerListDataModel> dueDataSubList = new ArrayList<>();
        for (CustomerListDataModel dueData : dueDataList) {
            if (dueData.getDb().equals(dbName))
                dueDataSubList.add(dueData);
        }
        return dueDataSubList;
    }

    public List<PosInstRcv> getRecSettlementData() {
        return new ArrayList<>(settlementDataList);
    }

    public static ArrayList<CustomerListDataModel> getDueDataByDbName(List<CustomerListDataModel> dueDataList, String foCode, String creator) {
        ArrayList<CustomerListDataModel> dueDataSubList = new ArrayList<>();
        for (CustomerListDataModel dueData : dueDataList) {
            if (dueData.getFoCode().equals(foCode) && dueData.getCreator().equals(creator))
                dueDataSubList.add(dueData);
        }
        Collections.sort(dueDataSubList, CustomerListDataModel.DueDataNachName);
        return dueDataSubList;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GlobalClass.EKYC_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                processKycresponse(data.getStringExtra("EKYCDATA"));
                //Toast.makeText(getBaseContext(), "eKYC successful", Toast.LENGTH_SHORT).show();
            } else {
                Utils.alert(this, "There was an error performing eKyc");
            }
        }
    }

    private void processKycresponse(String kycStatus) {
        JSONObject jsonObj = null;
        Log.d("kyc", kycStatus);
        try {
            jsonObj = new JSONObject(kycStatus);
            // Getting JSON Array node
            JSONArray Authentication = jsonObj.getJSONArray("KYCRES");

            // looping through All Contacts
            for (int i = 0; i < Authentication.length(); i++) {
                JSONObject c = Authentication.getJSONObject(i);

                String status = c.getString("status");
                if (status.equals("1")) {
                    String uuid = c.getString("uuid");
                    updateUUID(uuid);

                } else {
                    Utils.alert(this, c.getString("errcode") + "\n" + c.getString("errmsg"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateUUID(String uuid) {
        String msg = "Updating UUID";
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

        JsonObject params = new JsonObject();
        params.addProperty("SmCode", String.valueOf(dueData.getCaseCode()));
        params.addProperty("Creator", dueData.getCreator());
        params.addProperty("KYCUUID", uuid);

        Utils.alert(this, params.toString());

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> call = apiService.updateUUID(GlobalClass.Token,GlobalClass.dbname,params);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Utils.alert(ActivityCollection.this, "UUID updated Successfully");
                } else {
                    Utils.alert(ActivityCollection.this, "UUID cannot be updated");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Utils.alert(ActivityCollection.this, "UUID cannot be updated");
            }
        });
    }
}
