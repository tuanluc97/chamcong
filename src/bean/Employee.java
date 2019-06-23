package bean;

import java.util.List;

public class Employee {
    String id;
    String name;
    List<DateInfo> listDateInfo;

    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DateInfo> getListDateInfo() {
        return listDateInfo;
    }

    public void setListDateInfo(List<DateInfo> listDateInfo) {
        this.listDateInfo = listDateInfo;
    }
}
