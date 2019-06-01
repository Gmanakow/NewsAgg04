package manakov.sample.newsagg04.RssItem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import manakov.sample.newsagg04.R;

public class RssItemRecyclerAdapter extends RecyclerView.Adapter<RssItemRecyclerAdapter.ViewHolder> {
    private ArrayList<RssItem> list;
    private View.OnClickListener onClickListener;

    public RssItemRecyclerAdapter(ArrayList<RssItem> list) {
        this.list = list;
    }

    @Override
    @NonNull
    public RssItemRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.rss_item_layout,
                parent,
                false
        );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position){
        viewHolder.rssItemTitleTextView.setText(
                list.get(position).getTitle()
        );
        viewHolder.rssItemDateTextView.setText(
                list.get(position).getDate()
        );
        viewHolder.rssItemLinkTextView.setText(
                list.get(position).getLink()
        );
        viewHolder.rssItemDescriptionTextView.setText(
                list.get(position).getDescription()
        );
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView rssItemTitleTextView;
        private TextView rssItemDateTextView;
        private TextView rssItemLinkTextView;
        private TextView rssItemDescriptionTextView;

        private ViewHolder(View view){
            super(view);

            rssItemTitleTextView       = view.findViewById(R.id.rssItemTitleTextView       );
            rssItemDateTextView        = view.findViewById(R.id.rssItemDateTextView        );
            rssItemLinkTextView        = view.findViewById(R.id.rssItemLinkTextView        );
            rssItemDescriptionTextView = view.findViewById(R.id.rssItemDescriptionTextView );

            view.setTag(this);
            view.setOnClickListener(onClickListener);
        }
    }

    public void setList(ArrayList<RssItem> list){
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
