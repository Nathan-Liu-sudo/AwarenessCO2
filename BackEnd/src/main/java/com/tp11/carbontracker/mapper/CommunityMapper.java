package com.tp11.carbontracker.mapper;

import com.tp11.carbontracker.entity.CommunityData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommunityMapper {

    @Select("SELECT * FROM suburb WHERE suburb = #{suburb}")
    @Results({
            @Result(property = "postcode", column = "postcode"),
            @Result(property = "suburb", column = "suburb")
    })
    CommunityData findSuburbBySuburbName(@Param("suburb") String suburb);

    @Select("SELECT average_emissions_per_customer_kg_co2e FROM electricity WHERE postcode = #{postcode} AND year = #{year}")
    double findElecByPostcodeAndYear(@Param("postcode") Integer postcode, @Param("year") Integer year);

    @Select("SELECT average_emissions_per_customer_kg_co2e FROM gas WHERE postcode = #{postcode} AND year = #{year}")
    double findGasByPostcodeAndYear(@Param("postcode") Integer postcode, @Param("year") Integer year);
}
