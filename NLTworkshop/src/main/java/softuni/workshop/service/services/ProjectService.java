package softuni.workshop.service.services;

public interface ProjectService {

    void importProjects();

    boolean areImported();

    String readProjectsXmlFile();

    String exportFinishedProjects();
}
