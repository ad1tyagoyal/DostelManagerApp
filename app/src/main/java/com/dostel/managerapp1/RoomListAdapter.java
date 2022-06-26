package com.dostel.managerapp1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.ViewHolder> {

    private List<RoomListItem> rooms;
    private Context context;


    public RoomListAdapter(List<RoomListItem> rooms, Context context) {
        this.rooms = rooms;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        RoomListItem roomItem = rooms.get(position);

        holder.roomNo.setText(roomItem.getRoomNo());
    }


    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView roomNo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roomNo = (TextView) itemView.findViewById(R.id.text_view_room_no_list);
        }
    }
}
