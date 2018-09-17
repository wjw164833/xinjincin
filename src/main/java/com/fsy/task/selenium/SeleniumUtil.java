package com.fsy.task.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * selenium自动化支持程度不高  , 故不采用
 */
public class SeleniumUtil {
    public String getSchoolToken(String username ,String password){
        // TODO Auto-generated method stub
        //如果测试的浏览器没有安装在默认目录，那么必须在程序中设置
        //bug1:System.setProperty("webdriver.chrome.driver", "C://Program Files (x86)//Google//Chrome//Application//chrome.exe");
        //bug2:System.setProperty("webdriver.chrome.driver", "C://Users//Yoga//Downloads//chromedriver_win32//chromedriver.exe");
        //System.setProperty("webdriver.chrome.driver", "D://tanzhenTest//chromedriver_win32//chromedriver.exe");
        FirefoxDriver firefoxDriver = new FirefoxDriver();
//        String username = "gyu-160308401009";
//
//        String passowrd = "123456";

//        String loginDomain = "gyu";


//		WebDriver driver = new ChromeDriver();

        firefoxDriver.get("http://sso.njcedu.com");
        // 获取 网页的 title
        //System.out.println("The testing page title is: " + firefoxDriver.getTitle());

        WebDriverWait webDriverWait=new WebDriverWait(firefoxDriver,5);


        WebElement e= firefoxDriver.findElement(By.id("password"));

        e.clear();

        //System.out.println("清空密码框内容----------------------------------------------------------------------------------------------------------");

        e.sendKeys(password);

        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("loginId"))).sendKeys(username);

        WebElement e1= firefoxDriver.findElement(By.id("loginBtn"));

        e1.click();

        String schoolToken = firefoxDriver.manage().getCookieNamed("schoolToken").getValue();


        firefoxDriver.quit();
//        firefoxDriver.close();

        return schoolToken;
    }
}
