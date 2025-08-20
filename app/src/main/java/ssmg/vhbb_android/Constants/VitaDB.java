package ssmg.vhbb_android.Constants;

/**
 * Constants relating to VitaDB
 */
public class VitaDB {

    //region JSON
    public static final String JSON_NAME                = "name";
    public static final String JSON_ICON                = "icon";
    public static final String JSON_VERSION             = "version";
    public static final String JSON_AUTHOR              = "author";
    public static final String JSON_TYPE                = "type";
    public static final String JSON_DESCRIPTION         = "description";
    public static final String JSON_ID                  = "id";
    public static final String JSON_DATE                = "date";
    public static final String JSON_LONG_DESCRIPTION    = "long_description";
    public static final String JSON_DOWNLOADS           = "downloads";
    public static final String JSON_SOURCE              = "source";
    public static final String JSON_RELEASE_PAGE        = "release_page";
    public static final String JSON_SIZE                = "size";
    public static final String JSON_DATA_SIZE           = "data_size";
    public static final String JSON_URL                 = "url";
    public static final String JSON_DATA                = "data";
    public static final String JSON_SCREENSHOTS         = "screenshots";
    //endregion


    //region TYPE
    public static final int TYPE_ALL                = 0;
    public static final int TYPE_ORIGINAL_GAMES     = 1;
    public static final int TYPE_GAME_PORTS         = 2;
    public static final int TYPE_UTILISES           = 4;
    public static final int TYPE_EMULATORS          = 5;
    //endregion


    //region URL
    public static final String PARENT_URL               = "https://www.rinnegatamante.eu/vitadb";
    public static final String HOMEBREW_LIST_JSON_URL   = String.format("%s/list_hbs_json.php", PARENT_URL);
    public static final String PLUGIN_LIST_JSON_URL     = String.format("%s/list_plugins_json.php", PARENT_URL);
    public static final String INFO_PARENT_URL          = String.format("%s/#/info/", PARENT_URL);
    public static final String ICONS_PARENT_URL         = String.format("%s/icons/", PARENT_URL);
    //endregion


    //region USER-AGENT
    public static final String UA_REQUEST_HEADER        = "User-Agent";
    public static final String UA_REQUEST_VALUE         = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36";
    //endregion

}
