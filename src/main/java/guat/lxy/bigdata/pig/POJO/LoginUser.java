package guat.lxy.bigdata.pig.POJO;

import lombok.Data;

@Data
public class LoginUser {
  private long id;
  private String name;
  private String password;
  private String email;
  private String phone;
}
