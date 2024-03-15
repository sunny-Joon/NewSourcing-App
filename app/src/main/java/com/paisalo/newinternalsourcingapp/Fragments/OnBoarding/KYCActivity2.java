package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.RoomDatabase.DaoClass;
import com.paisalo.newinternalsourcingapp.RoomDatabase.DatabaseClass;
import com.paisalo.newinternalsourcingapp.RoomDatabase.KycDataClass;

public class KYCActivity2 extends AppCompatActivity {

    EditText monthlyincome, expensemonthly, futureincome, agricultureincome, pensionincome, interestincome, otherincome, earningmemberincome, loanAmount;

    Spinner earningmembertype, businessdetail, loanpurpose, occuption, loanduration, selectbank;

    String monthlyIncome,expense,futureIncome,agricultureIncome,pensionIncome,interestIncome,otherIncome,earningMemberType,earningMemberIncome,loanamount,businessDetails,loanPurpose,occupation,loanDuration,
            selectedBank,age,aadhaarId,name,dob,gender,guardian,relationshipWithBorrower,address1,address2,address3,city,pin,state,mobile,voterId,pan,drivingLicense,motherFirstName,motherMiddleName,motherLastName,fatherFirstName,fatherMiddleName,fatherLastName,maritalStatus,spouseFirstName,spouseMiddleName,spouseLastName;

    private Button savedata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kycactivity2);

        savedata = findViewById(R.id.savedata);

        monthlyincome = findViewById(R.id.monthlyincome);
        expensemonthly = findViewById(R.id.expensemonthly);
        futureincome = findViewById(R.id.futureincome);
        agricultureincome = findViewById(R.id.agricultureincome);
        pensionincome = findViewById(R.id.pensionincome);
        interestincome = findViewById(R.id.interestincome);
        otherincome = findViewById(R.id.otherincome);
        earningmembertype = findViewById(R.id.earningmembertype);
        earningmemberincome = findViewById(R.id.earningmemberincome);
        loanAmount = findViewById(R.id.loanAmount);
        businessdetail = findViewById(R.id.businessdetail);
        loanpurpose = findViewById(R.id.loanpurpose);
        occuption = findViewById(R.id.occuption);
        loanduration = findViewById(R.id.loanduration);
        selectbank = findViewById(R.id.selectbank);

        Intent intent = getIntent();
         age = intent.getStringExtra("age");
         aadhaarId = intent.getStringExtra("aadhaarId");
         name = intent.getStringExtra("name");
         dob = intent.getStringExtra("dob");
         gender = intent.getStringExtra("gender");
         guardian = intent.getStringExtra("guardian");
         relationshipWithBorrower = intent.getStringExtra("relationshipWithBorrower");
         address1 = intent.getStringExtra("address1");
         address2 = intent.getStringExtra("address2");
         address3 = intent.getStringExtra("address3");
         city = intent.getStringExtra("city");
          pin = intent.getStringExtra("pin");
         state = intent.getStringExtra("state");
         mobile = intent.getStringExtra("mobile");
         voterId = intent.getStringExtra("voterId");
         pan = intent.getStringExtra("pan");
         drivingLicense = intent.getStringExtra("drivingLicense");
         motherFirstName = intent.getStringExtra("motherFirstName");
         motherMiddleName = intent.getStringExtra("motherMiddleName");
         motherLastName = intent.getStringExtra("motherLastName");
         fatherFirstName = intent.getStringExtra("fatherFirstName");
         fatherMiddleName = intent.getStringExtra("fatherMiddleName");
         fatherLastName = intent.getStringExtra("fatherLastName");
         maritalStatus = intent.getStringExtra("maritalStatus");
         spouseFirstName = intent.getStringExtra("spouseFirstName");
         spouseMiddleName = intent.getStringExtra("spouseMiddleName");
         spouseLastName = intent.getStringExtra("spouseLastName");

        savedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SaveEditTextData().execute();
            }
        });
    }// onCreate Closed

    private class SaveEditTextData extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            // Get text from EditText fields
            monthlyIncome = monthlyincome.getText().toString();
            expense = expensemonthly.getText().toString();
            futureIncome = futureincome.getText().toString();
            agricultureIncome = agricultureincome.getText().toString();
            pensionIncome = pensionincome.getText().toString();
            interestIncome = interestincome.getText().toString();
            otherIncome = otherincome.getText().toString();
            earningMemberIncome = earningmemberincome.getText().toString();
            loanamount = loanAmount.getText().toString();
//            earningMemberType = earningmembertype.getSelectedItem().toString();
//            selectedBank = selectbank.getSelectedItem().toString();
//            loanDuration = loanduration.getSelectedItem().toString();
//            occupation = occuption.getSelectedItem().toString();
//            loanPurpose = loanpurpose.getSelectedItem().toString();
//            businessDetails = businessdetail.getSelectedItem().toString();
            businessDetails = "DETAILS";
            loanPurpose = "loanpurpose";
            occupation = "occuption";
            loanDuration = "loanduration";
            earningMemberType = "TYPE";
            selectedBank = "selectbank";


            DatabaseClass database = DatabaseClass.getInstance(KYCActivity2.this);
            DaoClass dao = database.dao();
            KycDataClass entity = new KycDataClass();

            entity.setAadhaarId(aadhaarId.trim());
            entity.setName(name.trim());
            entity.setAge(age.trim());
            entity.setDob(dob.trim());
            entity.setGender(gender.trim());
            entity.setGuardian(guardian.trim());
            entity.setRelationshipWithBorrower(relationshipWithBorrower.trim());
            entity.setAddress1(address1.trim());
            entity.setAddress2(address2.trim());
            entity.setAddress3(address3.trim());
            entity.setCity(city.trim());
            entity.setPin(pin.trim());
            entity.setState(state.trim());
            entity.setMobile(mobile.trim());
            entity.setVoterId(voterId.trim());
            entity.setPan(pan.trim());
            entity.setDrivingLicense(drivingLicense.trim());
            entity.setMotherFirstName(motherFirstName.trim());
            entity.setMotherMiddleName(motherMiddleName.trim());
            entity.setMotherLastName(motherLastName.trim());
            entity.setFatherFirstName(fatherFirstName.trim());
            entity.setFatherMiddleName(fatherMiddleName.trim());
            entity.setFatherLastName(fatherLastName.trim());
            entity.setMaritalStatus(maritalStatus.trim());
            entity.setSpouseFirstName(spouseFirstName.trim());
            entity.setSpouseMiddleName(spouseMiddleName.trim());
            entity.setSpouseLastName(spouseLastName.trim());
            entity.setMonthlyIncome(monthlyIncome.trim());
            entity.setExpense(expense.trim());
            entity.setFutureIncome(futureIncome.trim());
            entity.setAgricultureIncome(agricultureIncome.trim());
            entity.setPensionIncome(pensionIncome.trim());
            entity.setInterestIncome(interestIncome.trim());
            entity.setOtherIncome(otherIncome.trim());
            entity.setEarningMemberType(earningMemberType.trim());
            entity.setEarningMemberIncome(earningMemberIncome.trim());
            entity.setLoanAmount(loanamount.trim());
            entity.setBusinessDetails(businessDetails.trim());
            entity.setLoanPurpose(loanPurpose.trim());
            entity.setOccupation(occupation.trim());
            entity.setLoanDuration(loanDuration.trim());
            entity.setSelectedBank(selectedBank.trim());
            entity.FiCode = "0012";

            dao.insertKycData(entity);

            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(KYCActivity2.this,"Saved", Toast.LENGTH_SHORT).show();
        }
    }
}

