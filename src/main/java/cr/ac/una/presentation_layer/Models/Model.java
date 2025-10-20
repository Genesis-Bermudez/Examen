package cr.ac.una.presentation_layer.Models;

import cr.ac.una.domain_layer.Project;
import cr.ac.una.domain_layer.Task;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {
    private Project proyecto;
    private Task task;
    private List<Project> proyectos;
    private List<Task> tasks;

    public static final String PROJECT = "project";
    public static final String TASK = "task";
    public static final String PROJECTS = "projects";
    public static final String TASKS = "tasks";

    public Model() {
        Project project = new Project(-1);
        Task task = null;
        /*Service service=Service.getInstance();
        projects = service.getProyects();*/
        tasks = new ArrayList<>();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(PROJECT);
        firePropertyChange(TASK);
        firePropertyChange(PROJECTS);
        firePropertyChange(TASKS);
    }

    public Project getProject() { return proyecto; }
    public void setProject(Project proyecto) {
        this.proyecto = proyecto;
        firePropertyChange(PROJECT);
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
        firePropertyChange(TASK);
    }

    public List<Project> getProjects() {
        return proyectos;
    }

    public void setProjects(List<Project> proyectos) {
        this.proyectos = proyectos;
        firePropertyChange(PROJECTS);
    }

    public List<Task> getTasks() {
        return tasks;
    }
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        firePropertyChange(TASKS);
    }
}
