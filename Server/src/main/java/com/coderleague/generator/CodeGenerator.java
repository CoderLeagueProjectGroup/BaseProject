package com.coderleague.generator;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by DELL on 2018/10/22.
 */
public class CodeGenerator {

    /**
     * 读取控制台内容
     * @param tip
     * @return
     */
    public static String scanner(String tip){
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入"+tip+":");
        System.out.println(help);
        if(scanner.hasNext()){
            String ipt = scanner.next();
            if(StringUtils.isNotEmpty(ipt)){
                return ipt;
            }
        }
        throw new IllegalArgumentException("请输入正确的"+tip+"！");
    }

    public static void main(String[] args) {
        //代码生成器
        AutoGenerator mpg = new AutoGenerator();
        //全局配置
        GlobalConfig gc=new GlobalConfig();
        String projectPath=System.getProperty("user.dir");
        gc.setOutputDir(projectPath+"/src/main/java");
        gc.setAuthor("qcb");
        gc.setOpen(false);
        //是否覆盖已有文件
        gc.setFileOverride(true);
        gc.setSwagger2(true);
        gc.setBaseColumnList(true);
        gc.setBaseResultMap(true);
        mpg.setGlobalConfig(gc);
        //数据源配置
        DataSourceConfig dsc=new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/sys_db?useUnicode=true&useSSL=false&characterEncoding=utf8");
        dsc.setUsername("qcb");
        dsc.setPassword("123456");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        mpg.setDataSource(dsc);
        //包配置
        PackageConfig pc=new PackageConfig();
//        pc.setModuleName(scanner("模块名"));
        pc.setModuleName("user");
        pc.setParent("com.coderleague.module");
        mpg.setPackageInfo(pc);

        //自定义配置
        InjectionConfig cfg=new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                //自定义输出文件名
                return projectPath+"/src/main/java/com/coderleague/module/"
                        +pc.getModuleName()+"/mapper/xml/"+tableInfo.getEntityName()
                        +"Mapper"+ StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(new TemplateConfig().setXml(null));
        //策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
//        strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
//        List<String> tableList = new ArrayList<>();
//        String tableName;
//        while (true){
//            tableName = scanner("表名");
//            if("q".equalsIgnoreCase(tableName)){
//                break;
//            }
//            tableList.add(tableName);
//        }
//        strategy.setInclude(tableList.toArray(new String[tableList.size()]));
        strategy.setInclude("user","department","role","module","role_module","action","module_action");
//        strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);
//        strategy.setTablePrefix(pc.getModuleName() + "_");
        //驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);
        //是否生成实体时，生成字段注解
        strategy.entityTableFieldAnnotationEnable(true);
        //逻辑删除属性名称
        strategy.setLogicDeleteFieldName("delete_flag");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
