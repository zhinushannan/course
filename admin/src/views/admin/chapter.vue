<template>

  <div>

    <h4 class="lighter">
      <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
      <router-link to="/business/course" class="pink"> {{course.name}} </router-link>
    </h4>
    <hr>
    <p>
      <router-link class="btn btn-white btn-default btn-round" to="/business/course">
        <i class="ace-icon fa fa-arrow-left"></i>
        返回课程
      </router-link>
      &nbsp;
      <button class="btn btn-white btn-default btn-round" @click="add()">
        <i class="ace-icon fa fa-edit"></i>
        新增
      </button>
      &nbsp;
      <button v-on:click="list(1)" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-refresh"></i>
        刷新
      </button>
    </p>

    <pagination ref="pagination" v-bind:itemCount="8" v-bind:list="list"></pagination>

    <table id="simple-table" class="table  table-bordered table-hover">
    <thead>
    <tr>
      <th>ID</th>
      <th>名称</th>
      <th>课程ID</th>
      <th>操作</th>
    </tr>
    </thead>

    <tbody>

    <tr v-for="chapter in chapters">

      <td>{{ chapter.id }}</td>
      <td>{{ chapter.name }}</td>
      <td>{{ chapter.courseId }}</td>
      <td>
        <div class="hidden-sm hidden-xs btn-group">
          <button class="btn btn-xs btn-info" @click="toSection(chapter)">
            小节
          </button>

          <button class="btn btn-xs btn-info" @click="edit(chapter)">
          编辑
        </button>

          <button class="btn btn-xs btn-danger" @click="del(chapter.id)">
            删除
          </button>

        </div>

        <div class="hidden-md hidden-lg">
          <div class="inline pos-rel">
            <button class="btn btn-minier btn-primary dropdown-toggle" data-position="auto" data-toggle="dropdown">
              <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
            </button>

            <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
              <li>
                <a class="tooltip-success" data-rel="tooltip" href="#" title="Edit" @click="edit(chapter)">
																			<span class="green">
																				<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																			</span>
                </a>
              </li>

              <li>
                <a class="tooltip-error" data-rel="tooltip" href="#" title="Delete" @click="del(chapter.id)">
																			<span class="red">
																				<i class="ace-icon fa fa-trash-o bigger-120"></i>
																			</span>
                </a>
              </li>
            </ul>
          </div>
        </div>
      </td>

    </tr>

    </tbody>
    </table>

    <div id="form-modal" class="modal fade" role="dialog" tabindex="-1">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button aria-label="Close" class="close" data-dismiss="modal" type="button"><span
                aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">表单</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label class="col-sm-2 control-label">名称</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" placeholder="名称" v-model="chapter.name">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">课程</label>
                <div class="col-sm-10">
                  <p class="form-control-static">{{ course.name }}</p>
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button class="btn btn-default" data-dismiss="modal" type="button">取消</button>
            <button class="btn btn-primary" type="button" @click="save()">保存</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

  </div>
</template>

<script>
import Pagination from "@/components/pagination";

export default {
  components: {Pagination},
  name: "chapter",
  data() {
    return {
      chapters: [],
      chapter: {},
      course: {}
    }
  },
  mounted() {
    let _this = this;
    _this.$refs.pagination.size = 5;
    let course = SessionStorage.get("course") || {};
    if (Tool.isEmpty(course)) {
      _this.$router.push("/welcome");
    }
    _this.course = course;
    _this.list(1);
  },
  methods: {
    add() {
      let _this = this
      _this.chapter = {}
      $("#form-modal").modal("show")
    },
    edit(chapter) {
      let _this = this
      _this.chapter = $.extend({}, chapter)
      $("#form-modal").modal("show")
    },
    del(id) {
      let _this = this
      Confirm.show("删除大章后将不可恢复，确认删除？", function () {
        Loading.show()
        _this.$ajax.delete(process.env.VUE_APP_SERVER + '/business/admin/chapter/delete/' + id).then((response) => {
          let resp = response.data
          if (resp.success) {
            _this.list(1)
            Toast.success("删除成功！")
          }
        })
        Loading.hide()
      })
    },
    list(page) {
      let _this = this
      Loading.show()
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/chapter/list', {
        page: page,
        size: _this.$refs.pagination.size,
        courseId: _this.course.id
      }).then((response) => {
        Loading.hide();
        let resp = response.data;
        _this.chapters = resp.data.data;
        _this.$refs.pagination.render(page, resp.data.total);
      })
    },
    save() {
      let _this = this

      if (!Validator.require(_this.chapter.name, "名称")
        || !Validator.length(_this.chapter.courseId, "课程ID", 1, 8)) {
        return
      }

      _this.chapter.courseId = _this.course.id

      Loading.show()
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/chapter/save', _this.chapter).then((response) => {
        let resp = response.data
        if (resp.success) {
          $("#form-modal").modal("hide")
          _this.list(1)
          Toast.success("保存成功！")
        } else {
          Toast.warning(resp.message)
        }
      })
      Loading.hide()
    },
    toSection(chapter) {
      let _this = this;
      SessionStorage.set("chapter", chapter);
      _this.$router.push("/business/section")
    }
  }
}
</script>

<style scoped>

</style>