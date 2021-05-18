package selenium_test;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SeleniumTest {

	public static String searchKeyword = "天氣";
	private static WebDriver driver;
	
	public static void main(String[] args) throws InterruptedException , WebDriverException , IOException {
		ChromeOptions options = new ChromeOptions();
		// SSL certificates エラー（警告）画面を回避するドライバー設定
		options.addArguments("ignore-certificate-errors");
		// ChromeDriver 使用すること & ChromeDriverのファイルパス(本プロジェクト内)を設定
		System.setProperty("webdriver.chrome.driver", "webdriver/chromedriver.exe");
		// Chrome ドライバーインスタンス作成
		driver = new ChromeDriver(options);
		// ChromeDriver 画面サイズ設定
		driver.manage().window().setSize(new Dimension(1180 , 748));

		new SeleniumTest().fsLogin("waynewei@future-shop.co.jp", "123123");
	}

	// FS3ユーザ画面自動的にログインする
	public void fsLogin(String mail , String pwd) throws InterruptedException , WebDriverException , IOException  {
		// ChromeDriver で FS3ユーザ画面を開く
		driver.get("https://dev062.future-shop.jp/");
		// ByXPathでログインを探し出す、押下
		driver.findElement(ByXPath.xpath("//*[@id=\"fs_Top\"]/div[2]/header/div[1]/div[3]/nav/ul/li[2]/span/a")).click();
		// nameでメルアドを探し出す、入力
		driver.findElement(By.name("mailAddress")).sendKeys(mail);
		// 少々お待ち
		Thread.sleep(1000);
		// nameでパスワードを探し出す
		WebElement login = driver.findElement(By.name("password"));
		// パスワード入力、1秒待つ、ログイン情報を送信
		login.sendKeys(pwd);
		Thread.sleep(1000);
		login.submit();

		// ページが更新するまで待つ（Timeoutは 3 秒)
//		new WebDriverWait(driver, 3).until((ExpectedCondition<Boolean>) webDriver
//				-> webDriver.getTitle().toLowerCase().startsWith(searchKeyword));

		// ブラウザの画面をキャプチャして、指定のパスに保存
//		File screenshotFile = new File("D:/Java/MyPracticeWorkspace/selenium_test/src/main/java/selenium_test/screenshot.png");
//		Files.write(screenshotFile.toPath(), ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));

//		Thread.sleep(3000);
		// ブラウザに停止のjavascriptを実行
//		((JavascriptExecutor) driver).executeScript("window.stop();");

		System.out.println("Login Done .");
		// ブラウザを閉じる
//		driver.close();
//		System.out.println("Browser Closed .");
	}
	}