package cn.fengyunxiao.nest.dao;

import cn.fengyunxiao.nest.entity.Blog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface BlogDao {
    @Options(useGeneratedKeys = true, keyProperty = "bid", keyColumn = "bid")
    @Insert("insert into blog(`rank`,title,keyword,content,modtime,url) " +
            " values(#{rank},#{title},#{keyword},#{content},#{modtime},#{url})")
    public int insert(Blog blog);

    @Update("update blog set `rank`=#{rank},title=#{title},keyword=#{keyword}, " +
            " content=#{content},modtime=#{modtime},url=#{url} where bid=#{bid}")
    public int update(Blog blog);

    @Select("select * from blog where bid=#{bid}")
    public Blog get(@Param("bid") int bid);

    // 查找最后一个草稿
    @Select("select bid from blog where `rank`<0 order by bid desc limit 1")
    public Integer getLastDraft();

    @Select("SELECT t1.bid,t1.title FROM blog AS t1 JOIN (SELECT ROUND(RAND() * (SELECT MAX(bid)-#{number} FROM blog)) AS bid) AS t2 WHERE t1.bid >= t2.bid ORDER BY t1.bid ASC LIMIT #{number}")
    public List<Blog> listRand(@Param("number") int number);

    // 按 key 精确度匹配，key 长度 > 1
    @Select("select bid,`rank`,title,keyword,modtime from blog where " +
            " MATCH (title,keyword) AGAINST (#{key} IN NATURAL LANGUAGE MODE) limit #{cur},#{per}")
    public List<Blog> searchByKey2(@Param("key") String key,
                                   @Param("cur") int cur,
                                   @Param("per") int per);

    // key 为 null 或长度=1时使用。后发表的排在最前
    @Select("select bid,`rank`,title,keyword,modtime from blog order by bid desc limit #{cur},#{per}")
    public List<Blog> listByPage(@Param("cur") int cur, @Param("per") int per);

    @Select("select count(*) from blog")
    public int countAll();

    @Select("select bid from blog where url = #{url}")
    public Integer getBidByUrl(@Param("url") String url);
}
