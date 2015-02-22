package com.sprhib.controller;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.sprhib.editor.TeamEditorSupport;
import com.sprhib.model.Organization;
import com.sprhib.model.Team;
import com.sprhib.service.OrganizationService;
import com.sprhib.service.TeamService;
import com.sprhib.validator.TeamValidator;

@Controller
@RequestMapping(value = "/team")
public class TeamController {

	private final static Logger logger = Logger.getLogger(TeamController.class);

	@Autowired
	private TeamService teamService;
	@Autowired
	private OrganizationService organizationService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Team.class, new TeamEditorSupport(
				teamService));
		binder.registerCustomEditor(Organization.class,
				new OrganizationEditorSupport(organizationService));
		binder.addValidators(new TeamValidator(teamService));
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addTeamPage() {
		ModelAndView modelAndView = new ModelAndView("add-team-form");
		modelAndView.addObject("team", new Team());
		modelAndView.addObject("organizations",
				organizationService.getOrganizations());
		return modelAndView;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView addingTeam(
			@Validated @ModelAttribute("team") Team team, BindingResult result,
			Model model) {
		ModelAndView modelAndView = new ModelAndView("home");
		String message = null;
		if (result.hasErrors()) {
			message = "Team does not contain valid fields.";
		} else {
			logger.info("addingTeam team: " + team);
			message = "Team was successfully added.";
			teamService.addTeam(team);
		}
		modelAndView.addObject("message", message);
		return modelAndView;
	}

	@RequestMapping(value = "/list")
	public ModelAndView listOfTeams() {
		ModelAndView modelAndView = new ModelAndView("list-of-teams");

		List<Team> teams = teamService.getTeams();
		modelAndView.addObject("teams", teams);

		return modelAndView;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editTeamPage(@PathVariable Integer id) {
		ModelAndView modelAndView = new ModelAndView("edit-team-form");
		Team team = teamService.getTeam(id);
		modelAndView.addObject("team", team);
		modelAndView.addObject("organizations",
				organizationService.getOrganizations());

		return modelAndView;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public ModelAndView editingTeam(@ModelAttribute Team team,
			@PathVariable Integer id) {

		ModelAndView modelAndView = new ModelAndView("home");

		teamService.updateTeam(team);

		String message = "Team was successfully edited.";
		modelAndView.addObject("message", message);

		return modelAndView;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ModelAndView deleteTeam(@PathVariable Integer id) {
		teamService.deleteTeam(id);
		String message = "Team was successfully deleted.";

		ModelAndView modelAndView = new ModelAndView("home");
		modelAndView.addObject("message", message);
		return modelAndView;
	}

}
