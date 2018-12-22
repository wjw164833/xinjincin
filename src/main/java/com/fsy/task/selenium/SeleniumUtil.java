package com.fsy.task.selenium;

import com.fsy.task.domain.Exercise;
import com.fsy.task.domain.User;
import com.fsy.task.util.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Map;

/**
 * selenium自动化支持程度不高  , 故不采用
 */
public class SeleniumUtil {

    public static void main(String[] args) {
        //yau-1020616014027 123456
        User user = new SeleniumUtil().getUser("yau-1020616014027" , "123456");
        return;
    }
    public User getUser(String username , String password){
        //System.setProperty("webdriver.gecko.driver","F:\\Mozilla Firefox\\geckodriver.exe");
        System.setProperty("webdriver.gecko.driver","/Users/fushiyong/Downloads/geckodriver");


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

        WebDriverWait webDriverWait=new WebDriverWait(firefoxDriver,5);

        WebElement agreeButton = firefoxDriver.findElement(By.className("agree_btn"));

        agreeButton.click();


        WebElement e= firefoxDriver.findElement(By.id("password"));

        e.clear();

        e.sendKeys(password);

        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("loginId"))).sendKeys(username);

        WebElement e1= firefoxDriver.findElement(By.id("loginBtn"));

        e1.click();

        String schoolToken = null;
//        int count = 0;


        do{

//            System.out.println("获取token第" + count  + "次 ");
            try {
                schoolToken = firefoxDriver.manage().getCookieNamed("schoolToken").getValue();
                Thread.sleep(Constant.TIMEOUT * 1000);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }catch (Exception e3){
                e3.printStackTrace();
            }
        }while(schoolToken == null);



//        System.out.println("获取到token:" + schoolToken );

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
        String userSchoolId = firefoxDriver.getPageSource();


        String nickName = firefoxDriver.findElementByXPath("/html/body/span[1]/div/ul/li[2]/div[4]").getText().split(",")[1];

        firefoxDriver.quit();
//        firefoxDriver.close();

        User user =  User.builder()
                .username(username)
                .password(password)
                .userId(getUserId(userSchoolId))
                .schoolId(getSchoolId(userSchoolId))
                .schoolCode(username.split("-")[0])
                .schoolToken(schoolToken)
                .nickName(nickName)
                .build();
        return user;
    }



    private String getUserId(String resp) {
        String userIdPattern = "arr[\"lUserId\"]=";
        int userIndexStart = resp.indexOf(userIdPattern);
        int userIndexEnd = resp.indexOf(";"  , userIndexStart);
        return resp.substring(userIndexStart +userIdPattern.length()  , userIndexEnd  ) ;
    }

    private String getSchoolId(String resp) {
        String userIdPattern = "arr[\"lSchoolId\"]=";
        int schoolIndexStart = resp.indexOf(userIdPattern);
        int schoolIndexEnd = resp.indexOf(";"  , schoolIndexStart);
        return resp.substring(schoolIndexStart  +userIdPattern.length()  , schoolIndexEnd  ) ;
    }
}
