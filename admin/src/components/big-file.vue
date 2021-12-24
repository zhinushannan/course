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
    name: 'big-file',
    props: {
      text: {
        default: "上传大文件"
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
        let file = _this.$refs.file.files[0]

        let key = hex_md5(file)
        let key10 = parseInt(key, 16) // 十六进制转十进制
        let key62 = Tool._10to62(key10) // 十进制转六十二进制（26个大写字母、26个小写字母、10数字）

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
          Toast.warning("文件格式不正确！只支持上传：" + suffixs.join(", "))
          $("#" + _this.inputId + "-input").val("")
          return
        }

        // 文件分片
        let shardSize = 1 * 1204 * 1024; // 以2MB为一个分片
        let shardIndex = 0; // 分片索引
        let fileShard = this.getFileShard(shardIndex, shardSize);
        let size = file.size;
        let shardTotal = Math.ceil(size / shardSize);

        let param = {
          "shardIndex": shardIndex,
          "shardSize": shardSize,
          "shardTotal": shardTotal,
          "use": _this.use,
          "name": file.name,
          "suffix": suffix,
          "size": size,
          "key": key62,
        }
        _this.upload(param);
      },

      selectFile() {
        let _this = this
        $("#" + _this.inputId + "-input").trigger("click")
      },

      upload(param) {
        let _this = this
        // 将文件转为base64进行传输
        let shardIndex = param["shardIndex"]
        let shardTotal = param["shardTotal"]
        let shardSize = param["shardSize"]
        let fileReader = new FileReader()
        fileReader.onload = function (e) {
          param["shard"] = e.target.result

          Loading.show()
          _this.$ajax.post(process.env.VUE_APP_SERVER + "/file/admin/upload", param).then((response) => {
            Loading.hide()
            let resp = response.data
            if (shardIndex < shardTotal) {
              // 上传下一个分片
              param["shardIndex"] = param["shardIndex"] + 1
              _this.upload(param)
            } else {
              _this.afterUpload(resp)
            }
            $("#" + _this.inputId + "-input").val("")
          })
        }
        let fileShard = _this.getFileShard(shardIndex, shardSize)
        fileReader.readAsDataURL(fileShard);
      },
      getFileShard(shardIndex, shardSize) {
        let _this = this
        let file = _this.$refs.file.files[0]
        let start = (shardIndex - 1) * shardSize; // 当前分片起始位置
        let end = Math.min(start + shardSize, file.size); // 当前分片结束位置
        return file.slice(start, end);
      },
    }
  }
</script>

