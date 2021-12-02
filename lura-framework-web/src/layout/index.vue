<template>
  <el-row>
    <el-col :span="1">
      <sidebar></sidebar>
    </el-col>
    <el-col :span="23">
      <app-main></app-main>
    </el-col>
  </el-row>
</template>

<script>
import sidebar from "/@/components/sidebar/index.vue";
import AppMain from "./app-main.vue";
export default {
  name: "index",
  components: {AppMain, sidebar}
}
</script>

<style scoped>

</style>
