package softuni.workshop.service.services;

import softuni.workshop.data.entities.Project;
import softuni.workshop.service.dtos.ProjectDto;

import java.util.List;

public interface ProjectService {

    void importProjects();

    boolean areImported();

    String readProjectsXmlFile();

    String exportFinishedProjectsXml();
    String exportFinishedProjectsText();

    List<ProjectDto> getFinishedProjects();
}
