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
      shardSize: {
        default: 1024 * 1024
      },
      url: {
        default: "oss-append"
      },
      afterUpload: {
        type: Function,
        default: null
      },
    },
    data: function () {
      return {
        file: '',
      }
    },
    methods: {
      uploadFile() {
        let _this = this
        _this.file = _this.$refs.file.files[0]

        let key = hex_md5(_this.file.name + _this.file.size + _this.file.type)
        let key10 = parseInt(key, 16) // 十六进制转十进制
        let key62 = Tool._10to62(key10) // 十进制转六十二进制（26个大写字母、26个小写字母、10数字）

        // 判断文件格式
        let suffixs = _this.suffixs
        let fileName = _this.file.name

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
        let shardSize = _this.shardSize; // 以2MB为一个分片
        let shardIndex = 1; // 分片索引
        let size = _this.file.size;
        let shardTotal = Math.ceil(size / shardSize);

        let param = {
          "shardIndex": shardIndex,
          "shardSize": shardSize,
          "shardTotal": shardTotal,
          "use": _this.use,
          "name": _this.file.name,
          "suffix": suffix,
          "size": size,
          "key": key62,
        }
        _this.check(param);
      },

      check(param) {
        let _this = this
        _this.$ajax.get(process.env.VUE_APP_SERVER + "/file/admin/check/" + param["key"]).then((response) => {
          let resp = response.data
          if (resp.success) {
            let obj = resp.data
            if (!obj) {
              param["shardIndex"] = 1
              console.log("没有找到文件记录，从分片1开始上传")
              _this.upload(param)
            } else if (obj["shardIndex"] === obj["shardTotal"]) {
              Toast.success("文件极速秒传成功！")
              _this.afterUpload(resp)
              $("#" + _this.inputId + "-input").val("")
            } else {
              param["shardIndex"] = obj["shardIndex"] + 1
              console.log("找到文件记录，从分片" + param["shardIndex"] + "开始上传")
              _this.upload(param)
            }
          } else {
            Toast.warning("文件上传失败")
            $("#" + _this.inputId + "-input").val("")
          }
        })
      },

      upload(param) {
        let _this = this
        // 将文件转为base64进行传输
        let shardIndex = param["shardIndex"]
        let shardTotal = param["shardTotal"]
        let shardSize = param["shardSize"]
        let fileReader = new FileReader()

        Progress.show(parseInt((shardIndex - 1) * 100 / shardTotal))

        fileReader.onload = function (e) {
          param["shard"] = e.target.result

          Loading.show()
          // _this.$ajax.post(process.env.VUE_APP_SERVER + "/file/admin/upload", param).then((response) => {
          _this.$ajax.post(process.env.VUE_APP_SERVER + "/file/admin/" + _this.url, param).then((response) => {
            Loading.hide()
            let resp = response.data
            if (shardIndex < shardTotal) {
              // 上传下一个分片
              Progress.show(parseInt(shardIndex * 100 / shardTotal))
              param["shardIndex"] = param["shardIndex"] + 1
              _this.upload(param)
            } else {
              Progress.hide()
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
        let start = (shardIndex - 1) * shardSize; // 当前分片起始位置

        let end = Math.min(start + shardSize, _this.file.size); // 当前分片结束位置

        return _this.file.slice(start, end);
      },
      selectFile() {
        let _this = this
        $("#" + _this.inputId + "-input").trigger("click")
      },
    }
  }
</script>

