<template>

  <div>
    <p>
      <button class="btn btn-white btn-default btn-round" @click="add()">
        <i class="ace-icon fa fa-edit"></i>
        新增
      </button>
      &nbsp;
      <button class="btn btn-white btn-default btn-round" @click="list(1)">
        <i class="ace-icon fa fa-refresh"></i>
        刷新
      </button>
    </p>

    <Pagination ref="pagination" v-bind:list="list" v-bind:itemCount="8"/>

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
          <button class="btn btn-xs btn-success">
            <i class="ace-icon fa fa-check bigger-120"></i>
          </button>

          <button class="btn btn-xs btn-info">
            <i class="ace-icon fa fa-pencil bigger-120"></i>
          </button>

          <button class="btn btn-xs btn-danger">
            <i class="ace-icon fa fa-trash-o bigger-120"></i>
          </button>

          <button class="btn btn-xs btn-warning">
            <i class="ace-icon fa fa-flag bigger-120"></i>
          </button>
        </div>

        <div class="hidden-md hidden-lg">
          <div class="inline pos-rel">
            <button class="btn btn-minier btn-primary dropdown-toggle" data-position="auto" data-toggle="dropdown">
              <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
            </button>

            <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
              <li>
                <a class="tooltip-info" data-rel="tooltip" href="#" title="View">
																			<span class="blue">
																				<i class="ace-icon fa fa-search-plus bigger-120"></i>
																			</span>
                </a>
              </li>

              <li>
                <a class="tooltip-success" data-rel="tooltip" href="#" title="Edit">
																			<span class="green">
																				<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																			</span>
                </a>
              </li>

              <li>
                <a class="tooltip-error" data-rel="tooltip" href="#" title="Delete">
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

    <div class="modal fade" role="dialog" tabindex="-1">
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
                <label class="col-sm-2 control-label">课程ID</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" placeholder="课程ID" v-model="chapter.courseId">
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
  comments: { Pagination },
  name: "chapter",
  data() {
    return {
      chapters: [],
      chapter: {}
    }
  },
  mounted() {
    let _this = this
    _this.$refs.pagination.size = 5
    _this.list(1)
  },
  methods: {
    add() {
      let _this = this
      $(".modal").modal("show")

    },
    list(page) {
      let _this = this
      _this.$ajax.post('http://127.0.0.1:9000/business/admin/chapter/list', {
        page: page,
        size: _this.$refs.pagination.size
      }).then((response) => {
        _this.chapters = response.data.data
        _this.$refs.pagination.render(page, response.data.total)
        console.log(_this.chapters)
      })
    },
    save() {
      let _this = this
      _this.$ajax.post('http://127.0.0.1:9000/business/admin/chapter/save', _this.chapter).then((response) => {
        console.log(response)
      })
    }
  }
}
</script>

<style scoped>

</style>