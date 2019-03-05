package cn.fengyunxiao.nest.dao;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import cn.fengyunxiao.nest.entity.Chat;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ChatDao {
	@Select("select * from chat where cid<#{cid} order by cid desc limit #{number}")
	public List<Chat> list(@Param("cid") int cid, @Param("number") int number);
	
	@Insert("insert into chat(male,content,pubtime) values(#{male},#{content},now())")
	public int insertMessage(Chat message);
}
