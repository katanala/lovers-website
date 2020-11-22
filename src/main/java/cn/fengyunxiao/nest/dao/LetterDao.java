package cn.fengyunxiao.nest.dao;

import cn.fengyunxiao.nest.entity.Letter;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface LetterDao {
    @Select({ "select * from letter where lid<#{lid} order by lid desc limit #{number}" })
    List<Letter> listLetter(@Param("lid") final int lid, @Param("number") final int number);

    @Select({ "SELECT * FROM letter AS t1 JOIN (SELECT ROUND(RAND() * (SELECT MAX(lid)-#{number} FROM letter)) AS lid) AS t2 WHERE t1.lid >= t2.lid ORDER BY t1.lid ASC LIMIT #{number}" })
    List<Letter> listByRand(@Param("number") final int number);

    @Insert({ "insert into letter(nickname,content,ip,email,pubtime) values(#{nickname},#{content},#{ip},#{email},now())" })
    int insertLetter(final Letter letter);

    @Update({ "update `letter` set nickname=#{nickname},content=#{content} where lid=#{lid}" })
    int updateLetter(final Letter letter);

    @Update({ "update letter set zan=zan+1 where lid=#{lid}" })
    int zanLetter(@Param("lid") final int lid);
}
