package org.hubz.pmd;

import net.sourceforge.pmd.PMD;

import java.io.IOException;

/**
 * @author hubz
 * @date 2023/7/24 15:42
 */
public class PMDRun {

    public static void main(String[] args) throws IOException {
        try {
            String codeDir = "E:\\Coding\\SpringAll\\CheckStyleStudy";
            String rulesets = "E:\\Coding\\SpringAll\\CheckStyleStudy\\ali-pmd.xml";
            // 运行PMD
            PMD.runPmd("--dir", codeDir,
                    "--format", "text",
                    "--rulesets", rulesets,
                    "--encoding", "UTF-8",
                    "--threads","5");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}