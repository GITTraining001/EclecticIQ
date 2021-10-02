package Tests;
import PageClasses.HomePage;
import TestDataProvider.TestDataProvider;
import Utils.Initializer;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Test1 extends HomePage {
    @Test
    public void TestSortingOfFirstAndSecondColumn(){
        System.out.print("------Classname: "+"-----------");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(super.sortTableByColumn(0), "Verify filtering by col 0");
        softAssert.assertTrue(super.sortTableByColumn(1), "Verify filtering by col 1");
        softAssert.assertAll();
    }
    @Test(dataProvider = "TestData", dataProviderClass = TestDataProvider.class)
    public void TestFilterHappyPath(Map<String, Object> obj){
        String className = super.methodName;

        LinkedHashMap<String, ArrayList> filterWordsMap = (LinkedHashMap<String, ArrayList>) obj.get(className);
        List<String> filterWords = (ArrayList<String>) filterWordsMap.get("filterwords");
        Assert.assertTrue(super.filterTableContents(filterWords));
    }
    @Test(dataProvider = "TestData", dataProviderClass = TestDataProvider.class)
    public void TestFilterToFail(Map<String, Object> obj){
        String className = super.methodName;
        LinkedHashMap<String, ArrayList> filterWordsMap = (LinkedHashMap<String, ArrayList>) obj.get(className);
        List<String> filterWords = (ArrayList<String>) filterWordsMap.get("filterwords");
        Assert.assertTrue(super.filterTableContents(filterWords));
    }
}
