package cn.fengyunxiao.nest.dao;

import cn.fengyunxiao.nest.entity.Link;
import cn.fengyunxiao.nest.entity.Reply;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface LinkDao {
    @Insert({ "insert into `link`(`href`,`title`,`rank`) " +
            " values(#{href},#{title},#{rank})" })
    int insert(Link link);

    @Select({ "select * from `link` order by `rank` asc" })
    List<Link> listLink();

    @Select({ "select * from `link` where href=#{href} limit 1" })
    Link getLink(@Param("href") String href);

    @Delete({"delete from `link` where `href` = #{href}"})
    int delete(@Param("href") String href);

    @Update("update `link` set `rank`=#{rank},`title`=#{title} " +
            "where `href`=#{href}")
    int update(Link link);
}
