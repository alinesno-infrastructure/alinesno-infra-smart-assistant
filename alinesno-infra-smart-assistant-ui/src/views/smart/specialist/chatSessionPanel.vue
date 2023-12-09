<template>
  <div class="chat-session-list-box">

    <!-- 创建频道 -->
    <div class="chat-builder">
      <el-button type="primary" text bg icon="Link">加入频道</el-button>
      <el-button type="danger" text bg icon="ChatRound">创建频道</el-button>
    </div>

    <div class="chat-session" v-for="(item , index) in 9" :key="item">
      <div class="chat-icon">
        <img :src="'http://data.linesno.com/icons/sepcialist/dataset_' + (index+65)+ '.png'" 
          style="width:40px;height:40px;border-radius: 50%;" />
      </div>
      <div class="chat-title">
        #公共频道
      </div>
      <div class="chat-desc">
        企业内部公共频道和聊天室
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>

.chat-builder {
    float: left;
    width: 100%;
    padding: 10px;
    text-align: center;
    margin-bottom: 10px;
}

.chat-session-list-box {
  .chat-session {
    margin-bottom: 10px;
    background: #fafafa;
    border-radius: 5px;
    float: left;
    width: 100%;
    height: 60px;

    .chat-title {
      font-size: 15px;
      font-weight: bold;
      margin-left: 60px;
      padding-top: 10px;
      float: left;
      line-height: 1rem;
      padding-bottom: 5px;
    }

    .chat-desc {
      font-size: 13px;
      margin-left: 60px;
      float: left;
      color: #a5a5a5;
      line-height: 16px;
    }

    .chat-icon {
      position: absolute;
      margin: 8px;
    }
  }
}
</style>