package cn.fengyunxiao.nest.dao;

import cn.fengyunxiao.nest.entity.Install;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;

@Mapper
@Repository
public interface InstallDao {
    @Insert({ "insert into install(`key`, `value`, `intro`) VALUES (#{key},#{value},#{intro}) ON DUPLICATE KEY UPDATE `value`=#{value},`intro`=#{intro}" })
    int insertOrupdate(final Install install);

    @Select({ "select * from install" })
    List<Install> listInstall();

    @Select({ "select now()" })
    Timestamp getCurrentTime();
}
