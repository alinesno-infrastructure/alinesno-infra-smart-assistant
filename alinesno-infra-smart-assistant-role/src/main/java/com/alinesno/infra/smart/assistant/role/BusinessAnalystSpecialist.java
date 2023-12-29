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
//        String functionYaml = "non_functional_requirements:\n" +
//                "  - performance_requirements:  # 性能需求\n" +
//                "    - PE-1: \"系统应能够同时处理大量用户的访问请求\"\n" +
//                "    - PE-2: \"用户通过小程序提交信息后，系统应在5秒内向用户显示确认信息\"\n" +
//                "    - PE-3: \"系统应在10秒内响应用户的查询请求，并在此期间在屏幕上显示查询结果\"\n" +
//                "  - security_requirements:  # 安全性需求\n" +
//                "    - SE-1: \"所有涉及个人身份信息的数据传输应使用加密技术进行保护\"\n" +
//                "    - SE-2: \"用户登录受到身份验证和访问控制策略的限制\"\n" +
//                "    - SE-3: \"只有经过身份验证的用户才能访问系统\"\n" +
//                "    - SE-4: \"用户只能访问其相关的课堂信息\"\n" +
//                "  - software_quality_attributes:  # 软件质量属性\n" +
//                "    - QA-1: \"系统应每天24小时可用，可用率为99.9%\"\n" +
//                "    - QA-2: \"系统崩溃导致信息丢失的概率不应超过1%\"\n" +
//                "  - other_requirements:  # 其他需求\n" +
//                "    - OR-1: \"系统应具有用户友好的界面\"\n" +
//                "    - OR-2: \"系统应支持多种语言\"\n" +
//                "    - OR-3: \"系统应与不同的操作系统兼容\"" ;
//
//        String jsonStr = ParserUtils.convertYamlToJson(functionYaml) ;
//        System.out.println(jsonStr);
//
//        JSONObject jsonObject = JSONObject.parseObject(jsonStr) ;
//
//        // Get JSONArray for non_functional_requirements
//        JSONArray nonFunctionalRequirementsArray = jsonObject.getJSONArray("non_functional_requirements");
//
//        // Parse JSON Array to List<FunctionalRequirement>
//        List<Requirements.FunctionalRequirement> functionalRequirements = nonFunctionalRequirementsArray.toJavaList(Requirements.FunctionalRequirement.class);
//
//        // Access the parsed data
//        for (Requirements.FunctionalRequirement functionalRequirement : functionalRequirements) {
//            List<Requirements.Requirement> performanceRequirements = functionalRequirement.getPerformance_requirements() ; // .getPerformanceRequirements();
//            if (performanceRequirements != null) {
//                for (Requirements.Requirement requirement : performanceRequirements) {
//                    System.out.println("Code: " + requirement.getCode() + ", Description: " + requirement.getDescription());
//                }
//            }
//
//            List<Requirements.Requirement> securityRequirements = functionalRequirement.getSecurity_requirements() ; // .getSecurityRequirements();
//            if (securityRequirements != null) {
//                for (Requirements.Requirement requirement : securityRequirements) {
//                    System.out.println("Code: " + requirement.getCode() + ", Description: " + requirement.getDescription());
//                }
//            }
//
//            // Similarly, add null check for other requirement types (softwareQualityAttributes, otherRequirements)
//        }
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



















