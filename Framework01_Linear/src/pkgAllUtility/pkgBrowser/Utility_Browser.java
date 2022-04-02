package pkgAllUtility.pkgBrowser;

import org.junit.Assert;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.chrome.ChromeDriver;
import pkgGlobalObjects.GlobalObjects;

public class Utility_Browser extends GlobalObjects{

    public void mLaunchBrowser(){
        if (! hmGeneralProperties.get("browserType").toString().equals("chrome")){return;}

        // currently supporting chrome driver
        // please insert appropriate code for the compatible browser on your system
        try{oDriver = new ChromeDriver();}catch (Exception e){}
        System.out.println("Log:: Chrome browser launched");
    }

    public void mQuitBrowser(){
        // currently supporting chrome driver
        if (null != oDriver) oDriver.quit();
        System.out.println("Log:: Chrome browser quit");
    }

}
