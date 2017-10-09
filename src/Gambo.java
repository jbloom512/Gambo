import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.FileWriter;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner; 



public class Gambo {


  public static void main(String[] args){

	Scanner reader = new Scanner(System.in);
	System.out.println("Steam User?");
	String usr = reader.next();
	System.out.println("Steam Password?");

	String pwd = reader.next();

	
    WebDriver driver = Gambo_Login(usr,pwd);
    Monitor_Crash(driver);
    
  }
  
  public static WebDriver Gambo_Login(String usr, String pwd) {
	  
		WebDriver driver = new ChromeDriver();
		driver.get("https://gamdom.com/crash");
		WebDriverWait wait = new WebDriverWait(driver, 5); 
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"iAgree\"]"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"router-container\"]/div/div/a"))).click();
	    Steam_Login(driver, usr,pwd);
	    
		return driver;
  }
		

  public static void Steam_Login(WebDriver driver, String usr, String pwd) {
		
		WebDriverWait wait = new WebDriverWait(driver, 5); 
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"steamAccountName\"]"))).sendKeys(usr);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"steamPassword\"]"))).sendKeys(pwd);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"imageLogin\"]"))).click();
		WebDriverWait wait30 = new WebDriverWait(driver, 30); 
		wait30.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"success_continue_btn\"]/div[1]"))).click();
		driver.get("https://gamdom.com/crash");

  }
  
  public static void Monitor_Crash(WebDriver driver) {
	  
	  while(true) {
		  String crash = driver.getPageSource().substring(0,150).split("<title>")[1].split("-")[0].trim();
		  
		  if(crash.contains("crashed at") == true) {
			  
			  String timestamp = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
			  //System.out.println(crash);
			  
			  crash = crash.split("crashed at")[1].split("x")[0];
			  System.out.println(crash);
			  
			  try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			  
				File csv = new File("crashes.csv");
				try {
					PrintWriter out = new PrintWriter(new FileWriter(csv, true));				
					out.println(timestamp + ',' + crash);
					out.close();
					
				}
				catch (IOException ex){
					System.out.printf("Error: %s\n", ex);
					
				}  
		  	}    
	  	}
  	}
}


