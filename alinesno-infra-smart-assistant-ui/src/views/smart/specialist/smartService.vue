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

                  <el-tooltip class="box-item" effect="dark" content="选择专家下一步执行" placement="top" >
                    <el-button type="warning" text bg size="large" @click="dialogVisible = true" >
                      <i class="fa-solid fa-user-astronaut icon-btn"></i>
                    </el-button>
                  </el-tooltip>

                  <el-tooltip class="box-item" effect="dark" content="提交任务给Agent执行" placement="top" >
                    <el-button type="primary" text bg size="large" @click="jobDialogVisible = true" >
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

    <el-dialog v-model="dialogVisible" title="选择专家服务Agent" width="70%" :before-close="handleClose" append-to-body>
      <RoleAgent />
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">关闭</el-button>
        </span>
      </template>
    </el-dialog>

    <el-dialog v-model="jobDialogVisible" title="选择任务执行Agent" width="70%" :before-close="handleClose" append-to-body>
      <JobAgent />
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="jobDialogVisible = false">关闭</el-button>
        </span>
      </template>
    </el-dialog>

  </div>
</template>

<script setup>
import { ref } from 'vue'
import ChatList from './chatList.vue'

import RoleAgent from './agent/roleAgent.vue'
import JobAgent from './agent/jobAgent.vue'

const dialogVisible = ref(false)
const jobDialogVisible = ref(false)

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

const handleClose = () => {
}

</script>

<style lang="scss" scoped>
.icon-btn{
  font-size: 20px;
}
</style>