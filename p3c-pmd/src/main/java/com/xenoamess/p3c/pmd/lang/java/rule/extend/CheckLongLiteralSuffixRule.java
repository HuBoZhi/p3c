package com.xenoamess.p3c.pmd.lang.java.rule.extend;

import com.xenoamess.p3c.pmd.lang.java.rule.AbstractAliRule;
import net.sourceforge.pmd.lang.ast.GenericToken;
import net.sourceforge.pmd.lang.java.ast.ASTVariableDeclarator;
import net.sourceforge.pmd.lang.java.ast.ASTVariableDeclaratorId;
import net.sourceforge.pmd.lang.java.ast.ASTVariableInitializer;

import java.util.Objects;

/**
 * @author hubz
 * @date 2023/7/31 23:28
 **/
public class CheckLongLiteralSuffixRule extends AbstractAliRule {

    private static final String ERROR_LONG_VALUE_SUFFIX = "l";

    @Override
    public Object visit(ASTVariableDeclarator node, Object data) {
        if (node.getType() == Long.class || node.getType() == long.class) {
            ASTVariableInitializer initializer = node.getFirstChildOfType(ASTVariableInitializer.class);
            ASTVariableDeclaratorId variableDeclaratorId = node.getFirstChildOfType(ASTVariableDeclaratorId.class);
            if (initializer != null) {
                GenericToken genericToken = initializer.jjtGetFirstToken();
                if (Objects.nonNull(genericToken)) {
                    String fieldValue = genericToken.getImage();
                    if (fieldValue.endsWith(ERROR_LONG_VALUE_SUFFIX)) {
                        addViolationWithMessage(data, node, "java.extend.CheckLongLiteralSuffixRule.rule.msg",
                                new Object[]{variableDeclaratorId.getName(), fieldValue});
                    }
                }

            }
        }
        return super.visit(node, data);
    }
}
