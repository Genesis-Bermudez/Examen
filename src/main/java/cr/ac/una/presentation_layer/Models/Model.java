package cr.ac.una.presentation_layer.Models;

import cr.ac.una.domain_layer.Project;
import cr.ac.una.domain_layer.Task;
import cr.ac.una.service_layer.DataService;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {
    private Project project;
    private Task task;
    private List<Project> projects;
    private List<Task> tasks;

    public static final String PROJECT = "project";
    public static final String TASK = "task";
    public static final String PROJECTS = "projects";
    public static final String TASKS = "tasks";

    public Model() {
        this.project = new Project(-1);
        this.task = null;
        DataService service = DataService.getInstance();
        this.projects = service.leerProyectos();
        this.tasks = new ArrayList<>();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(PROJECT);
        firePropertyChange(TASK);
        firePropertyChange(PROJECTS);
        firePropertyChange(TASKS);
    }

    public Project getProject() { return project; }
    public void setProject(Project project) {
        this.project = project;
        firePropertyChange(PROJECT);
    }

    public Task getTask() { return task; }
    public void setTask(Task task) {
        this.task = task;
        firePropertyChange(TASK);
    }

    public List<Project> getProjects() { return projects; }
    public void setProjects(List<Project> projects) {
        this.projects = projects;
        firePropertyChange(PROJECTS);
    }

    public List<Task> getTasks() { return tasks; }
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        firePropertyChange(TASKS);
    }
}
