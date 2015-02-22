package com.sprhib.controller;

import java.beans.PropertyEditorSupport;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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

import com.sprhib.editor.TeamEditorSupport;
import com.sprhib.editor.TeamMemberEditorSupport;
import com.sprhib.model.Member;
import com.sprhib.model.Team;
import com.sprhib.service.TeamMemberService;
import com.sprhib.service.TeamService;
import com.sprhib.validator.TeamMemberValidator;
import com.sprhib.validator.TeamValidator;

@Controller
@RequestMapping(value = "/teammember")
public class TeamMemberController {

	@Autowired
	private TeamMemberService teamMemberService;

	@Autowired
	private TeamService teamService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Member.class, new TeamMemberEditorSupport(
				teamMemberService));
		binder.addValidators(new TeamMemberValidator(teamMemberService));

		binder.registerCustomEditor(Set.class, "teams",
				new PropertyEditorSupport() {

					@Override
					public void setAsText(String text)
							throws IllegalArgumentException {
						Set<Team> teams = new HashSet<>();
						for (String idString : text.split(",")) {
							int id = Integer.parseInt(idString);
							Team team = teamService.getTeam(id);
							if (team != null) {
								teams.add(team);
							}
						}
						setValue(teams);
					}

					@Override
					public String getAsText() {
						String teamsText = "";
						if (getValue() != null) {
							Set<Team> teams = (Set<Team>) getValue();
							for (Team t : teams) {
								teamsText += teamsText.length() == 0 ? t
										.getName() : ", " + t.getName();
							}
						}
						return teamsText;
					}

				});
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addTeamMember() {
		LinkedList<Team> teams = new LinkedList<>(teamService.getTeams());

		ModelAndView modelAndView = new ModelAndView("add-teammember-form");
		modelAndView.addObject("teammember", new Member());
		modelAndView.addObject("teams", teams);
		return modelAndView;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView addingTeamMember(
			@Validated @ModelAttribute Member teammember, BindingResult result) {

		ModelAndView modelAndView = new ModelAndView("home");
		
		String message = "Team member was successfully added.";
		if (result.hasErrors()) {
			message = "Team member cant be added.";
		} else {
			teamMemberService.addMember(teammember);
		}
		modelAndView.addObject("teammember", teammember);
		modelAndView.addObject("message", message);
		return modelAndView;
	}

	@RequestMapping(value = "/list")
	public ModelAndView listOfTeamMembers() {
		ModelAndView modelAndView = new ModelAndView("list-of-teammembers");

		List<Member> members = teamMemberService.getMembers();
		modelAndView.addObject("teammembers", members);

		return modelAndView;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editTeamMemberPage(@PathVariable Integer id) {
		Member teamMember = teamMemberService.getMember(id);

		ModelAndView modelAndView = new ModelAndView("edit-teammember-form");
		modelAndView.addObject("teammember", teamMember);
		modelAndView.addObject("teams", teamService.getTeams());
		return modelAndView;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public ModelAndView editingTeamMember(@ModelAttribute Member teamMember,
			@PathVariable Integer id) {

		teamMemberService.updateMember(teamMember);

		String message = "Team member was successfully edited.";
		ModelAndView modelAndView = new ModelAndView("home");
		modelAndView.addObject("message", message);
		return modelAndView;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ModelAndView deleteTeamMember(@PathVariable Integer id) {
		teamMemberService.deleteMember(id);
		String message = "Team member was successfully deleted.";

		ModelAndView modelAndView = new ModelAndView("home");
		modelAndView.addObject("message", message);
		return modelAndView;
	}

}
