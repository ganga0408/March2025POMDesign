package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.ExcelUtil;

public class ProductInfoTest extends BaseTest {
	
	//BT --> BC --> @Test
	
	@BeforeClass
	public void prodInfoSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@DataProvider
	public Object[][] getProducts() {
		return new Object[][] {
			{"macbook", "MacBook Pro"},
			{"samsung", "Samsung SyncMaster 941BW"},
			{"imac", "iMac"},
			{"canon", "Canon EOS 5D"}
		};
	}
	
	@DataProvider
	public Object[][] getProductTestData() {
		return ExcelUtil.getTestData("product");
	}
	
	
//	@Test
//	public void searchTest() {
//		searchResultsPage = accPage.doSearch("macbook");
//		productInfoPage = searchResultsPage.selectProduct("MacBook Pro");
//		String actHeader = productInfoPage.getProductHeader();
//		Assert.assertEquals(actHeader, "MacBook Pro");
//	}
	
	@Test(dataProvider = "getProductTestData")
	public void productHeaderTest(String searchKey, String productName) {
		searchResultsPage = accPage.doSearch(searchKey);
		productInfoPage = searchResultsPage.selectProduct(productName);
		String actHeader = productInfoPage.getProductHeader();
		Assert.assertEquals(actHeader, productName);
	}
	
	@DataProvider
	public Object[][] getProductImages() {
		return new Object[][] {
			{"macbook", "MacBook Pro", 4},
			{"samsung", "Samsung SyncMaster 941BW", 1},
			{"canon", "Canon EOS 5D", 3}
		};

	}
	
//	@Test
//	public void productImagesCountTest() {
//		searchResultsPage = accPage.doSearch("samsung");
//		productInfoPage = searchResultsPage.selectProduct("Samsung SyncMaster 941BW");
//		int actImagesCount = productInfoPage.getProductImages();
//		Assert.assertEquals(actImagesCount, 1);
//	}
	
	@Test(dataProvider="getProductImages")
	public void productImagesCountTest(String searchKey, String productName, int imageCount) {
		searchResultsPage = accPage.doSearch(searchKey);
		productInfoPage = searchResultsPage.selectProduct(productName);
		int actImagesCount = productInfoPage.getProductImages();
		Assert.assertEquals(actImagesCount, imageCount);
	}
	
	@Test
	public void productInfoTest() {
		searchResultsPage = accPage.doSearch("macbook");
		productInfoPage = searchResultsPage.selectProduct("MacBook Pro");
		Map<String, String> productDataMap = productInfoPage.getProductData();
		
		SoftAssert softAssert = new SoftAssert();
		
		softAssert.assertEquals(productDataMap.get("productname"), "MacBook Pro");
		
		softAssert.assertEquals(productDataMap.get("Brand"), "Apple");
		softAssert.assertEquals(productDataMap.get("Availability"), "Out Of Stock");
		softAssert.assertEquals(productDataMap.get("Reward Points"), "800");
		softAssert.assertEquals(productDataMap.get("Product Code"), "Product 18");
		
		softAssert.assertEquals(productDataMap.get("productprice"), "$2,000.00");
		softAssert.assertEquals(productDataMap.get("extaxprice"), "$2,000.00");
		
		softAssert.assertAll();
			
	}
	
	//AAA pattern --> Arrange Act Assert
	//Arrange --> pre requisition of product
	//Act --> Selection of product 
	//then Assert
	//only one hard assert in test case
	//but we can have multiple soft assertions in a single test case.
	//In this pgm we have written 7 assertions. This is not good practice.
	//Assert class is hard assertion.
	
	//Hard Assert means the program gets terminated when the assert gets failed. Only one assert has to be there in a single test case.
	//Soft Assert means the program proceeds further even the soft assert gets failed. we can have multiple soft asserts in one single test case.
	
	//HashMap doesn't maintain the data in the same order.
	//Linked HashMap maintains the data in the same order.
	//TreeMap for the sorting order for the keys.

}
