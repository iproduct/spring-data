package softuni.workshop.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import softuni.workshop.service.services.CompanyService;
import softuni.workshop.service.services.EmployeeService;
import softuni.workshop.service.services.ProjectService;

@Controller
public class HomeController extends BaseController {

    private final ProjectService projectService;
    private final EmployeeService employeeService;
    private final CompanyService companyService;

    @Autowired
    public HomeController(ProjectService projectService, EmployeeService employeeService, CompanyService companyService) {
        this.projectService = projectService;
        this.employeeService = employeeService;
        this.companyService = companyService;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @GetMapping("/home")
    public ModelAndView home() {
        ModelAndView modelAndView = super.view("home");

        boolean areImported = this.companyService.areImported()
                && this.projectService.areImported()
                && this.employeeService.areImported();
        modelAndView.addObject("areImported",  areImported);
        return modelAndView;
    }
}
