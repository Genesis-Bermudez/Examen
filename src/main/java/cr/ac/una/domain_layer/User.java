package cr.ac.una.domain_layer;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class User {
    @XmlID
    private String id;
    private String name;
    private String email;

    // ---- Constructores ----

    public User(){}

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // ---- Gets ----

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }

    public boolean equals(User value){
        return this.id.equals(value.getId());
    }

    // ---- toString ----

    @Override
    public String toString() {
        return name;
    }
}
