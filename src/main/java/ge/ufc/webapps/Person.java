package ge.ufc.webapps;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "person", propOrder = { "firstname", "lastname", "age" })
public class Person
{
    private static final Gson gson = new GsonBuilder().create();
    @XmlElement(name = "first-name")
    public String firstname;
    @XmlElement(name = "last-name")
    public String lastname;
    @XmlElement
    public int age;
    @XmlAttribute
    public int id;
    public Person() {}
    public Person(String firstname,String lastname,int age)
    {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
    }
    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
