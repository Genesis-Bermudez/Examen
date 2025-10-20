package cr.ac.una.presentation_layer.Controllers;

import cr.ac.una.domain_layer.Project;
import cr.ac.una.domain_layer.Task;
import cr.ac.una.domain_layer.User;
import cr.ac.una.presentation_layer.Models.Model;
import cr.ac.una.service_layer.DataService;

import java.util.ArrayList;
import java.util.List;

public class ProjectAndTaskController {

    private final DataService service;
    private final Model model;

    public ProjectAndTaskController(DataService service, Model model) {
        this.service = service;
        this.model = model;
        this.model.setProjects(service.leerProyectos());
        this.model.setTasks(new ArrayList<>());
        this.model.setProject(new Project(-1)); // proyecto "vacío"
    }

    public List<Project> getProjects() {
        return model.getProjects();
    }

    public List<User> getUsers() {
        return service.readUsers();
    }

    public void addProject(String description, User leader) {
        Project project = new Project(description, leader); // constructor correcto
        service.agregarProyecto(project);
        model.setProjects(service.leerProyectos());
    }

    public void addTask(Project project, String description, String date, String priority, String status, User responsible) {
        if (project == null) return;
        Task t = new Task(description, date, priority, status, responsible); // constructor correcto
        service.agregarTareas(project, t);
        reloadTasksForProject(project);
    }

    public void updateTask(Project project, Task task, String priority, String status) {
        if (task == null || project == null) return;
        task.setPriority(priority);
        task.setStatus(status);
        service.actualizarTarea(project, task);
        reloadTasksForProject(project);
    }

    public void selectProject(Project project) {
        model.setProject(project != null ? project : new Project(-1));
        if (project == null) model.setTasks(new ArrayList<>());
        else model.setTasks(project.getTasks() != null ? project.getTasks() : new ArrayList<>());
    }

    public void reloadTasksForProject(Project p) {
        List<Project> projs = service.leerProyectos();
        model.setProjects(projs);
        if (p == null) {
            model.setProject(new Project(-1));
            model.setTask(null);
            return;
        }
        Project reloaded = projs.stream()
                .filter(pr -> pr.getCode().equals(p.getCode()))
                .findFirst().orElse(null);
        if (reloaded != null) {
            model.setProject(reloaded);
            model.setTasks(reloaded.getTasks() != null ? reloaded.getTasks() : new ArrayList<>());
        } else {
            model.setProject(new Project(-1));
            model.setTasks(new ArrayList<>());
        }
    }

    public Project getSelectedProject() {
        return model.getProject();
    }
}
