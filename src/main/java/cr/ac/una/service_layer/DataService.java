package cr.ac.una.service_layer;

import cr.ac.una.data_access_layer.Data;
import cr.ac.una.data_access_layer.IFileStore;
import cr.ac.una.domain_layer.*;
import cr.ac.una.utilities.ChangeType;

import java.util.ArrayList;
import java.util.List;

public class DataService implements IService<Project> {

    private final IFileStore<Data> dataFileStore;
    private final List<IServiceObserver<Project>> observers = new ArrayList<>();

    public DataService(IFileStore<Data> dataFileStore) {
        this.dataFileStore = dataFileStore;
    }

    private Data getData() {
        List<Data> all = dataFileStore.readAll();
        return (all.isEmpty()) ? Data.getInstance() : all.get(0);
    }

    private void save(Data data) {
        dataFileStore.writeAll(List.of(data));
    }

    // ------------------ PROYECTOS ------------------

    @Override
    public void agregar(Project project) {
        Data data = getData();
        data.getProjects().add(project);
        save(data);
        notify(ChangeType.CREATED, project);
    }

    @Override
    public void borrar(int id) {
        Data data = getData();
        Project removed = null;
        for (Project p : data.getProjects()) {
            if (p.getCode().equals(String.valueOf(id))) { removed = p; break; }
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
            if (data.getProjects().get(i).getCode() == project.getCode()) {
                data.getProjects().set(i, project);
                break;
            }
        }
        save(data);
        notify(ChangeType.UPDATED, project);
    }

    @Override
    public List<Project> leerTodos() {
        return getData().getProjects();
    }

    @Override
    public Project leerPorId(int id) {
        return getData().getProjects().stream()
                .filter(p -> p.getCode().equals(String.valueOf(id)))
                .findFirst().orElse(null);
    }

    @Override
    public void addObserver(IServiceObserver<Project> l) {
        if (l != null) observers.add(l);
    }

    private void notify(ChangeType t, Project e) {
        for (IServiceObserver<Project> l : observers) l.onDataChanged(t, e);
    }

    // ------------------ USUARIOS ------------------

    public List<User> readUsers() {
        return getData().getUsers();
    }

    // ------------------ TAREAS ------------------

    public void agregrarTareas(Project project, Task task) {
        Data data = getData();
        for (Project p : data.getProjects()) {
            if (p.getCode() == project.getCode()) {
                p.getTasks().add(task);
                data.getTasks().add(task);
                break;
            }
        }
        save(data);
        notify(ChangeType.UPDATED, project);
    }

    public void actualizarTarea(Project project, Task tarea) {
        Data data = getData();
        save(data);
        notify(ChangeType.UPDATED, project);
    }
}
