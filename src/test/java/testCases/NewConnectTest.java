package testCases;

import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.annotations.Test;

import supportGUI.AppForTest;

public class NewConnectTest {

	AppForTest aft = new AppForTest();
	
	@Test(priority=1)
	public void J1170118(){
		Assert.assertTrue(aft.returnTrue());
	}
	
	@Test(priority=2)
	public void J1170119(){
		Assert.assertTrue(aft.returnTrue());
	}
	
	@Test(priority=3)
	public void J11701110(){
		Assert.assertTrue(aft.returnTrue());
	}
	
	@Test(priority=4)
	public void J11701111(){
		Assert.assertTrue(aft.returnTrue());
	}
	
	@Test(priority=5)
	public void J11701112(){
		Assert.assertTrue(aft.returnTrue());
	}
	
	@Test(priority=6)
	public void J11701113(){
		Assert.assertTrue(aft.returnTrue());
	}
}
