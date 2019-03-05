package cn.fengyunxiao.nest.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import cn.fengyunxiao.nest.entity.Chart;
import org.apache.ibatis.annotations.*;

import cn.fengyunxiao.nest.entity.Ip;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface IpDao {
	@Select("select * from ip order by iid desc limit #{current},#{per}")
	public List<Ip> listIp(@Param("current") int current, @Param("per") int per);

	@Select("select count(*) as total from ip")
	public int total();
	
	@Insert("insert into ip(ip,region,city,isp,curtime) values(#{ip},#{region},#{city},#{isp},now())")
	public int insertIp(Ip ip);


    /********************************************** 统计部分 **********************************************/
	// 前 5 城市
    @Select("select city as name,count(*) as num from ip group by city order by num desc limit 5")
	public List<Chart> listTop5City();

    // 前 5 运营商
	@Select("select isp as name,count(*) as num from ip group by isp order by num desc limit 5")
	public List<Chart> listTop5Isp();

	// 最后 5 月
	@Select("select DATE_FORMAT(curtime,'%Y-%m') as name,count(*) as num from ip where curtime>#{time} group by name order by name desc limit 5")
	public List<Chart> listMonth5(@Param("time") Timestamp time);

	// 最后 5 天
	@Select("select DATE_FORMAT(curtime,'%Y-%m-%d') as name,count(*) as num from ip where curtime>#{time} group by name order by name desc limit 5")
	public List<Chart> listDay5(@Param("time") Timestamp time);
}
