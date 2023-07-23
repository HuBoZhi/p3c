package com.xenoamess.p3c.pmd.lang.java.rule.extend;

import com.xenoamess.p3c.pmd.lang.java.rule.AbstractAliRule;
import net.sourceforge.pmd.lang.java.ast.ASTFormalParameters;

/**
 * @author hubz
 * @date 2023/7/20 12:45
 **/
public class MethodParamsNumRule extends AbstractAliRule {

    private static final int PARAMSNUM = 5;

    @Override
    public Object visit(ASTFormalParameters node, Object data) {
        if (node.getNumChildren() > PARAMSNUM) {
            addViolationWithMessage(data, node, "java.extend.MethodParamsNumRule.rule.msg");
        }
        return super.visit(node, data);
    }

}
 
 