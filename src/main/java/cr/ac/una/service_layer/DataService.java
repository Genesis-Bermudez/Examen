package cr.ac.una.service_layer;

import cr.ac.una.data_access_layer.Data;
import cr.ac.una.data_access_layer.IFileStore;
import cr.ac.una.data_access_layer.DataFileStore;
import cr.ac.una.domain_layer.*;
import cr.ac.una.utilities.ChangeType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DataService implements IService<Project> {

    private static DataService instance;
    private final IFileStore<Data> dataFileStore;
    private final List<IServiceObserver<Project>> observers = new ArrayList<>();

    // ---- Constructor ----

    private DataService() {
        this.dataFileStore = new DataFileStore(new File("data.xml"));
    }

    // ---- GetInstance ----

    public static DataService getInstance() {
        if (instance == null) {
            instance = new DataService();
        }
        return instance;
    }

    // ---- GetData ----

    private Data getData() {
        List<Data> all = dataFileStore.readAll();
        return (all.isEmpty()) ? Data.getInstance() : all.get(0);
    }

    // ---- Save ----

    private void save(Data data) {
        dataFileStore.writeAll(List.of(data));
    }

    // ---- Proyectos ----

    public void agregarProyecto(Project p) {
        Data data = getData();
        data.getProjects().add(p);
        save(data);
        notify(ChangeType.CREATED, p);
    }

    @Override
    public void agregar(Project project) {
        agregarProyecto(project);
    }

    @Override
    public void borrar(int id) {
        Data data = getData();
        Project removed = null;
        for (Project p : data.getProjects()) {
            if (p.getCode().equals(String.valueOf(id))) {
                removed = p;
                break;
            }
        }
        if (removed != null) {
            data.getProjects().remove(removed);
            save(data);
            notify(ChangeType.DELETED, removed);
        }
    }

    @Override
    public void actualizar(Project project) {
        Data data = getData();
        for (int i = 0; i < data.getProjects().size(); i++) {
            if (data.getProjects().get(i).getCode().equals(project.getCode())) {
                data.getProjects().set(i, project);
                break;
            }
        }
        save(data);
        notify(ChangeType.UPDATED, project);
    }

    public List<Project> leerProyectos() { return leerTodos(); }

    @Override
    public List<Project> leerTodos() { return getData().getProjects(); }

    // ---- Usuarios ----

    public List<User> readUsers() { return getData().getUsers(); }

    // ---- Tareas ----

    public void agregarTareas(Project project, Task task) {
        Data data = getData();

        for (Project p : data.getProjects()) {
            if (p.getCode().equals(project.getCode())) {
                p.getTasks().add(task);
                break;
            }
        }
        data.getTasks().add(task);
        save(data);
        notify(ChangeType.UPDATED, project);
    }

    public void actualizarTarea(Project project, Task task) {
        Data data = getData();
        for (Project p : data.getProjects()) {
            if (p.getCode().equals(project.getCode())) {
                List<Task> tasks = p.getTasks();
                for (int i = 0; i < tasks.size(); i++) {
                    if (tasks.get(i).getCode().equals(task.getCode())) {
                        tasks.set(i, task);
                        break;
                    }
                }
            }
        }
        save(data);
    }

    public List<Task> leerTodasTareas() {
        return getData().getTasks();
    }

    public List<Task> leerTareas() {
        return leerTodasTareas();
    }

    // ---- Observadores ----

    @Override
    public void addObserver(IServiceObserver<Project> l) { if (l != null) observers.add(l); }

    private void notify(ChangeType t, Project e) {
        for (IServiceObserver<Project> l : observers) {
            l.onDataChanged(t, e);
        }
    }
}
