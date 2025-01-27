package com.example.mindbodyearthmk3.utils.tipschatmessageutils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindbodyearthmk3.R;
import com.example.mindbodyearthmk3.database.TipsChatMessage;

public class TipsChatAdapter extends ListAdapter<TipsChatMessage, TipsChatAdapter.ChatViewHolder> {

    public TipsChatAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<TipsChatMessage> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<TipsChatMessage>() {
                @Override
                public boolean areItemsTheSame(@NonNull TipsChatMessage oldItem, @NonNull TipsChatMessage newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull TipsChatMessage oldItem, @NonNull TipsChatMessage newItem) {
                    return oldItem.getTextMessage().equals(newItem.getTextMessage()) &&
                            oldItem.isUserMessage() == newItem.isUserMessage();
                }
            };

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat_message, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        TipsChatMessage message = getItem(position);
        holder.bind(message);
    }

    static class ChatViewHolder extends RecyclerView.ViewHolder {
        private final TextView messageTextView;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
        }

        public void bind(TipsChatMessage message) {
            messageTextView.setText(message.getTextMessage());
            // You can style user and AI messages differently here
        }
    }
}
