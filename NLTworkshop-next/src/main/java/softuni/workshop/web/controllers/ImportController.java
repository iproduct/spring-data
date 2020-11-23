package softuni.workshop.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.workshop.service.services.CompanyService;
import softuni.workshop.service.services.EmployeeService;
import softuni.workshop.service.services.ProjectService;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Controller
@RequestMapping("/import")
public class ImportController extends BaseController {

    private final EmployeeService employeeService;
    private final ProjectService projectService;
    private final CompanyService companyService;

    @Autowired
    public ImportController(EmployeeService employeeService, ProjectService projectService, CompanyService companyService) {
        this.employeeService = employeeService;
        this.projectService = projectService;
        this.companyService = companyService;
    }

    @GetMapping("/xml")
    public ModelAndView xml() {
        ModelAndView modelAndView = super.view("xml/import-xml");
        boolean[] areImported = new boolean[]{
                this.companyService.areImported(), this.projectService.areImported(), this.employeeService.areImported()};
        modelAndView.addObject("areImported", areImported);
        return modelAndView;
    }

    @GetMapping("/companies")
    public ModelAndView companies() throws IOException {
        ModelAndView modelAndView = super.view("xml/import-companies");
        modelAndView.addObject("companies", this.companyService.readCompaniesXmlFile());
        return modelAndView;
    }

    @PostMapping("/companies")
    public ModelAndView companiesConfirm() throws JAXBException {
        this.companyService.importCompanies();
        return super.redirect(areAllImported() ? "/home" : "/import/xml");
    }

    @GetMapping("/projects")
    public ModelAndView projects() throws IOException {
        return new ModelAndView("xml/import-projects", "projects", this.projectService.readProjectsXmlFile());
    }

    @PostMapping("/projects")
    public ModelAndView projectsConfirm() throws JAXBException {
        this.projectService.importProjects();
        return super.redirect(areAllImported() ? "/home" : "/import/xml");
    }

    @GetMapping("/employees")
    public ModelAndView employees() throws IOException {
        return new ModelAndView("xml/import-employees", "employees", this.employeeService.readEmployeesXmlFile());
    }

    @PostMapping("/employees")
    public ModelAndView employeesConfirm() throws JAXBException {
        this.employeeService.importEmployees();
        return super.redirect(areAllImported() ? "/home" : "/import/xml");
    }

    // Utils
    private boolean areAllImported() {
        return companyService.areImported() && this.projectService.areImported() && this.employeeService.areImported();
    }

}
