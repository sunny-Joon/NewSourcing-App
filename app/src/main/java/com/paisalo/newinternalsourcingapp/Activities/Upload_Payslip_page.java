package com.paisalo.newinternalsourcingapp.Activities;


import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.JsonObject;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.ImeiMappingModel;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;
import com.paisalo.newinternalsourcingapp.Utils.CameraUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Upload_Payslip_page extends AppCompatActivity {
    Button btnUploadImage;
    private ImageView imageViewSelectedImage;
    private Uri selectedImageUri;
    String smcode;
    Button btnSubmit;
    File pickedFile;
    TextView editTextProductName;
    private ProgressDialog progressDialog;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private static final int PICK_IMAGE_REQUEST = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_payslip_page);
        getSupportActionBar().show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        smcode=intent.getStringExtra("SMCODE");
        editTextProductName=findViewById(R.id.editTextProductName);
        editTextProductName.setText(smcode);
        btnUploadImage=findViewById(R.id.btnUploadImage);
        imageViewSelectedImage=findViewById(R.id.imageViewSelectedImage);
        btnSubmit=findViewById(R.id.btnSubmit);
        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChoiceDialog();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   if (pickedFile!=null && editTextCaseCode.getText().toString().trim().length()<8)
                saveReciept(pickedFile,editTextProductName.getText().toString().trim());
            }
        });

    }


    private void saveReciept(File pickedFile, String smCode) {
        progressDialog = ProgressDialog.show(Upload_Payslip_page.this, "", "Loading...", true, false);
        Log.d("TAG", "saveReciept: api hitt");

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        RequestBody Body = RequestBody.create(MediaType.parse("*/*"),pickedFile);
        MultipartBody.Part ImagesParts = MultipartBody.Part.createFormData("FileName",pickedFile.getName(),Body);

        final RequestBody smcoderequest = RequestBody.create(MediaType.parse("text/plain"), smCode);
        Call<JsonObject> call=apiInterface.saveReciptOnpayment(GlobalClass.Token,GlobalClass.dbname,ImagesParts,smcoderequest);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressDialog.dismiss();
                failAlert(1,"Upload Successfully","Receipt Upload successfully");
                //Log.d("TAG", "onResponse: "+response.body());
                //.d("TAG", "onResponse: "+response.headers());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();
                // Log.d("TAG", "onFailure: "+t.getMessage());
                failAlert(2,"Upload Image Failed","Receipt Uploading failed,Try again");
            }
        });
    }

    private void failAlert(int i, String upload_successfully, String s){
        AlertDialog.Builder builder = new AlertDialog.Builder(Upload_Payslip_page.this);
        // Set the dialog title and message
        builder.setTitle(upload_successfully)
                .setMessage(s);
        // Add OK button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(i==1){
                    dialog.dismiss();
                    finish();
                }else{
                    dialog.dismiss();
                }
                // Dismiss the dialog
            }
        });
        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void showChoiceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an option")
                .setItems(R.array.choice_array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        switch (which) {
                            case 0:
                                // Option 1 selected
                                openGallery();
                                break;
                            case 1:
                                // Option 2 selected
                                takePhoto();
                                break;
                            default:
                                break;
                        }
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void takePhoto() {


       /* ImagePicker.with(this)
                .cameraOnly()
                .start(REQUEST_TAKE_PHOTO);*/
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }
    private void setImagepath(File file) {

        Toast.makeText(this, "Checking File: "+file.getAbsolutePath()+"", Toast.LENGTH_SHORT).show();

        if (file.length() != 0) {

            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

            if (myBitmap != null) {
                imageViewSelectedImage.setVisibility(View.VISIBLE);
                imageViewSelectedImage.setImageBitmap(myBitmap);
                Log.e("CHeckingmyBitmap22",myBitmap+"");
                pickedFile=file;
            } else {
                Toast.makeText(this, "Bitmap Null", Toast.LENGTH_SHORT).show();
                Log.e("BitmapImage", "Null");
            }
        } else {
            Toast.makeText(this, "Filepath Empty", Toast.LENGTH_SHORT).show();

        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            // Get the selected image URI
            selectedImageUri = data.getData();

            // Update the ImageView with the selected image
            if (imageViewSelectedImage != null) {
                imageViewSelectedImage.setVisibility(View.VISIBLE);
                imageViewSelectedImage.setImageURI(selectedImageUri);

                pickedFile=new File(getRealPathFromURI(selectedImageUri));
            }
        }/* else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {

            if (data != null) {
                selectedImageUri = data.getData();
                CropImage.activity(selectedImageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setMultiTouchEnabled(true)
                        .start(this);
            } else {
                Log.e("ImageData", "Null");
                Toast.makeText(Upload_Payslip_page.this, "Image Data Null", Toast.LENGTH_SHORT).show();
            }

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            Exception error = null;
            if (data != null) {
                Uri imageUri = CameraUtils.finaliseImageCropUri(resultCode, data, 300, error, false);
                //Toast.makeText(activity, imageUri.toString(), Toast.LENGTH_SHORT).show();
                File tempCroppedImage = new File(imageUri.getPath());
                Log.e("tempCroppedImage", tempCroppedImage.getPath() + "");


                if (error == null) {

                    if (imageUri != null) {

                        if (tempCroppedImage.length() > 100) {
                            (new File(selectedImageUri.getPath())).delete();
                            try {

                                File croppedImage = CameraUtils.moveCachedImage2Storage(this, tempCroppedImage, true, 1);

                                setImagepath(croppedImage);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast.makeText(this, "CroppedImage FIle Length:" + tempCroppedImage.length() + "", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, imageUri.toString() + "", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, error.toString() + "", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "CropImage data: NULL", Toast.LENGTH_SHORT).show();
            }
            }
*/

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}