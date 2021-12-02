<template>
<!--  <logo :collapse="isCollapse" />-->
  <el-menu
      default-active="2"
      class="el-menu-vertical-demo"
      :collapse="isCollapse"
      @open="handleOpen"
      @close="handleClose"
  >
    <el-menu-item index="1" @click="toPath('zipkin')">
        <el-icon><location /></el-icon>
        <template #title>Zipkin</template>
    </el-menu-item>
    <el-menu-item index="2" @click="toPath('nacos')">
      <el-icon><icon-menu /></el-icon>
      <template #title>Nacos</template>
    </el-menu-item>
    <el-menu-item index="3" @click="toPath('sentinel')">
      <el-icon><document /></el-icon>
      <template #title>Sentinel</template>
    </el-menu-item>
  </el-menu>
</template>

<script>
import {
  Location,
  Document,
  Menu as IconMenu,
  Setting,
} from '@element-plus/icons'
import logo from './Logo.vue'
export default {
  name: "sidebar",
  components: {
    Location,
    Document,
    Setting,
    IconMenu,
    logo
  },
  data() {
    const isCollapse = true
    const handleOpen = (key, keyPath) => {
      console.log(key, keyPath)
    }
    const handleClose = (key, keyPath) => {
      console.log(key, keyPath)
    }
    return {
      isCollapse,
      handleOpen,
      handleClose,
    }
  },
   methods: {
     toPath(app) {
       this.$router.push({path: `/app/${app}`})
     }
   }
}
</script>

<style scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}
</style>
