<template>
  <el-config-provider namespace="ep">
    
    <div class="flex main-container">
        <!-- <Header/> -->
        <div w="full" py="4">
          <router-view></router-view>
        </div>
        <Footer/>
    </div>
  </el-config-provider>
</template>

<style>
#app {
  text-align: center;
  color: var(--ep-text-color-primary);
}

.main-container {
  height: calc(100vh - var(--ep-menu-item-height) - 3px);
}
</style>
