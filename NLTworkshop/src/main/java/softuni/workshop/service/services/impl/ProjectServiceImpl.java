package softuni.workshop.service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.data.entities.Company;
import softuni.workshop.data.entities.Project;
import softuni.workshop.data.repositories.CompanyRepository;
import softuni.workshop.data.repositories.ProjectRepository;
import softuni.workshop.exceptions.CustomXmlException;
import softuni.workshop.exceptions.EntityNotFoundException;
import softuni.workshop.service.dtos.ProjectDto;
import softuni.workshop.service.dtos.ProjectRootDto;
import softuni.workshop.service.services.ProjectService;
import softuni.workshop.util.XmlParser;

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
    public void importProjects() {
        ProjectRootDto projectRootDto = xmlParser.parseXml(ProjectRootDto.class, PROJECTS_PATH);

        for (ProjectDto projectDto : projectRootDto.getProjectDtoList()) {
            Project project = modelMapper.map(projectDto, Project.class);
            project.setCompany(
                    companyRepository.findByName(project.getCompany().getName()).orElseThrow(
                            () -> new EntityNotFoundException(
                                    String.format("Company '%s' not found.", project.getCompany().getName())
                            )
                    )
            );

            this.projectRepository.saveAndFlush(project);
        }
    }

    @Override
    public boolean areImported() {
       return this.projectRepository.count() > 0;
    }

    @Override
    public String readProjectsXmlFile() {
        try {
            return String.join("\n", Files.readAllLines(Path.of(PROJECTS_PATH)));
        } catch (IOException e) {
            throw new CustomXmlException(e.getMessage(), e);
        }
    }

    @Override
    public String exportFinishedProjectsXml(){
        return xmlParser.exportXml(new ProjectRootDto(getFinishedProjects()), ProjectRootDto.class);
    }

    @Override
    public String exportFinishedProjectsText(){
        StringBuilder sb = new StringBuilder();
        getFinishedProjects().forEach(proj ->
                sb.append("Project Name: ").append(proj.getName())
                .append("\n     Description: ").append(proj.getDescription())
                .append("\n     ").append(proj.getPayment())
                .append("\n")
        );
        return sb.toString();
    }

    public List<ProjectDto> getFinishedProjects(){
        List<Project> projects = projectRepository.findAllByFinished(true);
        return projects.stream()
                .map(proj -> modelMapper.map(proj, ProjectDto.class))
                .collect(Collectors.toList());
    }
}
