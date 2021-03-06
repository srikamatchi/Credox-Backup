package selenium.com.day2;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;
 
public class Ebay_Select_Webelement_Ex {
	
	public static WebDriver driver; 
	public static int iBroType = 1; // 1 - chrome, 2 - FF, 3 - IE,4 - HTMLUnitDriver
	public static String sURL = "https://www.ebay.in/";
	

	public static void main(String[] args) throws Exception {
		browserInvoke();
		browsercustomization();
		navigateURL();
		getPageInfo();
		ebaySearch("iPhone 7", "Mobile Phones");
		getSearchResult();
		getPageInfo();
	//	closeBrowser();
		

	}
	
	public static void browserInvoke() {
		
		switch (iBroType) {
		case 1:
			System.out.println("User Option is : "+iBroType+" , So Invoking Chrome Browser");
			System.setProperty("webdriver.chrome.driver", "./Driver/chromedriver.exe");
			driver = new ChromeDriver();
			break;
		case 2:
			System.out.println("User Option is : "+iBroType+" , So Invoking FF Browser");
			System.setProperty("webdriver.gecko.driver", "./Driver/geckodriver.exe");
			driver = new FirefoxDriver();
			break;
		case 3:
			System.out.println("User Option is : "+iBroType+" , So Invoking Edge Browser");
			System.setProperty("webdriver.edge.driver", "./Driver/MicrosoftWebDriver.exe");
			driver = new EdgeDriver();
			break;
		case 4:
			System.out.println("User Option is : "+iBroType+" , So Invoking Headless Browser");
			driver = new HtmlUnitDriver();
			break;
		default:
			System.out.println("User Option is wrong: "+iBroType+" , So Invoking Default Chrome Browser");
			System.setProperty("webdriver.chrome.driver", "./Driver/chromedriver.exe");
			driver = new ChromeDriver();
			break;
		}
		
	}
	
	public static void browsercustomization() {
		if(iBroType==2) {
			driver.manage().deleteAllCookies();
		}else {
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
		}
	}
	
	public static void navigateURL() {
		driver.get(sURL);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	}
	
	public static void getPageInfo() {
		System.out.println("Page Tile is : "+driver.getTitle());
		System.out.println("Page Current URL is : "+driver.getCurrentUrl());
	}
	
	public static void closeBrowser() {
		int iSize = 	driver.getWindowHandles().size();
		System.out.println("Total Window Opened is : "+iSize);
		if(iSize==1) {
			driver.close();
		}else {
			driver.quit();
		}
		
	}
	
	public static void ebaySearch(String sSearch,String sCat) {
		WebElement oTxt,oDropDown,oBtn;
		oTxt = driver.findElement(By.xpath("//input[@id='gh-ac']"));
		oTxt.clear();
		oTxt.sendKeys(sSearch);
		oDropDown = driver.findElement(By.xpath("//select[@id='gh-cat']"));
		Select oSelect = new Select(oDropDown);
		oSelect.selectByVisibleText(sCat);
		
		oBtn = driver.findElement(By.xpath("//input[@id='gh-btn']"));
		oBtn.submit();
	}
	
	public static void getSearchResult() {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		WebElement oResultText,oElement;
		oResultText = driver.findElement(By.xpath("//span[@class='listingscnt']"));
		String sText = oResultText.getText();
		System.out.println("Search Result is : "+sText);
		sText = sText.replaceAll("[^0-9]", "").trim();
		int iText = Integer.parseInt(sText);
		if(iText>0) {
			System.out.println("Search Result is Available");
			List<WebElement> oList = driver.findElements(By.xpath("//ul[@id='ListViewInner']/li"));
			for(int i=0;i<oList.size();i++) {
				oElement = oList.get(i);
				System.out.println(oElement.findElement(By.className("vip")).getText());
			}
		}else {
			System.out.println("Search Result is 0");
		}
	}

}























