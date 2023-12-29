package com.alinesno.infra.smart.assistant.role;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alinesno.infra.smart.assistant.api.adapter.TaskContentDto;
import com.alinesno.infra.smart.assistant.role.context.RoleChainContext;
import com.alinesno.infra.smart.assistant.role.utils.ParserUtils;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据库设计专家
 */
@Slf4j
@Component
public class DatabaseDesignExpert extends PlatformExpert {

    private static final String DB_TABLE_DESIGN_PROMPT_ID = "uvaWo9Es" ; // 数据库表设计专家
    private static final String DB_DDL_DESIGN_PROMPT_ID = "YOFieUPq" ; // 数据库DDL设计专家

    private static final String DATABASE_DESIGN_PREFIX = "database_design"  ;

    @LiteflowComponent(value = "dd_table" , name="生成数据库表列表信息")
    public class GeneratorTableParse extends NodeComponent {

        @Override
        public void process() throws Exception {

            // 获取上下文
            RoleChainContext roleContext = this.getContextBean(RoleChainContext.class) ;
            roleContext.setStartTime(System.currentTimeMillis());

            // 通过上下文传入
            String businessId = generatorId() ;

            // 获取到参数
            Map<String , Object> params = this.getRequestData();
            brainRemoteService.chatTask(params , businessId , DB_TABLE_DESIGN_PROMPT_ID);

            log.debug("params = {}" , params);

            // 设置上下文
            roleContext.setBusinessId(businessId);
            roleContext.setUserContent(params.get("label1").toString());

        }
    }

    @LiteflowComponent(value = "dd_table_result" , name="生成数据库表列表信息")
    public class GeneratorTableResultParse extends NodeComponent {

        @Override
        public void process() throws Exception {

            // 默认等待生成时间
            Thread.sleep(DEFAULT_SLEEP_TIME);

            // 获取上下文
            RoleChainContext roleContext = this.getContextBean(RoleChainContext.class) ;

            String businessId = roleContext.getBusinessId() ;  // 获取到业务Id
            TaskContentDto content = brainRemoteService.chatContent(businessId);

            log.debug("promptId = {} , content = {}" , DB_TABLE_DESIGN_PROMPT_ID , content);

            // 设置上下文
            roleContext.setAssistantContent(content);

        }
    }

    @LiteflowComponent(value = "dd_table_ddl" , name="生成数据库表结构信息")
    public class GeneratorTableDDLParse extends NodeComponent {

        @Override
        public void process() throws Exception {

            // 获取上下文
            RoleChainContext roleContext = this.getContextBean(RoleChainContext.class) ;
            TaskContentDto taskContentDto = roleContext.getAssistantContent() ;

            List<TaskContentDto.CodeContent> codeList =taskContentDto.getCodeContent() ;
            TaskContentDto.CodeContent codeContent = codeList.get(0) ;

            String tables = ParserUtils.convertYamlToJson(codeContent.getContent()) ;
            List<Table> tables1 = convertJsonToBean(tables);

            List<String> businessIds = new ArrayList<>() ;

            for (Table table : tables1) {
                System.out.println(JSONObject.toJSON(table));

                // 通过上下文传入
                String businessId = generatorId() ;

                // 获取到参数
                Map<String , Object> params = this.getRequestData();
                params.put("label2" , table.getName()+":" + table.getTableName() + "," + table.getDesc())  ;

                brainRemoteService.chatTask(params , businessId , DB_DDL_DESIGN_PROMPT_ID);

                businessIds.add(businessId) ;
            }

            roleContext.setBusinessIds(businessIds);
        }
    }

    @LiteflowComponent(value = "dd_table_ddl_result" , name="生成数据库表结构信息")
    public class GeneratorTableDDLResultParse extends NodeComponent {

        @Override
        public void process() throws Exception {

            // 默认等待生成时间
            Thread.sleep(DEFAULT_SLEEP_TIME);

            // 获取上下文
            RoleChainContext roleContext = this.getContextBean(RoleChainContext.class) ;
            List<String> businessIds = roleContext.getBusinessIds() ;  // 获取到业务Id
            List<TaskContentDto> taskContentDtos = new ArrayList<>() ;

            // 使用所有的任务内容等待结果
            for(String bId : businessIds){
                TaskContentDto content = brainRemoteService.chatContent(bId);
                log.debug("promptId = {} , content = {}" , DB_TABLE_DESIGN_PROMPT_ID , content);
                taskContentDtos.add(content) ;
            }

            // 设置上下文
            roleContext.setAssistantContents(taskContentDtos) ;
        }
    }

    @LiteflowComponent(value = "dd_agg", name="聚合所有的结果内容并返回")
    public class GeneratorTableAggParse extends NodeComponent {

        @Override
        public void process() throws Exception {

            // 获取上下文
            RoleChainContext roleContext = this.getContextBean(RoleChainContext.class) ;
            List<TaskContentDto> taskContentDtos = roleContext.getAssistantContents() ;

            System.out.println("--->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>---");

            for(TaskContentDto dto : taskContentDtos){
                for(TaskContentDto.CodeContent codeDto : dto.getCodeContent()){
                    System.out.println(codeDto.getContent());
                }
            }

            System.out.println("--->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>---");
        }
    }

    public static List<Table> convertJsonToBean(String json) {
        List<Table> tables = new ArrayList<>();

        JSONObject jsonObject = JSON.parseObject(json);
        JSONObject tablesObject = jsonObject.getJSONObject("tables");

        for (String tableName : tablesObject.keySet()) {
            JSONArray tableArray = tablesObject.getJSONArray(tableName);

            Table table = new Table() ;
            table.setTableName(tableName);

            for (int i = 0; i < tableArray.size(); i++) {
                JSONObject tableObject = tableArray.getJSONObject(i);

                String name = tableObject.getString("name") ;
                String desc = tableObject.getString("desc") ;

                if(StringUtils.isNotBlank(name)){
                   table.setName(name);
                }else if(StringUtils.isNotBlank(desc)){
                    table.setDesc(desc);
                }

            }
            tables.add(table);
        }

        return tables;
    }

    @Data
    public static class Table {
        private String tableName ;
        private String name;
        private String desc;
    }

}
