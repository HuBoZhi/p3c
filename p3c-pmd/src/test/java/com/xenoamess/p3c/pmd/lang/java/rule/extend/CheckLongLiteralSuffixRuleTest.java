package com.xenoamess.p3c.pmd.lang.java.rule.extend;

import net.sourceforge.pmd.testframework.SimpleAggregatorTst;

/**
 * @author hubz
 * @date 2023/8/6 17:46
 **/
public class CheckLongLiteralSuffixRuleTest extends SimpleAggregatorTst {
    private static final String RULESET = "java-ali-extend";

    @Override
    public void setUp() {
        addRule(RULESET, "CheckLongLiteralSuffixRule");
    }
}
