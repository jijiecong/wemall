<template>
  <div class="app-container">
    <el-form
      ref="dataForm"
      :rules="rules"
      :model="dataForm"
      status-icon
      label-width="300px"
    >
      <el-tabs tab-position="left">
        <el-tab-pane label="首页配置">
          <el-form-item label="新品首发栏目商品显示数量" prop="litemall_wx_index_new">
            <el-input v-model="dataForm.litemall_wx_index_new" />
          </el-form-item>
          <el-form-item label="人气推荐栏目商品显示数量" prop="litemall_wx_index_hot">
            <el-input v-model="dataForm.litemall_wx_index_hot" />
          </el-form-item>

          <!--          <el-form-item label="品牌制造商直供栏目品牌商显示数量" prop="litemall_wx_index_brand">-->
          <!--            <el-input v-model="dataForm.litemall_wx_index_brand"/>-->
          <!--          </el-form-item>-->
          <!--          <el-form-item label="专题精选栏目显示数量" prop="litemall_wx_index_topic">-->
          <!--            <el-input v-model="dataForm.litemall_wx_index_topic"/>-->
          <!--          </el-form-item>-->
          <!--          <el-form-item label="分类栏目显示数量" prop="litemall_wx_catlog_list">-->
          <!--            <el-input v-model="dataForm.litemall_wx_catlog_list"/>-->
          <!--          </el-form-item>-->
          <!--          <el-form-item label="分类栏目商品显示数量" prop="litemall_wx_catlog_goods">-->
          <!--            <el-input v-model="dataForm.litemall_wx_catlog_goods"/>-->
          <!--          </el-form-item>-->
        </el-tab-pane>
        <!--          会员页图片-->
        <el-tab-pane label="会员配置">
          <el-form-item label="会员页展示图片">
            <el-upload
              :headers="headers"
              :action="uploadPath"
              :show-file-list="false"
              :on-success="uploadPicUrl"
              class="avatar-uploader"
              accept=".jpg,.jpeg,.png,.gif"
            >
              <img v-if="dataForm.litemall_wx_memberPicUrl" :src="dataForm.litemall_wx_memberPicUrl" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon" />
            </el-upload>
          </el-form-item>
        </el-tab-pane>
        <!--          <el-tab-pane label="其他配置">-->
        <!--            <el-form-item label="商品分享功能" prop="litemall_wx_share">-->
        <!--              <el-switch v-model="dataForm.litemall_wx_share"/>-->
        <!--            </el-form-item>-->
        <!--          </el-tab-pane>-->
      </el-tabs>
      <el-form-item>
        <el-button @click="cancel">取消</el-button>
        <el-button type="primary" @click="update">确定</el-button>
      </el-form-item>
    </el-form>

  </div>
</template>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #20a0ff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
}
.avatar {
  width: 145px;
  height: 145px;
  display: block;
}
.op-container {
  display: flex;
  justify-content: center;
}
</style>

<script>
import { listWx, updateWx } from '@/api/config'
import { uploadPath } from '@/api/storage'
import { getToken } from '@/utils/auth'

export default {
  name: 'ConfigWx',
  data() {
    return {
      uploadPath,
      dataForm: {
        litemall_wx_index_new: 0,
        litemall_wx_index_hot: 0,
        litemall_wx_memberPicUrl: ''
      },
      rules: {
        litemall_wx_index_new: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        litemall_wx_index_hot: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        litemall_wx_memberPicUrl: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    headers() {
      return {
        'X-Litemall-Admin-Token': getToken()
      }
    }
  },
  created() {
    this.init()
  },
  methods: {
    init: function() {
      listWx().then(response => {
        this.dataForm = response.data.data
        this.dataForm.litemall_wx_share = this.dataForm.litemall_wx_share === 'true'
      })
    },
    cancel() {
      this.init()
    },
    update() {
      this.$refs['dataForm'].validate((valid) => {
        if (!valid) {
          return false
        }
        this.doUpdate()
      })
    },
    uploadPicUrl: function(response) {
      console.log(response.data.url)
      this.dataForm.litemall_wx_memberPicUrl = response.data.url
      console.log(this.dataForm.litemall_wx_memberPicUrl)
    },
    doUpdate() {
      updateWx(this.dataForm)
        .then(response => {
          this.$notify.success({
            title: '成功',
            message: '小程序配置成功'
          })
        })
        .catch(response => {
          this.$notify.error({
            title: '失败',
            message: response.data.errmsg
          })
        })
    }
  }
}
</script>
