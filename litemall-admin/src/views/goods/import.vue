<template>
  <div class="app-container">
    <el-card class="box-card">
      <el-upload
        class="upload-demo"
        drag
        action="http://localhost:9527/admin/goods/importGoods"
        :headers="headers"
        :on-success="uploadExcelUrl"
        :multiple="false"
      >
        <i class="el-icon-upload" />
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div slot="tip" class="el-upload__tip">只能上传excel文件，且不超过500kb</div>
      </el-upload>
    </el-card>
  </div>
</template>

<script>
import { getToken } from '@/utils/auth'

export default {
  name: 'GoodsImport',
  data() {
    return {
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
  },
  methods: {
    uploadExcelUrl: function(response) {
      this.$notify.success({
        title: '成功',
        message: '导入成功'
      })
      this.$router.push({ path: '/goods/list' })
    }
  }
}
</script>
