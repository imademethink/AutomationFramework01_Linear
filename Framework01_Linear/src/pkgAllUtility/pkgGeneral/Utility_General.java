package pkgAllUtility.pkgGeneral;
import pkgGlobalObjects.GlobalObjects;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Utility_General extends GlobalObjects {

    public void mGetCommandLineParam(String[] args){

        hmGeneralProperties.put("env", System.getProperty("env"));

        hmGeneralProperties.put("browserType", System.getProperty("browserType"));

        if(args.length <1) return;

        // read rest case ids e.g. TC1 TC4
        String sTestCaseIdToBeExecuted = "";
        for(int k=0; k < args.length; k++){
            sTestCaseIdToBeExecuted = sTestCaseIdToBeExecuted + args[k] + " ";
        }

        hmGeneralProperties.put("test_case_id", sTestCaseIdToBeExecuted);


//        // Pass on test case names e.g. Search, Payment to execute
//        for (int n=0; n < args.length; n ++){
//            // Prepare test bed before every test case
//            // e.g. navigate to base url + login
//            System.out.println(args[n]);
//            // Close-remove test bed after every test case
//        }
//
//        // Pass on test case priority e.g. high, medium, low
//        for (int n=0; n < args.length; n ++){
//            System.out.println(args[n]);
//        }
    }

    public void mInvokeMyWebApp(){
        try{
            oProcess = Runtime.getRuntime().exec(
                    "cmd /d start D:\\TonyStark\\SimpleWebApp\\demo_site_runner.bat");
        }catch(Exception eBatchFile){
            eBatchFile.printStackTrace();
        }
    }

    public void mCloseMyWebApp(){
        try{
            oProcess.destroy();
        }catch(Exception eBatchFile){
            eBatchFile.printStackTrace();
        }
    }

    public void mMakeDbConnection(){
        try{
            String sDbUrl = "jdbc:sqlite:D://TonyStark//PlentyUtilities//data_input//format11_excel//simple_sqlite//Tony.db";
            Class.forName("org.sqlite.JDBC");
            oConn         = DriverManager.getConnection(sDbUrl);
        }catch (SQLException | ClassNotFoundException eDb) { System.out.println(eDb.getMessage()); }
    }

    public void mCloseDbConnection(){
        try{
            oConn.close();
        }catch (SQLException eDb) { System.out.println(eDb.getMessage()); }
    }

}
