package com.xenoamess.p3c.pmd.lang.java.rule.extend;

import com.xenoamess.p3c.pmd.I18nResources;
import net.sourceforge.pmd.lang.java.ast.ASTVariableDeclaratorId;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;

/**
 * @author hubz
 * @date 2023/7/27 21:35
 **/
public class CheckBooleanPrefixRule extends AbstractJavaRule {

    private static final String NO_EXPECT_BOOLEAN_PREFIX = "is";

    @Override
    public Object visit(ASTVariableDeclaratorId node, Object data) {
        if (node.getType() == Boolean.class || node.getType() == boolean.class) {
            if (node.getName().startsWith(NO_EXPECT_BOOLEAN_PREFIX)) {
                String message = I18nResources.getMessage("java.extend.CheckBooleanPrefixRule.rule.msg", node.getName());
                asCtx(data).addViolationWithMessage(node, message);
            }
        }
        return super.visit(node, data);
    }
}
