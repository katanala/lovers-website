package cn.fengyunxiao.nest.dao;

import cn.fengyunxiao.nest.entity.Blog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface BlogDao {
    @Options(useGeneratedKeys = true, keyProperty = "bid", keyColumn = "bid")
    @Insert({ "insert into blog(`rank`,title,keyword,content,modtime,url)  values(#{rank},#{title},#{keyword},#{content},#{modtime},#{url})" })
    int insert(final Blog blog);

    @Update({ "update blog set `rank`=#{rank},title=#{title},keyword=#{keyword},  content=#{content},modtime=#{modtime},url=#{url} where bid=#{bid}" })
    int update(final Blog blog);

    @Delete("delete from `blog` where `bid`=#{bid}")
    int delete(@Param("bid") int bid);

    @Select({ "select * from blog where `bid`=#{bid}" })
    Blog get(@Param("bid") int bid);

    @Select({ "select bid from blog where `rank`<0 order by bid desc limit 1" })
    Integer getLastDraft();

    @Select({ "SELECT t1.bid,t1.title FROM blog AS t1 JOIN (SELECT ROUND(RAND() * (SELECT MAX(bid)-#{number} FROM blog)) AS bid) AS t2 WHERE t1.bid >= t2.bid ORDER BY t1.bid ASC LIMIT #{number}" })
    List<Blog> listRand(@Param("number") final int number);

    @Select({ "select bid,`rank`,title,keyword,modtime from blog where  MATCH (title,keyword) AGAINST (#{key} IN NATURAL LANGUAGE MODE) limit #{cur},#{per}" })
    List<Blog> searchByKey2(@Param("key") final String key, @Param("cur") final int cur, @Param("per") final int per);

    @Select({ "select bid,`rank`,title,keyword,modtime from blog order by bid desc limit #{cur},#{per}" })
    List<Blog> listByPage(@Param("cur") final int cur, @Param("per") final int per);

    @Select({ "select count(*) from blog" })
    int countAll();

    @Select({ "select bid from blog where url = #{url}" })
    Integer getBidByUrl(@Param("url") final String url);
}
