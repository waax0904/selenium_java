package selenium_test;

import java.io.IOException;

import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SeleniumTest {

	public static String searchKeyword = "weather";
	private static WebDriver driver;

	public static void main(String[] args) throws InterruptedException, WebDriverException, IOException {
		ChromeOptions options = new ChromeOptions();
		// SSL certificates エラー（警告）画面を回避するドライバー設定
		options.addArguments("ignore-certificate-errors");
		// ChromeDriver 使用すること & ChromeDriverのファイルパス(本プロジェクト内)を設定
		System.setProperty("webdriver.chrome.driver", "webdriver/chromedriver.exe");
		// Chrome ドライバーインスタンス作成
		driver = new ChromeDriver(options);
		// ChromeDriver 画面サイズ設定
		driver.manage().window().setSize(new Dimension(1280, 720));
		new SeleniumTest().googleSearch();
	}

	public void googleSearch() throws InterruptedException, WebDriverException, IOException {
		// ChromeDriver で google を開く
		driver.get("https://www.google.com/");
		// ByXPathで検索バーを探し出す、
		WebElement searchBar = driver.findElement(ByXPath.xpath("/html/body/div[1]/div[3]/form/div[1]/div[1]/div[1]/div/div[2]/input"));
		searchBar.sendKeys(searchKeyword);
		searchBar.sendKeys(Keys.ENTER);

//		// ページが更新するまで待つ（Timeoutは 3 秒)
//		new WebDriverWait(driver, 3).until((ExpectedCondition<Boolean>) webDriver
//				-> webDriver.getTitle().toLowerCase().startsWith(searchKeyword));
//		// ブラウザの画面をキャプチャして、指定のパスに保存
//		File screenshotFile = new File("D:/Java/MyPracticeWorkspace/selenium_test/src/main/java/selenium_test/screenshot.png");
//		Files.write(screenshotFile.toPath(), ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
//		// ブラウザに停止のjavascriptを実行
//		((JavascriptExecutor) driver).executeScript("window.stop();");

		// ブラウザを閉じる
//		driver.close();
//		System.out.println("Browser Closed .");
	}
}