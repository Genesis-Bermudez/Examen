package cr.ac.una.domain_layer;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlID;

@XmlAccessorType(XmlAccessType.FIELD)
public class User {
    @XmlID
    private String id;
    private String name;
    private String email;
    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    public User(){}

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

    @Override
    public String toString() {
        return name;
    }
}
