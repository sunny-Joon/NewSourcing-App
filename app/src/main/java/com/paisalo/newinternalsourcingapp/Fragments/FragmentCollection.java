package com.paisalo.newinternalsourcingapp.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.paisalo.newinternalsourcingapp.Activities.ActivityCollection;
import com.paisalo.newinternalsourcingapp.Activities.OnlinePaymentActivity;
import com.paisalo.newinternalsourcingapp.Activities.Upload_Payslip_page;
import com.paisalo.newinternalsourcingapp.Adapters.AdapterDueData;
import com.paisalo.newinternalsourcingapp.Adapters.AdapterInstallment;
import com.paisalo.newinternalsourcingapp.Adapters.AdapterInstallmentTemp;
import com.paisalo.newinternalsourcingapp.BuildConfig;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.Modelclasses.DueData;
import com.paisalo.newinternalsourcingapp.Modelclasses.Installment;
import com.paisalo.newinternalsourcingapp.Modelclasses.InstallmentTemp;
import com.paisalo.newinternalsourcingapp.Modelclasses.PosInstRcv;
import com.paisalo.newinternalsourcingapp.Modelclasses.PosInstRcvNew;
import com.paisalo.newinternalsourcingapp.Modelclasses.QRCollStatus;
import com.paisalo.newinternalsourcingapp.Modelclasses.SmCode_DateModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.QrUrlData;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;
import com.paisalo.newinternalsourcingapp.Utils.MyTextWatcher;
import com.paisalo.newinternalsourcingapp.Utils.Utils;
import com.paisalo.newinternalsourcingapp.location.GpsTracker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import a.a.d.G;
import cz.msebera.android.httpclient.Header;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentCollection extends AbsCollectionFragment {
    private static final String ARG_DB_NAME = "param1";
    private static final String ARG_DB_DESC = "param2";
    private String mDbName;
    private String mDbDesc;
    private ListView lv;
    private boolean isDialogActive;
    private int collectionAmount;
    private int latePmtIntAmt;
    private boolean isProcessingEMI=false;
    String userid;
    private String SchmCode;
    private String SMCode;
    GpsTracker gpsTracker;
    Dialog dialogConfirm;
    Dialog dialogQrcode;
    Dialog dialogQrcodePayment;
    AlertDialog dialog;
    ImageView  righticon;
    private ProgressDialog progressDialog;
    ArrayList<InstallmentTemp> insttemplist;
    public FragmentCollection() {
    }

    public static FragmentCollection newInstance(String dbName, String dbDesc) {
        FragmentCollection fragment = new FragmentCollection();

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        userid= GlobalClass.Id;
        lv = (ListView) view.findViewById(R.id.lvDueData);
        gpsTracker=new GpsTracker(getContext());
        dialogConfirm = new Dialog(getContext());
        dialogQrcode = new Dialog(getContext());
        dialogQrcodePayment = new Dialog(getContext());

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int checked=0;
                AdapterDueData adapterDueData = (AdapterDueData) parent.getAdapter();
                final DueData dueData = (DueData) adapterDueData.getItem(position);
                List<SmCode_DateModel> list=GlobalClass.getList(getActivity());

                if (list!=null){
                    for (int i=0;i<list.size();i++) {
                        if (list.get(i).getSmcode().trim().equals(dueData.getCaseCode())){
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                            if (list.get(i).getTranDate().equals(sdf.format(new Date()))){
                                Utils.alert(getActivity(),"You already checked payment status please wait at least 24 hours");
                                checked=1;
                                break;
                            }else{
                                checked=0;
                            }
                        }
                    }
                }

                if (checked==0){
                    ArrayList<String> menuOptions = new ArrayList<>();
                    menuOptions.add("EMI Paying");
                    menuOptions.add("EMI Not Paying");

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    String[] mOptions = new String[menuOptions.size()];
                    mOptions = menuOptions.toArray(mOptions);
                    builder.setItems(mOptions, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which==0){
                                showPaymentDialog(parent,position);
                            }else{
                                DialogForEMINotPaying(getContext(),parent,position);
                            }
                        }
                    });
                    builder.create().show();
                }
            }
        });

        Log.e("MDatabaseName",mDbName+"");
        Log.e("MDB_DESC",mDbDesc+"");
        lv.setAdapter(new AdapterDueData(getContext(), R.layout.layout_item_collection, ((ActivityCollection) getActivity()).getDueDataByDbName(mDbName)));
        SearchView searchView = (SearchView) view.findViewById(R.id.searchViewDueData);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ((AdapterDueData) lv.getAdapter()).getFilter().filter(newText);
                return false;
            }
        });
        return view;
    }
    public  void DialogForEMINotPaying(Context context,AdapterView<?> parent,int position) {
        AdapterDueData adapterDueData = (AdapterDueData) parent.getAdapter();
        final DueData dueData = (DueData) adapterDueData.getItem(position);
        // Create a dialog view by inflating the layout XML
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_layout_notpay_emi, null);

        // Initialize views from the layout
        Spinner spinEMIReasons=dialogView.findViewById(R.id.spinEMIReasons);
        ImageView imgViewCal = dialogView.findViewById(R.id.imgViewCal);
        TextInputEditText tietPromisingDate = dialogView.findViewById(R.id.tietPromisingDate);
        Button saveDataForEMINOTPAY=dialogView.findViewById(R.id.saveDataForEMINOTPAY);

        final DatePicker datePicker = dialogView.findViewById(R.id.datePicker);
        datePicker.setMinDate(System.currentTimeMillis());
        // Create a DatePickerDialog and set its date set listener
        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String selectedDate =( dayOfMonth<=9?"0"+dayOfMonth:dayOfMonth) + "-" + (monthOfYear<9?"0"+(monthOfYear + 1):(monthOfYear + 1) )+ "-" + year;
                tietPromisingDate.setText(selectedDate);
            }
        };

        imgViewCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, dateSetListener, 2023, 0, 1);
                datePickerDialog.show();
            }
        });

        // Create and configure the custom dialog
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder.setView(dialogView);
        builder.setCancelable(true);

        final androidx.appcompat.app.AlertDialog dialog = builder.create();
        saveDataForEMINOTPAY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tietPromisingDate.getText().length()<1){
                    Toast.makeText(context, "Please Choose date on Calender Icon Click", Toast.LENGTH_SHORT).show();
                    tietPromisingDate.setError("Please Choose date on Calender Icon Click");
                }else{

                    ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
                    Log.d("TAG", "onClick: "+getRCPromisToPayJson(Utils.getNotNullText(tietPromisingDate),spinEMIReasons.getSelectedItem().toString(),dueData.getCreator(),dueData.getCustName(),dueData.getCaseCode(),dueData.getMobile(),dueData.getAadhar()));
                    Call<JsonObject> call=apiInterface.insertRcPromiseToPay(GlobalClass.Token,GlobalClass.dbname,getRCPromisToPayJson(Utils.getNotNullText(tietPromisingDate),spinEMIReasons.getSelectedItem().toString(),dueData.getCreator(),dueData.getCustName(),dueData.getCaseCode(),dueData.getMobile(),dueData.getAadhar()));
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            Log.d("TAG", "onResponse: "+response.body());
                            Toast.makeText(context, "Data is valid. Saving...", Toast.LENGTH_SHORT).show();

                            if (response.body()!=null){
                                if (response.body().get("statusCode").getAsInt()==200){
                                    Utils.alert(context,response.body().get("message").getAsString());
                                    dialog.dismiss();
                                }
                            }else{
                                Utils.alert(context,response.body().get("message").getAsString());
                                dialog.dismiss();
                            }

                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Utils.alert(context,"Something went wrong!!");
                            dialog.dismiss();
                        }
                    });

                }
            }
        });
        dialog.show();
    }

    private JsonObject getdataQr(String smcode) {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("mid", "randomId");
        jsonObject.addProperty("terminalId", smcode);
        jsonObject.addProperty("req", "abc");
        return jsonObject;
    }

    public static Retrofit getClientNew(String BASE_URL) {
        Retrofit retrofit = null;
        if (retrofit==null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder(
            );
            httpClient.connectTimeout(1, TimeUnit.MINUTES);
            httpClient.readTimeout(1,TimeUnit.MINUTES);
            httpClient.addInterceptor(logging);
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }



    private void showPaymentDialog(AdapterView<?> parent,int position) {

        AdapterDueData adapterDueData = (AdapterDueData) parent.getAdapter();
        final DueData dueData = (DueData) adapterDueData.getItem(position);
        Log.d("DueData",dueData.toString());
        Log.d("DueDataSchmCode",dueData.getSchmCode());
        insttemplist=new ArrayList<>();
        SchmCode=dueData.getSchmCode();
        SMCode=dueData.getCaseCode();
        latePmtIntAmt=0;
        adapterDueData.notifyDataSetChanged();
        final int maxDue = dueData.getMaxDueAmount();
        final int latePaymentInterest = dueData.getInterestAmt();
        final List<Installment> installments = dueData.getInstData();

        final View dialogView = getActivity().getLayoutInflater().inflate(R.layout.layout_dialog_collect, null);
        final RadioGroup radioGroup = (RadioGroup) dialogView.findViewById(R.id.rgCollectionType);
        //  dialogView.setBackgroundResource(R.color.colorLightGreen);
        AppCompatButton cancel = (AppCompatButton) dialogView.findViewById(R.id.btnCollectRight);
        cancel.setText("Cancel");

        final AppCompatButton collect = (AppCompatButton) dialogView.findViewById(R.id.btnCollectLeft);
        collect.setText("Collect");
        collect.setEnabled(false);

        final LinearLayout llLatePayment = dialogView.findViewById(R.id.llLatePmtInterest);
        final Button onlinepayment = dialogView.findViewById(R.id.onlinepayment);
        final ImageButton prossingFees = dialogView.findViewById(R.id.prossingFees);
        final CheckBox chkLatePayment = dialogView.findViewById(R.id.chkLatePmtInterest);
        final TextView tvLatePayAmount = dialogView.findViewById(R.id.tvLatePmtInterestAmt);

        if (latePaymentInterest > 0) {
            llLatePayment.setVisibility(View.VISIBLE);
            tvLatePayAmount.setText(String.valueOf(latePaymentInterest));
        }
        onlinepayment.setEnabled(false);
        onlinepayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int totCollectAmt;
                if(radioGroup.getCheckedRadioButtonId()==R.id.rbLumpSumAmount){
                    totCollectAmt=collectionAmount;
                }else{
                    totCollectAmt=collectionAmount+latePmtIntAmt;
                }

                Intent intent=new Intent(getContext(), OnlinePaymentActivity.class);
                intent.putExtra("Price",totCollectAmt);
                getActivity().startActivity(intent);
            }
        });

        prossingFees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getQrCode(SMCode);
                dialog.dismiss();
                dialogQrcode.setContentView(R.layout.dialogqr_layout);
                dialogQrcode.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialogQrcode.setCancelable(false);
                // dialogConfirm.getWindow().getAttributes().windowAnimations = R.style.animation;
                ImageView  imageQrCode=dialogQrcode.findViewById(R.id.qrcode);
                TextView  tv_heading=dialogQrcode.findViewById(R.id.text_smcode);
                TextView  text_uploadimage=dialogQrcode.findViewById(R.id.text_uploadimage);
                text_uploadimage.setVisibility(View.GONE);
                tv_heading.setText("Loan A/C- "+SMCode);
                righticon=dialogQrcode.findViewById(R.id.righticon);
                righticon.setVisibility(View.GONE);
                Button btnSave = dialogQrcode.findViewById(R.id.button_saveEmi);
                Button btncancel = dialogQrcode.findViewById(R.id.button_Closedialog);
                btncancel.setVisibility(View.GONE);
                ImageView img_close = dialogQrcode.findViewById(R.id.img_close);

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        text_uploadimage.setVisibility(View.VISIBLE);
                        btncancel.setVisibility(View.VISIBLE);
                        qrCodePaymentConfirmAPI(dueData);
                    /*  dialogConfirm.dismiss();
                        saveDeposit(SchmCode,dueData, totCollectAmt,latePmtIntAmt,tglBtnPaidBy.isChecked() ? "F" : "B");
                        //Toast.makeText(MainActivity.this, "okay clicked", Toast.LENGTH_SHORT).show();*/
                    }
                });

                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getContext(), Upload_Payslip_page.class);
                        intent.putExtra("SMCODE",dueData.getCaseCode());
                        startActivity(intent);
                        dialogQrcode.dismiss();
                        // Toast.makeText(MainActivity.this, "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                img_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogQrcode.dismiss();
                        // Toast.makeText(MainActivity.this, "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                dialogQrcode.show();
                progressDialog = ProgressDialog.show(getContext(), "", "Loading...", true, false);
                ApiInterface apiInterface= getClientNew("https://erpservice.paisalo.in:980/PDL.USERSERvice.API/api/").create(ApiInterface.class);
                // Log.d("TAG", "checkCrifScore: "+getdatalocation(login, login));
                Call<QrUrlData> call=apiInterface.getCheckQrCode(SMCode);
                call.enqueue(new Callback<QrUrlData>() {
                    @Override
                    public void onResponse(Call<QrUrlData> call, Response<QrUrlData> response) {
                        Log.d("TAG", "onResponse: "+response.body());
                        progressDialog.dismiss();
                        if(response.body().getStatusCode().equals("200")){
                            Glide.with(getContext())
                                    .load(response.body().getData())
                                    // .asGif()
                                    //  .placeholder(R.drawable.ic_qr_code)
                                    .into(imageQrCode);
                        }else{
                            dialogQrcode.dismiss();
                            Toast.makeText(getContext(), "QR code not found", Toast.LENGTH_SHORT).show();
                        }

                      /*  if(response.body().getStatusCode().equals("200")){
                            try {
                                JSONObject Qrjo=new JSONObject(response.body().getData());
                                String qr=Qrjo.getString("QrCodeUrl");
                                Glide.with(getContext())
                                        .load(qr)
                                        // .asGif()
                                        .placeholder(R.drawable.ic_qr_code)
                                        .into(imageQrCode);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }*/
                    }

                    @Override
                    public void onFailure(Call<QrUrlData> call, Throwable t) {
                        Log.d("TAG", "onFailure: "+t.getMessage());
                        Toast.makeText(getContext(), "QR code not generated", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        dialogQrcode.dismiss();
                    }
                });

            }
        });

        final ToggleButton tglBtnPaidBy = (ToggleButton) dialogView.findViewById(R.id.tglPaidBy);
        tglBtnPaidBy.setVisibility(BuildConfig.APPLICATION_ID.equals("com.softeksol.paisalo.jlgsourcing") ? View.VISIBLE : View.GONE);
        tglBtnPaidBy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dialogView.setBackgroundResource(isChecked ? R.color.red : R.color.green);
            }
        });

        final TextView tvSelectedCount = (TextView) dialogView.findViewById(R.id.tvCollectSelected);
        final TextView tvTotDue = (TextView) dialogView.findViewById(R.id.tvCollectTotalAmt);
        TextView tvSundary = (TextView) dialogView.findViewById(R.id.tvCollectSundry);
        //int total=dueData.getInstallmentSum(false);
        //final int dif =total-  dueData.getInstsAmtDue();
        tvSundary.setText(String.valueOf(dueData.getSundryBalance()));

        final TextInputEditText teitLumpSumAccount = (TextInputEditText) dialogView.findViewById(R.id.tietLumSumAmount);
        final TextInputEditText tietOtherEMIAmount = (TextInputEditText) dialogView.findViewById(R.id.tietOtherEMIAmount);
        final TextInputEditText tietPFAmount = (TextInputEditText) dialogView.findViewById(R.id.tietPFAmount);
        final TextInputEditText tietEMIAmount = (TextInputEditText) dialogView.findViewById(R.id.tietEMIAmount);
        final TextInputLayout tilLumpsumAccount = (TextInputLayout) dialogView.findViewById(R.id.tilLumSumAmount);
        final LinearLayout linearLayout16 = (LinearLayout) dialogView.findViewById(R.id.linearLayout16);

        tietEMIAmount.addTextChangedListener(new MyTextWatcher(tietEMIAmount) {
            @Override
            public void validate(EditText editText, String text) {

                teitLumpSumAccount.setText(String.valueOf(Utils.getNotNullInt(tietOtherEMIAmount)+Utils.getNotNullInt(tietPFAmount)+Utils.getNotNullInt(tietEMIAmount)));

            }
        });
        tietPFAmount.addTextChangedListener(new MyTextWatcher(tietEMIAmount) {
            @Override
            public void validate(EditText editText, String text) {

                teitLumpSumAccount.setText(String.valueOf(Utils.getNotNullInt(tietOtherEMIAmount)+Utils.getNotNullInt(tietPFAmount)+Utils.getNotNullInt(tietEMIAmount)));

            }
        });
        tietOtherEMIAmount.addTextChangedListener(new MyTextWatcher(tietEMIAmount) {
            @Override
            public void validate(EditText editText, String text) {

                teitLumpSumAccount.setText(String.valueOf(Utils.getNotNullInt(tietOtherEMIAmount)+Utils.getNotNullInt(tietPFAmount)+Utils.getNotNullInt(tietEMIAmount)));

            }
        });



        teitLumpSumAccount.addTextChangedListener(new MyTextWatcher(teitLumpSumAccount) {
            @Override
            public void validate(EditText editText, String text) {
                int amt = Utils.getNotNullInt(editText);
                collectionAmount = amt;
                tvTotDue.setText(text);
                collect.setEnabled(false);
                onlinepayment.setEnabled(false);

                if (amt < 1) {
                    editText.setError("Amount should be greater than or equals to 1");
                    return;
                }
                if (amt > maxDue) {
                    editText.setError("Amount should be less than or equals to " + maxDue);
                    return;
                }
                //collectEnableDisable(latePaymentInterest, collect, tvTotDue);
                collect.setEnabled(true);
                onlinepayment.setEnabled(true);

                editText.setError(null);
            }
        });

        tilLumpsumAccount.setVisibility(View.INVISIBLE);
        linearLayout16.setVisibility(View.GONE);
        final ListView lvc = (ListView) dialogView.findViewById(R.id.lvcCollectInstallments);
        lvc.setItemsCanFocus(false);
        lvc.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lvc.setAdapter(new AdapterInstallment(getContext(), R.layout.layout_item_installment, installments));
        lvc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //System.out.println("clicked" + position);
                Installment installment = (Installment) parent.getItemAtPosition(position);
                installment.setSelected(!installment.isSelected());
                CheckBox chb = (CheckBox) view.findViewById(R.id.cbItemInstallmentSelected);
                chb.setChecked(installment.isSelected());
                int selectedCount = DueData.getSelectedCount(installments);
                int selectedAmount = DueData.getInstallmentSum(installments, true);
                collectionAmount = selectedAmount;
                tvSelectedCount.setText(String.valueOf(selectedCount));

                collect.setEnabled(collectionAmount + latePmtIntAmt> (latePmtIntAmt>0?latePmtIntAmt-1:0));
                onlinepayment.setEnabled(collectionAmount + latePmtIntAmt> (latePmtIntAmt>0?latePmtIntAmt-1:0));

                tvTotDue.setText(String.valueOf(collectionAmount+ latePmtIntAmt));
                if(installment.isSelected()==true){
                    InstallmentTemp intemp=new InstallmentTemp();
                    intemp.setAmount(installment.getAmount());
                    intemp.setDueDate(installment.getDueDate());
                    insttemplist.add(intemp);
                }else{
                    insttemplist.remove(position);
                }

            }
        });

        chkLatePayment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    latePmtIntAmt =latePaymentInterest;
                }else{
                    latePmtIntAmt=0;
                }
                if(radioGroup.getCheckedRadioButtonId()==R.id.rbLumpSumAmount) {
                    tvTotDue.setText(String.valueOf(collectionAmount));
                }else {
                    tvTotDue.setText(String.valueOf(collectionAmount+latePmtIntAmt));
                }
                collect.setEnabled(collectionAmount> (latePmtIntAmt>0?latePmtIntAmt-1:0));
                onlinepayment.setEnabled(collectionAmount> (latePmtIntAmt>0?latePmtIntAmt-1:0));

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select one or more Installments");
        builder.setView(dialogView);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                chkLatePayment.setEnabled(true);
                chkLatePayment.setChecked(false);
                switch (checkedId) {
                    case R.id.rbFixedAmount:
                        lvc.setEnabled(true);
                        tilLumpsumAccount.setVisibility(View.INVISIBLE);
                        linearLayout16.setVisibility(View.GONE);
                        teitLumpSumAccount.setText("0");
                        break;
                    case R.id.rbLumpSumAmount:
                        dueData.clearSection(installments);
                        tvSelectedCount.setText("0");
                        lvc.setEnabled(false);
                        ((AdapterInstallment) lvc.getAdapter()).notifyDataSetChanged();
                        tilLumpsumAccount.setVisibility(View.VISIBLE);
                        linearLayout16.setVisibility(View.VISIBLE);
                        tilLumpsumAccount.setHint(getResources().getString(R.string.lump_sum_amount) + " (Max " + maxDue + ")");
                        collect.setEnabled(false);
                        onlinepayment.setEnabled(false);
                        teitLumpSumAccount.setText("0");
                        break;
                }
            }
        });

        dialog = builder.create();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        collect.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dueData.setEnabled(false);
                collect.setEnabled(false);
                onlinepayment.setEnabled(false);
                int totCollectAmt;
                if(radioGroup.getCheckedRadioButtonId()==R.id.rbLumpSumAmount){
                    totCollectAmt=collectionAmount;
                }else{
                    totCollectAmt=collectionAmount+latePmtIntAmt;
                }
                if(radioGroup.getCheckedRadioButtonId()==R.id.rbLumpSumAmount) {
                    saveDepositeWithPFAndEmiAmount(dueData.getCreator(), dueData.getCustName(), dueData.getCaseCode(), dueData.getMobile(), totCollectAmt, Utils.getNotNullInt(tietEMIAmount), Utils.getNotNullInt(tietPFAmount), Utils.getNotNullInt(tietOtherEMIAmount));
                }

                dialogConfirm.setContentView(R.layout.dialogconfirmation_layout);
                dialogConfirm.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialogConfirm.setCancelable(false);
                // dialogConfirm.getWindow().getAttributes().windowAnimations = R.style.animation;

                ListView  list_dialog=dialogConfirm.findViewById(R.id.list_dialog);
                TextView text_instamt = dialogConfirm.findViewById(R.id.text_instamt);
                TextView text_totalamt = dialogConfirm.findViewById(R.id.text_totalamt);
                list_dialog.setAdapter(new AdapterInstallmentTemp(getContext(), R.layout.layout_item_installmenttemp, insttemplist));
                text_totalamt.setText(totCollectAmt+"");
                text_instamt.setText(latePmtIntAmt+"");

                Button btnSave = dialogConfirm.findViewById(R.id.button_saveEmi);
                Button btncancel = dialogConfirm.findViewById(R.id.button_Closedialog);

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        dialogConfirm.dismiss();
                        //String st=  String.valueOf((totCollectAmt+latePmtIntAmt)+" money received");
                        //  textToSpeech.speak(st,TextToSpeech.QUEUE_FLUSH,null);
                        // saveDeposit(SchmCode,dueData, totCollectAmt,latePmtIntAmt,tglBtnPaidBy.isChecked() ? "F" : "B");
                        if(GlobalClass.DATABASE_NAME.equalsIgnoreCase("SBIPDLCOL")){
                            saveRecipetNewAmount(SchmCode,dueData, totCollectAmt,latePmtIntAmt,tglBtnPaidBy.isChecked() ? "F" : "B");
                        }else{
                            saveDeposit(SchmCode,dueData, totCollectAmt,latePmtIntAmt,tglBtnPaidBy.isChecked() ? "F" : "B");
                        }

                        //Toast.makeText(MainActivity.this, "okay clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogConfirm.dismiss();
                        // Toast.makeText(MainActivity.this, "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                });
                dialogConfirm.show();
            }
        });
        dialog.show();
    }


    private  void qrCodePaymentConfirmAPI(DueData dueData){
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateString = dateFormat.format(currentDate);
        QRCollStatus instRcv = new QRCollStatus();
        instRcv.setCaseCode(dueData.getCaseCode());
        instRcv.setCreator(dueData.getCreator());
        instRcv.setDate(dateString);
        instRcv.setFoCode(dueData.getFoCode());
        instRcv.setBorrowerName(dueData.getCustName());
        instRcv.setPartyCd(dueData.getPartyCd());
        instRcv.setPayFlag("E");
        instRcv.setCollPoint("FIELD");
        instRcv.setPaymentMode("QR");
        dialogQrcodePayment.setContentView(R.layout.dialogqrpayment_layout);
        dialogQrcodePayment.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogQrcodePayment.setCancelable(false);
        // dialogConfirm.getWindow().getAttributes().windowAnimations = R.style.animation;
        ImageView  imagesuccess=dialogQrcodePayment.findViewById(R.id.success_sign);
        Button btnSave = dialogQrcodePayment.findViewById(R.id.button_qrClosedialog);
        // Button btncancel = dialogQrcode.findViewById(R.id.button_Closedialog);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogQrcodePayment.dismiss();
                dialogConfirm.dismiss();
                ((ActivityCollection) getActivity()).refreshData(FragmentCollection.this);
                getLoginLocation("Collection","");
            }
        });


        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<JsonObject> call=apiInterface.insertQRPayment(instRcv,GlobalClass.Token,GlobalClass.dbname,GlobalClass.Id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("TAG", "onResponse UpdateQrRcCollection: "+response.body());
                // progressDialog.dismiss();
                if (response.body()!=null){
                    if (response.body().get("statusCode").getAsInt()==200){

                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        List<SmCode_DateModel> list=GlobalClass.getList(getActivity());
                        if (list==null){
                            list=new ArrayList<>();
                        }
                        SmCode_DateModel smCodeDateModel=new SmCode_DateModel(dueData.getCaseCode(),sdf.format(new Date()));
                        list.add(smCodeDateModel);
                        GlobalClass.saveList(getActivity(),list);
                        dialogQrcodePayment.show();
                        dialogQrcode.dismiss();
                        Glide.with(getContext())
                                .asGif()
                                .load(R.drawable.righticon)
                                .into(imagesuccess);

                    }else{
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                qrCodePaymentConfirmAPI(dueData);
                            }
                        }, 9000);
                        // failAlert(dueData);
                    }
                } else{
                    // Toast.makeText(getContext(), "Payment not found try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // Log.d("TAG", "onFailure: "+t.getMessage());
                // Toast.makeText(getContext(), "Try to get Status again", Toast.LENGTH_SHORT).show();
                // progressDialog.dismiss();
                // failAlert(dueData);
            }
        });

    }

    private void failAlert(DueData dueData){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // Set the dialog title and message
        builder.setTitle("Payment not received")
                .setMessage("Try again to get status or Upload payment receipt.");
        // Add OK button
        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Perform any action here when OK is clicked
                qrCodePaymentConfirmAPI(dueData);
                dialog.dismiss(); // Dismiss the dialog
            }
        });
        builder.setNegativeButton("Upload Image", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(getContext(), Upload_Payslip_page.class);
                intent.putExtra("SMCODE",dueData.getCaseCode());
                startActivity(intent);
                dialog.dismiss();

                // Dismiss the dialog
            }
        });
        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void saveRecipetNewAmount(String schmCode, DueData dueData, int totCollectAmt, int latePmtIntAmt, String s) {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false); // This will prevent users from cancelling the dialog by tapping outside
        progressDialog.show();
        PosInstRcvNew instRcv = new PosInstRcvNew();
        instRcv.setCaseCode(dueData.getCaseCode());
        instRcv.setCreator(dueData.getCreator());
        instRcv.setDataBaseName(dueData.getDb());
        instRcv.setIMEI(GlobalClass.Imei);
        instRcv.setInstRcvAmt(totCollectAmt - latePmtIntAmt);
        instRcv.setInstRcvDateTimeUTC(new Date());
        instRcv.setFoCode(dueData.getFoCode());
        instRcv.setCustName(dueData.getCustName());
        instRcv.setPartyCd(dueData.getPartyCd());
        instRcv.setInterestAmt(latePmtIntAmt);
        instRcv.setPayFlag("E");
        instRcv.setCollPoint("FIELD");
        instRcv.setPaymentMode("CASH");
        instRcv.setCollBranchCode("");

        ApiInterface apiInterface=ApiClient.getClient().create(ApiInterface.class);
        Call<JsonObject> call=apiInterface.insertRcDistributionNew(instRcv,GlobalClass.Token,GlobalClass.dbname,GlobalClass.Id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("TAG", "insertRcDistributionNew: "+response.body());
                //  Toast.makeText(getContext(), "Data is valid. Saving...", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                if (response.body()!=null){
                    if (response.body().get("statusCode").getAsInt()==200){
                        Utils.alert(getContext(),response.body().get("message").getAsString());
                        ((ActivityCollection) getActivity()).refreshData(FragmentCollection.this);
                        getLoginLocation("Collection","");
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("TAG", "onFailure: "+t.getMessage());
                progressDialog.dismiss();
                Utils.alert(getContext(),t.getMessage());


            }
        });
    }


    private void saveDepositeWithPFAndEmiAmount(String creator, String custName, String caseCode, String mobile, int totCollectAmt, int EmiAmt, int pfAmt, int otherAmt) {

        ApiInterface apiInterface=ApiClient.getClient().create(ApiInterface.class);
        Call<JsonObject> call=apiInterface.insertRcDistribution(GlobalClass.Token,GlobalClass.dbname,getJsonOfRcDist(creator,custName,caseCode,mobile,totCollectAmt,EmiAmt,pfAmt,otherAmt));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("TAG", "onResponse: "+response.body());
                Toast.makeText(getContext(), "Data is valid. Saving...", Toast.LENGTH_SHORT).show();

                if (response.body()!=null){
                    if (response.body().get("statusCode").getAsInt()==200){
                        Utils.alert(getContext(),response.body().get("message").getAsString());
                        ((ActivityCollection) getActivity()).refreshData(FragmentCollection.this);
                        getLoginLocation("Collection","");

                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("TAG", "onFailure: "+t.getMessage());

            }
        });
    }
    public  JsonObject getRCPromisToPayJson(String date, String reason, String creator, String custName, String caseCode, String mobile, String adhar) {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date gotDate = null;
        try {
            gotDate = inputDateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = outputDateFormat.format(gotDate);


        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDateCurrent = df.format(c);
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty( "id", 0);
        jsonObject.addProperty( "reason", reason);
        jsonObject.addProperty( "creator", creator);
        jsonObject.addProperty( "caseCode", caseCode);
        jsonObject.addProperty( "customerName", custName);
        jsonObject.addProperty( "aadhar", adhar);
        jsonObject.addProperty( "dateToPay", formattedDate);
        jsonObject.addProperty( "createdOn",  formattedDateCurrent);
        jsonObject.addProperty( "createdBy", userid);
        jsonObject.addProperty( "modifiedOn",formattedDateCurrent);
        jsonObject.addProperty( "modifiedBy", userid);

        return  jsonObject;
    }



    private JsonObject getJsonOfRcDist(String creator, String custName, String caseCode, String mobile, int totCollectAmt, int emiAmt, int pfAmt, int otherAmt) {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("id", 0);
        jsonObject.addProperty("creator", creator);
        jsonObject.addProperty("foCode", "");
        jsonObject.addProperty("caseCode", caseCode);
        jsonObject.addProperty("customerName", custName);
        jsonObject.addProperty("mobile", mobile);
        jsonObject.addProperty("totalDue",String.valueOf( totCollectAmt));
        jsonObject.addProperty("userID", GlobalClass.Id);
        jsonObject.addProperty("emi", String.valueOf(emiAmt));
        jsonObject.addProperty("pf", String.valueOf(pfAmt));
        jsonObject.addProperty("others", String.valueOf(otherAmt));
        jsonObject.addProperty("createdBy", GlobalClass.Id);
        return  jsonObject;
    }

    public String getName() {
        return getArguments().getString(ARG_DB_DESC);
    }

    private void collectEnableDisable(int latePaymentInterest, AppCompatButton collect, TextView tvTotDue) {
        collect.setEnabled(collectionAmount > (latePaymentInterest > 0 ? latePaymentInterest - 1 : 0));
        //collect.setEnabled(collectionAmount > 0);
        tvTotDue.setText(String.valueOf(collectionAmount));
        //collect.setEnabled(latePaymentInterest > 0);
    }

    private void saveDeposit(String SchmCode, DueData dueData, int collectedAmount, int latePmtAmount, String depBy) {
        PosInstRcv instRcv = new PosInstRcv();
        instRcv.setCaseCode(dueData.getCaseCode());
        instRcv.setCreator(dueData.getCreator());
        instRcv.setDataBaseName(dueData.getDb());
        instRcv.setIMEI(GlobalClass.Imei);
        instRcv.setInstRcvAmt(collectedAmount - latePmtAmount);
        instRcv.setInstRcvDateTimeUTC(new Date());
        instRcv.setFoCode(dueData.getFoCode());
        instRcv.setCustName(dueData.getCustName());
        instRcv.setPartyCd(dueData.getPartyCd());
        instRcv.setInterestAmt(latePmtAmount);
        instRcv.setPayFlag(depBy);

        Gson gson =new Gson();
        JsonObject jsonObject= gson.fromJson(instRcv.toString(), JsonObject.class);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> call = apiService.saveDeposit(GlobalClass.Token,GlobalClass.dbname,jsonObject);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    ((ActivityCollection) getActivity()).refreshData(FragmentCollection.this);
                    getLoginLocation("Collection", "");
                } else {
                    Toast.makeText(getContext(), "Error: " + response.code(), Toast.LENGTH_LONG).show();
                    Log.d("eKYC Response", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("eKYC Response", t.getLocalizedMessage());
            }
        });
    }



    public void refreshData() {
        AdapterDueData adapterDueData = (AdapterDueData) lv.getAdapter();
        adapterDueData.clear();
        adapterDueData.addAll(((ActivityCollection) getActivity()).getDueDataByDbName(mDbName));
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


    private void ConfirmationDialog(int totCollectAmt, int latePmtIntAmt){
        dialogConfirm.setContentView(R.layout.dialogconfirmation_layout);
        dialogConfirm.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogConfirm.setCancelable(false);
        // dialogConfirm.getWindow().getAttributes().windowAnimations = R.style.animation;

        ListView  list_dialog=dialogConfirm.findViewById(R.id.list_dialog);
        TextView text_instamt = dialogConfirm.findViewById(R.id.text_instamt);
        TextView text_totalamt = dialogConfirm.findViewById(R.id.text_totalamt);
        list_dialog.setAdapter(new AdapterInstallmentTemp(getContext(), R.layout.layout_item_installmenttemp, insttemplist));
        text_totalamt.setText(totCollectAmt+"");
        text_instamt.setText(latePmtIntAmt+"");

        Button btnSave = dialogConfirm.findViewById(R.id.button_saveEmi);
        Button btncancel = dialogConfirm.findViewById(R.id.button_Closedialog);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogConfirm.dismiss();
                //Toast.makeText(MainActivity.this, "okay clicked", Toast.LENGTH_SHORT).show();
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogConfirm.dismiss();
                // Toast.makeText(MainActivity.this, "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        dialogConfirm.show();

    }

    private void getLoginLocation(String login,String address){
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<JsonObject> call=apiInterface.livetrack(GlobalClass.Token,GlobalClass.dbname,getdatalocation(login,address));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("TAG", "onResponse: "+response.body());
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("TAG", "onFailure: "+t.getMessage());

            }
        });
    }

    private JsonObject getdatalocation(String login, String address) {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("userId", GlobalClass.Id);
        jsonObject.addProperty("deviceId", GlobalClass.DevId);
        jsonObject.addProperty("Creator", GlobalClass.Creator);
        jsonObject.addProperty("trackAppVersion", BuildConfig.VERSION_NAME);
        jsonObject.addProperty("latitude",gpsTracker.getLatitude()+"");
        jsonObject.addProperty("longitude", gpsTracker.getLongitude()+"");
        jsonObject.addProperty("appInBackground",login);
        jsonObject.addProperty("Activity",SMCode);
        jsonObject.addProperty("Address",Utils.getAddress(gpsTracker.getLatitude(),gpsTracker.getLongitude(),getContext()));
        return jsonObject;
    }

}
