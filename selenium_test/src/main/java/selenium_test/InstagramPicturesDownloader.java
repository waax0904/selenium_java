package selenium_test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class InstagramPicturesDownloader {
	
	static String target_url = 
//			"https://www.instagram.com/p/B_pkjVijRe9/";
			"https://www.instagram.com/p/CE_bR-osw4M/";
	static String fileName;
	private static ChromeDriver driver;
	private static ArrayList<String> img_urls = new ArrayList<String>();
	int img_pages = 0;
	static SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss.SSS");
	
	public static void main(String[] args) throws InterruptedException , WebDriverException , IOException  {
		ChromeOptions options = new ChromeOptions();
		// headlessモードでドライバーを実行する (表示しない)
//		 options.addArguments("--headless");
		// ChromeDriver 使用すること & ChromeDriverのファイルパス(本プロジェクト内)を設定
		System.setProperty("webdriver.chrome.driver", "webdriver/chromedriver.exe");
		// Chrome ドライバーインスタンス作成
		driver = new ChromeDriver(options);
		
		new InstagramPicturesDownloader().picutresUrlsCollector(target_url);
		for( String url_ : img_urls ) {
			new Thread(() -> {
				try {
					pictureDownload(url_ , fileName, (img_urls.indexOf(url_)+1));
				} catch (IOException e) {
				}
			}).start();
			System.out.println("Download start : " + (img_urls.indexOf(url_)+1));
		}
		java.awt.Desktop.getDesktop().open(new File("C://Users//tp1908026//IG crawler//"));
	}
	
	public void picutresUrlsCollector(String url) throws InterruptedException {
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		fileName = url.split("/p/")[1];
		fileName = fileName.substring(0 , fileName.length()-1);

		// ドライバーのページソースを String に
		String pageSource = driver.getPageSource();
		// ドライバーよるのソースを jsoup ドキュメント に変更
        Document doc = Jsoup.parse(pageSource);
        
        Elements next = doc.getElementsByClass("Yi5aA");
        img_pages = next.size() == 0 ? 1 : next.size();
        System.out.println("Detected " + img_pages + " Picture(s).");

		if (img_pages <= 2) {
			Elements images = doc.getElementsByClass("KL4Bh");
	    	img_urls.add(images.get(0).select("div img").attr("src"));
		    if (img_pages == 2)
		    	img_urls.add(images.get(1).select("div img").attr("src"));
		}
		else {
			driver.findElement(By.className("_6CZji")).click();
			while( img_pages > 0 ) {
				try {
					doc = Jsoup.parse(driver.getPageSource());
					Elements images = doc.getElementsByClass("KL4Bh");
			    	img_urls.add(images.get(0).select("div img").attr("src"));

			    	img_pages--;
			    	
			    	if ( img_pages == 1 ) 
			    		img_urls.add(images.get(1).select("div img").attr("src"));

			    	driver.findElement(By.className("_6CZji")).click();
			    	Thread.sleep(500);
				}
				catch(NoSuchElementException e) {
					System.out.println("Picture(s) url get ready .....");
					break;
				}
			}
		}
		driver.close();
		System.out.println(img_urls.size());
	}

	public synchronized static void pictureDownload(String imgUrl , String fileName , int imgPage) throws IOException {
		String file_ = "D://IG crawler//"+ fileName + "_" + imgPage + ".jpg";
		URL url = new URL(imgUrl);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		int responseCode = httpConn.getResponseCode();
		if ( responseCode == HttpURLConnection.HTTP_OK ) {
			InputStream inputStream = httpConn.getInputStream();
			
			// opens an output stream to save into file
			FileOutputStream outputStream = new FileOutputStream(file_);
			int bytesRead = -1;
			byte[] buffer = new byte[1024];
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			outputStream.close();
			inputStream.close();
		}
	}
}