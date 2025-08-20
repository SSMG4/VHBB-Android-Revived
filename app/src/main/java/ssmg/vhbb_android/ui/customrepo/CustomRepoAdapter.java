package ssmg.vhbb_android.ui.customrepo;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ssmg.vhbb_android.R;
import ssmg.vhbb_android.Utils.DownloadUtils;

public class CustomRepoAdapter extends RecyclerView.Adapter<CustomRepoAdapter.ViewHolder> {

    private final Activity mActivity;
    private final ArrayList<CustomRepoItem> mCustomRepoList;
    private final ArrayList<CustomRepoItem> mCustomRepoListFull;

    public CustomRepoAdapter (Activity activity, ArrayList<CustomRepoItem> customRepoList) {
        this.mActivity = activity;
        this.mCustomRepoList = customRepoList;
        this.mCustomRepoListFull = new ArrayList<>(mCustomRepoList);
    }

    @NonNull
    @Override
    public CustomRepoAdapter.ViewHolder onCreateViewHolder (@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_customrepo, viewGroup, false);
        return new CustomRepoAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder (@NonNull CustomRepoAdapter.ViewHolder holder, int position) {
        CustomRepoItem currentItem = mCustomRepoList.get(position);

        String dataUrlID = currentItem.getDataUrl();
        String dateID = currentItem.getDate();

        holder.mTitle.setText(String.format("%s %s", currentItem.getName(), currentItem.getVersion()));
        holder.mAuthor.setText(currentItem.getAuthor());
        holder.mDescription.setText(currentItem.getDescription());

        holder.mDate.setText(String.format("(%s)", dateID));
        holder.mDate.setVisibility(!dateID.equals("") ? View.VISIBLE : View.GONE);

        holder.mDownload.setOnClickListener(v -> DownloadUtils.VHBBDownloadManager(mActivity, v.getContext(), Uri.parse(currentItem.getUrl()), currentItem.getFilename()));

        holder.mDownloadData.setVisibility(!dataUrlID.equals("") ? View.VISIBLE : View.GONE);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)holder.mDescription.getLayoutParams();
        lp.setMargins(0,
                (int)mActivity.getResources().getDimension(R.dimen.homebrew_padding_small),
                (int)mActivity.getResources().getDimension(!dataUrlID.equals("") ? R.dimen.homebrew_desc_margin_sec : R.dimen.homebrew_desc_margin_def),
                0);
        holder.mDescription.setLayoutParams(lp);

        if (!dataUrlID.equals("")) holder.mDownloadData.setOnClickListener(v -> DownloadUtils.VHBBDownloadManager(mActivity, v.getContext(), Uri.parse(dataUrlID), currentItem.getDataFilename()));
    }

    @Override
    public int getItemCount () {
        return mCustomRepoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle, mAuthor, mDescription, mDate;
        public ImageButton mDownload, mDownloadData;

        public ViewHolder (View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.textview_name);
            mAuthor = itemView.findViewById(R.id.textview_author);
            mDescription = itemView.findViewById(R.id.textview_desc);
            mDate = itemView.findViewById(R.id.textview_date);
            mDownload = itemView.findViewById(R.id.download);
            mDownloadData = itemView.findViewById(R.id.downloadData);
        }
    }

    //region Filter

    public Filter getFilter () {
        return mFilter;
    }

    private final Filter mFilter = new Filter() {
        @Override
        protected FilterResults performFiltering (CharSequence constraint) {
            ArrayList<CustomRepoItem> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mCustomRepoListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (CustomRepoItem item : mCustomRepoListFull)
                    if (item.getName().toLowerCase().contains(filterPattern))
                        filteredList.add(item);
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults (CharSequence constraint, FilterResults results) {
            mCustomRepoList.clear();
            //noinspection unchecked
            mCustomRepoList.addAll((ArrayList<CustomRepoItem>)results.values);
            notifyDataSetChanged();
        }
    };

    //endregion

}
