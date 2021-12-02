<template>
  <iframe :src="url[app]" width="100%" height="800"></iframe>
</template>

<script>
export default {
  name: "app-frame",

  data() {
    return {
      url: {
        nacos: 'http://localhost:8848/nacos',
        zipkin: 'http://localhost:9411',
        sentinel: 'http://localhost:8858'
      },
      app: undefined
    }
  },
  mounted() {
    this.app = this.$route.params.app
  }
}
</script>

<style scoped>

</style>
