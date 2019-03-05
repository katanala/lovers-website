package cn.fengyunxiao.nest.dao;

import java.util.List;

import org.apache.ibatis.annotations.*;

import cn.fengyunxiao.nest.entity.Letter;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LetterDao {
	@Select("select * from letter where lid<#{lid} order by lid desc limit #{number}")
	public List<Letter> listLetter(@Param("lid") int lid, @Param("number") int number);
	
	@Select("SELECT * FROM letter AS t1 JOIN (SELECT ROUND(RAND() * (SELECT MAX(lid)-#{number} FROM letter)) AS lid) AS t2 WHERE t1.lid >= t2.lid ORDER BY t1.lid ASC LIMIT #{number}")
	public List<Letter> listByRand(@Param("number") int number);

	// @Options(useGeneratedKeys = true, keyProperty = "lid", keyColumn = "lid")
	@Insert("insert into letter(nickname,content,ip,pubtime) values(#{nickname},#{content},#{ip},now())")
	public int insertLetter(Letter letter);
	
	@Update("update letter set zan=zan+1 where lid=#{lid}")
	public int zanLetter(@Param("lid") int lid);
}
