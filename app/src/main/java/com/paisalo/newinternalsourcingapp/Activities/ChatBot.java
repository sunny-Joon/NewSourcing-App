package com.paisalo.newinternalsourcingapp.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.paisalo.newinternalsourcingapp.Adapters.MessageAdapter;
import com.paisalo.newinternalsourcingapp.R;
import java.util.ArrayList;
import java.util.List;

        public class ChatBot extends AppCompatActivity  implements MessageAdapter.MessageClickListener{

            private RecyclerView recyclerView;
            private MessageAdapter messageAdapter;
            private List<String> messages;
            private LinearLayout inputLayout;
            private EditText editTextMessage;
            private ImageButton buttonSend;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                setContentView(R.layout.activity_chat_bot);

                recyclerView = findViewById(R.id.recyclerView);
                inputLayout = findViewById(R.id.inputLayout);
                editTextMessage = findViewById(R.id.editTextMessage);
                buttonSend = findViewById(R.id.buttonSend);

                messages = new ArrayList<>();
                messages.add("Message 1");
                messages.add("Message 2");
                messages.add("Message 3");

                messageAdapter = new MessageAdapter(messages, this);
                recyclerView.setAdapter(messageAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));

                buttonSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String message = editTextMessage.getText().toString().trim();
                        if (!message.isEmpty()) {
                            messageAdapter.addMessage(message);
                            editTextMessage.setText("");
                            recyclerView.smoothScrollToPosition(messages.size() - 1);
                        }
                    }
                });
            }

            @Override
            public void onMessage1Click() {
                messageAdapter.addClickableMessage("Message 1");
                messageAdapter.addPredefinedMessages(1);
                recyclerView.smoothScrollToPosition(messages.size() - 1);
            }

            @Override
            public void onMessage2Click() {
                messageAdapter.addClickableMessage("Message 2");
                messageAdapter.addPredefinedMessages(2);
                recyclerView.smoothScrollToPosition(messages.size() - 1);
            }

            @Override
            public void onMessage3Click() {
                messageAdapter.addClickableMessage("Message 3");
                inputLayout.setVisibility(View.VISIBLE);
            }
        }