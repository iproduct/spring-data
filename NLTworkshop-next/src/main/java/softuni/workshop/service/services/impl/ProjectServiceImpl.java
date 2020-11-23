package softuni.workshop.service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.data.entities.Company;
import softuni.workshop.data.entities.Project;
import softuni.workshop.data.repositories.CompanyRepository;
import softuni.workshop.data.repositories.ProjectRepository;
import softuni.workshop.data.repositories.ProjectRepository;
import softuni.workshop.excepion.EntityNotFoundException;
import softuni.workshop.service.dtos.ProjectDto;
import softuni.workshop.service.dtos.ProjectRootDto;
import softuni.workshop.service.services.ProjectService;
import softuni.workshop.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProjectServiceImpl implements ProjectService {
    private final static String PROJECTS_PATH = "src/main/resources/files/xmls/projects.xml";

    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, CompanyRepository companyRepository, XmlParser xmlParser, ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.companyRepository = companyRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }

    @Override
    public void importProjects() throws JAXBException {
        ProjectRootDto projectRootDto = this.xmlParser.parseXml(ProjectRootDto.class, PROJECTS_PATH);

        for (ProjectDto projectDto : projectRootDto.getProjectDtoList()) {
            Project project = this.modelMapper.map(projectDto, Project.class);
            Company company = companyRepository.findByName(project.getCompany().getName())
                    .orElseThrow(() ->  new EntityNotFoundException(
                            String.format("Company '%s' not found.", project.getCompany().getName()))
                    );
            project.setCompany(company);
            projectRepository.saveAndFlush(project);
        }
    }

    @Override
    public boolean areImported() {
        return this.projectRepository.count() > 0;
    }

    @Override
    public String readProjectsXmlFile() throws IOException {
        return String.join("\n", Files.readAllLines(Path.of(PROJECTS_PATH)));
    }

    @Override
    public String exportFinishedProjects() {
        List<Project> projects = projectRepository.findAllByFinished(true);
        List<ProjectDto> projectDtos = projects.stream()
                .map(p -> modelMapper.map(p, ProjectDto.class))
                .collect(Collectors.toList());
        return xmlParser.exportXml(new ProjectRootDto(projectDtos), ProjectRootDto.class);
    }

}
