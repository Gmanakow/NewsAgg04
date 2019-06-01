package manakov.sample.newsagg04.UrlItem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import manakov.sample.newsagg04.R;

public class UrlItemRecyclerAdapter extends RecyclerView.Adapter<UrlItemRecyclerAdapter.ViewHolder> {
    private ArrayList<UrlItem> list;
    private View.OnClickListener onClickListener;

    public UrlItemRecyclerAdapter(ArrayList<UrlItem> list) {
        this.list = list;
    }

    @Override
    @NonNull
    public UrlItemRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(
            R.layout.url_item_layout,
            parent,
            false
        );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position){
        viewHolder.urlItemTitleTextView.setText(
                list.get(position).getTitle()
        );
        viewHolder.urlItemLinkTextView.setText(
                list.get(position).getLink()
        );
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView urlItemTitleTextView;
        private TextView urlItemLinkTextView;

        private ViewHolder(View view){
            super(view);

            urlItemTitleTextView = view.findViewById(R.id.urlItemTitleTextView );
            urlItemLinkTextView  = view.findViewById(R.id.urlItemLinkTextView  );

            view.setTag(this);
            view.setOnClickListener(onClickListener);
        }
    }

    public void setList(ArrayList<UrlItem> list){
        this.list.clear();
        this.list.addAll(list);

        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public void setOnClickListener(View.OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }
}
