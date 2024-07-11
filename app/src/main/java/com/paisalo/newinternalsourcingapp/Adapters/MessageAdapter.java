package com.paisalo.newinternalsourcingapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paisalo.newinternalsourcingapp.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> messages;
    private static final int VIEW_TYPE_MESSAGE = 1;
    private static final int VIEW_TYPE_CLICKABLE = 2;
    private static final int VIEW_TYPE_USER_MESSAGE = 3;

    private MessageClickListener clickListener;

    public MessageAdapter(List<String> messages, MessageClickListener clickListener) {
        this.messages = messages;
        this.clickListener = clickListener;
    }

    @Override
    public int getItemViewType(int position) {
        String message = messages.get(position);
        if (message.equals("Message 1") || message.equals("Message 2") || message.equals("Message 3")) {
            return VIEW_TYPE_CLICKABLE;
        } else if (message.startsWith("User: ") || message.startsWith("Clicked: ")) {
            return VIEW_TYPE_USER_MESSAGE;
        }
        return VIEW_TYPE_MESSAGE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_CLICKABLE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clickable_message, parent, false);
            return new ClickableMessageViewHolder(view);
        } else if (viewType == VIEW_TYPE_USER_MESSAGE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_message, parent, false);
            return new UserMessageViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
            return new MessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String message = messages.get(position);
        if (holder.getItemViewType() == VIEW_TYPE_CLICKABLE) {
            ClickableMessageViewHolder clickableHolder = (ClickableMessageViewHolder) holder;
            clickableHolder.clickableMessageTextView.setText(message);
            clickableHolder.clickableMessageTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (message.equals("Message 1")) {
                        clickListener.onMessage1Click();
                    } else if (message.equals("Message 2")) {
                        clickListener.onMessage2Click();
                    } else if (message.equals("Message 3")) {
                        clickListener.onMessage3Click();
                    }
                }
            });
        } else if (holder.getItemViewType() == VIEW_TYPE_USER_MESSAGE) {
            UserMessageViewHolder userMessageHolder = (UserMessageViewHolder) holder;
            userMessageHolder.userMessageTextView.setText(message.startsWith("User: ") ? message.substring(6) : message.substring(9)); // Removing "User: " or "Clicked: " prefix
        } else {
            MessageViewHolder messageHolder = (MessageViewHolder) holder;
            messageHolder.messageTextView.setText(message);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView messageTextView;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
        }
    }

    public static class ClickableMessageViewHolder extends RecyclerView.ViewHolder {
        public TextView clickableMessageTextView;

        public ClickableMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            clickableMessageTextView = itemView.findViewById(R.id.clickableMessageTextView);
        }
    }

    public static class UserMessageViewHolder extends RecyclerView.ViewHolder {
        public TextView userMessageTextView;

        public UserMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            userMessageTextView = itemView.findViewById(R.id.userMessageTextView);
        }
    }

    public interface MessageClickListener {
        void onMessage1Click();
        void onMessage2Click();
        void onMessage3Click();
    }

    public void addPredefinedMessages(int messageNumber) {
        int position = messages.size();
        if (messageNumber == 1) {
            messages.add("Message 1 from predefined set 1");
            messages.add("Message 2 from predefined set 1");
            messages.add("Message 3 from predefined set 1");
            messages.add("Message 4 from predefined set 1");
            messages.add("Message 5 from predefined set 1");
        } else if (messageNumber == 2) {
            messages.add("Message 1 from predefined set 2");
            messages.add("Message 2 from predefined set 2");
            messages.add("Message 3 from predefined set 2");
            messages.add("Message 4 from predefined set 2");
        }
        notifyItemRangeInserted(position, 5);
    }

    public void addMessage(String message) {
        messages.add("User: " + message);
        notifyItemInserted(messages.size() - 1);
    }

    public void addClickableMessage(String message) {
        messages.add("Clicked: " + message);
        notifyItemInserted(messages.size() - 1);
    }
}
