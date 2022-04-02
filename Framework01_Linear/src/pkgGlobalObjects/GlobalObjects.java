package pkgGlobalObjects;

import org.openqa.selenium.WebDriver;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

public class GlobalObjects {
    public static HashMap<String, Object> hmUserDataData      = new HashMap<>();
    public static HashMap<String, Object> hmGeneralProperties = new HashMap<>();
    public static HashMap<Integer, String> hmTCResult         = new HashMap<>();
    public static WebDriver oDriver                           = null;
    public static String sArySearchItem[]                     = null;

    public static final String sPropertiesFilePath             = "\\resource_data\\Ecommerce.properties";

    public static final String sUserDataExcelFilePath          = "\\resource_data\\User_data.xlsx";
    public static final String sUserDataExcelSheetName         = "User_info";

    public static final String sTestCaseExcelFilePath          = "\\resource_data\\Test_Scenario.xlsx";
    public static final String sTestCaseUserDataExcelSheetName= "Ecommerce";

    public static final String sCsvFilePath                     = "\\resource_data\\Ecommerce_search_items.csv";

    public static ArrayList<Integer> aryLstTCRowCnt              = new ArrayList();

    public static Process oProcess                               = null;

    public static Connection oConn                               = null;
}
