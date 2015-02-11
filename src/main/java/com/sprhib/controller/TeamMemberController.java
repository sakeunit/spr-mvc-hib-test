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

import com.sprhib.editor.TeamEditorSupport;
import com.sprhib.editor.TeamMemberEditorSupport;
import com.sprhib.model.Member;
import com.sprhib.model.Team;
import com.sprhib.service.TeamMemberService;
import com.sprhib.service.TeamService;
import com.sprhib.validator.TeamMemberValidator;

@Controller
@RequestMapping(value = "/teammember")
public class TeamMemberController {

	private final static Logger logger = Logger.getLogger(TeamController.class);

	@Autowired
	private TeamMemberService service;
	@Autowired
	private TeamService teamService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Member.class, new TeamMemberEditorSupport(
				service));
		binder.registerCustomEditor(Team.class, new TeamEditorSupport(
				teamService));
		binder.addValidators(new TeamMemberValidator());
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addTeamMember() {
		ModelAndView modelAndView = new ModelAndView("add-teammember-form");
		modelAndView.addObject("teammember", new Member());
		modelAndView.addObject("teams", teamService.getTeams());
		return modelAndView;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView addingTeamMember(
			@Validated @ModelAttribute("teammember") Member member,
			BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("home");
		String message = "Team member was successfully added.";
		if (result.hasErrors()) {
			message = "Team member cant be added.";
		} else {
			service.addMember(member);
		}
		modelAndView.addObject("message", message);
		return modelAndView;
	}

	@RequestMapping(value = "/list")
	public ModelAndView listOfTeamMembers() {
		ModelAndView modelAndView = new ModelAndView("list-of-teammembers");

		List<Member> members = service.getMembers();
		modelAndView.addObject("teammembers", members);

		return modelAndView;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editTeamMemberPage(@PathVariable Integer id) {
		Member teamMember = service.getMember(id);

		ModelAndView modelAndView = new ModelAndView("edit-teammember-form");
		modelAndView.addObject("teammember", teamMember);
		modelAndView.addObject("teams", teamService.getTeams());
		return modelAndView;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public ModelAndView editingTeamMember(@ModelAttribute Member teamMember,
			@PathVariable Integer id) {

		service.updateMember(teamMember);

		String message = "Team member was successfully edited.";
		ModelAndView modelAndView = new ModelAndView("home");
		modelAndView.addObject("message", message);
		return modelAndView;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ModelAndView deleteTeamMember(@PathVariable Integer id) {
		service.deleteMember(id);
		String message = "Team member was successfully deleted.";

		ModelAndView modelAndView = new ModelAndView("home");
		modelAndView.addObject("message", message);
		return modelAndView;
	}

}
