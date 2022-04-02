package pkgExecutor;

import pkgGlobalObjects.GlobalObjects;
import pkgAllUtility.pkgFileHandling.Utility_Filehandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class Executor extends GlobalObjects {

    public void TCExecute(){
        // get list of test id
        String sTestCaseIds      = hmGeneralProperties.get("test_case_id").toString();
        String [] sAryTestCaseId = sTestCaseIds.split(" ");

        for(String sOneTC : sAryTestCaseId){
            System.out.println("\nLog:: Executing " + sOneTC);
            aryLstTCRowCnt =  new Utility_Filehandler().mExcel_GetRowNum_UniqueId(sOneTC);
            System.out.println("Log:: " + sOneTC + " has total " + aryLstTCRowCnt.size() + " steps");

            for (int k=0; k < aryLstTCRowCnt.size();  k++){
                System.out.println("Log:: Executing step " +
                        new Utility_Filehandler().mExcel_ReadDataRow(aryLstTCRowCnt.get(k)).get("Test_step").toString() + " " +
                        new Utility_Filehandler().mExcel_ReadDataRow(aryLstTCRowCnt.get(k)).get("Step_description").toString()
                );
                // Actual automarion execution code

                // Updating temporary results (randomly Pass or Fail)
                String sResult = "Pass";
                if( ! new Random().nextBoolean()){ sResult = "False";}
                hmTCResult.put(aryLstTCRowCnt.get(k),sResult);
            }
            System.out.println();
        }
    }


    public void TCResultUpdate(){
        for (HashMap.Entry<Integer,String> eachEntry : hmTCResult.entrySet()){
            new Utility_Filehandler().mExcel_SetCellValue(
                    Integer.parseInt(eachEntry.getKey().toString()),
                    5,
                    eachEntry.getValue());
        }
    }

}
