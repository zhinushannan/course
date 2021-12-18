<template>
  <div>
    <button type="button" v-on:click="selectFile()" class="btn btn-white btn-default btn-round">
      <i class="ace-icon fa fa-upload"></i>
      {{ text }}
    </button>
    <input type="file" class="hidden" ref="file" @change="uploadFile()" v-bind:id="inputId + '-input'">
  </div>

</template>

<script>
  export default {
    name: 'file',
    props: {
      text: {
        default: "上传文件"
      },
      inputId: {
        default: "file-upload"
      },
      suffixs: {
        default: []
      },
      use: {
        default: ""
      },
      afterUpload: {
        type: Function,
        default: null
      },
    },
    data: function () {
      return {
      }
    },
    methods: {
      uploadFile() {
        let _this = this
        let formData = new window.FormData()
        // key: "file" 必须和后端controller参数名保持一致
        let file = _this.$refs.file.files[0]

        // 判断文件格式
        let suffixs = _this.suffixs
        let fileName = file.name

        let suffix = fileName.substr(fileName.lastIndexOf(".") + 1, fileName.length).toLowerCase()
        let validateSuffix = false
        for (let i = 0; i < suffixs.length; i++) {
          if (suffixs[i].toLowerCase() === suffix) {
            validateSuffix = true;
            break;
          }
        }
        if (!validateSuffix) {
          Toast.warning("文件格式不整齐！只支持上传：" + suffixs.join(", "))
          $("#" + _this.inputId + "-input").val("")
          return
        }


        formData.append("file", file)
        formData.append("use", _this.use)
        Loading.show()
        _this.$ajax.post(process.env.VUE_APP_SERVER + "/file/admin/upload", formData).then((response) => {
          Loading.hide()
          let resp = response.data
          _this.afterUpload(resp)
          $("#" + _this.inputId + "-input").val("")
        })
      },

      selectFile() {
        $("#file-upload-input").trigger("click")
      }
    }
  }
</script>

