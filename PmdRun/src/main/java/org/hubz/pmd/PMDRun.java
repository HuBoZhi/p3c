package org.hubz.pmd;

import net.sourceforge.pmd.PMD;
import org.apache.commons.cli.*;
import org.apache.commons.io.FileUtils;
import org.mozilla.universalchardet.UniversalDetector;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author hubz
 * @date 2023/7/24 15:42
 */
public class PMDRun {

    public static void main(String[] args) {
        CommandLineParser cliParser = new DefaultParser();
        HelpFormatter helpFormatter = new HelpFormatter();

        try {
            loadRules();
        } catch (Exception e) {
            throw new RuntimeException("规则文件加载失败", e);
        }

        try {
            Option codeDirOption = new Option("cd", "codeDir", true, "code dir");
            codeDirOption.setRequired(true);
            Option outputOption = new Option("od", "outputFileDir", true, "result file path");
            outputOption.setRequired(true);


            Options options = new Options();
            options.addOption(codeDirOption);
            options.addOption(outputOption);


            CommandLine cli = null;
            try {
                cli = cliParser.parse(options, args);
            } catch (ParseException e) {
                // 解析失败是用 HelpFormatter 打印 帮助信息
                helpFormatter.printHelp(">>>>>> PMD Run options", options);
                e.printStackTrace();
            }

            String codeDir = "";
            String outputFilePath = "";
            //根据不同参数执行不同逻辑
            if (cli.hasOption("codeDir")) {
                codeDir = cli.getOptionValue("codeDir");
            } else if (cli.hasOption("cd")) {
                codeDir = cli.getOptionValue("cd");
            }
            if (cli.hasOption("outputFileDir")) {
                outputFilePath = cli.getOptionValue("outputFileDir");
            } else if (cli.hasOption("od")) {
                outputFilePath = cli.getOptionValue("od");
            }

            if ("".equals(codeDir) || "".equals(outputFilePath)) {
                helpFormatter.printHelp(">>>>>> PMD Run options", options);
            }

            // 运行PMD
            PMD.runPmd("--dir", codeDir,
                    "--rulesets", ".\\rules\\pmd-rules.xml",
                    "--format", "vbhtml",
                    "--report-file", outputFilePath,
                    "--threads", "5");

            // 处理输出的文件结果
            File outputFile = new File(outputFilePath);
            String encoding = detectEncoding(outputFilePath);
            String resultStr = FileUtils.readFileToString(outputFile, encoding);
            int start = resultStr.indexOf("<html>");
            int end = resultStr.indexOf("</html>");
            String dealStr = resultStr.substring(start, Math.min(end, resultStr.length()));
            FileUtils.writeStringToFile(outputFile, dealStr + "</html>", StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadRules() throws IOException {
        String path = "/pmd-rules.xml";
        String filePath = ".\\rules\\";
        String fileName = "pmd-rules.xml";
        InputStream is = PMDRun.class.getResourceAsStream(path);
        File file = new File(filePath + fileName);
        if (!file.exists()) {
            FileUtils.copyInputStreamToFile(is, file);
        }
    }

    public static String detectEncoding(String filePath) throws IOException {
        byte[] buf = new byte[4096];
        FileInputStream fis = new FileInputStream(filePath);

        // (1)
        UniversalDetector detector = new UniversalDetector(null);

        // (2)
        int nread;
        while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
            detector.handleData(buf, 0, nread);
        }
        // (3)
        detector.dataEnd();

        // (4)
        String encoding = detector.getDetectedCharset();
        detector.reset();

        fis.close();

        return encoding;
    }

}