package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("EP-100: Design the Open Cart App Login Page")
@Feature("F-101: design open cart login feature")
@Story("US-50: develop login core features: title, url, user is able to login")
public class LoginPageTest extends BaseTest {

	// to write test cases we use @Test annotation.
	// In framework we never write main method. TestNg will take care of it.
	// We never print anything in testng class. Better we print at page level not
	// test level.
	// without assertion test is not completed.
	
	@Description("login page title test...")
	@Owner("Naveen Automation Labs")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void loginPageTitleTest() {
		String actTitle = loginPage.getLoginPageTitle();
//		Assert.assertEquals(actTitle, "Account Login");
		ChainTestListener.log("login page title :" + actTitle);
		Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE);
	}

	@Description("login page url test...")
	@Owner("Naveen Automation Labs")
	@Severity(SeverityLevel.NORMAL)
	@Test
	public void loginPageURLTest() {
		String actURL = loginPage.getLoginPageURL();
//		Assert.assertTrue(actURL.contains("route=account/login"));
		ChainTestListener.log("login page url :" + actURL);
		Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_FRACTION_URL));
	}

	@Description("forgot password link exist test...")
	@Owner("Naveen Automation Labs")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void isForgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}

	@Description("login page header test...")
	@Owner("Naveen Automation Labs")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void isLoginPageHeaderExistTest() {
		Assert.assertTrue(loginPage.isheaderExist());
	}

	@Description("user is able to login to app...")
	@Owner("Naveen Automation Labs")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority=Integer.MAX_VALUE)
	public void loginTest() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}

//	@Test
//	public void isLoginPageImgHeaderExistTest() {
//		Assert.assertTrue(loginPage.isImgHeaderExist());
//	}
//
//	@Test
//	public void isLoginPageSearchBarEnabledTest() {
//		Assert.assertTrue(loginPage.isSearchBarEnabled());
//	}
//
//	@Test
//	public void isLoginPageWishListLinkExistTest() {
//		Assert.assertTrue(loginPage.isWishListLinkExist());
//	}
//
//	@Test
//	public void isLoginPageReturnsLinkExistTest() {
//		Assert.assertTrue(loginPage.isReturnsLinkExist());
//	}

}
