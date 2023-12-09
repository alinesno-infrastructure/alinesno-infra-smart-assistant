<template>
  <el-scrollbar class="scroll-panel" v-loading="loading" ref="scrollbarRef" always>

    <div ref="innerRef" style="height: auto;">
      <div class="robot-chat-ai-say-box" v-for="(item, index) in messageList" :key="index">
        <div class="chat-ai-header" :class="item.roleType=='person'?'say-right-window':''">
          <div class="header-images">
            <img src="http://data.linesno.com/icons/sepcialist/dataset_55.png" />
          </div>
        </div>
        <div class="chat-ai-say-body" :class="item.roleType=='person'?'say-right-window':''" style="max-width:90%">
          <div class="say-message-info"> {{ item.name }} {{ item.dateTime }}</div>
          <div class="say-message-body markdown-body" v-html="readerHtml(item.chatText)"></div>
        </div>
      </div>
    </div>

  </el-scrollbar>
</template>

<script setup>

import {
  chatMessage
} from '@/api/smart/assistant/robot'

import { getParam } from '@/utils/ruoyi'

import { computed, ref } from 'vue';
import MarkdownIt from 'markdown-it';
import mdKatex from '@traptitech/markdown-it-katex';
// import mila from 'markdown-it-link-attributes';
import hljs from 'highlight.js';


const messageList = ref([
  { roleType: 'agent', name: '数据库设计Agent', date: '07-16 19:19:51', chatText: '你现在是一名Java计算机面试官，请生成微服务架构相关的，现在需要生成题目做于Java面试题，主要题目类型如下：\n\n- 单选题： single_choice  \n- 多选题： multiple_choice  \n- 填空题： fill_in_the_blank  \n\n现在请使用yaml生成单选题5题，多选题2题，填写题3题，参考下面的示例生成yaml文件，并返回yaml格式\n题目的评分标准按题目的难度来设定分数，总分是100分，请生成10道题目。\n\n下面是生成的示例数据：\n```yaml\n- title: 三国谁最厉害\n  desc: 这里考的是三国中哪个最厉害的角色\n  type: single_choice\n  score: 10\n  answers: \n  \t- A: 张三\n  \t- B: 王五\n  \t- C: 李四\n   \t- D: 吕布\n  rightAnswer: D\n\n- title: 三国谁最厉害，下面哪个是错的\n  desc: 这里考的是三国中哪个最厉害的角色\n  type: multiple_choice\n  score: 10\n  answers: \n  \t- A: 张三\n  \t- B: 王五\n  \t- C: 李四\n   \t- D: 吕布\n  rightAnswer: A|B|C # 多个答案使用|号进行分隔\n\n- title: 三国谁最厉害\n  desc: 这里考的是三国中哪个最厉害的角色\n  type: fill_in_the_blank\n  score: 10\n  answers: 吕布\n```\n\n下面是返回的格式，请生成10道题目\n```yaml\n\u003c在这里生成考试题目\u003e\n```\n "},{"role":"system","content":"你现在是一名Java计算机面试官，请生成微服务架构相关的，现在需要生成题目做于Java面试题，你将会收到一份面试题目的需求，根据需求生成内容。' },
  { roleType: 'person', name: '平台管理员', date: '07-18 20:19:51', chatText: '给出查询出alinesno.code.test的字典接口代码' },
])
const loading = ref(true)

// 滚动条的处理_starter
const innerRef = ref(null);
const scrollbarRef = ref(null);

function initChatBoxScroll(){

  const element = innerRef.value;  // 获取滚动元素
  const scrollHeight = element.scrollHeight ;

  // TODO 待处理滚动条没有到底部的问题
  console.log('scrollHeight = ' + scrollHeight);
  scrollbarRef.value.setScrollTop(scrollHeight) ;
}


const mdi = new MarkdownIt({
  html: false,
  linkify: true,
  highlight(code, language) {
    const validLang = !!(language && hljs.getLanguage(language));
    if (validLang) {
      const lang = language || '';
      return highlightBlock(hljs.highlight(code, { language: lang }).value, lang);
    }
    return highlightBlock(hljs.highlightAuto(code).value, '');
  },
});

// mdi.use(mila, { attrs: { target: '_blank', rel: 'noopener' } });
mdi.use(mdKatex, { blockClass: 'katexmath-block rounded-md p-[10px]', errorColor: ' #cc0000' });

function highlightBlock(str, lang) {
  return `<pre class="code-block-wrapper"><code class="hljs code-block-body ${lang}">${str}</code></pre>`;
}


function readerHtml(chatText){
  return mdi.render(chatText) ;
}

/** 获取到会话信息 */
function handleChatMessage(){
  let businessId = getParam('businessId') ;
  console.log('businessId = ' + businessId) ;

  chatMessage(businessId).then(response => {
    messageList.value = response.data ; 
    loading.value = false ;
    initChatBoxScroll() ;
  })
}

handleChatMessage() ;

</script>

<style lang="scss" scoped>

.scroll-panel{
    padding-bottom: 10px;
    float: left;
    width: 100%;
    height: calc(100% - 65px);
    overflow: hidden;;
}

.robot-chat-ai-say-box {
  float: left;
  width: 100%;

  .say-right-window {
    float: right !important;

    .say-message-info {
      text-align: right !important;
    }
  }

  .chat-ai-header {
    float: left;
    width: 50px;
    margin: 10px;

    .header-images {
      padding: 5px;

      img {
        width: 100%;
        border-radius: 50%;
      }

    }

  }

  .chat-ai-say-body {
    float: left;
    margin-top: 15px;
    font-size: 14px;

    .say-message-info {
      font-size: 13px;
      margin-bottom: 5px;
      color: #999;
    }

    .say-message-body {
      padding: 10px;
      line-height: 1.4rem;
      background: #fafafa;
      border-radius: 3px;
    }

  }


}

</style>