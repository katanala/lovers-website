package cn.fengyunxiao.nest.dao;

import cn.fengyunxiao.nest.entity.Reply;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface ReplyDao {
    @Insert({ "insert into reply(lid,nickname,content,ip,curtime) values(#{lid},#{nickname},#{content},#{ip},#{curtime})" })
    int insert(final Reply reply);

    @Select({ "<script> select * from reply where lid in <foreach collection='lids' item='lid' open='(' separator=',' close=')'>#{lid}</foreach> limit 200 </script>" })
    List<Reply> listByLid(@Param("lids") final List<Integer> lids);

    @Select({ "select * from reply where replyid<#{replyid} order by replyid desc limit #{number}" })
    List<Reply> listReply(@Param("replyid") final int replyid, @Param("number") final int number);

    @Update({ "update `reply` set nickname=#{nickname},content=#{content} where replyid=#{replyid}" })
    int updateReply(final Reply reply);
}
