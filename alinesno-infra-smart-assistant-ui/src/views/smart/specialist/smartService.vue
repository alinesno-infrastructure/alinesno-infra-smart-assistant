<!-- 意见审批  -->
<template>
  <div class="acp-dashboard" style="padding: 0px 10px !important">
    <div class="smart-container">
      <el-row>
        <el-col :span="18">
          <div class="robot-chat-windows">
            <div class="robot-chat-header">
              <div class="chat-header-title">
                数据库高级工程师 Agent
              </div>
              <div class="chat-header-desc">
                请确认生成的内容是否正确，请确认是否进入下一步 
              </div>
            </div>
            <div class="robot-chat-body">
              <!-- 聊天窗口_start -->
              <ChatList />
              <!-- 聊天窗口_end -->
            </div>
            <div class="robot-chat-footer" style="float:left;width:100%">
              <div class="chat-completion">

                <textarea placeholder="请用 “输入你的审批意见” 描述您的问题，如：缺少生成的内容" 
                          maxlength="1000" 
                          rows="2"  
                          style="resize: none;float: left;width:calc(100% - 250px);background: #fafafa;margin: 10px;"></textarea>

                <div class="chat-operation" style="margin-top: 5px;float: right;">

                  <el-tooltip class="box-item" effect="dark" content="回退重新生成" placement="top" >
                    <el-button type="danger" text bg size="large">
                      <i class="fa-solid fa-paper-plane icon-btn"></i>
                    </el-button>
                  </el-tooltip>

                  <el-tooltip class="box-item" effect="dark" content="编辑生成内容" placement="top" >
                    <el-button type="warning" text bg size="large" @click="handleEditorContent()" >
                      <i class="fa-solid fa-pen-nib icon-btn"></i>
                    </el-button>
                  </el-tooltip>

                  <el-tooltip class="box-item" effect="dark" content="提交任务给Agent执行" placement="top" >
                    <el-button type="primary" text bg size="large" @click="dialogVisible = true" >
                      <i class="fa-solid fa-truck-fast icon-btn"></i>
                    </el-button>
                  </el-tooltip>

                </div>
              </div>
            </div>
          </div>
        </el-col>

        <el-col :span="6">
          <div class="robot-chat-help-container">
            <div class="robot-chat-help-panel">
              <div class="robot-chat-help-title">专家服务Agent</div>
              <div class="robot-chat-help-item-list-panel">
                <div class="process-panel">
                  <ul>
                    <li class="item-process" v-for="(item,index) in favouriteList" :key="index">
                      <img style="width:30px;height:30px;border-radius: 50%;position: absolute;" :src="'http://data.linesno.com/icons/sepcialist/dataset_' + (index+35)+ '.png'" />
                      <div style="margin-left: 40px;white-space: nowrap;overflow: hidden;text-overflow: ellipsis; margin-top: -2px;">
                        {{ item.name }} 
                      </div>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
            <div class="robot-chat-help-panel">
              <div class="robot-chat-help-title">任务执行Agent</div>
              <div class="robot-chat-help-item-list-panel">
                <div class="process-panel">
                  <ul>
                    <li class="item-process" v-for="(item,index) in helpAutoList" :key="item.id">
                      <img style="width:30px;height:30px;border-radius: 50%;position: absolute;" :src="'http://data.linesno.com/icons/sepcialist/dataset_' + (index+15)+ '.png'" />
                      <div style="margin-left: 40px;white-space: nowrap;overflow: hidden;text-overflow: ellipsis; margin-top: -2px;">
                        {{ item.name }} 
                      </div>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </el-col>
       
      </el-row>
    </div>

    <el-dialog v-model="dialogVisible" title="选择专家服务Agent" width="75%" :before-close="handleClose" append-to-body>

      <!-- 打开角色管理 -->
      <RoleAgent :businessId="businessId" />

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">关闭</el-button>
        </span>
      </template>
    </el-dialog>

    <el-dialog v-model="editDialogVisible" title="编辑生成内容" width="60%" :before-close="handleClose" append-to-body>

      <!-- 编辑生成的内容 -->
      <el-form :model="form" ref="ChainRef" v-loading="editorLoading" label-width="0px">
        <el-row>
            <el-col :span="24">
              <el-form-item prop="currentTaskContent">
                  <el-input rows="20" resize="none" type="textarea" v-model="currentTaskContent" placeholder="请输入任务名称" maxlength="50" />
              </el-form-item>
            </el-col>
        </el-row>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="submitAssistantContentForm">更新</el-button>
          <el-button @click="editDialogVisible = false">关闭</el-button>
        </span>
      </template>
    </el-dialog>

  </div>
</template>

<script setup>
import { ref } from 'vue'
import ChatList from './chatList'

import {
  chatAssistantContent , 
  updateAssistantContent
} from '@/api/smart/assistant/robot'

import { getParam } from '@/utils/ruoyi'

import RoleAgent from './agent/roleAgent'

const businessId  = ref("") ;
const editorLoading = ref(true) ;
const dialogVisible = ref(false)
const editDialogVisible = ref(false)
const currentTaskContent = ref("")

const favouriteList = ref([
  { id: '1', icon: 'fa-solid fa-truck-fast', name: '技术指导Agent' },
  { id: '2', icon: 'fa-solid fa-pen-nib', name: '技术学习材料Agent' },
  { id: '3', icon: 'fa-solid fa-pen-ruler', name: '解决方案编写Agent' },
  { id: '4', icon: 'fa-solid fa-paint-roller', name: '技术架构分析Agent' },
  { id: '7', icon: 'fa-solid fa-file-word', name: '代码工程结构Agent' },
  { id: '4', icon: 'fa-solid fa-compass-drafting', name: '需求转代码Agent' },
  { id: '6', icon: 'fa-solid fa-user-nurse', name: '数据库设计Agent' },
])

const helpAutoList = ref([
  { id: '1', icon: 'fa-solid fa-truck-fast', name: '技术指导Agent' },
  { id: '2', icon: 'fa-solid fa-pen-nib', name: '技术学习材料Agent' },
  { id: '8', icon: 'fa-brands fa-dashcube', name: '考核题目生成Agent' },
  { id: '3', icon: 'fa-solid fa-pen-ruler', name: '解决方案编写Agent' },
  { id: '4', icon: 'fa-solid fa-paint-roller', name: '技术架构分析Agent' },
  { id: '7', icon: 'fa-solid fa-file-word', name: '代码工程结构Agent' },
  { id: '4', icon: 'fa-solid fa-compass-drafting', name: '需求转代码Agent' },
  { id: '5', icon: 'fa-solid fa-feather', name: '技术架构Agent' },
  { id: '6', icon: 'fa-solid fa-user-nurse', name: '数据库设计Agent' },
])

const data = reactive({
   form: {},
   queryParams: {
      pageNum: 1,
      pageSize: 10,
      dbName: undefined,
      dbDesc: undefined
   },
   rules: {
      dbName: [{ required: true, message: "名称不能为空", trigger: "blur" }] , 
      jdbcUrl: [{ required: true, message: "连接不能为空", trigger: "blur" }],
      dbType: [{ required: true, message: "类型不能为空", trigger: "blur" }] , 
      dbUsername: [{ required: true , message: "用户名不能为空", trigger: "blur"}],
      dbPasswd: [{ required: true, message: "密码不能为空", trigger: "blur" }] , 
      dbDesc: [{ required: true, message: "备注不能为空", trigger: "blur" }] 
   }
});

const { queryParams, form, rules } = toRefs(data);

const handleClose = () => {
  dialogVisible.value = false ; 
  editDialogVisible.value = false ;
}

/** 编辑生成内容 */
function handleEditorContent(){
  editDialogVisible.value = true ; 

  chatAssistantContent(businessId.value).then(response => {
    currentTaskContent.value = response.data.genContent ; 
    editorLoading.value = false ;
  })
}

/** 提交流程按钮 */
function submitAssistantContentForm() {
  proxy.$refs["ChainRef"].validate(valid => {

    if (valid) {
      updateAssistantContent(businessId.value , currentTaskContent.value).then(response => {
        proxy.$modal.msgSuccess("更新成功");
      });
    }
  });
};

  
businessId.value = getParam('businessId') ;
console.log('businessId = ' + businessId) ;

</script>

<style lang="scss" scoped>
.icon-btn{
  font-size: 20px;
}
</style>