package com.sprhib.frontend;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.DefaultCssErrorHandler;

public class SeleniumAppTest {

	private final static String ROOT = "http://localhost:8181";
	private WebDriver browser;

	@Before
	public void setup() {
		browser = new HtmlUnitDriver();
		LogManager.getLogger(DefaultCssErrorHandler.class).setLevel(Level.ERROR);
	}

	@Test
	public void testAddOrganization() {
		browser.get(url("/spr-mvc-hib/organization/add"));
		browser.findElement(By.id("name")).sendKeys("Mark Inc");
		browser.findElement(By.id("telephone")).sendKeys("+1082655");
		browser.findElement(By.id("address")).sendKeys("Santa Clara, US");
		browser.findElement(
				By.xpath("/html/body/div/form/table/tbody/tr[4]/td[1]/input"))
				.submit();
		assertEquals("Organization was successfully added.", browser
				.findElement(By.id("message")).getText());
	}

	@Test
	public void testAddEmptyOrganization() {
		browser.get(url("/spr-mvc-hib/organization/add"));
		browser.findElement(
				By.xpath("/html/body/div/form/table/tbody/tr[4]/td[1]/input"))
				.submit();
		assertEquals("Organization cant have empty fields.", browser
				.findElement(By.id("message")).getText());
	}

	@Test
	public void testTeamPage() {
		// TODO
	}

	@Test
	public void testTeamMemberPage() {
		// TODO
	}

	@After
	public void tearDown() {
		browser.close();
	}

	private String url(String path) {
		return ROOT + path;
	}
}
