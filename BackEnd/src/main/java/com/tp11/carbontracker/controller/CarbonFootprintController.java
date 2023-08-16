package com.tp11.carbontracker.controller;

import com.tp11.carbontracker.entity.CommunityData;
import com.tp11.carbontracker.service.CarbonFootprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class CarbonFootprintController {
    private final CarbonFootprintService carbonFootprintService;

    @Autowired
    public CarbonFootprintController(CarbonFootprintService carbonFootprintService) {
        this.carbonFootprintService = carbonFootprintService;
    }

    @GetMapping("/")
    public String helloWorld() {
        return "Hello, World!";
    }

    @GetMapping("/getUsage")
    public ResultDTO getUsage(@RequestParam String suburb, @RequestParam Integer year) {
        CommunityData communityData = carbonFootprintService.findSuburbBySuburbName(suburb);
        if (communityData != null) {
            double elec = carbonFootprintService.findElecByPostcodeAndYear(communityData.getPostcode(), year);
            double gas = carbonFootprintService.findGasByPostcodeAndYear(communityData.getPostcode(), year);

            return new ResultDTO(year, communityData.getPostcode(), elec, gas);
        } else {
            return new ResultDTO(0,0,0.0,0.0);
        }
    }

    // 定义一个DTO类来封装返回结果
    public static class ResultDTO {
        private Integer year;
        private Integer postcode;
        private double elec;
        private double gas;

        public Integer getYear() {
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }

        public Integer getPostcode() {
            return postcode;
        }

        public void setPostcode(Integer postcode) {
            this.postcode = postcode;
        }

        public double getElec() {
            return elec;
        }

        public void setElec(double elec) {
            this.elec = elec;
        }

        public double getGas() {
            return gas;
        }

        public void setGas(double gas) {
            this.gas = gas;
        }

        public ResultDTO(Integer year, Integer postcode, double elec, double gas) {
            this.year = year;
            this.postcode = postcode;
            this.elec = elec;
            this.gas = gas;
        }

    }

}
