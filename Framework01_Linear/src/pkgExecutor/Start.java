package pkgExecutor;

import pkgAllUtility.pkgGeneral.Utility_General;
import pkgAllUtility.pkgFileHandling.Utility_Filehandler;
import pkgAllUtility.pkgBrowser.Utility_Browser;

public class Start {
    public static void main(String[] args) {
        System.out.println("======Linear framework execution begin======");

        // explicit calls to init and tear down
        // test case sequencing control missing
        // test case priority control missing
        // dependent jar files to included manually - which is always error prone
        // parallel test execution missing
        // test case filtering missing
        // tests written in excel and used as steps (2 step process)
        // report generation calls are explicit
        // report generation basic (excel)
        // error screenshot in reports missing
        // logging mechanism very poor
        // before/ after action for test case/ suite missing
        // test case grouping missing
        // command line compilation and running not so easy
        // parameter/ data passing mechanism has limitations
        // individual test failure recovery mechanism - try/ catch

        GlobalInit(args);

        ExecuteTests();

        GenerateReport();

        GlobalTearDown();

        System.out.println("======Linear framework execution stop======");
    }



    public static void  GlobalInit(String[] args){
        // Get environment name or global params (e.g. user credentials) from command line
        System.out.println("Log: Reading command line params TC1 TC2 TC4");
        System.out.println("Log: Reading command line system properties -Denv -DbrowserType");
        new Utility_General().mGetCommandLineParam(args);

        // Read properties file
        System.out.println("Log: Reading properties file - application url, valid user credentials etc");
        new Utility_Filehandler().mProperties_ReadAllPropertiesIntoHashMap();

        // Read user data from excel file
        System.out.println("Log: Reading excel file - user profile data");
        new Utility_Filehandler().mExcel_ReadData2DAry();

        // Read e-commerce search items from csv file
        System.out.println("Log: Reading csv file - e-commerce search items");
        new Utility_Filehandler().mCSV_ReadRowContent(0);

        // Make database connection
        System.out.println("Log: Making required environment database connection");
        new Utility_General().mMakeDbConnection();

        // Make website up
        System.out.println("Log: Making required environment web-app up");
        new Utility_General().mInvokeMyWebApp(); // or docker-compose up

        // Launch Chrome browser instance
        System.out.println("Log: Launching web browser");
        new Utility_Browser().mLaunchBrowser();
    }

    public static void  ExecuteTests(){
        new Executor().TCExecute();
    }

    public static void  GenerateReport(){
        new Executor().TCResultUpdate();
    }

    public static void  GlobalTearDown(){

        // Shutdown Chrome browser instance
        System.out.println("Log: Shutting down web browser");
        new Utility_Browser().mQuitBrowser();

        // File handler, temporary data clearance
            System.out.println("Log: Closing properties file");
            System.out.println("Log: Closing excel file");
            System.out.println("Log: Closing csv file");
        new Utility_Filehandler().mProperties_Clear();
        new Utility_Filehandler().mExcel_ClearUserData();

        // Close Db connection
        System.out.println("Log: Making required environment Db connection close");
        new Utility_General().mCloseDbConnection();

        // Shut down web-application
        System.out.println("Log: Making required environment web-app down");
        new Utility_General().mCloseMyWebApp(); // or docker-compose down

        System.out.println("Log: Prepare test report");
    }
}
