package myapplication.example.spider_induction_task1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class lap_display_adapter extends RecyclerView.Adapter<lap_display_adapter.lap_display_ViewHolder> {
   private ArrayList<lap_display> adapter_lap_display;
    public static class lap_display_ViewHolder extends RecyclerView.ViewHolder{
        public TextView adapter_lap_time;
        public TextView adapter_lap_count;

        public lap_display_ViewHolder( @NonNull View itemView ) {
            super(itemView);
            adapter_lap_time = (TextView)itemView.findViewById(R.id.lap_time);
            adapter_lap_count = (TextView)itemView.findViewById(R.id.lap_number);
        }
    }
    public lap_display_adapter(ArrayList<lap_display> exampleList) {
        adapter_lap_display = exampleList;
    }

    @Override
    public lap_display_ViewHolder onCreateViewHolder(  ViewGroup parent, int viewType ) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lap_display, parent, false);
        lap_display_ViewHolder ldvh = new lap_display_ViewHolder(v);
        return ldvh;
    }

    @Override
    public void onBindViewHolder( lap_display_ViewHolder holder, int position ) {
        lap_display currentItem = adapter_lap_display.get(position);
        holder.adapter_lap_time.setText(currentItem.getLap_time());
        holder.adapter_lap_count.setText(currentItem.getLap_number()+"");
    }

    @Override
    public int getItemCount() {
        return adapter_lap_display.size();
    }
}
