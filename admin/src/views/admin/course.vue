<template>
  <div>
    <p>
      <button class="btn btn-white btn-default btn-round" v-on:click="add()">
        <i class="ace-icon fa fa-edit"></i>
        新增
      </button>
      &nbsp;
      <button class="btn btn-white btn-default btn-round" v-on:click="list(1)">
        <i class="ace-icon fa fa-refresh"></i>
        刷新
      </button>
    </p>


    <pagination ref="pagination" v-bind:itemCount="8" v-bind:list="list"></pagination>

    <div class="row">
      <div v-for="course in courses" class="col-md-4">
        <div class="thumbnail search-thumbnail">
          <img v-show="!course.image" class="media-object" src="/static/image/demo-course.jpg" style="width: 100%;"/>
          <img v-show="course.image" class="media-object" style="width: 100%;" v-bind:src="course.image"/>
          <div class="caption">
            <div class="clearfix">
              <span class="pull-right label label-primary info-label">{{ COURSE_LEVEL | optionKV(course.level) }}</span>
              <span class="pull-right label label-primary info-label">{{
                  COURSE_CHARGE | optionKV(course.charge)
                }}</span>
              <span class="pull-right label label-primary info-label">{{
                  COURSE_STATUS | optionKV(course.status)
                }}</span>
            </div>

            <h3 class="search-title">
              <a class="blue" href="#">{{ course.name }}</a>
            </h3>

            <div v-for="teacher in teachers.filter(t=>{return t.id===course.teacherId})" class="profile-activity clearfix">
              <div>
                <img v-show="!teacher.image" class="pull-left" src="/ace/assets/images/avatars/avatar5.png">
                <img v-show="teacher.image" class="pull-left" v-bind:src="teacher.image">
                <a class="user" href="#"> {{teacher.name}} </a>
                <br>
                {{teacher.position}}
              </div>
            </div>

            <p class="blue bolder bigger-150">
              <i class="fa fa-rmb"></i>&nbsp;{{ course.price }}
            </p>
            <p>{{ course.summary }}</p>
            <p>
              <span class="badge badge-info">{{course.id}}</span>
              <span class="badge badge-info">排序：{{course.sort}}</span>
              <span class="badge badge-info">{{course.time | formatSecond}}</span>
            </p>
            <p>
              <button class="btn btn-white btn-xs btn-info btn-round bigger-110" v-on:click="toChapter(course)">
                大章
              </button>
              &nbsp;
              <button class="btn btn-white btn-xs btn-info btn-round bigger-110" v-on:click="toContent(course)">
                内容
              </button>
              &nbsp;
              <button class="btn btn-white btn-xs btn-info btn-round bigger-110" v-on:click="openSortModal(course)">
                排序
              </button>
              &nbsp;
              <button class="btn btn-white btn-xs btn-info btn-round bigger-110" v-on:click="edit(course)">
                编辑
              </button>
              &nbsp;
              <button class="btn btn-white btn-xs btn-warning btn-round bigger-110" v-on:click="del(course.id)">
                删除
              </button>
            </p>
          </div>
        </div>
      </div>
    </div>

    <!-- 新增modal -->
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
                <label class="col-sm-2 control-label">分类</label>
                <div class="col-sm-10">
                  <ul id="tree" class="ztree"></ul>
                </div>
              </div>

              <div class="form-group">
                <label class="col-sm-2 control-label">名称</label>
                <div class="col-sm-10">
                  <input v-model="course.name" class="form-control">
                </div>
              </div>

              <div class="form-group">
                <label class="col-sm-2 control-label">讲师</label>
                <div class="col-sm-10">
                  <select v-model="course.teacherId" class="form-control">
                    <option v-for="o in teachers" v-bind:value="o.id">{{ o.name }}</option>
                  </select>
                </div>
              </div>

              <div class="form-group">
                <label class="col-sm-2 control-label">概述</label>
                <div class="col-sm-10">
                  <input v-model="course.summary" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">时长</label>
                <div class="col-sm-10">
                  <input v-model="course.time" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">价格（元）</label>
                <div class="col-sm-10">
                  <input v-model="course.price" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">封面</label>
                <div class="col-sm-10">
                  <File
                      v-bind:input-id="'image-upload'"
                      v-bind:suffixs="['jpg', 'jpeg', 'png']"
                      v-bind:text="'上传封面'"
                      v-bind:use="FILE_USE.COURSE.key"
                      v-bind:after-upload="afterUpload"></File>
                  <div v-show="course.image" class="row">
                    <div class="col-md-4">
                      <img v-show="course.image" v-bind:src="course.image" class="img-responsive" alt="">
                    </div>
                  </div>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">级别</label>
                <div class="col-sm-10">
                  <select v-model="course.level" class="form-control">
                    <option v-for="o in COURSE_LEVEL" v-bind:value="o.key">{{ o.value }}</option>
                  </select>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">收费</label>
                <div class="col-sm-10">
                  <select v-model="course.charge" class="form-control">
                    <option v-for="o in COURSE_CHARGE" v-bind:value="o.key">{{ o.value }}</option>
                  </select>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">状态</label>
                <div class="col-sm-10">
                  <select v-model="course.status" class="form-control">
                    <option v-for="o in COURSE_STATUS" v-bind:value="o.key">{{ o.value }}</option>
                  </select>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">报名数</label>
                <div class="col-sm-10">
                  <input v-model="course.enroll" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">顺序</label>
                <div class="col-sm-10">
                  <input v-model="course.sort" class="form-control">
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button class="btn btn-default" data-dismiss="modal" type="button">取消</button>
            <button class="btn btn-primary" type="button" v-on:click="save()">保存</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- 新增modal -->

    <!-- 排序modal -->
    <div id="course-sort-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">排序</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label class="control-label col-lg-3">
                  当前排序
                </label>
                <div class="col-lg-9">
                  <input class="form-control" v-model="sort.oldSort" name="oldSort" disabled>
                </div>
              </div>
              <div class="form-group">
                <label class="control-label col-lg-3">
                  新排序
                </label>
                <div class="col-lg-9">
                  <input class="form-control" v-model="sort.newSort" name="newSort">
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-white btn-default btn-round" data-dismiss="modal">
              <i class="ace-icon fa fa-times"></i>
              取消
            </button>
            <button type="button" class="btn btn-white btn-info btn-round" v-on:click="updateSort()">
              <i class="ace-icon fa fa-plus blue"></i>
              更新排序
            </button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- 排序modal -->

  </div>
</template>

<script>
import Pagination from "../../components/pagination";
import File from "../../components/file";

export default {
  components: {Pagination, File},
  name: "business-course",
  data: function () {
    return {
      course: {},
      courses: [],
      COURSE_LEVEL: COURSE_LEVEL,
      COURSE_CHARGE: COURSE_CHARGE,
      COURSE_STATUS: COURSE_STATUS,
      FILE_USE: FILE_USE,
      categorys: [],
      tree: {},
      sort: {
        id: "",
        oldSort: 0,
        newSort: 0
      },
      teachers: [],
    }
  },
  mounted: function () {
    let _this = this;
    _this.$refs.pagination.size = 5;
    _this.allCategory();
    _this.allTeacher();
    _this.list(1);
    // sidebar激活样式方法一
    // this.$parent.activeSidebar("business-course-sidebar");

  },
  methods: {
    /**
     * 点击【新增】
     */
    add() {
      let _this = this;
      _this.course = {
        sort: _this.$refs.pagination.total + 1
      };
      _this.tree.checkAllNodes(false);
      $("#form-modal").modal("show");
    },

    /**
     * 点击【编辑】
     */
    edit(course) {
      let _this = this;
      _this.course = $.extend({}, course);
      _this.listCategory(course.id);
      $("#form-modal").modal("show");
    },

    /**
     * 列表查询
     */
    list(page) {
      let _this = this;
      Loading.show();
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/course/list', {
        page: page,
        size: _this.$refs.pagination.size,
      }).then((response) => {
        Loading.hide();
        let resp = response.data;
        _this.courses = resp.data.data;
        _this.$refs.pagination.render(page, resp.data.total);
      })
    },

    /**
     * 点击【保存】
     */
    save() {
      let _this = this;

      // 保存校验
      if (1 != 1
          || !Validator.require(_this.course.name, "名称")
          || !Validator.length(_this.course.name, "名称", 1, 50)
          || !Validator.length(_this.course.summary, "概述", 1, 2000)
          || !Validator.length(_this.course.image, "封面", 1, 100)
      ) {
        return;
      }

      let categorys = _this.tree.getCheckedNodes();
      if (Tool.isEmpty(categorys)) {
        Toast.warning("请选择分类")
        return;
      }
      _this.course.categorys = categorys;

      Loading.show();
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/course/save', _this.course).then((response) => {
        Loading.hide();
        let resp = response.data;
        if (resp.success) {
          $("#form-modal").modal("hide");
          _this.list(1);
          Toast.success("保存成功！");
        } else {
          Toast.warning(resp.message)
        }
      })
    },

    /**
     * 点击【删除】
     */
    del(id) {
      let _this = this;
      Confirm.show("删除课程后不可恢复，确认删除？", function () {
        Loading.show();
        _this.$ajax.delete(process.env.VUE_APP_SERVER + '/business/admin/course/delete/' + id).then((response) => {
          Loading.hide();
          let resp = response.data;
          if (resp.success) {
            _this.list(1);
            Toast.success("删除成功！");
          }
        })
      });
    },

    /**
     * 点击【大章】
     */
    toChapter(course) {
      let _this = this;
      SessionStorage.set(SESSION_KEY_COURSE, course)
      _this.$router.push("/business/chapter")
    },
    /**
     * 列表查询分类
     */
    allCategory() {
      let _this = this;
      Loading.show();
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/category/all').then((response)=>{
        Loading.hide();
        let resp = response.data;
        _this.categorys = resp.data;
        _this.initTree();
      })
    },
    initTree() {
      let _this = this;

      let setting = {
        check: {
          enable: true
        },
        data: {
          simpleData: {
            idKey: "id",
            pIdKey: "parent",
            rootPId: "00000000",
            enable: true
          }
        }
      };

      let zNodes = _this.categorys;

      _this.tree = $.fn.zTree.init($("#tree"), setting, zNodes);
    },

    listCategory(courseId) {
      let _this = this;
      Loading.show();
      _this.$ajax.post(process.env.VUE_APP_SERVER + "/business/admin/course/list-category/" + courseId).then((response) => {
        Loading.hide();
        console.log("查询课程下所有分类结果：", response);
        let resp = response.data;
        let categorys = resp.data;

        // 勾选查询到的分类
        _this.tree.checkAllNodes(false)
        for (let i = 0; i < categorys.length; i++) {
          let node = _this.tree.getNodeByParam("id", categorys[i].categoryId);
          _this.tree.checkNode(node, true)
        }
      })
    },

    /**
     * 点击【内容】
     */
    toContent(course) {
      let _this = this;
      SessionStorage.set(SESSION_KEY_COURSE, course);
      _this.$router.push("/business/content");
    },

    openSortModal(course) {
      let _this = this;
      _this.sort = {
        id: course.id,
        oldSort: course.sort,
        newSort: course.sort
      }
      $("#course-sort-modal").modal("show")
    },

    updateSort() {
      let _this = this;
      if (_this.sort.newSort === _this.sort.oldSort) {
        Toast.warning("排序没有变化");
        return;
      }
      Loading.show();
      _this.$ajax.post(process.env.VUE_APP_SERVER + "/business/admin/course/sort", _this.sort).then((response) => {
        Loading.hide();
        let resp = response.data;

        if (resp.success) {
          Toast.success("更新排序成功！");
          $("#course-sort-modal").modal("hide");
          _this.list(1);
        } else {
          Toast.error("更新排序失败");
        }

      })
    },

    allTeacher() {
      let _this = this;
      Loading.show();
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/teacher/all').then((response)=>{
        Loading.hide();
        let resp = response.data;
        _this.teachers = resp.data;
      })
    },

    afterUpload(resp) {
      let _this = this
      let image = resp.data
      console.log("封面地址：", image.path)
      _this.course.image = image.path
    },

  }
}
</script>

<style scoped>

.caption h3 {
  font-size: 20px;
}

@media (max-width: 1199px) {
  .caption h3 {
    font-size: 16px;
  }
}

</style>
