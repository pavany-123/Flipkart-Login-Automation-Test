import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import com.mailosaur.MailosaurClient;
import com.mailosaur.MailosaurException;
import com.mailosaur.models.MessageSearchParams;
import com.mailosaur.models.SearchCriteria;

import io.github.bonigarcia.wdm.WebDriverManager;

public class flipkartloginwithOTP {

	    String apiKey = "7BCb1PhdlbJYP54XI7pznDIyxJTZLdmi";
	    String serverId = "188nydrt";
	    String serverDomain = "188nydrt.mailosaur.net";
	    
	    public String getRandomEmail() {
			return "user" + System.currentTimeMillis()+"@"+serverDomain;
	    	
	    }
	    
	    @Test
	    public void tc1() throws InterruptedException, IOException, MailosaurException {
	    	String emailId = getRandomEmail();
	    	WebDriverManager.chromedriver().setup();
	    	WebDriver driver=new ChromeDriver();
	    	driver.manage().window().maximize();
	    	driver.get("https://www.flipkart.com/");
	    	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    	
	    	driver.findElement(By.xpath("//a[@title='Login']")).click();
	    	Thread.sleep(3000);
	    	//driver.findElement(By.xpath("//a[@class='_14Me7y']")).click();
	    	//Thread.sleep(3000);
	    	driver.findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys("anything@188nydrt.mailosaur.net");
	    	Thread.sleep(3000);
	    	driver.findElement(By.xpath("//button[text()='Request OTP']")).click();
	    	
	    	
	    	MailosaurClient mailosaur = new MailosaurClient(apiKey);

	        MessageSearchParams params = new MessageSearchParams();
	        params.withServer(serverId);

	        SearchCriteria criteria = new SearchCriteria();
	        criteria.withSentTo("asdas@" + serverDomain);

	        com.mailosaur.models.Message message = mailosaur.messages().get(params, criteria);
	        
	        
	        String subject = message.subject();
	        System.out.println(subject);
	        
	        System.out.println("------get OTP-----");
	        Pattern patrn = Pattern.compile("Flipkart Account - .*([0-9]{6}).*");
	        Matcher match = patrn.matcher(subject);
	        match.find();
	        
	        String otp = match.group(1);
	        
	        System.out.println(otp);

	    	driver.findElement(By.xpath("//div[@class='HSKgdN']")).sendKeys(otp);
	    	Thread.sleep(3000);
	    	driver.findElement(By.xpath("//button[text()='Verify']")).click();
	    	
	    	}
	   
	
		
	
}