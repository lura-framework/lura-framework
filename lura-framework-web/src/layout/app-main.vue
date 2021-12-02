<template>
  <router-view :key="key" />
</template>

<script>
export default {
  name: "app-main",

  computed: {
    key() {
      return this.$route.path
    }
  }

}
</script>

<style scoped>

</style>
