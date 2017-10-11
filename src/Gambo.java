import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Scanner; 


public class Gambo {


  public static void main(String[] args){

	List<WebDriver> open_Drivers = new ArrayList<>();
	  
	Scanner reader = new Scanner(System.in);
	System.out.println("Steam User?");
	String usr = reader.next();
	System.out.println("Steam Password?");
	String pwd = reader.next();
	System.out.println("Enter Bet Amount?");
	String bet = reader.next();

	System.out.println("Enter Multiplyer?")
	String multiplyer = reader.next();
	
    open_Drivers.add(Gambo_Login(usr,pwd));
    Monitor_Crash(open_Drivers.get(0), bet, multiplyer);

	System.out.println("Enter Crash Cutoff?");
	String cutoff = reader.next();
	
    open_Drivers.add(Gambo_Login(usr,pwd));
    Monitor_Crash(open_Drivers.get(0), bet, cutoff);

    
    reader.close();
  }
  
  public static WebDriver Gambo_Login(String usr, String pwd) {
	  
		System.setProperty("webdriver.chrome.silentOutput", "true"); 
		Logger logger = Logger.getLogger(""); 
		logger.setLevel(Level.OFF); 
	  
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
		WebDriverWait wait60 = new WebDriverWait(driver, 60); 
		wait60.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"success_continue_btn\"]/div[1]"))).click();
		driver.get("https://gamdom.com/crash");

  }
  

  public static void Monitor_Crash(WebDriver driver, String bet, String multiplyer) { 

  public static void Monitor_Crash(WebDriver driver, String bet, String cutoff) { 

	  
	  while(true) {
		  String crash = driver.getPageSource().substring(0,150).split("<title>")[1].split("-")[0].trim();
		  
		  if(crash.contains("crashed at") == true) {
			  
			  String timestamp = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
			  System.out.println(crash);
			  
			  crash = crash.split("crashed at")[1].split("x")[0];
			  
			  System.out.println(crash);
			  
			  String bet_made = Repeater_Auto_Bet(driver, bet, crash, cutoff);

			  try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			  
				File csv = new File("crashes.csv");
				try {
					PrintWriter out = new PrintWriter(new FileWriter(csv, true));				
					out.println(timestamp + ',' + crash + ',' + bet_made);
					out.close();
					
				}
				catch (IOException ex){
					System.out.printf("Error: %s\n", ex);
					
				}
		  	}    
	  	}

	  Repeater_Auto_Bet(driver, bet, crash, multiplyer);
  	}
  public static void Repeater_Auto_Bet(WebDriver driver, String bet, String recentCrash, String multiplyer) {

  	}
  public static String Repeater_Auto_Bet(WebDriver driver, String bet, String crash, String cutoff) {

	  
      float crash_num = Float.parseFloat(crash);
      float cutoff_num = Float.parseFloat(cutoff);

      if (crash_num <= cutoff_num) {
	  

	  WebElement enterBet = driver.findElement(By.xpath("//*[@id=\"controls-inner-container\"]/div[1]/div/div/div/input"));
	  WebElement buttonBet = driver.findElement(By.xpath("//*[@id=\"controls-inner-container\"]/div[5]/div/button/span"));
	  
	  double crashed = Double.parseDouble(recentCrash);
	  try {
		  if(crashed <= 1)
	  		enterBet.sendKeys(bet);
	  		buttonBet.click();
	  		
	  }catch(Exception e) {
		  System.out.println("Exception Caught: " + e);
		  return "Stop";
	  }
	  Monitor_Crash(driver, bet, recentCrash); 
  	  }else {
  	  Monitor_Crash(driver, bet, recentCrash);

    	  	WebElement enterBet = driver.findElement(By.xpath("//*[@id=\"controls-inner-container\"]/div[1]/div/div/div/input"));
    	  	WebElement enterCutoff = driver.findElement(By.xpath("//*[@id=\"controls-inner-container\"]/div[2]/div/div/div/input"));
    	  	WebElement clickBet = driver.findElement(By.xpath("//*[@id=\"controls-inner-container\"]/div[5]/div/button/span"));
	  
    	  	try {
    	  		enterBet.clear();
    	  		enterBet.sendKeys(bet);
    	  		enterCutoff.clear();
    	  		enterCutoff.sendKeys(cutoff);
	  		clickBet.click();
	  		return "Bet";

    	  	}catch(Exception e) {
    	  		System.out.println("Exception Caught: " + e);
    	  		return "Error";
	  }
	  	
	  
	  //Monitor_Crash(driver, bet);
	  
      }
      else; 
      	return "No_Bet";

  }
}


