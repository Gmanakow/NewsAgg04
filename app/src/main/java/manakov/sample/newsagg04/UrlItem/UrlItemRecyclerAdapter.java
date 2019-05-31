package manakov.sample.newsagg04.UrlItem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import manakov.sample.newsagg04.R;

public class UrlItemRecyclerAdapter extends RecyclerView.Adapter<UrlItemRecyclerAdapter.ViewHolder> {
    private ArrayList<UrlItem> list;
    private View.OnClickListener onClickListener;

    public UrlItemRecyclerAdapter(ArrayList<UrlItem> list) {
        this.list = list;
    }

    @Override
    public UrlItemRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(
            R.layout.url_item_layout,
            parent,
            false
        );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UrlItemRecyclerAdapter.ViewHolder viewHolder, int position){
        viewHolder.urlItemTitleTextView.setText(
                list.get(position).getTitle()
        );
        viewHolder.urlItemLinkTextView.setText(
                list.get(position).getLink()
        );
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView urlItemTitleTextView;
        public TextView urlItemLinkTextView;

        public ViewHolder(View view){
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
