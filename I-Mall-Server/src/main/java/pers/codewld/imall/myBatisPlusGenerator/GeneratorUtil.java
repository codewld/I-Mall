package pers.codewld.imall.myBatisPlusGenerator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

/**
 * <p>
 * MyBatis Plus Generator 工具类
 * </p>
 *
 * @author codewld
 * @since 2022-02-04
 */
public class GeneratorUtil {

    private static HashMap<String, Object> configuration;

    private static final String baseDir = System.getProperty("user.dir") + "\\src\\main";

    static void loadConfigurations() throws FileNotFoundException {
        configuration = new Yaml().load(new FileInputStream(baseDir + "\\resources\\config\\generator.yml"));
    }

    public static void main(String[] args) {
        try {
            loadConfigurations();
            String url = (String) configuration.get("url");
            String username = (String) configuration.get("username");
            String password = (String) configuration.get("password");
            String author = (String) configuration.get("author");
            String parent = (String) configuration.get("parent");
            Boolean isolated = (Boolean) configuration.get("isolated");
            String baseOutputDir = Objects.equals(isolated, true) ? baseDir + "\\myBatisPlusGenerator" : baseDir;
            String JavaOutputDir = baseOutputDir + "\\java";
            String XMLOutputDir = baseOutputDir + "\\resources\\mapper";

            FastAutoGenerator.create(url, username, password)
                    .globalConfig(builder -> {
                        builder.author(author) // 设置作者
                                .disableOpenDir()  // 禁止打开输出目录
                                .enableSwagger() // 开启 swagger 模式
                                .fileOverride() // 覆盖已生成文件
                                .outputDir(JavaOutputDir); // 指定输出目录
                    })
                    .packageConfig(builder -> {
                        builder.parent(parent) // 父包名
                                // .serviceImpl("service")  // serviceImpl包名
                                .pathInfo(Collections.singletonMap(OutputFile.mapperXml, XMLOutputDir)); // 设置mapperXml生成路径
                    })
                    .strategyConfig(builder -> {
                        builder.notLikeTable(new LikeTable("relation")) // 过滤关系表
                                .entityBuilder()
                                .enableLombok() // 开启Lombok
                                .enableRemoveIsPrefix() // 去除is前缀
                                .controllerBuilder()
                                .enableRestStyle() // 自动注解@RestController
                                .mapperBuilder()
                                .enableMapperAnnotation() // 自动注解@Mapper
                                .serviceBuilder()
                                .convertServiceFileName((entityName -> entityName + ConstVal.SERVICE)); // 更改Service命名方式
                    })
                    .templateEngine(new FreemarkerTemplateEngine())
                    .execute();
        } catch (FileNotFoundException e) {
            System.out.println("配置文件不存在或路径错误，请检查。");
            e.printStackTrace();
        }
    }
}
