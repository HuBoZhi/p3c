package org.hubz.pmd;

import net.sourceforge.pmd.PMD;
import org.apache.commons.cli.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author hubz
 * @date 2023/7/24 15:42
 */
public class PMDRun {

    public static void main(String[] args) throws IOException {
        try {
            String path = "/pmd-rules.xml";
            String filePath = ".\\rules\\";
            String fileName = "pmd-rules.xml";
            InputStream is = PMDRun.class.getResourceAsStream(path);
            File file = new File(filePath + fileName);
            if (!file.exists()) {
                System.out.println("dasdadas");
                FileUtils.copyInputStreamToFile(is, file);
            }


            Option codeDirOption = new Option("d", "codeDir", true, "code dir");
            codeDirOption.setRequired(true);
            Option rulesetsOption = new Option("rule", "rulesets", true, "rulesets file path");
            rulesetsOption.setRequired(true);


            Options options = new Options();
            options.addOption(codeDirOption);
            options.addOption(rulesetsOption);


            CommandLine cli = null;
            CommandLineParser cliParser = new DefaultParser();
            HelpFormatter helpFormatter = new HelpFormatter();

            try {
                cli = cliParser.parse(options, args);
            } catch (ParseException e) {
                // 解析失败是用 HelpFormatter 打印 帮助信息
                helpFormatter.printHelp(">>>>>> PMD Run options", options);
                e.printStackTrace();
            }

            String codeDir = "";
            String rulesets = "";
            //根据不同参数执行不同逻辑
            if (cli.hasOption("codeDir")) {
                codeDir = cli.getOptionValue("codeDir");
            } else if (cli.hasOption("d")) {
                codeDir = cli.getOptionValue("d");
            }
            if (cli.hasOption("codeDir")) {
                codeDir = cli.getOptionValue("codeDir");
            } else if (cli.hasOption("d")) {
                codeDir = cli.getOptionValue("d");
            }

            if ("".equals(codeDir)) {
                helpFormatter.printHelp(">>>>>> PMD Run options", options);
            }

            // 运行PMD
            PMD.runPmd("--dir", codeDir,
                    "--rulesets", rulesets,
                    "--format", "vbhtml",
                    "--threads", "5");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}