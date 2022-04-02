package pkgAllUtility.pkgFileHandling;
import org.junit.Assert;
import pkgGlobalObjects.GlobalObjects;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class Utility_Filehandler extends GlobalObjects {

    public void mProperties_ReadAllPropertiesIntoHashMap(){
        String sFilePath = System.getProperty("user.dir") + sPropertiesFilePath;

        File oFile              = new File(sFilePath);
        if (!oFile.exists()) {
            Assert.fail("UtilLog: Given properties file does not exists " + sFilePath);
        }

        Properties oProp = new Properties();
        try{
            oProp.load(new FileInputStream(sFilePath));
            Set<String> stAllPropertyNames = oProp.stringPropertyNames();
            for (String sOnePropertyName : stAllPropertyNames) {
                hmGeneralProperties.put(sOnePropertyName,oProp.getProperty(sOnePropertyName));
            }
        }catch(FileNotFoundException exFile){
            Assert.fail("UtilLog: Given properties file not found by FileInputStream " + sFilePath);
        }catch(IOException exFile){
            Assert.fail("UtilLog: Given properties file reading error " + sFilePath);
        }
    }

    public void mExcel_ReadData2DAry(){
        String sExcelFile = System.getProperty("user.dir") + sUserDataExcelFilePath;
        String sSheetName = sUserDataExcelSheetName;

        try {
            FileInputStream oInStream = new FileInputStream(new File(sExcelFile));
            XSSFWorkbook oWrkBook     = new XSSFWorkbook(oInStream);
            XSSFSheet oWrkSheet       = oWrkBook.getSheet(sSheetName);

            for (int r=0; r < oWrkSheet.getLastRowNum(); r++){
                if( r != 0){continue;}
                XSSFRow oRow          = oWrkSheet.getRow(r);
                // filling key and value in hashmap
                hmUserDataData.put(oRow.getCell(0).toString(),oRow.getCell(1).toString());
            }
            oWrkBook.close();
            oInStream.close();
        }catch(IOException eXl){
            Assert.fail("UtilLog: Given excel file parsing error");
        }
    }

    public void mCSV_ReadRowContent(int nRequiredLineCnt){
        String sFilePath = System.getProperty("user.dir") + sCsvFilePath;

        File oFile              = new File(sFilePath);
        if (!oFile.exists()) {
            Assert.fail("UtilLog: Given csv file does not exists " + sFilePath);
        }

        BufferedReader oBufRd   = null;
        String sOneLine         = null;
        int  nActualLineNo      = -1;
        try{
            oBufRd = new BufferedReader(new FileReader(oFile));
            while (  (sOneLine = oBufRd.readLine()) != null ){
                nActualLineNo ++;
                if(nRequiredLineCnt == nActualLineNo){ break; }
            }
            sArySearchItem = sOneLine.split(",");
            oBufRd.close();
        }catch(FileNotFoundException exFile){
            Assert.fail("UtilLog: Given csv file not found by buffered reader " + sFilePath);
        }catch(IOException exFile){
            Assert.fail("UtilLog: Given csv file line reading error " + sFilePath);
        }
        if(nRequiredLineCnt > nActualLineNo){
            Assert.fail("UtilLog: Given csv file line index not found " + sFilePath);
        }
    }

    public ArrayList<Integer> mExcel_GetRowNum_UniqueId(String sUniqueId){
        String sExcelFile = System.getProperty("user.dir") + sTestCaseExcelFilePath;
        String sSheetName = sTestCaseUserDataExcelSheetName;

        ArrayList<Integer> aryLstRowNum =  new ArrayList();
        try {
            FileInputStream oInStream = new FileInputStream(new File(sExcelFile));
            XSSFWorkbook oWrkBook     = new XSSFWorkbook(oInStream);
            XSSFSheet oWrkSheet       = oWrkBook.getSheet(sSheetName);
            int nRowIndex             = -1;

            for (int k=0; k<= oWrkSheet.getLastRowNum(); k++){
                if(oWrkSheet.getRow(k).getCell(0).toString().matches(sUniqueId)){
                    aryLstRowNum.add(k);
                }
            }

            oWrkBook.close();
            oInStream.close();
        }catch(IOException eXl){
            Assert.fail("UtilLog: Given excel file parsing error");
        }
        return aryLstRowNum;
    }

    public HashMap<String, Object> mExcel_ReadDataRow(int nRowIndex){
        String sExcelFile = System.getProperty("user.dir") + sTestCaseExcelFilePath;
        String sSheetName = sTestCaseUserDataExcelSheetName;

        HashMap<String, Object> hmDataRow = new HashMap<String, Object>();
        try {
            FileInputStream oInStream = new FileInputStream(new File(sExcelFile));
            XSSFWorkbook oWrkBook     = new XSSFWorkbook(oInStream);
            XSSFSheet oWrkSheet       = oWrkBook.getSheet(sSheetName);

            for (int k=0; k< oWrkSheet.getRow(nRowIndex).getLastCellNum(); k++){
                hmDataRow.put(
                        oWrkSheet.getRow(0).getCell(k).toString(), // key
                        oWrkSheet.getRow(nRowIndex).getCell(k)              // value
                );
            }
            oWrkBook.close();
            oInStream.close();
        }catch(IOException eXl){
            Assert.fail("UtilLog: Given excel file parsing error");
        }
        return hmDataRow;
    }

    public static XSSFCell mExcel_SetCellValue(int nRowNum, int nColNum, Object objCellValue){

        String sExcelFile = System.getProperty("user.dir") + sTestCaseExcelFilePath;
        String sSheetName = sTestCaseUserDataExcelSheetName;

        XSSFCell oRequiredCellValue = null;
        try {
            FileInputStream oInStream  = new FileInputStream(new File(sExcelFile));
            XSSFWorkbook oWrkBook      = new XSSFWorkbook(oInStream);
            XSSFSheet oWrkSheet        = oWrkBook.getSheet(sSheetName);
            boolean bFreshWorksheet    = false;
            if(null == oWrkSheet){
                oWrkBook.createSheet(sSheetName);
                oWrkSheet = oWrkBook.getSheet(sSheetName);
                oWrkSheet.createRow(nRowNum);
                oWrkSheet.getRow(nRowNum).createCell(nColNum);
                bFreshWorksheet = true;
            }
            if(nRowNum > oWrkSheet.getLastRowNum() && !bFreshWorksheet){
                oWrkSheet.createRow(nRowNum);
            }
            if(nColNum >= oWrkSheet.getRow(nRowNum).getLastCellNum() && !bFreshWorksheet){
                oWrkSheet.getRow(nRowNum).createCell(nColNum);
            }
            oRequiredCellValue = oWrkSheet.getRow(nRowNum).getCell(nColNum);

            if( (objCellValue instanceof Integer ) || (objCellValue instanceof Long ) ){
                oRequiredCellValue.setCellType(CellType.NUMERIC);
                oRequiredCellValue.setCellValue((int)objCellValue); // int or long
            } else
            if( (objCellValue instanceof Float ) || (objCellValue instanceof Double ) ){
                oRequiredCellValue.setCellType(CellType.NUMERIC);
                oRequiredCellValue.setCellValue((float)objCellValue); // float or double
            } else
            if(    (objCellValue instanceof String) && ( ! objCellValue.toString().isEmpty())   ){
                oRequiredCellValue.setCellType(CellType.STRING);
                oRequiredCellValue.setCellValue(objCellValue.toString());
            } else
            if(    (objCellValue instanceof String) && ( objCellValue.toString().isEmpty())     ){
                oRequiredCellValue.setCellType(CellType.BLANK);
                oRequiredCellValue.setCellValue("");
            } else
            if (objCellValue instanceof Boolean){
                oRequiredCellValue.setCellType(CellType.BOOLEAN);
                oRequiredCellValue.setCellValue((boolean)objCellValue);
            }
            // also need to add for objCellValue instanceof Date

            oInStream.close();
            FileOutputStream oOutStream = new FileOutputStream(new File(sExcelFile));
            oWrkBook.write(oOutStream);
            oWrkBook.close();
            oOutStream.close();
        }catch(IOException eXl){
            eXl.printStackTrace();
            Assert.fail("UtilLog: Given excel file parsing error");
        }
        return oRequiredCellValue;
    }

    public void mProperties_Clear(){
        hmGeneralProperties.clear();
    }

    public void mExcel_ClearUserData(){
        hmUserDataData.clear();
    }

}
