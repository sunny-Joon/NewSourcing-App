package com.paisalo.newinternalsourcingapp.Activities;

import static com.paisalo.newinternalsourcingapp.GlobalClass.SubmitAlert;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.paisalo.newinternalsourcingapp.Adapters.CustomAdapter;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.EsignListModels.Guarantor;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.EsignListModels.PendingESignFI;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;
import com.paisalo.newinternalsourcingapp.Utils.CustomProgressDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SecondEsignActivity extends AppCompatActivity {

    FragmentManager fm;
    FragmentTransaction ft;
    ListView lvLoanDetails;
    Button btnLoanDetailsDownloadDoc;
    private PendingESignFI borrower;
    ArrayList<Guarantor> guarantor;
    TextView tvLoanDetailCaseLocation, tvLoanDetailFmName, tvLoanDetailFiCode, tvLoanDetailAmount, tvLoanDetailPeriod, tvLoanDetailInterestRate;

    CustomProgressDialog customProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_esign);

        customProgressDialog = new CustomProgressDialog(SecondEsignActivity.this);

        btnLoanDetailsDownloadDoc = findViewById(R.id.btnLoanDetailsDownloadDoc);
        lvLoanDetails = findViewById(R.id.lvLoanDetails);

        tvLoanDetailCaseLocation = findViewById(R.id.tvLoanDetailCaseLocation);
        tvLoanDetailFmName = findViewById(R.id.tvLoanDetailFmName);
        tvLoanDetailFiCode = findViewById(R.id.tvLoanDetailFiCode);
        tvLoanDetailAmount = findViewById(R.id.tvLoanDetailAmount);
        tvLoanDetailPeriod = findViewById(R.id.tvLoanDetailPeriod);
        tvLoanDetailInterestRate = findViewById(R.id.tvLoanDetailInterestRate);
        fm = getSupportFragmentManager();
         ft = fm.beginTransaction();
        Intent intent = getIntent();
        if (intent != null) {
            borrower = (PendingESignFI) intent.getSerializableExtra(GlobalClass.ESIGN_BORROWER);
            if (borrower != null) {
                tvLoanDetailCaseLocation.setText(String.valueOf(borrower.getpCity()));
                tvLoanDetailFmName.setText(borrower.getFname());
                tvLoanDetailFiCode.setText(String.valueOf(borrower.getCode()));
                tvLoanDetailAmount.setText(String.valueOf(borrower.getLoanAmt()));
                tvLoanDetailPeriod.setText(String.valueOf(borrower.getPeriod()));
                tvLoanDetailInterestRate.setText(String.valueOf(borrower.getIntRate()));
            }
        }

        if (intent != null) {
            guarantor = (ArrayList<Guarantor>) intent.getSerializableExtra(GlobalClass.ESIGN_GUARANTOR);
            if (guarantor != null) {
                tvLoanDetailCaseLocation.setText(String.valueOf(borrower.getpCity()));
                tvLoanDetailFmName.setText(borrower.getFname());
                tvLoanDetailFiCode.setText(String.valueOf(borrower.getCode()));
                tvLoanDetailAmount.setText(String.valueOf(borrower.getLoanAmt()));
                tvLoanDetailPeriod.setText(String.valueOf(borrower.getPeriod()));
                tvLoanDetailInterestRate.setText(String.valueOf(borrower.getIntRate()));

                CustomAdapter adapter = new CustomAdapter(this, R.layout.layout_esigner, guarantor);
                lvLoanDetails.setAdapter(adapter);
            }
        }

   btnLoanDetailsDownloadDoc.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           Log.d("TAG", "onClickList:2 ");
          // GlobalClass.showLottieAlertDialog(8,SecondEsignActivity.this);
           customProgressDialog.show();
           JsonObject jsonObject=new JsonObject();
           jsonObject.addProperty("DocName", "Esign");
           jsonObject.addProperty("dbName", "SBINEWDOC");
           // jsonObject.addProperty("FiCode", borrower.getCode());
           jsonObject.addProperty("FiCode", borrower.getCode());
           jsonObject.addProperty("FiCreator", borrower.getCreator());
           jsonObject.addProperty("UserID", GlobalClass.Id);
           HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
           logging.setLevel(HttpLoggingInterceptor.Level.BODY);
           OkHttpClient.Builder httpClient = new OkHttpClient.Builder(

           );
           httpClient.connectTimeout(2, TimeUnit.MINUTES);
           httpClient.readTimeout(2,TimeUnit.MINUTES);
           httpClient.addInterceptor(logging);
           Retrofit retrofit2 = new Retrofit.Builder()
                   .baseUrl("https://agra.Paisalo.in:8444/ESignSBIAV1/api/")
                   .addConverterFactory(GsonConverterFactory.create())
                   .client(httpClient.build())
                   .build();

           ApiInterface apiInterface = retrofit2.create(ApiInterface.class);
           customProgressDialog.show();
           Call<ResponseBody> call = apiInterface.DownloadDocSecondEsign(GlobalClass.LiveToken,jsonObject,"gzip,deflate,compress",GlobalClass.DevId,GlobalClass.DATABASE_NAME,GlobalClass.Imei);
           Log.d("TAG", "onClickList:3 ");

           Log.d("TAG", "onResponse1: " + GlobalClass.LiveToken+" "+ GlobalClass.dbname+" "+jsonObject.toString());
           call.enqueue(new Callback<ResponseBody>() {
               @Override
               public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                   Log.d("TAG", "onClickList:4 ");

                   Log.d("TAG", "onResponse1: " + response.body()+response.code()+response.message());

                   if(response.isSuccessful()){
                       customProgressDialog.dismiss();
                       Log.d("TAG", "onClickList:5 ");

                       Log.d("TAG", "onResponse2: " + response.body().contentLength());
                       File written = writeResponseBodyToDisk(response.body());
                       if(written==null){
                           Log.d("TAG", "onClickList:6 ");
                          // GlobalClass.dismissLottieAlertDialog();
                           customProgressDialog.dismiss();
                           SubmitAlert(SecondEsignActivity.this, "Error", "Document Not Download");
                           Log.d("TAG", "onResponse2: " + "null");

                       }else{
                           Log.d("TAG", "onClickList:11 ");
                           String path = written.getAbsolutePath();
                           Log.d("TAG", "onResponse2: " + path);
                           Intent intent = new Intent(SecondEsignActivity.this, FirstEsignActivity.class);
                           // Add data to the intent
                           intent.putExtra("SecondEsign", path);
                           intent.putExtra(GlobalClass.ESIGN_GUARANTOR, borrower);
                           startActivity(intent);

                           /*Fragment frag = MuPDFFragment.newInstance(path, false);
                           ft.add(R.id.pdfview, frag);
                           ft.commit();*/
                          // GlobalClass.dismissLottieAlertDialog();
                           customProgressDialog.dismiss();

                       }
                   }else{
                       Log.d("TAG", "onClickList:8 ");
                     //  GlobalClass.dismissLottieAlertDialog();
                       customProgressDialog.dismiss();

                       Log.d("TAG", "onResponse3: " + "UnSuccessful");
                   }
               }

               @Override
               public void onFailure(Call<ResponseBody> call, Throwable t) {
                   Log.d("TAG", "onClickList:9 ");
                   customProgressDialog.dismiss();

                   Log.d("TAG", "onResponse4: " + t.getMessage());

               }
           });
       }
   });

    }

    private File writeResponseBodyToDisk(ResponseBody body) {
        Log.d("TAG", "displayPdf1: "+ "display Pdf" );

        try {
            // Define the path where the file will be saved
            File pdfFile = new File(getBaseContext().getFilesDir(), borrower.getCode()+"_"+borrower.getCreator()+".pdf");
            Log.d("TAG", "displayPdf2: "+ pdfFile.getAbsolutePath() );

            if (pdfFile.exists() && pdfFile.isFile()) {
                pdfFile.delete();
            }

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(pdfFile);

                while (true) {
                    int read = inputStream.read(fileReader);
                    Log.d("TAG", "displayPdf3: "+ "pdfFile.getAbsolutePath()" );

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;

                    Log.d("TAG", "displayPdf4: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return pdfFile;
            } catch (IOException e) {
                Log.d("TAG", "writeResponseBodyToDisk: "+e.getMessage());
                return null;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return null;
        }
    }

}
