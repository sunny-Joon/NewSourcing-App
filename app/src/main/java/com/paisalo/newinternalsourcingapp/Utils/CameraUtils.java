package com.paisalo.newinternalsourcingapp.Utils;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.journeyapps.barcodescanner.Size;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CameraUtils {
    public static final int REQUEST_TAKE_PHOTO = 101;
    public static final int REQUEST_TAKE_PROFILE_PHOTO = 105;
    public static final int SELECT_PICTURE = 110;
    static String mCurrentPhotoPath;
    private static String TAG = "CameraUtils";


    public static void dispatchTakePictureIntent(Activity activity) throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {

            // Create the File where the photo should go
            File photoFile = createImageFile(activity);
            mCurrentPhotoPath = photoFile.getAbsolutePath();

            // Continue only if the File was successfully created
            if (photoFile != null) {
                //Uri photoURI = Uri.fromFile(photoFile);
                Uri photoURI = FileProvider.getUriForFile(activity,
                        activity.getApplicationContext().getPackageName() + ".provider", photoFile);
                ((OnCameraCaptureUpdate) activity).cameraCaptureUpdate(photoURI);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                activity.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    public static void dispatchTakePictureIntent(Fragment fragment) throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(fragment.requireActivity().getPackageManager()) != null) {

            // Create the File where the photo should go
            File photoFile = createImageFile(fragment.getActivity());
            mCurrentPhotoPath = photoFile.getAbsolutePath();

            // Continue only if the File was successfully created
            if (photoFile != null) {
                //Uri photoURI = Uri.fromFile(photoFile);
                Uri photoURI = FileProvider.getUriForFile(fragment.getActivity(), fragment.getActivity().getApplicationContext().getPackageName() + ".provider", photoFile);
                ((OnCameraCaptureUpdate) fragment).cameraCaptureUpdate(photoURI);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                fragment.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    public static void picMediaImage(Activity activity) throws IOException {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public static File createImageFile(Context context) throws IOException {
        return createImageFile(context, "JPEG");
    }

    public static File createImageFile(Context context, String fileExtention) throws IOException {
        return createImageFile(context, null, fileExtention);
    }

    public static File getImagePath() {
        return Utils.getFilePath(Environment.DIRECTORY_PICTURES);
    }

    public static File createImageFile(Context context, String fileName, String fileExtention) throws IOException {
        return Utils.createFile(context, fileName, fileExtention, Environment.DIRECTORY_PICTURES);
    }

    private static File getTempFile(Context context, String url) {
        File file = null;
        try {
            String fileName = Uri.parse(url).getLastPathSegment();
            file = File.createTempFile(fileName, null, context.getCacheDir());
        } catch (IOException e) {
            // Error while creating file
        }
        return file;
    }

    public interface OnCameraCaptureUpdate {
        void cameraCaptureUpdate(Uri uriImage);
    }

 /*   public static byte[] finaliseImageCrop(int resultCode, Intent data, int MaxDimentions, @Nullable Exception error) {
        return finaliseImageCrop(resultCode, data, MaxDimentions, error, false);
    }*/

  /*  public static byte[] finaliseImageCrop(int resultCode, Intent data, int MaxDimentions, @Nullable Exception error, Boolean toGrayScale) {
        byte[] bytes = null;
        error = null;
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (resultCode == RESULT_OK) {
            Uri resultUri = result.getUri();
            Bitmap bitmap = BitmapUtility.getPic(resultUri, new Size(MaxDimentions, MaxDimentions));
            if (toGrayScale) {
                bitmap = BitmapUtility.androidGrayScale(bitmap);
            }
            bytes = BitmapUtility.getBitmapByteArray(bitmap);
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            error = result.getError();
        }
        return bytes;
    }*/

    /*public static Uri finaliseImageCropUri(int resultCode, Intent data,int MaxDimentions, @Nullable Exception error, Boolean toGrayScale){
        error=null;
        Uri resultUri=null;
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (resultCode == RESULT_OK) {
            resultUri = result.getUri();
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            error = result.getError();
        }
        return resultUri;
    }*/
  /*  public static Uri finaliseImageCropUri(int resultCode, Intent data, int MaxDimentions, @Nullable Exception error, boolean preserverAspectRatio) {
        //error=null;
        Uri resultUri = null;
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (resultCode == RESULT_OK) {
            //resultUri = result.getUri();
            try {
                resultUri = BitmapUtility.getPicUri(result.getUri(), result.getUri(), MaxDimentions, preserverAspectRatio);
            } catch (IOException e) {
                error = result.getError();
                e.printStackTrace();

            }
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            error = result.getError();
            Log.e("ErrorCameraUtilCrop",error.toString());
        }
        return resultUri;
    }

    public static boolean finaliseImageCropUri(int resultCode, Intent data, int MaxDimentions, Uri destinationUri, @Nullable Exception error, Boolean toGrayScale) throws IOException {
        error = null;
        Boolean status = false;
        Uri resultUri = null;
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (resultCode == RESULT_OK) {
            resultUri = result.getUri();
            destinationUri = BitmapUtility.getPicUri(resultUri, destinationUri, new Size(0, 300));
            (new File(resultUri.getPath())).deleteOnExit();
            status = true;
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            error = result.getError();
        }
        return status;
    }*/

   /* public static void toGreyScale(Context context, Uri imageUri, int MaxDimention) {
        Glide.with(context)
                .load(imageUri)
                .asBitmap()
                .transform(new GrayscaleTransformation(context))
                .into(new FileTarget(imageUri.getPath(), MaxDimention, MaxDimention));
    }*/

    public static File moveCachedImage2Storage(Context context, File cachedImage, boolean deleteSource,int type) throws IOException {
        File croppedImage = CameraUtils.createImageFile(context);
        Utils.copyFile(cachedImage, croppedImage);
        if (deleteSource) cachedImage.delete();
        if (croppedImage.exists()) {
            long fileSizeInBytes = croppedImage.length();
            long fileSizeInKB = fileSizeInBytes / 1024; // Convert bytes to KB

            Log.d("FileSize", "File size: " + fileSizeInKB + " KB");
        } else {
            Log.e("FileSize", "File doesn't exist");
        }
        if (type==1){
            croppedImage=compressToTargetSize(croppedImage.getPath(),70);
            if (croppedImage.exists()) {
                long fileSizeInBytes = croppedImage.length();
                long fileSizeInKB = fileSizeInBytes / 1024; // Convert bytes to KB

                Log.d("FileSize", "File size: " + fileSizeInKB + " KB");
            } else {
                Log.e("FileSize", "File doesn't exist");
            }
        }

        return croppedImage;
    }



    public static File compressToTargetSize(String imagePath, int targetFileSizeInKB) {
        File compressedFile = null;
        try {
            // Load the original image
            Bitmap originalBitmap = BitmapFactory.decodeFile(imagePath);

            // Define the desired width and height
            int desiredWidth = originalBitmap.getWidth(); // Set your desired width
            int desiredHeight = originalBitmap.getHeight(); // Set your desired height

            // Calculate the target file size in bytes
            int targetFileSize = targetFileSizeInKB * 1024; // Convert KB to bytes

            // Resize the original bitmap
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, desiredWidth, desiredHeight, true);

            // Initialize variables for compression
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int compressionQuality = 95; // Initial quality

            // Compress the image multiple times with decreasing quality until it reaches the target file size
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, compressionQuality, byteArrayOutputStream);

            while (byteArrayOutputStream.toByteArray().length > targetFileSize && compressionQuality > 0) {
                byteArrayOutputStream.reset();
                compressionQuality -= 5; // Adjust decrement as needed

                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, compressionQuality, byteArrayOutputStream);
            }

            // Create a file to save the compressed image
            compressedFile = new File(imagePath);

            // Save the compressed image to the file
            FileOutputStream fileOutputStream = new FileOutputStream(compressedFile);
            fileOutputStream.write(byteArrayOutputStream.toByteArray());
            fileOutputStream.flush();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return compressedFile;
    }
}
