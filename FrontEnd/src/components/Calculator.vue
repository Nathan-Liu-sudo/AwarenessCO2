<script setup lang="ts">

import { use } from "echarts/core";
import { CanvasRenderer } from "echarts/renderers";
import { PieChart } from "echarts/charts";
import {
  TitleComponent,
  TooltipComponent,
  LegendComponent
} from "echarts/components";
import VChart from "vue-echarts";
import { ref } from "vue";
import axios from 'axios';
import { ElMessage } from 'element-plus'

use([
  CanvasRenderer,
  PieChart,
  TitleComponent,
  TooltipComponent,
  LegendComponent
]);

defineProps<{ msg: string }>();

const page_container_height = ref("7%")
const showElecInput = ref(true)
const showGasInput = ref(true)
const showToCompareBtn = ref(true)
const showBcakBtn = ref(false)
const loading = ref(true)

const showInputFields = ref(false)
const showCard1 = ref(false)
const showCard2 = ref(false)
const initHeight = ref("500px")
const showCalculateBtn = ref(true)
const showCompareBtn = ref(false)
const elec = ref();
const gas = ref();
const newUserElec = ref()
const newUserGas = ref()

// define OptionType type
type OptionType = { label: string; value: string };

const years: OptionType[] = [
  { label: '2020', value: '2020' },
  { label: '2021', value: '2021' },
  { label: '2022', value: '2022' },
];

const suburbs: OptionType[] = [
  { label: 'Endeavour Hills', value: 'Endeavour Hills' },
  { label: 'Beaconsfield', value: 'Beaconsfield' },
  { label: 'Clyde and Clyde North', value: 'Clyde and Clyde North' },
  { label: 'Hallam', value: 'Hallam' },
  { label: 'Berwick and Harkaway', value: 'Berwick and Harkaway' },
  { label: 'Hampton Park', value: 'Hampton Park' },
  { label: 'Lysterfield South', value: 'Lysterfield South' },
  { label: 'Narre Warren North', value: 'Narre Warren North' },
  { label: 'Lynbrook and Lyndhurst', value: 'Lynbrook and Lyndhurst' },
  { label: 'Blind Bight, Tooradin, and Warneet', value: 'Blind Bight, Tooradin, and Warneet' },
  { label: 'Narre Warren and Narre Warren South', value: 'Narre Warren and Narre Warren South' },
  { label: 'Pearcedale', value: 'Pearcedale' },
  { label: 'Doveton and Eumemmerring', value: 'Doveton and Eumemmerring' },
  { label: 'Botanic Ridge, Cannons Creek, Cranbourne, Cranbourne East, Cranbourne North, Cranbourne South, Cranbourne West, Devon Meadows and Junction Village', value: 'Botanic Ridge, Cannons Creek, Cranbourne, Cranbourne East, Cranbourne North, Cranbourne South, Cranbourne West, Devon Meadows and Junction Village' },
];

const year = ref()
const suburb = ref()
const elecUsage = ref()
const gasUsage = ref()

const calculate = () => {
  if (elecUsage.value != null && gasUsage.value != null){
    loading.value = false
    showCard1.value = true
    // 更新 option 数据，重新计算饼图数据
    option.value.series[0].data = [
      { value: elecUsage.value * 0.85, name: "Electricity" },
      { value: gasUsage.value * 4, name: "Gas" },
    ];

    // 调用 renderChart 方法重新渲染图表
    renderChart();
  } else {
      ElMessage({
      message: 'Please complete the form. Make sure you did not input "e", "-", "+". ',
      type: 'warning',
    })
    loading.value = true
  }
  
};

const renderChart = () => {
  const chartDom = document.getElementById("chart");
  const myChart = VChart.init(chartDom);
  myChart.setOption(option.value);
};

const renderChart2 = () => {
  const chartDom = document.getElementById("chart2");
  const myChart = VChart.init(chartDom);
  myChart.setOption(option2.value);
  const chartDom2 = document.getElementById("chart3");
  const myChart2 = VChart.init(chartDom2);
  myChart2.setOption(option3.value);
};

// search data from db
const fetchData = () => {
  const apiUrl = 'localhost:8080/getUsage';
  axios
    .get(apiUrl, {
      params: {
        suburb: suburb.value,
        year: parseInt(year.value)
      }
    })
    .then(response => {
      elec.value = response.data.elec;
      gas.value = response.data.gas;
      newUserElec.value = Math.floor(elecUsage.value * 0.85 * 12)
      newUserGas.value = gasUsage.value * 4 * 12
      // 更新 option 数据，重新计算饼图数据
      option2.value.series[0].data = [
        { value: newUserElec.value, name: "your" },
        { value: elec.value, name: "others'" },
      ];
      option3.value.series[0].data = [
        { value: newUserGas.value, name: "your" },
        { value: gas.value, name: "others'"},
      ];
      renderChart2();
    })
    // .catch(error => {
    //   console.error('Error fetching data:', error);
    // });
};

const compareWithOthers = () => {
  if (year.value != null && suburb.value != null) {
    loading.value = false
    showCard2.value = true
    fetchData();
  } else {
    ElMessage({
      message: 'Please complete the form.',
      type: 'warning',
    })
  }
  
}

const toCompare = () => {
  if (elecUsage.value != null && gasUsage.value != null){
    page_container_height.value = "7%"
    showElecInput.value = false
    showGasInput.value = false
    loading.value = true

    showInputFields.value = true
    showCard1.value = false
    initHeight.value = "550px"
    showCalculateBtn.value = false
    showCompareBtn.value = true
    showToCompareBtn.value = false
    showBcakBtn.value = true
  } else {
      ElMessage({
      message: 'Please complete the form. Make sure you did not input "e", "-", "+". ',
      type: 'warning',
    })
  }
  
};

const goBack = () => {
  showToCompareBtn.value = true
  showBcakBtn.value = false
  page_container_height.value = "7%"
  showElecInput.value = true
  showGasInput.value = true
  initHeight.value = "500px"

  showInputFields.value = false
  loading.value = true
  
  showCompareBtn.value = false
  showCalculateBtn.value = true
  showCard2.value = false
};

const option = ref({
  title: {
    text: "",
    left: "center",
  },
  tooltip: {
    trigger: "item",
    formatter: "{a} <br/>{b} : {c} ({d}%)"
  },
  legend: {
    left: "center",
    top: "15%",
    data: ["Electricity", "Gas"]
  },
  color:['#00adff', '#fff900'],
  series: [
    {
      name: "Carbon emission from",
      type: "pie",
      radius: "55%",
      center: ["50%", "60%"],
      data: [
        { value: elecUsage.value * 0.85, name: "Electricity" },
        { value: gasUsage.value * 4, name: "Gas" },
        
      ],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: "rgba(0, 0, 0, 0.5)"
        }
      }
    }
  ]
});

const option2 = ref({
  title: {
    text: "from Electricity",
    left: "center",
    top: "13%",
  },
  tooltip: {
    trigger: "item",
    formatter: "{a} <br/>{b} : {c} ({d}%)"
  },
  legend: {
    data: ["your", "others'"],
  },
  color:['#00adff', '#3b5764'],
  series: [
    {
      name: 'Access From',
      type: 'pie',
      radius: ['40%', '70%'],
      center: ["50%", "60%"],
      avoidLabelOverlap: false,
      itemStyle: {
        borderRadius: 10,
        borderColor: '#fff',
        borderWidth: 2
      },
      label: {
        show: false,
        position: 'center'
      },
      emphasis: {
        label: {
          show: true,
          fontSize: 40,
          fontWeight: 'bold'
        }
      },
      labelLine: {
        show: false
      },
      data: [
        { value: newUserElec.value, name: "User" },
        { value: elec.value, name: suburb.value },
        
      ],
    }
  ]
});

const option3 = ref({
  title: {
    text: "from Gas",
    left: "center",
    top: "13%",
  },
  tooltip: {
    trigger: "item",
    formatter: "{a} <br/>{b} : {c} ({d}%)"
  },
  legend: {
    data: ["your", "others'"],
  },
  color:['#fff900', '#908e21'],
  series: [
    {
      name: 'Access From',
      type: 'pie',
      radius: ['40%', '70%'],
      center: ["50%", "60%"],
      avoidLabelOverlap: false,
      itemStyle: {
        borderRadius: 10,
        borderColor: '#fff',
        borderWidth: 2
      },
      label: {
        show: false,
        position: 'center'
      },
      emphasis: {
        label: {
          show: true,
          fontSize: 40,
          fontWeight: 'bold'
        }
      },
      labelLine: {
        show: false
      },
      data: [
        { value: newUserGas, name: "User" },
        { value: gas.value, name: suburb.value },
        
      ],
    }
  ]
});

</script>

<template>
  <div class="background">
      <img src="../assets/forest.jpg" style="width: 100%; height: 100%;"/>
  </div>
  <el-row class="page-container">
    <el-col :span=8>
      <!-- example components -->
      <el-card class="box-card">
        <template #header>
          <div>
            <img src="../assets/carbon-footprint.svg" alt="Carbon Footprint SVG" class="svg-image"/>
          </div>
          <div>
            <h4>Please complete the form.</h4>
          </div>
        </template>
        <div class="form-container">
          <el-select-v2 v-if="showInputFields" v-model="year" :options="years" placeholder="Pick a year" class="form-item" clearable/>
          <el-select-v2  v-if="showInputFields" v-model="suburb" :options="suburbs" placeholder="Select the suburb" class="form-item" allow-create :reserve-keyword="false" filterable clearable />
          <el-input-number :min="1" v-if="showElecInput" v-model="elecUsage" placeholder="Electricity Usage(KWh)" class="form-item" />
          <el-input-number :min="1" v-if="showGasInput" v-model="gasUsage" placeholder="Gas Usage(m^3)" class="form-item" />
          <el-button v-if="showCalculateBtn"  @click="calculate" type="success" round class="form-item">Calculate</el-button>
          <el-button v-if="showCompareBtn"  @click="compareWithOthers" type="success" round class="form-item">Compare</el-button>
          <el-button style="margin-left: 0px;" v-if="showToCompareBtn" @click="toCompare" type="primary" round class="form-item">Compare</el-button>
          <el-button style="margin-left: 0px;" v-if="showBcakBtn" @click="goBack" type="primary" round class="form-item">Go Back</el-button>
        </div>
      </el-card>
    </el-col>
    <el-col :span=16> 
      <el-card class="box-card2" v-loading="loading">
        <div v-if="showCard1">
          <div>
            <el-row>
              <el-col :span="16">
                <v-chart class="chart" :option="option" />
              </el-col>
              <el-col :span="8" class="carbon-result">
                <h3>Carbon Emission</h3>
                <p class="explanation-text">Electricity: {{elecUsage*0.85}} kg</p>
                <p class="explanation-text">Gas: {{gasUsage*4}} kg</p>
                <p class="explanation-text">Total: {{gasUsage*4+elecUsage*0.85}} kg</p>
              </el-col>
            </el-row> 
          </div>
          
        </div>
        <div v-if="showCard2">
          <div>
            <h2 class="compareTitle">Your Annual Carbon Emission VS {{ suburb }} residents average Annual Carbon Emission</h2>
            <el-row style="text-align: -webkit-center;">
              <el-col :span="12">
                <v-chart class="chart2" :option="option2" />
              </el-col>
              <el-col :span="12">
                <v-chart class="chart3" :option="option3" />   
              </el-col>
            </el-row>  
            <div class="compareComment">
              <h3>Carbon Emission</h3>
              <p class="explanation-text"><strong>Your carbon </strong>Electricity: {{newUserElec}} kg; Gas: {{newUserGas}} kg; Total: {{newUserElec+newUserGas}} kg</p>            
              <p class="explanation-text"><strong>{{ suburb }} carbon </strong>Electricity: {{elec}} kg; Gas: {{gas}} kg; Total: {{elec+gas}} kg</p>
            </div> 
          </div>
          
        </div>
      </el-card>
    </el-col>
  </el-row>
</template>

<style scoped>

.background{
    width:100%;   
    height:100%;  /**宽高100%是为了图片铺满屏幕 */
    z-index:-1;
    position: absolute;
}

.page-container {
  z-index:1;
  margin-top: v-bind(page_container_height);
}

.box-card {
  width: 420px;
  margin-left: 30%;
  border-radius: 20px;
  height: v-bind(initHeight);
}

.box-card2 {
  width: 70%;
  margin-left: 150px;
  border-radius: 20px;
  height: v-bind(initHeight);
}

.carbon-result {
  text-align: left;
  margin-top: 38px;
}

.svg-image {
  width: 100px; 
  height: auto; 
}

.form-container {
  margin-top: 20px; 
}

.form-item {
  margin-bottom: 35px; /* distance between inputs and date piacker */
  width: 70%;
  text-align: left;
}

.chart {
  width: 426px;
  height: 396px;
}

.chart2 {
  width: 250px;
  height: 250px;
}

.chart3 {
  width: 250px;
  height: 250px;
}

.compareComment {
  text-align: left;
  height: 96px;
  margin-left: 110px;
}

</style>