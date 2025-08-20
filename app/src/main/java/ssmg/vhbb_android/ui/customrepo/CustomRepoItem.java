package ssmg.vhbb_android.ui.customrepo;

import ssmg.vhbb_android.BaseItem;

/**
 * Custom Repository item
 */
public class CustomRepoItem extends BaseItem {

    private final String DataFilename;
    private final String DataUrl;
    private final String Date;

    public CustomRepoItem (String name, String filename, String dataFilename, String version, String author, String desc, String url, String dataUrl, String date) {
        super(name, filename, version, author, desc, url);
        this.DataFilename = dataFilename;
        this.DataUrl = dataUrl;
        this.Date = date;
    }

    public String getDataFilename () {
        return DataFilename;
    }

    public String getDataUrl () {
        return DataUrl;
    }

    public String getDate () {
        return Date;
    }

}
