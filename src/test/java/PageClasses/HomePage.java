package PageClasses;

import Tools.BrowserBaseclass;
import Utils.Initializer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;
import java.util.*;

public class HomePage extends Initializer {
    By sortSelect = By.xpath("//*[@id=\"sort-select\"]");
    By tableHeader = By.cssSelector(".filter__link");
    By tableRow = By.cssSelector(".table-row");
    By tableDataNames = By.cssSelector(".data-name");
    By filterData = By.cssSelector("#filter-input");
    By tableData = By.cssSelector(".table-data");

    public boolean sortTableByColumn(int colIndex) {
        System.out.println ("--------Here: --------");
        return sortData(colIndex);
    }

    public boolean filterTableContents(List<String> filterWords) {
        Map <String, Boolean> results = filterData(filterWords);
        for (String key : results.keySet()){
            if (!results.get(key)){
                return false;
            }
        }
        return true;
    }

    public Map<String, Boolean> filterData(List<String> filterWords) {
        super.isElementPresent(filterData, 160);
        Map <String, Boolean> results = new HashMap<String, Boolean>();
        for (String filterKey : filterWords) {
            try {
                super.type(filterData, filterKey);
            } catch (Exception e) {
                results.put(filterKey, false);
                return results;
            }
            List<WebElement> tableDataItems = super.fetchAllElements(tableData, 5);
            if (tableDataItems != null) {
                for (WebElement ele : tableDataItems) {
                    if (ele.getText().contains(filterKey)) {
                        results.put(filterKey, true);
                        break;
                    }
                    else{
                        results.put(filterKey, false);
                    }
                }
            }
            else{
                results.put(filterKey, false);
            }
        }
        super.clearTextBox(filterData);
        return results;
    }

    public boolean sortData(int colIndex) {
        if (!super.isElementPresent(sortSelect, 160)){
            return false;
        }
        super.fetchElement(sortSelect, 60);
        List<WebElement> sortBoxOptions = super.selectBoxItemList(sortSelect, 60);
        String firstColumnName = super.getElementText(sortBoxOptions.get(colIndex), 60);
        super.selectComboBoxByIndex(sortSelect, 30, colIndex);
        int colNum = this.fetchIndexOfSortedColumn(firstColumnName);
        int noOfRows = this.noOfRowsInTable();
        List<String> itemsInAColumn = fetchItemsInATableColumn(noOfRows, colNum);
        boolean isSorted = isListSorted(itemsInAColumn);
        return isSorted;
    }

    public boolean isListSorted(List<String> items) {
        List<String> sortedList = new ArrayList<>();
        List<Double> tempList = new ArrayList<>();
        for (String ele : items) {
            sortedList.add(ele);
        }
        Collections.sort(sortedList, new Comparator<String>() {
            public int compare(String s1, String s2) {
                try {
                    long d1 = Long.parseLong(s1);
                    long d2 = Long.parseLong(s2);
                    return Long.compare(d1, d2);
                } catch (Exception e) {
                    return s1.toLowerCase().compareTo(s2.toLowerCase());
                }
            }
        });
        for (int i = 0; i < items.size(); i++) {
            if (!items.get(i).equalsIgnoreCase(sortedList.get(i))) {
                return false;
            }
        }
        return true;
    }

    public List<WebElement> fetchAllTableHeaders() {
        List<WebElement> tableHeaders = super.fetchAllElements(tableHeader, 30);
        return tableHeaders;
    }

    public int noOfRowsInTable() {
        List<WebElement> tableRows = super.fetchAllElements(tableRow, 30);
        return tableRows.size();
    }

    public int fetchIndexOfSortedColumn(String colName) {
        List<WebElement> headers = this.fetchAllTableHeaders();
        int index = 0;
        for (WebElement header : headers) {
            if (header.getText().equalsIgnoreCase(colName)) {
                index = headers.indexOf(header);
                break;
            }
        }
        return index;
    }

    public List<String> fetchItemsInATableColumn(int noOfRows, int colNum) {
        List<String> colItems = new ArrayList<>();
        String colItem;
        for (int i = 0; i < noOfRows; i++) {
            colItem = super.fetchElement(By.xpath("//*[@id=\"app\"]/div[3]/div[2]/div[" + (i + 1) + "]/div[" + (colNum + 1) + "]"), 30).getText();
            double val = 0;
            try {
                if (colItem.toLowerCase().endsWith("k")) {
                    colItem = colItem.replaceAll("K", "").replaceAll("k", "");
                    val = Double.parseDouble(colItem);
                    colItem = new BigDecimal(val * 1000).toPlainString();
                }
                if (colItem.toLowerCase().endsWith("m")) {
                    colItem = colItem.replaceAll("M", "").replaceAll("m", "");
                    val = Double.parseDouble(colItem);
                    colItem = new BigDecimal(val * 1000000).toPlainString();
                }
                colItems.add(colItem);
            } catch (NumberFormatException e) {
                colItems.add(colItem);
            }

        }
        return colItems;
    }


    public void findNoOfRowsInTable() {

    }

}
