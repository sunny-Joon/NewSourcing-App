package com.paisalo.newinternalsourcingapp.Fragments.ApplicationFormFragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.Manifest;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.paisalo.newinternalsourcingapp.Adapters.KycRecyclerViewAdapter;
import com.paisalo.newinternalsourcingapp.ModelclassesRoom.KYCScanningModel;
import com.paisalo.newinternalsourcingapp.R;
import java.util.ArrayList;
import java.util.List;

public class KycScanningFragment extends Fragment implements KycRecyclerViewAdapter.OnItemClickListener {

    private KycRecyclerViewAdapter kycRecyclerViewAdapter;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 101;

    public KycScanningFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<KYCScanningModel> kycScanningList = new ArrayList<>();
        kycScanningList.add(new KYCScanningModel("John Doe", "Personal", "Aadhaar", "Front"));
        kycScanningList.add(new KYCScanningModel("Jane Doe", "Business", "Aadhaar", "Back"));
        kycScanningList.add(new KYCScanningModel("John Doe", "Personal", "Voter Id", "front"));
        kycScanningList.add(new KYCScanningModel("Jane Doe", "Business", "VoterId", "Back"));
        kycScanningList.add(new KYCScanningModel("John Doe", "Personal", "PassBook", "First Page"));
        kycScanningList.add(new KYCScanningModel("John Doe", "Personal", "Passbook", "Last Page"));
        kycScanningList.add(new KYCScanningModel("Jane Doe", "Business", "Co-Borrower Aadhaar", "Front"));
        kycScanningList.add(new KYCScanningModel("John Doe", "Personal", "Co-Borrower Aadhaar", "Back"));

        kycRecyclerViewAdapter = new KycRecyclerViewAdapter(requireContext(), kycScanningList, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kyc_scanning, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.kycScanningRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(kycRecyclerViewAdapter);

        return view;
    }

    @Override
    public void onItemClick(int position) {
        Log.d("Sunny", "sunny" + position);
        openCamera();
    }

    private void openCamera() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            startCameraIntent();
        } else {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCameraIntent();
            } else {

            }
        }
    }

    private void startCameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
}
