package cr.ac.una.domain_layer;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Project {
    @XmlID
    public String code;
    public String description;
    @XmlIDREF
    public User leader;

    @XmlElementWrapper(name = "tasks")
    @XmlIDREF
    public List<Task> tasks;

    // ---- Constructores ----

    public Project(){}

    public Project(String description, User leader) {
        int n = Code.nextProjectCode();
        this.code = String.format("PJ-%04d", n);
        this.leader = leader;
        this.description = description;
        this.tasks = new ArrayList<Task>();
    }

    public Project(int value){
        this.code = String.valueOf(value);
        this.leader = null;
        this.description = "";
        this.tasks = null;
    }

    // ---- Gets ----

    public String getCode(){
        return code;
    }
    public User getLeader(){
        return leader;
    }
    public String getDescription(){
        return description;
    }
    public List<Task> getTasks(){
        return tasks;
    }

    // ---- addTask ----

    public void addTask(Task task) throws Exception {
        if(!tasks.contains(task)){
            tasks.add(task);
        }
        else{
            throw new Exception("La tarea ya se encontraba en la lista");
        }
    }

}
