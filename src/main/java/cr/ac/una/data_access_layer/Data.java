package cr.ac.una.data_access_layer;
import cr.ac.una.domain_layer.*;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/*
* Data contiene todos los usuarios, proyectos y tareas.
* Sigue el patrón Singleton, asi como la estructura de la data xml brindada
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Data {
    private static Data instance = null;

    @XmlElementWrapper(name = "users")
    @XmlElement(name = "user")
    private List<User> users;

    @XmlElementWrapper(name = "projects")
    @XmlElement(name = "project")
    private List<Project> projects;

    @XmlElementWrapper(name = "tasks")
    @XmlElement(name = "task")
    private List<Task> tasks;

    // ---- Constructor ----

    private Data(){
        users = new ArrayList<User>();
        projects = new ArrayList<Project>();
        tasks = new ArrayList<Task>();
    }

    // ---- getInstance ----

    public static Data getInstance(){
        if(instance == null){
            instance = new Data();
        }
        return instance;
    }

    // ---- Gets ----

    public List<User> getUsers() {
        return users;
    }
    public List<Project> getProjects() {
        return projects;
    }
    public List<Task> getTasks() {
        return tasks;
    }
}

