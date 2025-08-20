package ssmga.vhbb_android.ui.cbpsdb;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ssmga.vhbb_android.Constants.CBPSDB;
import ssmga.vhbb_android.Constants.VHBBAndroid;
import ssmga.vhbb_android.R;
import ssmga.vhbb_android.Utils.DownloadUtils;

public class CBPSDBAdapter extends RecyclerView.Adapter<CBPSDBAdapter.ViewHolder> {

    private final Activity mActivity;
    private final ArrayList<CBPSDBItem> mCBPSDBList;
    private final ArrayList<CBPSDBItem> mCBPSDBListFull;

    public CBPSDBAdapter (Activity activity, ArrayList<CBPSDBItem> cbpsdbList) {
        this.mActivity = activity;
        this.mCBPSDBList = cbpsdbList;
        this.mCBPSDBListFull = new ArrayList<>(mCBPSDBList);
    }

    @NonNull
    @Override
    public CBPSDBAdapter.ViewHolder onCreateViewHolder (@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cbpsdb, viewGroup, false);
        return new CBPSDBAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder (@NonNull CBPSDBAdapter.ViewHolder holder, int position) {
        CBPSDBItem currentItem = mCBPSDBList.get(position);

        String idID = currentItem.getID();
        String iconID = currentItem.getIcon0();
        String urlID = currentItem.getUrl();
        String typeID = currentItem.getType();
        String dataUrlID = currentItem.getDataUrl();

        holder.mTitle.setText(currentItem.getName());
        holder.mAuthor.setText(currentItem.getAuthor());
        holder.mType.setText(String.format("(%s)", currentItem.getTypeString()));

        if (iconID.equals("None")) {
            switch (typeID) {
                case CBPSDB.TYPE_VPK:
                case CBPSDB.TYPE_DATA:
                    iconID = VHBBAndroid.DEFAULT_ICON_URL;
                    holder.mIcon.setVisibility(View.VISIBLE);
                    break;
                case CBPSDB.TYPE_PLUGIN:
                    holder.mIcon.setVisibility(View.GONE);
                    break;
            }
        } else {
            holder.mIcon.setVisibility(View.VISIBLE);
        }

        Picasso.get().load(iconID).fit().centerInside().into(holder.mIcon);

        holder.mDownload.setOnClickListener(v -> {
            String filename = urlID.substring(urlID.lastIndexOf("/") + 1);
            String filenameExtension = filename.substring(filename.lastIndexOf(".")).toLowerCase();
            // If URL does not contain the filename
            if (!(filenameExtension.equals(".vpk") || filenameExtension.equals(".zip") || filenameExtension.equals(".suprx") || filenameExtension.equals(".skprx"))) {
                if (typeID.equals(CBPSDB.TYPE_PLUGIN)) {
                    if (currentItem.getOptions().equals(CBPSDB.OPTIONS_KERNEL))
                        filename = idID + ".skprx"; // Kernel plugin
                    else
                        filename = idID + ".suprx"; // User plugin
                } else if (typeID.equals(CBPSDB.TYPE_VPK)) {
                    filename = idID + ".vpk";
                } else {
                    filename = idID;
                    Toast.makeText(v.getContext(), v.getContext().getString(R.string.err_parse_file_extension), Toast.LENGTH_SHORT).show();
                }
            }

            DownloadUtils.VHBBDownloadManager(mActivity, v.getContext(), Uri.parse(urlID), filename);
        });

        holder.mDownloadData.setVisibility(!dataUrlID.equals("None") ? View.VISIBLE : View.GONE);

        if (!dataUrlID.equals("None")) holder.mDownloadData.setOnClickListener(v -> {
            String filename = urlID.substring(urlID.lastIndexOf("/") + 1);
            filename = filename.substring(0, filename.lastIndexOf(".")) + "-data.zip";

            DownloadUtils.VHBBDownloadManager(mActivity, v.getContext(), Uri.parse(dataUrlID), filename);
        });
    }

    @Override
    public int getItemCount () {
        return mCBPSDBList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle, mAuthor, mType;
        public ImageButton mDownload, mDownloadData;
        public ImageView mIcon;

        public ViewHolder (View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.textview_name);
            mAuthor = itemView.findViewById(R.id.textview_author);
            mType = itemView.findViewById(R.id.textview_type);
            mDownload = itemView.findViewById(R.id.download);
            mDownloadData = itemView.findViewById(R.id.downloadData);
            mIcon = itemView.findViewById(R.id.image);
        }
    }

    //region Filters

    public Filter getSearchFilter () {
        return mSearchFilter;
    }

    private final Filter mSearchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering (CharSequence constraint) {
            ArrayList<CBPSDBItem> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mCBPSDBListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (CBPSDBItem item : mCBPSDBListFull)
                    if (item.getName().toLowerCase().contains(filterPattern))
                        filteredList.add(item);
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults (CharSequence constraint, FilterResults results) {
            mCBPSDBList.clear();
            //noinspection unchecked
            mCBPSDBList.addAll((ArrayList<CBPSDBItem>)results.values);
            notifyDataSetChanged();
        }
    };

    public Filter getTypeFilter () {
        return mTypeFilter;
    }

    private final Filter mTypeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering (CharSequence constraint) {
            ArrayList<CBPSDBItem> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0 || constraint.equals(CBPSDB.TYPE_ALL)) {
                filteredList.addAll(mCBPSDBListFull);
            } else {
                for (CBPSDBItem item : mCBPSDBListFull)
                    if (item.getType().contentEquals(constraint))
                        filteredList.add(item);
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults (CharSequence constraint, FilterResults results) {
            mCBPSDBList.clear();
            //noinspection unchecked
            mCBPSDBList.addAll((ArrayList<CBPSDBItem>)results.values);
            notifyDataSetChanged();
        }
    };

    //endregion

}
