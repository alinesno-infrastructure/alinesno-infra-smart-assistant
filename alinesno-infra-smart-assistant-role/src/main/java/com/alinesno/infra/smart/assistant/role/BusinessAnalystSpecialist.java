package com.alinesno.infra.smart.assistant.role;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alinesno.infra.smart.assistant.api.adapter.TaskContentDto;
import com.alinesno.infra.smart.assistant.role.context.RoleChainContext;
import com.alinesno.infra.smart.assistant.role.utils.ParserUtils;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 需求分析专家
 */
@Slf4j
@Component
public class BusinessAnalystSpecialist extends PlatformExpert {

    private static final String STEP_01 = "mOO5Fq5s" ;
    private static final String STEP_02 = "V5yZ1l3H" ;
    private static final String STEP_03 = "RgPSt3oS" ;
    private static final String STEP_04 = "3hmyCqhJ" ;
    private static final String STEP_05 = "wbqZCMp8" ;

    @LiteflowComponent(value = "BA_STEP_01" , name="需求分析_需求文档分析")
    public class BA_STEP_01 extends NodeComponent {

        @Override
        public void process() throws Exception {
            RoleChainContext roleContext = this.getContextBean(RoleChainContext.class) ;
            String businessId = generatorId() ;

            Map<String , Object> params = this.getRequestData();
            brainRemoteService.chatTask(params , businessId , STEP_01);

            roleContext.setBusinessId(businessId);
            roleContext.setUserContent(params.get("label1").toString());
        }
    }

    @LiteflowComponent(value = "BA_STEP_02" , name="需求分析_项目介绍分析")
    public class BA_STEP_02 extends NodeComponent {

        @Override
        public void process() throws Exception {
            RoleChainContext roleContext = this.getContextBean(RoleChainContext.class) ;
            String businessId = generatorId() ;

            Map<String , Object> params = this.getRequestData();
            brainRemoteService.chatTask(params , businessId , STEP_02);

            roleContext.setBusinessId(businessId);
            roleContext.setUserContent(params.get("label1").toString());
        }
    }

    @LiteflowComponent(value = "BA_STEP_03" , name="需求分析_项目功能分析")
    public class BA_STEP_03 extends NodeComponent {

        @Override
        public void process() throws Exception {
            RoleChainContext roleContext = this.getContextBean(RoleChainContext.class) ;
            String businessId = generatorId() ;

            Map<String , Object> params = this.getRequestData();
            brainRemoteService.chatTask(params , businessId , STEP_03);

            // 等待获取到内容并解析

            // >>>>>>>>>>>>>>>>>>>>>>> 获取结果并解析 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.
            int retryCount = 0 ;
            while (retryCount <= MAX_RETRY_COUNT) {

                Thread.sleep(DEFAULT_SLEEP_TIME);

                TaskContentDto content = brainRemoteService.chatContent(businessId);
                log.debug("promptId = {} , content = {}" , STEP_03 , content);

                if(content.getTaskStatus() == 2){
                    List<FunctionBean> functionBeanList = parseFunctionModule(content.getCodeContent().get(0).getContent());
                    roleContext.setFunctionBeanList(functionBeanList);
                    break ;
                }

                retryCount ++ ;
                log.debug("生效获取业务[{}]次数:{}" , businessId , retryCount);
            }

            roleContext.setBusinessId(businessId);
            roleContext.setUserContent(params.get("label1").toString());
        }
    }

    @LiteflowComponent(value = "BA_STEP_04" , name="需求分析_项目功能细化分析")
    public class BA_STEP_04 extends NodeComponent {

        @Override
        public void process() throws Exception {
            RoleChainContext roleContext = this.getContextBean(RoleChainContext.class) ;
            List<FunctionBean> functionBeanList  = roleContext.getFunctionBeanList() ;
            List<FunctionBean> functionBeanBusList = new ArrayList<>() ;
            List<FunctionBean> functionBeanAssisList = new ArrayList<>() ;

            for(FunctionBean functionBean : functionBeanList){

                String businessId = generatorId() ;
                functionBean.setBusinessId(businessId);

                functionBeanBusList.add(functionBean) ;

                Map<String , Object> params = this.getRequestData();
                params.put("label1" , functionBean.getName()) ;
                brainRemoteService.chatTask(params , businessId , STEP_04);
            }


            // >>>>>>>>>>>>>>>>>>>>>>> 获取结果并解析 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.
            int retryCount = 0 ;
            while (retryCount <= MAX_RETRY_COUNT) {

                Thread.sleep(DEFAULT_SLEEP_TIME * 5L);
                boolean isFinish = true ;

                // 等待获取内容并解析
                for(FunctionBean functionBean : functionBeanBusList){
                    TaskContentDto content = brainRemoteService.chatContent(functionBean.getBusinessId());
                    log.debug("promptId = {} , content = {}" , STEP_03 , content);

                    if(content.getTaskStatus() != 2){
                        isFinish = false ;
                        break ;
                    }else{

                        String functionYaml = content.getCodeContent().get(0).getContent();
                        functionBean.setAssistantContent(parseFunctionContent(functionYaml)) ;

                        functionBeanAssisList.add(functionBean) ;
                    }
                }

                if(isFinish){
                    break ;
                }

                retryCount ++ ;
                log.debug("生效获取业务次数:{}" , retryCount);
            }

            // >>>>>>>>>>>>>>>>>>>> 输出内容 >>>>>>>>>>>>>>>>>>>>
            log.debug("functionBeanAssisList = {}" , JSONObject.toJSONString(functionBeanAssisList));
        }
    }

    @LiteflowComponent(value = "BA_STEP_05" , name="需求分析_项目非功能性")
    public class BA_STEP_05 extends NodeComponent {

        @Override
        public void process() throws Exception {
            RoleChainContext roleContext = this.getContextBean(RoleChainContext.class) ;
            String businessId = generatorId() ;

            Map<String , Object> params = this.getRequestData();
            brainRemoteService.chatTask(params , businessId , STEP_05);

            // >>>>>>>>>>>>>>>>>>>>>>> 获取结果并解析 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.
            int retryCount = 0 ;
            while (retryCount <= MAX_RETRY_COUNT) {

                Thread.sleep(DEFAULT_SLEEP_TIME);

                TaskContentDto content = brainRemoteService.chatContent(businessId);
                log.debug("promptId = {} , content = {}" , STEP_03 , content);

                if(content.getTaskStatus() == 2){
                    // 解析获取非功能性需求

                    break ;
                }

                retryCount ++ ;
                log.debug("生效获取业务[{}]次数:{}" , businessId , retryCount);
            }

            roleContext.setBusinessId(businessId);
            roleContext.setUserContent(params.get("label1").toString());
        }
    }

    @LiteflowComponent(value = "BA_STEP_AGG" , name="需求分析_聚合成文档")
    public class BA_STEP_AGG extends NodeComponent {

        @Override
        public void process() throws Exception {

        }
    }

    /**
     * 解析出功能列表
     *
     * @param functionYaml
     * @return
     */
    public List<FunctionBean> parseFunctionModule(String functionYaml) {

        String jsonStr = ParserUtils.convertYamlToJson(functionYaml) ;
        JSONObject jsonObject = JSONObject.parseObject(jsonStr) ;

        JSONArray functionalModules =  jsonObject.getJSONObject("requirements_description").getJSONObject("functional_requirements").getJSONArray("functional_modules") ;

        List<FunctionBean> functionBeanList = new ArrayList<>() ;

        for(int i = 0 ; i < functionalModules.size() ;  i ++){

            FunctionBean functionBean = new FunctionBean();

            JSONObject jsonObject1 = functionalModules.getJSONObject(i)  ;
            String moduleNumber = jsonObject1.getString("module_number") ;
            System.out.println("moduleNumber = " + moduleNumber);

            functionBean.setName(moduleNumber);
            functionBean.setLevel("" + (i + 1));
            functionBeanList.add(functionBean);

            JSONArray primaryFunctions = jsonObject1.getJSONArray("primary_functions") ;

            for(int j = 0 ; j < primaryFunctions.size() ;  j ++){
                JSONObject jsonObject2 = primaryFunctions.getJSONObject(j)  ;
                String functionNumber = jsonObject2.getString("function_number") ;
                System.out.println("--->>> functionNumber = " + functionNumber);

                FunctionBean functionBean2 = new FunctionBean();
                functionBean2.setName(functionNumber);
                functionBean2.setLevel((i+1) + "." + (j+1));
                functionBean2.setParentLevel("" + (i+1));
                functionBeanList.add(functionBean2);

                JSONArray secondaryFunctions = jsonObject2.getJSONArray("secondary_functions") ;

                if(secondaryFunctions != null && !secondaryFunctions.isEmpty()){
                    for(int n = 0 ; n < secondaryFunctions.size() ;  n ++){
                        JSONObject jsonObject3 = secondaryFunctions.getJSONObject(n)  ;
                        String secondaryFunctionNumber = jsonObject3.getString("secondary_function_number") ;
                        System.out.println("------>>> secondaryFunctionNumber = " + secondaryFunctionNumber);

                        FunctionBean functionBean3 = new FunctionBean();
                        functionBean3.setName(secondaryFunctionNumber);
                        functionBean3.setLevel((i+1) + "." + (j+1) + "." + (n+1));
                        functionBean3.setParentLevel((i+1) + "." + (j+1) );
                        functionBeanList.add(functionBean3);
                    }
                }

            }
        }

        System.out.println(JSONObject.toJSONString(functionBeanList));

        return functionBeanList ;
    }

    public String parseFunctionContent(String functionYaml) {

        String jsonStr = ParserUtils.convertYamlToJson(functionYaml) ;
        System.out.println(jsonStr);

        JSONObject jsonObject = JSONObject.parseObject(jsonStr) ;

        return jsonObject.getJSONObject("function").getString("desc") ;

    }

//    public static void main(String[] args) {
//
//        // 非功能性需求
//
//       List<FunctionBean> functionBeanList = new ArrayList<>() ;
//       FunctionBean f1 = new FunctionBean() ;
//       f1.setName("性能需求");
//       f1.setItemList(List.of("系统应能够支持大量同时用户访问" , "用户通过应用程序向系统提交信息后，系统应在3秒内向用户显示确认信息" ,"系统应在5秒内响应用户查询，并在此期间在屏幕上显示查询结果" ));
//
//        FunctionBean f2 = new FunctionBean() ;
//        f2.setName("安全性需求");
//        f2.setItemList(List.of( "所有涉及功能信息或个人身份信息的网络事务应根据加密算法进行加密" , "用户通过应用程序向系统提交信息后，系统应在3秒内向用户显示确认信息" ,"系统应在5秒内响应用户查询，并在此期间在屏幕上显示查询结果" ));
//
//        functionBeanList.add(f1) ;
//        functionBeanList.add(f2) ;
//
//        System.out.println(JSONObject.toJSONString(functionBeanList));
//    }

    /**
     * 功能列表
     */
    @Data
    public static class FunctionBean {
        private String businessId ; // 业务标识
        private String name ;  // 功能名称
        private String parentLevel ;   // 父类功能层级
        private String level ;   // 功能层级
        private String type ; // 功能类型(M-模块|F-功能|A-按钮)
        private String assistantContent ; // 生成的功能描述
        private List<FunctionBean> subFunction ; // 子类功能
        private List<String> itemList ; // 子类列表

        public FunctionBean copy(){
            FunctionBean b = new FunctionBean() ;
            BeanUtils.copyProperties(this , b);
            return b;
        }
    }

    @Data
    static class Requirements {
        private List<FunctionalRequirement> non_functional_requirements;

        @Data
        static class FunctionalRequirement {
            private List<Requirement> performance_requirements;
            private List<Requirement> security_requirements;
            private List<Requirement> software_quality_attributes;
            private List<Requirement> other_requirements;
        }

        @Data
        static class Requirement {
            private String code;
            private String description;
        }
    }

}



















