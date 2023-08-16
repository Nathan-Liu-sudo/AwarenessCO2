package com.tp11.carbontracker.service;

import com.tp11.carbontracker.entity.CommunityData;
import com.tp11.carbontracker.mapper.CommunityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarbonFootprintService {
    private final CommunityMapper communityMapper;

    @Autowired
    public CarbonFootprintService(CommunityMapper communityMapper) {
        this.communityMapper = communityMapper;
    }

    public CommunityData findSuburbBySuburbName(String suburb) {
        return communityMapper.findSuburbBySuburbName(suburb);
    }

    public double findElecByPostcodeAndYear(Integer postcode, Integer year) {
        return communityMapper.findElecByPostcodeAndYear(postcode, year);
    }

    public double findGasByPostcodeAndYear(Integer postcode, Integer year) {
        return communityMapper.findGasByPostcodeAndYear(postcode, year);
    }
}
