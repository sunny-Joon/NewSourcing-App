package com.paisalo.newinternalsourcingapp.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.paisalo.newinternalsourcingapp.R;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private EditText editText;
    private Button buttonSend, timeout, qr, qrDocs,wongid,secondesign;
    private TextView responseAbc, responseAbc1, response_docs,response_userid,response_sesign;
    private RecyclerView recyclerView;
   /* private ChatAdapter chatAdapter;
    private ArrayList<ChatMessage> chatMessages;
    private ChatDatabase chatDatabase;*/

    private boolean isQrDocsVisible = false;
    private boolean istimeoutVisible = false;
    private boolean isQrVisible = false;
    private boolean isWrongId = false;
    private boolean isSEsign = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

//        chatDatabase = Room.databaseBuilder(getApplicationContext(),
//                        ChatDatabase.class, "chat_database")
//                .build();

//        editText = findViewById(R.id.editText);
//        buttonSend = findViewById(R.id.buttonSend);
        qrDocs = findViewById(R.id.qrDocs);
        response_docs = findViewById(R.id.response_docs);

        wongid = findViewById(R.id.wongid);
        response_userid = findViewById(R.id.response_userid);

        secondesign = findViewById(R.id.secondesign);
        response_sesign = findViewById(R.id.response_sesign);



        timeout = findViewById(R.id.timeout);
        qr = findViewById(R.id.qr);
        responseAbc = findViewById(R.id.response_abc);
        responseAbc1 = findViewById(R.id.response_abc1);

       // recyclerView = findViewById(R.id.recycleview);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        chatMessages = new ArrayList<>();
//        chatAdapter = new ChatAdapter(chatMessages);
//        recyclerView.setAdapter(chatAdapter);

       /* buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editText.getText().toString();
                if (!message.isEmpty()) {
                  //  saveMessageToDatabase(message);
                    showSuccessDialog();
                    editText.setText("");
                }
            }
        });*/




        timeout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (istimeoutVisible) {
                    responseAbc.setVisibility(View.GONE);
                } else {
                    timeout();
                    responseAbc.setVisibility(View.VISIBLE);

                }
                istimeoutVisible = !istimeoutVisible;

            }
        });

        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isQrVisible) {
                    responseAbc1.setVisibility(View.GONE);
                } else {
                    qr();
                    responseAbc1.setVisibility(View.VISIBLE);

                }
                isQrVisible = !isQrVisible;
            }
        });

        qrDocs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isQrDocsVisible) {
                    response_docs.setVisibility(View.GONE);
                } else {

                    QRDocs();
                    response_docs.setVisibility(View.VISIBLE);
                }
                isQrDocsVisible = !isQrDocsVisible;
            }
        });

        wongid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isWrongId) {
                    response_userid.setVisibility(View.GONE);
                } else {
                    WrongUserID();
                    response_userid.setVisibility(View.VISIBLE);
                }
                isWrongId = !isWrongId;
            }
        });


        secondesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSEsign) {
                    response_sesign.setVisibility(View.GONE);
                } else {
                    SecondEsign();
                    response_sesign.setVisibility(View.VISIBLE);
                }
                isSEsign = !isSEsign;
            }
        });



    //    loadMessagesFromDatabase();
    }


//    private void saveMessageToDatabase(String message) {
//        ChatMessageEntity chatMessageEntity = new ChatMessageEntity(message);
//        new Thread(() -> {
//            chatDatabase.chatMessageDao().insert(chatMessageEntity);
//        }).start();
//    }

  /*  private void loadMessagesFromDatabase() {
        new Thread(() -> {

            List<ChatMessageEntity> messageEntities = chatDatabase.chatMessageDao().getAllMessages();
            runOnUiThread(() -> {
                for (ChatMessageEntity entity : messageEntities) {
                    chatMessages.add(new ChatMessage("User: " + entity.getMessage(), true));
                }
                chatAdapter.notifyDataSetChanged();

            });
        }).start();
    }*/

    private void timeout() {
        String response = "1=> Try 2 or 3 Times If Not Solved\n" +
                "2=> Try After 10 min\n" +
                "3=> Contact Support Number";
        responseAbc.setText(response);
        responseAbc.setVisibility(View.VISIBLE);
    }
    private void qr() {
        String response = "1=> Check Your aadhar Format \nIs Correct like DOB, NAME...\n" +
                "2=> Go To APP INFO Setting \n Click Storage then (clear data and cache)\n then give all permissions \n Restart Your App\n" +
                "3=> Contact Support Number";
        responseAbc1.setText(response);
        responseAbc1.setVisibility(View.VISIBLE);
    }
    private void QRDocs() {
        String response = "1=> Check Your aadhar Format \nIs Correct like DOB, AdharID...\n" +
                "2=> Please Make Sure Stamp \n Properly Visible \n" +
                "3=> Go To APP INFO Setting \n Click Storage then \n (clear data and cache)\n then give all permissions \n and Restart Your App\n" +
                "4=> Contact Support Number";
        response_docs.setText(response);
        response_docs.setVisibility(View.VISIBLE);

    }

    private void WrongUserID() {
        String response = "1=> First fill UserName \n" +
                "2=> Click shareId device \n" +
                "3=> Fill All Detail And \n Click Save Button\n" +
                "4=> Then Fill UserName And \n Pass then click Login";
        response_userid.setText(response);
        response_userid.setVisibility(View.VISIBLE);

    }

    private void  SecondEsign(){
        String response = "1=> Check Santioned Date \n " +
                "2=> If Santiond Dated 15 Days Over \n So, Case Not Show in 2nd Esign  \n" +
                "3=> IF Case Not Show in 2nd Esign  \n Generate KYC Again \n And All Process \n" ;

        response_sesign.setText(response);
        response_sesign.setVisibility(View.VISIBLE);

    }








    /*private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Success");
        builder.setMessage("Your issue has been successfully saved. \n The Solution will be found as soon as possible");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }*/
}
