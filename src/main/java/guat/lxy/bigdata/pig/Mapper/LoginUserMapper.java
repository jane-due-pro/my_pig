package guat.lxy.bigdata.pig.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginUserMapper {
    @Select("SELECT password FROM application.loginuser WHERE name = #{name}")
    String getPasswordWithLoginUser(String name);
}
