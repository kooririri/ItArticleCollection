package local.hal.st31.android.itarticlecollection70443;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private LayoutInflater mInflater;
    private List<Bean> data;
    private OnItemClickListener   mOnItemClickListener;

    public ListAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }

    public void setData(List<Bean> data){
        this.data = data;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_cell,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, final int position) {
        Bean bean = data.get(position);
        holder.titleTextView.setText(bean.getTitle());
        holder.urlTextView.setText(bean.getUrl());
        if (mOnItemClickListener != null) {
            holder.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        LinearLayout itemLayout;
        TextView titleTextView;
        TextView urlTextView;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.txTitle);
            urlTextView = itemView.findViewById(R.id.txUrl);
            itemLayout = itemView.findViewById(R.id.itemLayout);

        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickLitener){
        this.mOnItemClickListener = mOnItemClickLitener;
    }
}
