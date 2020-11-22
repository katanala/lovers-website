package cn.fengyunxiao.nest.dao;

import cn.fengyunxiao.nest.entity.Chat;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface ChatDao {
    @Select({ "select * from `chat` where cid<#{cid} order by cid desc limit #{number}" })
    List<Chat> list(@Param("cid") final int cid, @Param("number") final int number);

    @Insert({ "insert into `chat`(male,content,pubtime) values(#{male},#{content},now())" })
    int insertChat(final Chat message);

    @Update({ "update `chat` set `content`=#{content} where cid=#{cid}" })
    int updateChat(final Chat chat);
}
