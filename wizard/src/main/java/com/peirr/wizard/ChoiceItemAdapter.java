package com.peirr.wizard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.coresthetics.wizard.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChoiceItemAdapter extends RecyclerView.Adapter<ChoiceItemAdapter.ViewHolder> {

    private final List<ChoiceItem> items;
    private ChoiceClickListener listener;

    public ChoiceItemAdapter(List<ChoiceItem> items) {
        this.items = items;
    }

    public void setListener(ChoiceClickListener listener) {
        this.listener = listener;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.widget_cb_h2, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChoiceItem item = items.get(position);
        holder.icon.setVisibility(item.getIconRes() != -1 ? View.VISIBLE : View.GONE);
        if(item.getIconRes() != -1){
            Glide.with(holder.itemView.getContext())
                    .load(item.getIconRes())
                    .into(holder.icon);
        }

        if(item.getTitle() != 0) {
            holder.title.setText(item.getTitle());
        }else{
            holder.title.setText(null);
        }

        if(item.getFooter() != 0) {
            holder.footer.setText(item.getFooter());
        }else{
            holder.footer.setText(null);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView footer;
        public ImageView icon;

        public ViewHolder(View view) {
            super(view);
            icon = view.findViewById(R.id.choice_icon);
            title = view.findViewById(R.id.choice_text);
            footer = view.findViewById(R.id.choice_subtext);
            view.setOnClickListener(v -> {
                if (listener != null && !items.isEmpty()) {
                    listener.onSelectedChoice(items.get(getAdapterPosition()));
                }
            });
        }
    }

}
