package com.mashibing.mapper;

import com.mashibing.entity.Air;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zjw
 * @description
 */
public interface AirMapper {

    @Select("select * from air where id = #{id}")
    Air findById(@Param("id") Long id);

    @Select("select count(1) from air")
    long findCount();

    @Select("select * from air limit #{offset},#{size}")
    List<Air> findByLimit(@Param("offset") long offset, @Param("size") long size);
}
