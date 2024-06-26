package TestCases;

import DriverFactory.DriverFactory;
import LIsteners.IInvokedListeners;
import LIsteners.ITestResult;
import Pages.P01_LoginPage;
import Pages.P02_HomePage;
import Utilities.DataUtility;
import Utilities.LogUtility;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.Duration;

import static DriverFactory.DriverFactory.getDriver;
import static DriverFactory.DriverFactory.setUpDriver;

@Listeners({ITestResult.class, IInvokedListeners.class})
public class TC02_AddProductsToCartTest {

    @BeforeMethod
    public void setUp() {
        setUpDriver(DataUtility.getDataFromFileProperty("dataEnv", "browserName"));
        getDriver().get(DataUtility.getDataFromFileProperty("dataEnv", "baseUrl"));
        LogUtility.info("open base url");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void addProductsToCart() {
        new P01_LoginPage(getDriver())
                .enterUserName(DataUtility.getJsonData("userDataLogin", "userName"))
                .enterPasswordName(DataUtility.getJsonData("userDataLogin", "Password"))
                .loginButton().addAllProductsToCart();
        Assert.assertTrue(new P02_HomePage(getDriver()).comparingProdSelectedWithCountOnCart());


    }


    @Test(priority = 1)
    public void addRandomProducts() {
        new P01_LoginPage(getDriver())
                .enterUserName(DataUtility.getJsonData("userDataLogin", "userName"))
                .enterPasswordName(DataUtility.getJsonData("userDataLogin", "Password"))
                .loginButton().addProductsRandom(2, 6);
        Assert.assertTrue(new P02_HomePage(getDriver()).comparingProdSelectedWithCountOnCart());

    }

    @Test
    public void goTOCartPage() {
        new P01_LoginPage(getDriver())
                .enterUserName(DataUtility.getJsonData("userDataLogin", "userName"))
                .enterPasswordName(DataUtility.getJsonData("userDataLogin", "Password"))
                .loginButton()
                .addProductsRandom(3, 6)
                .clickingOnCartIcon();
        Assert.assertTrue(new P02_HomePage(getDriver()).verifyCartUrl(DataUtility.getDataFromFileProperty("dataEnv", "cartUrl")));

    }


    @AfterMethod
    public void rearDown() {
        DriverFactory.tearDown();
    }


}
