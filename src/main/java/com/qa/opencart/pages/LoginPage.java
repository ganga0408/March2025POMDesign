package com.qa.opencart.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.Factory.DriverFactory;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;



public class LoginPage {
	
	//private By locators : page elements
	//public constructor of the page class
	//public page methods/actions: what is the feature functionality of the page.
	//Every page will have its own private webDriver also
	
	private WebDriver driver;//null
	private ElementUtil eleUtil;
	
	//private By locators:
	private final By emailID = By.id("input-email");
	private final By password = By.id("input-password");
	private final By loginBtn = By.xpath("//input[@value='Login']");
	private final By forgotPwdLink = By.linkText("Forgotten Password");
	private final By header = By.tagName("h2");
	private final By registerLink = By.linkText("Register");
//	private final By imgHeader = By.xpath("//img[@title='naveenopencart']");
//	private final By searchBar = By.name("search");
//	private final By wishListLink = By.linkText("Wish List");
//	private final By returnsLink = By.linkText("Returns");
	
	private final By loginErrorMessg = By.cssSelector("div.alert.alert-danger.alert-dismissible");
	
	private static final Logger log = LogManager.getLogger(LoginPage.class);

	//public Constructor:
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	//public page methods:
	@Step("getting login page title...")
	public String getLoginPageTitle() {
//		String title = driver.getTitle();
//		String title = eleUtil.waitForTitleIs("Account Login", 5);
		String title = eleUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, AppConstants.DEFAULT_SHORT_WAIT);
//		System.out.println("login page title :" + title);
		log.info("login page title :" + title);
		return title;
	}
	
	@Step("getting login page url...")
	public String getLoginPageURL() {
//		String url = driver.getCurrentUrl();
//		String url = eleUtil.waitForURLContains("route=account/login", 5);
		String url = eleUtil.waitForURLContains(AppConstants.LOGIN_PAGE_FRACTION_URL, AppConstants.DEFAULT_SHORT_WAIT);
//		System.out.println("login page url :" + url);
		log.info("login page url :" + url);
		return url;
	}
	
	@Step("forgot pwd link exist...")
	public boolean isForgotPwdLinkExist() {
//		return driver.findElement(forgotPwdLink).isDisplayed();
		return eleUtil.isElementDisplayed(forgotPwdLink);
	}
	
	//a private variable/By locator of a class is used in public method in java is called as Encapsulation.
	//Above method is an example of encapsulation.
	
	@Step("page header exist...")
	public boolean isheaderExist() {
//		return driver.findElement(header).isDisplayed();
		return eleUtil.isElementDisplayed(header);
	}
	
	@Step("login with username: {0} and password: {1}")
	public AccountsPage doLogin(String appUsername, String appPassword) {
//		System.out.println("Application credentials :" + appUsername + " : " + appPassword);
//		log.info("Application credentials :" + appUsername + " : " + appPassword);
		log.info("Application credentials :" + appUsername + " : " + "********");
//		driver.findElement(emailID).sendKeys(appUsername);
//		driver.findElement(password).sendKeys(appPassword);
//		driver.findElement(loginBtn).click();
//		eleUtil.waitForElementVisible(emailID, 10).sendKeys(appUsername);
		eleUtil.waitForElementVisible(emailID, AppConstants.DEFAULT_MEDIUM_WAIT).sendKeys(appUsername);
		eleUtil.doSendKeys(password, appPassword);
		eleUtil.doClick(loginBtn);
		return new AccountsPage(driver);
	}
	
	@Step("login with incorrect username: {0} and password: {1}")
	public boolean doLoginWithInvalidCredentials(String invalidUN, String invalidPWD) {
		log.info("Invalid Application credentials :" + invalidUN + " : " + invalidPWD);		
//		eleUtil.waitForElementVisible(emailID, AppConstants.DEFAULT_MEDIUM_WAIT).sendKeys(invalidUN);
		WebElement emailEle = eleUtil.waitForElementVisible(emailID, AppConstants.DEFAULT_MEDIUM_WAIT);
		emailEle.clear();
		emailEle.sendKeys(invalidUN);
		eleUtil.doSendKeys(password, invalidPWD);
		eleUtil.doClick(loginBtn);
		String errorMessg = eleUtil.doElementGetText(loginErrorMessg);
		log.info("invalid creds error message :" + errorMessg);
		if(errorMessg.contains(AppConstants.LOGIN_BLANK_CREDS_MESSG)) {
			return true;
		}
		else if(errorMessg.contains(AppConstants.LOGIN_INVALID_CREDS_MESSG)) {
			return true;
		}
		return false;
	}
		
	@Step("navigating to register page...")
	public RegisterPage navigateToRegisterPage() {
		log.info("trying to navigating to register page...");
		eleUtil.waitForElementVisible(registerLink, AppConstants.DEFAULT_SHORT_WAIT).click();
		return new RegisterPage(driver);
	}
	
	// 2 IMP rules:
	//1. Login page is not responsible for anything related to test cases or assertions.(should not write)
	//Login page says we will use our locators and we will give you something. we will return the behaviour of the page.
	//Page class is responsible only for current behaviour.
	//LoginPage Test is responsible for to check it is correct or not.
	
	//2. In the testing class, we should not write any Se code, any webdriver methods. Any driver related code.
	
	//Creator of Page Object Model (POM) is Martin Fowler
	//Main creator of Se is Simon Stewart
	
//	public boolean isImgHeaderExist() {
////		return driver.findElement(imgHeader).isDisplayed();
//		return eleUtil.isElementDisplayed(imgHeader);
//	}
//	
//	public boolean isSearchBarEnabled() {
////		return driver.findElement(searchBar).isEnabled();
//		return eleUtil.isElementEnabled(searchBar);
//	}
//	
//	public boolean isWishListLinkExist() {
////		return driver.findElement(wishListLink).isDisplayed();
//		return eleUtil.isElementDisplayed(wishListLink);
//	}
//	
//	public boolean isReturnsLinkExist() {
////		return driver.findElement(returnsLink).isDisplayed();
//		return eleUtil.isElementDisplayed(returnsLink);
//	}

}
