package cn.fengyunxiao.nest.dao;

import java.util.List;

import org.apache.ibatis.annotations.*;
import cn.fengyunxiao.nest.entity.Image;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ImageDao {
	@Insert("insert into image(bid,name,pubtime) values(#{bid},#{name},now())")
	public int insertImage(Image image);
	
	@Select("select * from image where bid=-1 and iid<#{iid} order by iid desc limit #{number}")
	public List<Image> listUploadImage(@Param("iid") long iid, @Param("number") int number);
	
	@Select("select * from image order by iid desc limit #{cur},#{number}")
	public List<Image> listImageByPage(@Param("cur") int cur, @Param("number") int number);
	
	@Delete("delete from image where iid = #{iid}")
	public int deleteImage(@Param("iid") int iid);
	
	@Select("select * from image where iid = #{iid}")
	public Image queryImage(@Param("iid") int iid);
	
	@Select("select count(*) from image")
	public int getTotal();
}
