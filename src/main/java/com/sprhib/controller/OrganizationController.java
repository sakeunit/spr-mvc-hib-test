package com.sprhib.controller;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sprhib.editor.OrganizationEditorSupport;
import com.sprhib.model.Organization;
import com.sprhib.service.OrganizationService;
import com.sprhib.validator.OrganizationValidator;

@Controller
@RequestMapping(value = "/organization")
public class OrganizationController {

	private final static Logger logger = Logger
			.getLogger(OrganizationController.class);

	@Autowired
	private OrganizationService organizationService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Organization.class,
				new OrganizationEditorSupport(organizationService));
		binder.setValidator(new OrganizationValidator(organizationService));
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addOrganizationPage() {
		ModelAndView modelAndView = new ModelAndView("add-organization-form");
		modelAndView.addObject("organization", new Organization());
		return modelAndView;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView addingOrganization(
			@Validated @ModelAttribute Organization organization,
			BindingResult result) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("home");

		String message = null;
		if (result.hasErrors()) {
			message = "Organization has errors.";
			modelAndView.addObject("error", result.getAllErrors());
			logger.warn("errors in binding {}." + result);
		} else {
			organizationService.addOrganization(organization);
			message = "Organization was successfully added.";
		}

		modelAndView.addObject("message", message);
		return modelAndView;
	}

	@RequestMapping(value = "/list")
	public ModelAndView listOfOrganizations() {
		ModelAndView modelAndView = new ModelAndView("list-of-organizations");

		List<Organization> organizations = organizationService
				.getOrganizations();
		modelAndView.addObject("organizations", organizations);

		return modelAndView;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editOrganizationPage(@PathVariable Integer id) {
		ModelAndView modelAndView = new ModelAndView("edit-organization-form");
		Organization organization = organizationService.getOrganization(id);
		modelAndView.addObject("organization", organization);
		return modelAndView;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public ModelAndView editingOrganization(
			@ModelAttribute Organization organization, @PathVariable Integer id) {

		ModelAndView modelAndView = new ModelAndView("home");

		organizationService.updateOrganization(organization);

		String message = "Organization was successfully edited.";
		modelAndView.addObject("message", message);

		return modelAndView;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ModelAndView deleteOrganization(@PathVariable Integer id) {
		ModelAndView modelAndView = new ModelAndView("home");
		organizationService.deleteOrganization(id);
		String message = "Organization was successfully deleted.";
		modelAndView.addObject("message", message);
		return modelAndView;
	}

}
