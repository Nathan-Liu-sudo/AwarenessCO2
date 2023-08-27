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
        <div v-if="showWelcome" class="front">
            <el-row style="color: aliceblue;">
                <el-col :xs="{ span: 24 }" :sm="{ span: 12 }" :md="{ span: 12 }" :lg="{ span: 12 }" class="left-content">
                    <div class="welcome_text">
                        <h1 class="welcome-title">Welcome to Awareness.CO2.</h1>
                        <strong>Join us on a mission to a brighter tomorrow</strong>
                    </div>
                    <div>
                        <el-button type="primary" @click="homePage" class="startBtn">Find your carbon footprint</el-button>
                    </div>
                    <div class="emptyDiv">

                    </div>
                </el-col>
                <el-col :xs="{ span: 24 }" :sm="{ span: 12 }" :md="{ span: 12 }" :lg="{ span: 12 }" class="right-content">
                    <div class="pitch">
                        <strong>
                            <strong style="color: rgb(237, 97, 10); font-size: 30px;">Victoria tops the list in carbon
                                emitters
                                in Australia. </strong>
                            According to information available at energy.gov.au, approximately 2.5 million Victorian
                            households
                            generate most of their greenhouse gas emissions from daily activities. (Ex. heating, cooling,
                            transportation and etc.)
                            By spreading awareness about the carbon footprints to these individuals, we believe we can help
                            provide a direction to you to monitor and thus take steps in reducing your carbon footprints.
                        </strong>
                    </div>
                </el-col>
            </el-row>

        </div>
    </div>
</template>

<style scoped>
/* 在大屏幕上，左侧内容在左边，右侧内容在右边 */
@media (min-width: 768px) {
    .left-content {
        order: 1;
        margin-top: 20%;
    }

    .right-content {
        order: 2;
        margin-top: 16%;
    }

    .startBtn {
        margin-top: 20px;
    }

}

/* 在小屏幕上，左侧内容在右边，右侧内容在左边 */
@media (max-width: 767px) {
    .left-content {
        order: 2;
    }

    .right-content {
        order: 1;
    }

    .startBtn {
        margin-top: 20px;
    }

    .emptyDiv {
        margin-top: 100px;
    }

    .welcome_text {
        font-family: Arial, sans-serif;
        line-height: 1.6;
        padding: 0;
        max-width: 60%;
        margin: auto;
        padding: 20px;
        text-align: center;
    }
}

.background {
    background-image: url('../assets/dark_forest.jpg');
    width: 100%;
    height: 100%;
    /* 背景图像的大小设置为 cover，保持宽高比并覆盖整个元素 */
    background-size: cover;

    /* 背景图像位置居中 */
    background-position: center;
    z-index: -1;
    position: absolute;

    /* 设置最小高度为100%视窗高度 */
    min-height: 100vh;

    /* 开启垂直滚动 */
    overflow-y: auto;
}

.front {
    z-index: 1;
    position: absolute;
}

.pitch {
    font-family: Arial, sans-serif;
    line-height: 1.6;
    padding: 0;
    max-width: 60%;
    margin: auto;
    padding: 20px;
    text-align: left;
}
</style>
