package softuni.workshop.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.workshop.service.services.EmployeeService;
import softuni.workshop.service.services.ProjectService;

@Controller
@RequestMapping("/export")
public class ExportController extends BaseController {
    private ProjectService projectService;
    private EmployeeService employeeService;

    @Autowired
    public ExportController(ProjectService projectService, EmployeeService employeeService) {
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    @GetMapping("/projects-if-finished-xml")
    public ModelAndView getAllFinishedProjectsXml() {
        return new ModelAndView("export/export-projects-if-finished","projectsIfFinished",
                projectService.exportFinishedProjectsXml());
    }

    @GetMapping("/projects-if-finished")
    public ModelAndView getAllFinishedProjects() {
        return new ModelAndView("export/export-projects-if-finished","projectsIfFinished",
                projectService.exportFinishedProjectsText());
    }

    @GetMapping("/employees-above-xml")
    public ModelAndView getEmployeesAgeAbove25Xml() {
        return new ModelAndView("export/export-employees-with-age","employeesAbove",
                employeeService.exportEmployeesWithAgeAboveXml(25));
    }

    @GetMapping("/employees-above")
    public ModelAndView getEmployeesAgeAbove25Text() {
        return new ModelAndView("export/export-employees-with-age","employeesAbove",
                employeeService.exportEmployeesWithAgeAboveText(25));
    }
}
