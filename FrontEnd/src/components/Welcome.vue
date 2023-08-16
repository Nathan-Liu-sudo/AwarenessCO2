<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const homePage = () => {    
    router.push('/cal');
}

import { ElMessage, ElMessageBox } from 'element-plus'
onMounted(() => {
    // 检查会话级别的存储中的标志
    const hasShownDialog = sessionStorage.getItem('hasShownDialog');

    // 如果标志不存在或为 false，则弹出对话框
    if (!hasShownDialog) {
      open();
    } else {
        showWelcome.value = true
    }
});

const open = () => {
  ElMessageBox.prompt('Please input password', {
    confirmButtonText: 'OK',
    showCancelButton: false,
    closeOnClickModal: false, // 阻止点击非对话框区域使对话框消失
    inputPattern:
      /tp11_app/,
    inputErrorMessage: 'Incorrect Password',
  })
    .then(({ value }) => {
      ElMessage({
        type: 'success',
        message: `Login Successfully`,
      })
      showWelcome.value = true
      ElMessageBox.close();
      // 标记已经显示过对话框
      sessionStorage.setItem('hasShownDialog', 'true');
      
    })
}

const showWelcome = ref(false)

</script>

<template>
    <div class="background">
        <img src="../assets/dark_forest.jpg" style="width: 100%; height: 100%;"/>
    </div>
    <div v-if="showWelcome" class="front">
        <el-row style="color: aliceblue;">
            <el-col :span=12 style="margin-top: 20%;">
                <div>
                    <h1 class="welcome-title">Welcome to Awareness.CO2.</h1>
                    <strong>Join us on a mission to a brighter tomorrow</strong>
                </div>
                <div>
                    <el-button type="primary" @click="homePage" class="startBtn">Find your carbon footprint</el-button>
                </div>
            </el-col>
            <el-col :span=12 style="margin-top: 16%;">
                <div class="pitch">
                    <strong>
                        <strong style="color: rgb(237, 97, 10); font-size: 30px;">Victoria tops the list in carbon emitters in Australia. </strong> 
                        According to information available at energy.gov.au, approximately 2.5 million Victorian households generate most of their greenhouse gas emissions from daily activities. (Ex. heating, cooling, transportation and etc.)
                        By spreading awareness about the carbon footprints to these individuals, we believe we can help provide a direction to you to monitor and thus take steps in reducing your carbon footprints.
                    </strong>
                </div>
                
            </el-col>
        </el-row>
    </div>
</template>

<style scoped>
.background{
    width:100%;   
    height:100%;  /**宽高100%是为了图片铺满屏幕 */
    z-index:-1;
    position: absolute;
}

.front{
    z-index:1;
    position: absolute;
}

.startBtn{
    margin-top: 20px;
}

.pitch {
    font-family: Arial, sans-serif;
    line-height: 1.6;
    margin: 0;
    padding: 0;
    max-width: 60%;
    margin: 0 auto;
    padding: 20px;
    text-align: left;
}

</style>