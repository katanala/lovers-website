package cn.fengyunxiao.nest.dao;

import cn.fengyunxiao.nest.entity.Image;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ImageDao {
    @Insert({ "insert into image(bid,name,pubtime) values(#{bid},#{name},now())" })
    int insertImage(final Image image);

    @Select({ "select * from image where bid=-1 and iid<#{iid} order by iid desc limit #{number}" })
    List<Image> listUploadImage(@Param("iid") final long iid, @Param("number") final int number);

    @Select({ "select * from image order by iid desc limit #{cur},#{number}" })
    List<Image> listImageByPage(@Param("cur") final int cur, @Param("number") final int number);

    @Delete({ "delete from image where iid = #{iid}" })
    int deleteImage(@Param("iid") final int iid);

    @Select({ "select * from image where iid = #{iid}" })
    Image queryImage(@Param("iid") final int iid);

    @Select({ "select count(*) from image" })
    int getTotal();
}
