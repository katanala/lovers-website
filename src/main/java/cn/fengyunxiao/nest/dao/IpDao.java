package cn.fengyunxiao.nest.dao;

import cn.fengyunxiao.nest.entity.Chart;
import cn.fengyunxiao.nest.entity.Ip;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Mapper
@Repository
public interface IpDao {
    @Select({ "select * from ip order by iid desc limit #{current},#{per}" })
    List<Ip> listIp(@Param("current") final int current, @Param("per") final int per);

    @Select({ "select count(*) as total from ip" })
    int total();

    @Select({ "select * from ip where ip=#{ip}" })
    Ip get(final String ip);

    @Insert({ "insert into ip(ip,region,city,isp,curtime) values(#{ip},#{region},#{city},#{isp},now())" })
    int insertIp(final Ip ip);

    @Select({ "select city as name,count(*) as num from ip group by city order by num desc limit 5" })
    List<Chart> listTop5City();

    @Select({ "select isp as name,count(*) as num from ip group by isp order by num desc limit 5" })
    List<Chart> listTop5Isp();

    @Select({ "select DATE_FORMAT(curtime,'%Y-%m') as name,count(*) as num from ip where curtime>#{time} group by name order by name desc limit 5" })
    List<Chart> listMonth5(@Param("time") final Timestamp time);

    @Select({ "select DATE_FORMAT(curtime,'%Y-%m-%d') as name,count(*) as num from ip where curtime>#{time} group by name order by name desc limit 5" })
    List<Chart> listDay5(@Param("time") final Timestamp time);
}
