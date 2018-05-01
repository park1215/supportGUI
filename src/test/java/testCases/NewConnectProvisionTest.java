package testCases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import util.BrowserFactory;
import util.Utilities;
import java.util.Properties;

public class NewConnectProvisionTest {
	
	public WebDriver driver;
	
	public Workbook workbook;
	
	public static String baseUrl; 
	
	public Properties prop;
	
	public SoftAssert softAssert = new SoftAssert();
	
	public String acctId;
	
	public String randomMac;
	
	public String serviceAgreementNumber;
	
	@Parameters({"browser", "salesChannel"})
	@BeforeTest
	public void beforeTest(@Optional String browser, @Optional String salesChannel){
		
		this.workbook = Utilities.loadExcelFile("Addresses.xlsx");
		
		this.prop = Utilities.loadPropertyFile("SupportGUI.properties");
		
		this.baseUrl = prop.getProperty("supportGuiUrl");
		
		System.out.println("SupportGUI URL : " + baseUrl);
		
		try {
			 if(browser.equalsIgnoreCase("chrome")){
			        driver = BrowserFactory.startBrowser("chrome", baseUrl);
			    }
			    else if(browser.equalsIgnoreCase("ie")){
			        driver = BrowserFactory.startBrowser("ie", baseUrl);
			    }
			    else{
			        driver = BrowserFactory.startBrowser("firefox", baseUrl);
			    }
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("before test");
	}
	
	@AfterTest
	public void afterTest(){
		System.out.println("after test");
		driver.quit();
		
	}
	
	@Parameters("salesChannel")
	@Test(priority=1)
	public void J1170118(String salesChannel){
		
		this.randomMac = Utilities.getRandomMac();
		
		String transactionReference = "SPark_"+randomMac.replaceAll(":", "");
				
		int currentRow = 1;

		Sheet workSheet = this.workbook.getSheet(salesChannel);
		
		int endRow = workSheet.getLastRowNum();
		
		System.out.println("endRow : " + endRow);
		
		int endCell = workSheet.getRow(currentRow).getLastCellNum();
		
		System.out.println("endCell : " + endCell);
		
		Map<String, String> dataMap = Utilities.getRowCellMap(workSheet, currentRow);
		
		Set<String> set = dataMap.keySet();
		
		for(String key : dataMap.keySet()){
			System.out.println(key + " - " + dataMap.get(key));
		}
		
		String username = dataMap.get("Username");

	    String password = dataMap.get("Password");

	    String salesChannelA = salesChannel;
	    
//	    String salesChannelA = dataMap.get("SalesChannel");

	    String customerType = dataMap.get("CustomerType");

	    String salesChannelType = dataMap.get("SalesChannelType");

	    String addressLine1 = dataMap.get("AddressLine1");

	    String firstName = dataMap.get("FirstName");

	    String lastName = dataMap.get("LastName");

	    String city = dataMap.get("City");

	    String state = dataMap.get("State");

	    String zipCode = dataMap.get("Zip");

	    String voipIncluded = dataMap.get("VoIPIncluded");

	    String voipMac = dataMap.get("VoIPMac");

	    String provisionModem = dataMap.get("provisionModem");

	    String activateVoIP = dataMap.get("activateVoIP");

	    String latitude = dataMap.get("Lat");

	    System.out.println("Latitude : " + latitude);

		String longitude = dataMap.get("Long");

	    System.out.println("Longitude : " + longitude);

	    String transactionType = dataMap.get("TransactionType");

	    Boolean includeVoip = false;
	    
		if (!voipIncluded.equalsIgnoreCase("no")){
	        includeVoip = true;
		}
			
		Boolean modemProvision = false;
		
	    if (!provisionModem.equalsIgnoreCase("no")){
	    	modemProvision = true;
	    }

	    Boolean voipActivation = false;
	    
	    if(!activateVoIP.equalsIgnoreCase("no")){
	    	voipActivation = true;
	    }
		    
	    System.out.println("voip is included in this order? : " + voipActivation.toString());

		String paymentType = dataMap.get("PaymentType");

		String packageName = dataMap.get("Package");

		String satelliteName = dataMap.get("Satellite");

		System.out.println("Payment type in the data table: " + paymentType);

		System.out.println("Package Name : " + packageName);

		System.out.println("Satellite Name : " + satelliteName);
		
		driver.findElement(By.xpath("//*[@id='document:body']/table/tbody/tr[2]/td/form/table/tbody/tr[3]/td[2]/input")).sendKeys(username);

		driver.findElement(By.xpath("//*[@id='document:body']/table/tbody/tr[2]/td/form/table/tbody/tr[4]/td[2]/input")).sendKeys(password);
		
		driver.findElement(By.name("submit")).click();
				
		driver.manage().timeouts().implicitlyWait(30,  TimeUnit.SECONDS);
		
		// addCustomerTab has id of "addCustomerForm:add" OR, sometimes "add".
	    // so I decided to use href, which is more reliable
		WebDriverWait wait = new WebDriverWait(driver, 10);
		
		Wait<WebDriver> fluentwait = new FluentWait<WebDriver>(driver)
				.withTimeout(120, TimeUnit.SECONDS)
				.pollingEvery(10, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
			
		WebElement ordersTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"addCustomerForm:orders\"]")));
		
		ordersTab.click();
	
		WebElement addCustomerTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"addCustomerForm:add\"]")));
		
		addCustomerTab.click();
		
		Select salesChannelDropdown = new Select(driver.findElement(By.xpath("//*[@id=\"addCustomerForm:salesChannelMenu\"]")));
		
		salesChannelDropdown.selectByValue(salesChannel.toUpperCase());
		
		System.out.println("Selected sales channel : " + salesChannel);
		
		Select customerTypeDropdown = new Select(driver.findElement(By.xpath("//*[@id=\"addCustomerForm:customerTypeMenu\"]")));
		
		customerTypeDropdown.selectByValue(customerType.toUpperCase());
		
		Select transactionTypeDropdown = new Select(driver.findElement(By.xpath("//*[@id=\"addCustomerForm:transactionTypeMenu\"]")));
		
		transactionTypeDropdown.selectByValue(transactionType);
		
		WebElement transactionReferenceField = driver.findElement(By.xpath("//*[@id=\"addCustomerForm:transactionReference\"]"));
	
		transactionReferenceField.sendKeys(transactionReference);
		
		WebElement firstNameField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"addCustomerForm:namesIdName1\"]")));
				
		firstNameField.sendKeys(firstName);
	
		WebElement lastNameField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"addCustomerForm:namesIdName3\"]")));
				
		lastNameField.sendKeys(lastName);
	
//		 if salesChannelType == 'Wholesale':
//		        businessNameField = WebDriverWait(driver, 30).until(
//		            EC.presence_of_element_located((By.ID,
//		                                            'addCustomerForm:businessNameId'))
//		        )
//
//		        businessNameField.send_keys('Spider & Co.')
//
//		        latitudeField = WebDriverWait(driver, 30).until(
//		            EC.presence_of_element_located((By.ID,
//		                                            'addCustomerForm:latitude'))
//		        )
//
//		        latitudeField.clear()
//		        latitudeField.send_keys(latitude)
//
//		        longitudeField = WebDriverWait(driver, 30).until(
//		            EC.presence_of_element_located((By.ID,
//		                                            'addCustomerForm:longitude'))
//		        )
//		        longitudeField.clear()
//		        longitudeField.send_keys(longitude)
		        
		WebElement addressLine1Field = driver.findElement(By.xpath("//*[@id=\"addCustomerForm:addressIdMaybeTableAddress1\"]"));
		
		addressLine1Field.sendKeys(addressLine1);
		
		WebElement cityField = driver.findElement(By.xpath("//*[@id=\"addCustomerForm:addressIdMaybeTableCity\"]"));
		
		cityField.sendKeys(city);
		
//	    if salesChannel == 'Prosperist':
//	        stateDropdown = WebDriverWait(driver, 10).until(
//	                EC.presence_of_element_located((By.XPATH, '//*[@id="addCustomerForm:addressIdMaybeTableProsperistState"]')))
//	        Select(stateDropdown).select_by_value(state)
//
//	    else:
//	        stateAddress = WebDriverWait(driver, 30).until(
//	            EC.presence_of_element_located((By.XPATH,
//	                                            '//*[@id="addCustomerForm:addressIdMaybeTableStateAddressState"]/option[7]'))
//	        )
//	        stateAddress.click()

		Select stateDropdown = new Select(driver.findElement(By.xpath("//*[@id=\"addCustomerForm:addressIdMaybeTableStateAddressState\"]")));

		stateDropdown.selectByValue(state);
		
		WebElement zipCodeField = driver.findElement(By.xpath("//*[@id=\"addCustomerForm:addressIdMaybeTableZip\"]"));
		
		zipCodeField.sendKeys(zipCode);
		
		WebElement phoneNumberField = driver.findElement(By.xpath("//*[@id=\"addCustomerForm:primaryPhoneIdMaybeTablePhoneNumber\"]"));

		phoneNumberField.sendKeys(prop.getProperty("customerPhoneNumber"));
		
		WebElement extensionNumberField = driver.findElement(By.xpath("//*[@id=\"addCustomerForm:primaryPhoneIdMaybeTableExtension\"]"));
		
		extensionNumberField.sendKeys(prop.getProperty("extensionNumber"));
		
		WebElement emailAddressField = driver.findElement(By.xpath("//*[@id=\"addCustomerForm:emailAddressId\"]"));
		
		//emailAddressField.sendKeys("sean.park@viasat.com"); raises an error sometimes.
		emailAddressField.sendKeys(prop.getProperty("customerEmail").toString());
		
		//emailAddressField.sendKeys("sean.park@viasat.com");
		
	    if (salesChannelType != "Wholesale"){
	        
	        WebElement birthdateField = driver.findElement(By.xpath("//*[@id=\"addCustomerForm:Birthdate\"]"));
	    
	        birthdateField.sendKeys(prop.getProperty("customerBirthdate"));
	    }
	    
	    //driver.save_screenshot(SupportPortalScreenshotDirectory + '/1_serviceability.png')
	    
	    WebElement nextButtonServiceability = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"addCustomerForm:nextButtonId\"]")));
	    
	    nextButtonServiceability.click();
	    
	    //Contacts Page
	    
	    WebElement customerReferenceField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("addCustomerForm:customerReference"))); 
	    		
	    customerReferenceField.sendKeys(transactionReference);
	    
	    WebElement accountReferenceField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("addCustomerForm:accountReference")));
	    
	    accountReferenceField.sendKeys(transactionReference);
	    
	    WebElement nextButtonContacts = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"addCustomerForm:nextButtonId\"]")));
	    
	    nextButtonContacts.click();
	    
	    //Package page
	    //Wait doesn't pick up the below web element.  Hate to use Thread.sleep, but it works.
	    try {
			Thread.sleep(3*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    //need to revisit this code for exception handling. Sometimes the application under test is not stable here.
	    WebElement nextButtonPackage = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"addCustomerForm:nextButtonId\"]")));

//	    WebElement nextButtonPackage = fluentwait.until(new Function<WebDriver, WebElement>(){
//	    	public WebElement apply(WebDriver driver){
//	    	return driver.findElement(By.xpath("//*[@name=\"addCustomerForm:nextButtonId\"]"));
//	    }});
	    
	    JavascriptExecutor js = (JavascriptExecutor) driver;  
	    js.executeScript("arguments[0].click();", nextButtonPackage);
	    
//	    nextButtonPackage.click();
	    

	    		
//	    		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"addCustomerForm:nextButtonId\"]")));
	    
//	    WebElement packageList = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"addCustomerForm:packagesHeaderLabel\"]"))); 
	    
//	    System.out.println(nextButton.toString());
	    
//	    try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	    
	    // Options page
	    
	    WebElement optionsTitle = driver.findElement(By.xpath("//*[@id=\"addCustomerForm:optionsLabel\"]"));
	    
	    System.out.println("Options Title displayed : " + optionsTitle.getText());
	    
	    WebElement lifetimeLeaseButton = driver.findElement(By.xpath("//*[@id=\"addCustomerForm:_1selectionPackages:_1\"]"));
	    
	    lifetimeLeaseButton.click();
	    
//		This is to add voip service.

	    try{
	    	if(includeVoip){
	    		WebElement voipSelectButton = driver.findElement(By.xpath("//*[@type=\"checkbox\"])[2]"));
	    		voipSelectButton.click();
	    		System.out.println("VoIP option is selected.");
	    	}
	    	else{
	    		System.out.println("VoIP option is NOT selected.");
	    	}
	    }
	    catch(Exception e){
	    	System.out.println("VoIP option select failed. It continues to next row");
	    	currentRow = currentRow+1;
	    	System.out.println("-----------------------------------------------------------------------");
	    	driver.quit();
	    	//continue;
	    }
	    
	    try {
			Thread.sleep(5*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    WebElement nextButtonOptions = driver.findElement(By.xpath("//*[@id=\"addCustomerForm:nextButtonId\"]"));
	    
	    nextButtonOptions.click();
	    
	    WebElement ccZipCodeField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"addCustomerForm:recurringPaymentIdRecurringPaymentMethodIdTableCreditCardIdAddressZip\"]")));
	    
	    Select paymentMethodDropdown = new Select(driver.findElement(By.xpath("//*[@id=\"addCustomerForm:recurringPaymentIdRecurringPaymentMethodIdTableselectPaymentTypeChoiceId\"]")));
	    
	    paymentMethodDropdown.selectByValue("CREDIT_CARD_RECURRING_PAYMENT");
	    
	    Select creditcardDropdown = new Select(driver.findElement(By.xpath("//*[@id=\"addCustomerForm:recurringPaymentIdRecurringPaymentMethodIdTableCreditCardIdcreditCardTypeId\"]")));
	    
	    creditcardDropdown.selectByValue("VISA");
	    
	    WebElement creditCardNumberField = driver.findElement(By.xpath("//*[@id=\"addCustomerForm:recurringPaymentIdRecurringPaymentMethodIdTableCreditCardIdNumberId\"]"));
	    
	    creditCardNumberField.sendKeys(prop.getProperty("creditCardNumber"));
	    
	    Select expirationMonthDropdown = new Select(driver.findElement(By.xpath("//*[@id=\"addCustomerForm:recurringPaymentIdRecurringPaymentMethodIdTableCreditCardIdExpireMonthIdMonthId\"]")));
	
	    expirationMonthDropdown.selectByValue(prop.getProperty("expirationMonth"));
	    
	    Select expirationYearDropdown = new Select(driver.findElement(By.xpath("//*[@id=\"addCustomerForm:recurringPaymentIdRecurringPaymentMethodIdTableCreditCardIdExpireYearIdYearId\"]")));
	    
	    expirationYearDropdown.selectByValue(prop.getProperty("expirationYear"));
	
	    WebElement ccFirstNameField = driver.findElement(By.xpath("//*[@id=\"addCustomerForm:recurringPaymentIdRecurringPaymentMethodIdTableCreditCardIdFirstNameId\"]"));
	    
	    ccFirstNameField.sendKeys(prop.getProperty("ccFirstName"));
	    
	    WebElement ccLastNameField = driver.findElement(By.xpath("//*[@id=\"addCustomerForm:recurringPaymentIdRecurringPaymentMethodIdTableCreditCardIdLastNameId\"]"));
	    
	    ccLastNameField.sendKeys(prop.getProperty("ccLastName"));
	    
	    WebElement businessNameField = driver.findElement(By.xpath("//*[@id=\"addCustomerForm:recurringPaymentIdRecurringPaymentMethodIdTableCreditCardIdBusinessNameId\"]"));
	  
	    businessNameField.sendKeys(prop.getProperty("businessName"));
	    
	    //WebElement ccZipCodeField = driver.findElement(By.xpath("//*[@id=\"addCustomerForm:recurringPaymentIdRecurringPaymentMethodIdTableCreditCardIdAddressZip\"]"));
	    
	    ccZipCodeField.sendKeys(zipCode);
	    
	    //Review page
	    
	    WebElement nextButtonPayment = driver.findElement(By.xpath("//*[@id=\"addCustomerForm:nextButtonId\"]"));
	    
	    nextButtonPayment.click();
	    
	    WebElement installationNotesLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"addCustomerForm:InstallerNotesLabel\"]")));
	    
	    WebElement scheduleButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"addCustomerForm:scheduleInstallationButtonId\"]")));
	
	    scheduleButton.click();
	    
	    WebElement selectedDateLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"addCustomerForm:_id90\"]/table/tbody/tr[1]/td[2]/table/tbody/tr[1]/td/b")));
	    
	    WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"addCustomerForm:submitButtonId\"]")));
	  
	    submitButton.click();
	    
	    try {
			Thread.sleep(20*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    // Confirmation page
	    
	    try{
		    WebElement printButton = fluentwait.until(new Function<WebDriver, WebElement>(){
	    	public WebElement apply(WebDriver driver){
	    	return driver.findElement(By.xpath("//*[@id=\"addCustomerForm:printButtonId\"]"));
	    }});
	    }
	    catch(Exception e){
	    	System.out.println("FSM scheduling screen hangs for more than four minutes.\n"
	    			+ "The confirmation page is not displayed. It continues to the next order.");
	    	currentRow = currentRow + 1;
	    	System.out.println("-----------------------------------------------------------------------");
	    	driver.quit();

	    	e.printStackTrace();
	    }
	    
	    WebElement orderReferenceNumberLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"addCustomerForm:serviceAgreementReference\"]")));

	    String orderReferenceNumber = orderReferenceNumberLabel.getText();
	    
	    System.out.println("Order Reference Number : " + orderReferenceNumber);

	    WebElement newOrderButton = driver.findElement(By.xpath("//*[@id=\"addCustomerForm:newOrderButtonId\"]"));
	    
	    newOrderButton.click();
	    
	    WebElement transactionInfoTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"addCustomerForm:transactionInfoLabel\"]")));
	    
	    driver.get(prop.getProperty("spyglassUrl"));
	    
	    System.out.println("Spyglass Url : " + prop.getProperty("spyglassUrl"));
		
		WebElement referenceTypeOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/table/tbody/tr[2]/td/div/div/div[1]/div/div/form/div/table/tbody/tr/td[1]/select/option[5]")));

		referenceTypeOption.click();
		
		//Select referenceTypeDropdown = new Select(driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td/div/div/div[1]/div/div/form/div/table/tbody/tr/td[1]/select")));
		
		//referenceTypeDropdown.selectByValue(prop.getProperty("EXTERNAL_ACCOUNT"));
		
		//referenceTypeDropdown.selectByValue(prop.getProperty("referenceType"));
		
		WebElement referenceValueField = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/table/tbody/tr[2]/td/div/div/div[1]/div/div/form/div/table/tbody/tr/td[2]/input")));
		
		referenceValueField.sendKeys(orderReferenceNumber);

		WebElement externalSystemOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/table/tbody/tr[2]/td/div/div/div[1]/div/div/form/div/table/tbody/tr/td[3]/div/select/option[2]")));

		externalSystemOption.click();
		
		WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/table/tbody/tr[2]/td/div/div/div[1]/div/div/form/div/table/tbody/tr/td[4]/input[1]")));

		searchButton.click();
		
		//This is to wait until the page can be loaded/updated with whole Spyglass data, so the user can inspect visually.
		try {
			Thread.sleep(20*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		searchButton.click();
		
		try {
			Thread.sleep(1000*20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		WebElement serviceAgreementNumberLabel = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"data\"]/table[1]/tbody/tr[2]/td/table/tbody/tr[2]/td[12]")));
		
		this.serviceAgreementNumber = serviceAgreementNumberLabel.getText();
		
		WebElement fsmCustomerCodeLabel = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"datatable\"]/tbody/tr[1]/td[1]/div[1]")));
		
		System.out.println("FSM Customer Code : " + fsmCustomerCodeLabel.getText());
		
		WebElement fsmOrderCode = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"datatable\"]/tbody/tr[1]/td[3]/div[1]")));

		System.out.println("FSM Order Code : " + fsmOrderCode.getText());
		
		WebElement acctIdLabel = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"data\"]/table[1]/tbody/tr[2]/td/table/tbody/tr[2]/td[9]")));
		
		this.acctId = acctIdLabel.getText();
		
		ArrayList<WebElement> tableList = (ArrayList)driver.findElements(By.tagName("table"));
//		
//		WebElement wbTransactionTable = tableList.get(10);
//		
//		System.out.println("table 10 : " + wbTransactionTable.toString());
//		
//		//WebElement transactionStatusLabel = wbTransactionTable.findElement(By.xpath("//*[@id=\"datatable\"]/tbody/tr[3]/td[7]/div[1]"));
//		
//		//WebElement transactionStatusLabel = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"datatable\"]/tbody/tr[1]/td[7]/div[1]")));
//		
//		//String transactionStatus = transactionStatusLabel.getText();
//
//		ArrayList<WebElement> wbTransactionTableList = (ArrayList)wbTransactionTable.findElements(By.tagName("div"));
//		
//		for(int i=0; i<wbTransactionTableList.size(); i++){
//			System.out.println("wbTransactionTableListItem "+ i + " : " + wbTransactionTableList.get(i).getText());
//		}
//		
//		String transactionStatus = wbTransactionTableList.get(12).getText();
	
		String transactionStatus = getTransactionStatus(this.driver);
		
		System.out.println("Transaction Status : " + transactionStatus);
		
//		validateStatus(this.driver, "DISPATCHED", this.softAssert);
		
		while(!transactionStatus.equalsIgnoreCase("DISPATCHED")){
			
			try {
				Thread.sleep(5*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			transactionStatus = getTransactionStatus(this.driver);
			
			if(transactionStatus.equals("ERROR")){

				Assert.fail("New_Connect transaction ended in ERROR...");
//				if(currentRow != lastRow){
//					System.out.println("New_Connect transaction ended in ERROR. Move to the next row...");
//				}
//				else{
//					System.out.println("New_Connect transaction ended in ERROR. This is the last row.");
//				}
			}
		}

		System.out.println("Order Created Succesfully. Back to the first page of Order Entry.");
		
		for(int i=0; i<tableList.size(); i++){
			
			System.out.println("Table "+ i +" :"+tableList.get(i).getText());
		}
				
	    System.out.println("testA");
	    
	    softAssert.assertAll();
	}
	
	@Parameters("salesChannel")
	@Test(priority=2)
	public void GID_1804006(String salesChannel){
		
		Boolean spoofProvision = Boolean.parseBoolean(prop.getProperty("spoofProvision"));
		
		System.out.println("spoofProvision " + spoofProvision);
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		
		if(spoofProvision){
		
			WebElement modemTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/table/tbody/tr[2]/td/div/ul/li[5]/a")));
			
			modemTab.click();
			
			WebElement internalAccountReferenceField = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/table/tbody/tr[2]/td/div/div/div[5]/div/div/form/div/input[1]")));
			
			internalAccountReferenceField.sendKeys(acctId);
			
			WebElement provisionButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/table/tbody/tr[2]/td/div/div/div[5]/div/div/form/div/input[2]")));
			
			provisionButton.click();
			
			driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
			
			WebElement provisionResultLabel = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/table/tbody/tr[2]/td/div/div/div[5]/div/div/div/h3")));

			String provisionResult = provisionResultLabel.getText();
			
			System.out.println("provisionResult 1" + provisionResult);
			
			while(provisionResult.indexOf("Success") < 0){
				
				provisionButton.click();

				driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
				
				provisionResult = provisionResultLabel.getText();
			
			}
			
			System.out.println("Spoof provisioning Result : " + provisionResult);
		}

		else{
		// Provisioning starts in InstallGUI page....
						
		String installGuiUrl = prop.getProperty("installGuiUrl");
		
		String installGuiWithMac = installGuiUrl+"?n="+randomMac;
		
		System.out.println(installGuiWithMac);
		
		driver.get(installGuiWithMac);

		WebElement customerCodeField = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"installerForm:activationKey\"]")));
		
		customerCodeField.sendKeys(serviceAgreementNumber);
		
		WebElement nextStepButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"installerForm:j_id36\"]")));
		
		nextStepButton.click();
		
		//Customer Confirmation New Installation page
		WebElement installerIdField = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"installerForm:installerId\"]")));
		
		installerIdField.sendKeys(prop.getProperty("installerId"));
		
		WebElement continueInstallButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"installerForm:j_id53\"]")));
		
		continueInstallButton.click();
		
		//Email Confirmation & Update page
		WebElement emailConfirmationYesButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"installerForm:j_id30\"]")));
				
		emailConfirmationYesButton.click();
		
		//Quality of Install page
		WebElement qoiContinueButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@type=\"submit\"]")));
		
		qoiContinueButton.click();
		
		//New Customer Account Setup page
		WebElement customerButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"installerForm:j_id27\"]")));
		
		customerButton.click();
		
		//New Customer Account Setup - "Please Authenticate Your Account" page
		
		WebElement lastFourField = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"installerForm:paymentAuthentication\"]")));
		
		String creditCardNumber = prop.getProperty("creditCardNumber");
		
		String lastFourCreditCardNumber = new StringBuilder(creditCardNumber.substring(12,16)).reverse().toString();
		
		System.out.println("last four credit card number : " + lastFourCreditCardNumber);
		
		lastFourField.sendKeys(lastFourCreditCardNumber);
		
		WebElement lastFourContinueButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@value=\"Continue\"]")));
		
		lastFourContinueButton.click();

		try {
			Thread.sleep(10*1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		WebElement iFrame = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));

		WebElement caContinueButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"installerForm:j_id25\"]")));

		System.out.println("caContinueButton : " + caContinueButton.getTagName());
		
		try {
			Thread.sleep(10*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		
		System.out.println("number of frames : " + frames.size());
		
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS) ;
		
		}
		
		//wait = new WebDriverWait(driver, 50);
		
		WebElement iFrame = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));
		
		System.out.println("iFrame tag name : " + iFrame.getTagName());
		
		//make sure the driver is out of any frames.
		//driver.switchTo().defaultContent();
		
		driver.switchTo().frame(1);
		
		//driver.switchTo().frame(iFrame);
		
		//WebElement getStartedButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"pnlElectronic\"]/div/div[1]/button[1]")));
		
		WebElement getStartedButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"pnlElectronic\"]/div/div[1]/button[1]")));
		
		//*[@id="requiredLocationCount"]

		getStartedButton.click();
		
		WebElement signatureField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"location1\"]/div[2]/div[1]/input")));
				
		signatureField.sendKeys("Spider Man");
		
		WebElement finishSubmitButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"lblFinishAndSubmitPopup\"]")));
		
		finishSubmitButton.click();
		
		WebElement signConfirmLabel = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"ctl00_cpBody_transactionPanel\"]/div/h4")));
				
		System.out.println("signConfirmLabel : " + signConfirmLabel.getText());

		driver.switchTo().defaultContent();
		
		WebElement customerAgreementContinueButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"installerForm:j_id25\"]")));
		
		customerAgreementContinueButton.click();
		
		WebElement activatingModemContinueButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"installerForm:j_id35\"]")));
		
		activatingModemContinueButton.click();
		
		WebElement activationConfirmationLabel = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"installerForm:j_id27\"]")));
		
		List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
		
		System.out.println("number of iframes : "+iframes.size());
		
		driver.get(prop.getProperty("spyglassUrl"));
		
		WebElement referenceValueField = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/table/tbody/tr[2]/td/div/div/div[1]/div/div/form/div/table/tbody/tr/td[2]/input")));
		
		referenceValueField.sendKeys(serviceAgreementNumber);

		WebElement referenceTypeOptionSA = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/table/tbody/tr[2]/td/div/div/div[1]/div/div/form/div/table/tbody/tr/td[1]/select/option[3]")));

		referenceTypeOptionSA.click();
		
		WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/table/tbody/tr[2]/td/div/div/div[1]/div/div/form/div/table/tbody/tr/td[4]/input[1]")));

		searchButton.click();
		
		try {
			Thread.sleep(10*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Transaction Status after provisioning : " + getTransactionStatus(this.driver));
		
		validateStatus(this.driver, "COMPLETED", this.softAssert);
		
		System.out.println("testB");
	}
	
	@Test(priority=3)
	public void GID_1804010(){
		
		System.out.println("testC");
	}
	
	@Test(priority=4)
	public void GID_1804120(){
		
		System.out.println("testD");
	}
	
	private void validateStatus(WebDriver driver, String status, SoftAssert sassert){
		
		String transactionStatus = getTransactionStatus(driver);
		
		sassert.assertEquals(transactionStatus, status);
	}
	
	private String getTransactionStatus(WebDriver driver){
		
		ArrayList<WebElement> tableList = (ArrayList)driver.findElements(By.tagName("table"));
		
		WebElement wbTransactionTable = tableList.get(10);
		
		ArrayList<WebElement> wbTransactionTableList = (ArrayList)wbTransactionTable.findElements(By.tagName("div"));
		
		String transactionStatus = wbTransactionTableList.get(12).getText();
		
		return transactionStatus;
	}
	
	private void validateBilling(WebDriver driver, SoftAssert sassert){
		
	}
	
	private void validateVolubill(WebDriver driver, SoftAssert sassert){
		
	}
	
	private void validateSdp(WebDriver driver, SoftAssert sassert){
		
	}
	
	private void validateSpr(WebDriver driver, SoftAssert sassert){
		
	}
	
	private void validateFsm(WebDriver driver, SoftAssert sassert){
		
	}

}

//System.setProperty("webdriver.chrome.driver", "C:\\Users\\spark\\workspace\\com.viasat.supportGUI\\resources\\drivers\\chromedriver.exe");
//
//WebDriver driver = new ChromeDriver();
//
//driver.get("http://www.google.com");
