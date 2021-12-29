package com.example.week1.ui.main;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.week1.ContactActivity;
import com.example.week1.PopupActivity;
import com.example.week1.R;
import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private ArrayList<PhoneBook> mList;
    private Context context;

    public CustomAdapter(Context context, ArrayList<PhoneBook> list) {
        this.context = context;
        this.mList = list;
    }
    /* CustomViewHolder constructed with textViews */
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView name;
        protected TextView phnumber;

        public CustomViewHolder(View view) {
            super(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(context, ContactActivity.class);
                        intent.putExtra("name",mList.get(pos).getName());
                        intent.putExtra("phnumber",mList.get(pos).getTel());
                        context.startActivity(intent);
                    }
                }
            });

            this.name = view.findViewById(R.id.name_listitem);
            this.phnumber = view.findViewById(R.id.tel_listitem);
        }
    }



    /* Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type
     * to represent an item. */
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    /* Called when notifyItemChanged */
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {
        PhoneBook data = mList.get(position);
        viewholder.name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        viewholder.phnumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

        viewholder.name.setGravity(Gravity.LEFT);
        viewholder.phnumber.setGravity(Gravity.LEFT);

        viewholder.name.setText(data.getName());
        viewholder.phnumber.setText(data.getTel());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}