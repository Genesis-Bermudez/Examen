package cr.ac.una.domain_layer;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlIDREF;

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

    public Task(String code, String description, String finishDate, String priority, String status, User worker) {
        this.code = code;
        this.description = description;
        this.finishDate = finishDate;
        this.priority = priority;
        this.status = status;
        this.worker = worker;
    }

    public Task() {}

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

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
