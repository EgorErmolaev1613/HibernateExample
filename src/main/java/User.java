import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    public int getId() {
        return id;
    }

    public User() {

    }

    public User(String name, int price) {
        this.name = name;
        this.age = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
