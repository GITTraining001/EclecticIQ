#EclecticIQ Tests Suite

##Test Cases
1. **TestSortingOfFirstAndSecondColumn** : Sorts table by first and second column and validates (Adding and removing rows or columns to table will not need any change to code or data files)
2. **TestFilterHappyPath**: Filters by text given in src/test/resources/TestData.yaml (add more search terms to filter by more)
3. **TestFilterToFail**: Filters by text given in src/test/resources/TestData.yaml. Designed to fail by giving search text as a number

##Test Data
Test data is stored in src/test/resources/TestData.yaml file
####Structure of TestData.yaml
```
[TestMethodname]://Do not modify
    [keyname]://Do not modify
        - [arrayitem0] //Can Modify
        - [arrayitem1]
        .
        .
        .
        -[arrayitem(n)]

```
##How to Run
1. Open command prompt
2. Navigate to project root folder ```cd <path to project root>```
3. Type ```gradle test```