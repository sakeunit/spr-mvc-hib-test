package com.sprhib.controller;

import java.util.Locale;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LinkController {
	
	private final static Logger logger = Logger.getLogger(LinkController.class);
	
	@RequestMapping(value="/")
	public ModelAndView mainPage(Locale locale) {
		logger.info("Locale is set to: " + locale);
		return new ModelAndView("home");
	}
	
	@RequestMapping(value="/index")
	public ModelAndView indexPage() {
		return new ModelAndView("home");
	}
}
