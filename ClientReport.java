package stepdefinition;

import java.awt.AWTException;
import java.io.File;
import java.time.Duration;
import java.util.Arrays;
import java.util.Comparator;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.google.common.collect.Comparators;
import baseclass.LibGlobal;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import pompages.ClientReportPOM;
import pompages.LoginPOM;
import userdata.TestUserData.UserType;

public class ClientReport extends LibGlobal {
	private ClientReportPOM clientReportPOM = new ClientReportPOM();
	private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

//CaseReport
	@When("the client clicks the reports menu and clicks the case report")
	public void the_client_clicks_the_reports_menu_and_clicks_the_case_report() throws AWTException {
		wait.until(ExpectedConditions.visibilityOf(clientReportPOM.getSaSidemenu()));
		clientReportPOM.getSaSidemenu().click();
		clientReportPOM.scrolldown();
		wait.until(ExpectedConditions.elementToBeClickable(clientReportPOM.getClientReport()));
		clientReportPOM.getClientReport().click();
		clientReportPOM.scrolldown();
		wait.until(ExpectedConditions.elementToBeClickable(clientReportPOM.getCaseReport()));
		clientReportPOM.getCaseReport().click();
		clientReportPOM.getSaSidemenu().click();
	}

//BillingReport
	@When("the client clicks the reports menu and clicks the billing report")
	public void the_client_clicks_the_reports_menu_and_clicks_the_billing_report() throws AWTException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOf(clientReportPOM.getSaSidemenu()));
		clientReportPOM.getSaSidemenu().click();
		clientReportPOM.scrolldown();
		wait.until(ExpectedConditions.elementToBeClickable(clientReportPOM.getClientReport()));
		clientReportPOM.getClientReport().click();
		clientReportPOM.scrolldown();
		wait.until(ExpectedConditions.elementToBeClickable(clientReportPOM.getBillingReport()));
		clientReportPOM.getBillingReport().click();
		clientReportPOM.getSaSidemenu().click();
	}

	@When("clicks the export button to download the report")
	public void clicks_the_export_button_to_download_the_report() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()=' Export']")));
		int retry = 0;
		while (retry < 3) {
			try {
				WebElement exportButton = driver.findElement(By.xpath("//div[text()=' Export']"));
				wait.until(ExpectedConditions.visibilityOf(exportButton));
				break;
			} catch (StaleElementReferenceException e) {
				retry++;
			}
		}
	}

	@Then("the client opens the report to verify the export report")
	public void the_client_opens_the_report_to_verify_the_export_report() {
		String downloadPath = System.getProperty("user.home") + "/Downloads/";
		File folder = new File(downloadPath);
		File downloadedFile = wait.until(driver -> Arrays.stream(folder.listFiles())
				.filter(file -> file.getName().endsWith(".xlsx") || file.getName().endsWith(".csv")).findFirst()
				.orElse(null));
		Assert.assertNotNull("Downloaded report not found", downloadedFile);
		downloadedFile.delete();
		LibGlobal.quitDriver();
	}

//AddFilter(case)
	@When("the client clicks the reports menu and clicks the case reports details")
	public void the_client_clicks_the_reports_menu_and_clicks_the_case_reports_details() throws AWTException {
		wait.until(ExpectedConditions.visibilityOf(clientReportPOM.getSaSidemenu()));
		clientReportPOM.getSaSidemenu().click();
		clientReportPOM.scrolldown();
		wait.until(ExpectedConditions.elementToBeClickable(clientReportPOM.getClientReport()));
		clientReportPOM.getClientReport().click();
		clientReportPOM.scrolldown();
		wait.until(ExpectedConditions.elementToBeClickable(clientReportPOM.getCaseReport()));
		clientReportPOM.getCaseReport().click();
	}

//AddFilter(Billing)
	@When("the client clicks the reports menu and clicks the billing reports details")
	public void the_client_clicks_the_reports_menu_and_clicks_the_billing_reports_details() throws AWTException {
		wait.until(ExpectedConditions.visibilityOf(clientReportPOM.getSaSidemenu()));
		clientReportPOM.getSaSidemenu().click();
		clientReportPOM.scrolldown();
		wait.until(ExpectedConditions.elementToBeClickable(clientReportPOM.getClientReport()));
		clientReportPOM.getClientReport().click();
		wait.until(ExpectedConditions.elementToBeClickable(clientReportPOM.getBillingReport()));
		clientReportPOM.getBillingReport().click();
		clientReportPOM.getSaSidemenu().click();
	}

	@When("clicks the add filter button to filter the data")
	public void clicks_the_add_filter_button_to_filter_the_data() throws AWTException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.elementToBeClickable(clientReportPOM.getAddfilter()));
		clientReportPOM.getAddfilter().click();
		clientReportPOM.clickPositionButton(driver, clientReportPOM.getThisMonth());

		int retry = 0;
		while (retry < 3) {
			try {
				WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(60));
				wait1.until(ExpectedConditions.elementToBeClickable(clientReportPOM.getExport()));
				clientReportPOM.getExport().click();
				break;
			} catch (StaleElementReferenceException e) {
				retry++;
			}
		}
	}

	@Then("the client opens the report to verify the case")
	public void the_client_opens_the_report_to_verify_the_case() {
		String downloadPath = System.getProperty("user.home") + "/Downloads/";
		File folder = new File(downloadPath);
		boolean fileFound = wait.until(driver -> {
			File[] files = folder.listFiles((dir, name) -> name.endsWith(".xlsx"));
			return files != null && files.length > 0;
		});
		Assert.assertTrue("Downloaded report not found", fileFound);
		if (fileFound) {
			File[] files = folder.listFiles((dir, name) -> name.endsWith(".csv"));
			System.out.println("File downloaded successfully: " + files[0].getAbsolutePath());
			files[0].delete();
		}
		if (driver != null) {
			LibGlobal.quitDriver();

		}
	}
}
