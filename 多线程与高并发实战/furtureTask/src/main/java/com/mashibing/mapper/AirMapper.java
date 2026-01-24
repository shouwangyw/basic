package com.mashibing.mapper;

import com.mashibing.entity.Air;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * @author zjw
 * @description
 */
public interface AirMapper {

    @Select("select * from air")
    List<Air> findAll();

    // 提供一个查询需要推送数据的SQL总条数
    @Select("select count(1) from air where monitor_time = #{monitorTime}")
    long selectCountNeedPush(@Param("monitorTime")String monitorTime);

    // 查询今日第一个需要推送的数据,目的是获取ID方便计算
    @Select("select id from air where monitor_time = #{monitorTime} order by id limit 1")
    Long selectOneNeedPush(@Param("monitorTime")String monitorTime);

    // 查询需要推送的数据的内容，基于ID范围去查询。
    @Select("select * from air where monitor_time = #{monitorTime} and id between #{fromId} and #{toId} order by id")
    List<Air> selectByIdAndLimit(@Param("monitorTime")String monitorTime,@Param("fromId") long fromId,@Param("toId") long toId);
}
