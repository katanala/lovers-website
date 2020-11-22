package cn.fengyunxiao.nest.dao;

import cn.fengyunxiao.nest.entity.Timeline;
import org.springframework.stereotype.*;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
@Repository
public interface TimelineDao {
    @Insert({ "insert into `timeline`(year,title,content1,content2,content3) values(#{year},#{title},#{content1},#{content2},#{content3})" })
    int insertTimeline(final Timeline timeline);

    @Update({ "update `timeline` set year=#{year},title=#{title},content1=#{content1},content2=#{content2},content3=#{content3} where lineid=#{lineid}" })
    int updateTimeline(final Timeline timeline);

    @Select({ "select * from `timeline` order by year desc" })
    List<Timeline> listTimeline();
}
