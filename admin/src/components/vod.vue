<template>
<big-file
  v-bind:input-id="inputId"
  v-bind:text="text"
  v-bind:suffixs="suffixs"
  v-bind:use="use"
  v-bind:after-upload="afterUpload"
  v-bind:shard-size="shardSize"
  v-bind:url="'oss-append'"
></big-file>
</template>

<script>
import BigFile from "@/components/big-file";
export default {
  name: "vod",
  components: {BigFile},
  props: {
    text: {
      default: "上传VOD"
    },
    inputId: {
      default: "vod-input"
    },
    suffixs: {
      default: []
    },
    use: {
      default: ""
    },
    shardSize: {
      default: 1024 * 1024
    },
    afterUpload: {
      type: Function,
      default: null
    },
  },
  methods: {
  }
}
</script>

<style scoped>

</style>