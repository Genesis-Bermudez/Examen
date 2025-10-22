package cr.ac.una.domain_layer;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Task {
    @XmlID
    public String code;
    public String description;
    public String finishDate;
    public String priority;
    public String status;

    @XmlIDREF
    public User worker;

    // ---- Constructores ----

    public Task() {}

    public Task(String description, String finishDate, String priority, String status, User worker) {
        int n = Code.nextTaskCode();
        this.code = String.format("T-%03d", n);
        this.description = description;
        this.finishDate = finishDate;
        this.priority = priority;
        this.status = status;
        this.worker = worker;
    }

    // ---- Gets ----

    public String getCode() {
        return code;
    }
    public String getDescription() {
        return description;
    }
    public String getFinishDate() {
        return finishDate;
    }
    public String getPriority() {
        return priority;
    }
    public String getStatus() {
        return status;
    }
    public User getWorker() {
        return worker;
    }

    // ---- Sets ----

    public void setPriority(String priority) {
        this.priority = priority;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
