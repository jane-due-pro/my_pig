package guat.lxy.bigdata.pig.POJO;

import lombok.Data;

@Data
public class Dept {
    int id;
    String name;
    public Dept(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
