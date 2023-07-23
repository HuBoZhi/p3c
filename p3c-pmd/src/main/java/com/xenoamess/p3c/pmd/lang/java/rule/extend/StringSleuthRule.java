package com.xenoamess.p3c.pmd.lang.java.rule.extend;

import com.xenoamess.p3c.pmd.I18nResources;
import net.sourceforge.pmd.lang.java.ast.*;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;

import java.util.List;

/**
 * @author hubz
 * @date 2023/7/23 18:06
 **/
public class StringSleuthRule extends AbstractJavaRule {

    private static final String TARGET_STRING = "fiberhome";
    private static final String ERROR_MESSAGE = I18nResources.getMessageWithExceptionHandled("java.extend.StringSleuthRule.rule.msg");

    /**
     * 类名检测
     *
     * @author hubz
     * @date 2023/7/23 18:16
     **/
    @Override
    public Object visit(ASTClassOrInterfaceDeclaration node, Object data) {
        if (node.getSimpleName().toLowerCase().contains(TARGET_STRING)) {
            asCtx(data).addViolationWithMessage(node, "类名【" + node.getSimpleName() + "】" + ERROR_MESSAGE);
        }
        return super.visit(node, data);
    }

    /**
     * 方法名检测
     *
     * @author hubz
     * @date 2023/7/23 18:16
     **/
    @Override
    public Object visit(ASTMethodDeclaration node, Object data) {
        if (node.getName().toLowerCase().contains(TARGET_STRING)) {
            asCtx(data).addViolationWithMessage(node, "方法名【" + node.getName() + "】" + ERROR_MESSAGE);
        }
        return super.visit(node, data);
    }

    /**
     * 属性名检测
     *
     * @author hubz
     * @date 2023/7/23 18:16
     **/
    @Override
    public Object visit(ASTFieldDeclaration node, Object data) {
        for (ASTVariableDeclaratorId id : node.findDescendantsOfType(ASTVariableDeclaratorId.class)) {
            if (id.getName().toLowerCase().contains(TARGET_STRING)) {
                asCtx(data).addViolationWithMessage(id, "属性名【" + id.getName() + "】" + ERROR_MESSAGE);
            }
        }
        return super.visit(node, data);
    }

    /**
     * 局部变量名检测
     *
     * @author hubz
     * @date 2023/7/23 18:39
     **/
    @Override
    public Object visit(ASTLocalVariableDeclaration node, Object data) {
        List<ASTVariableDeclaratorId> variableDeclarators = node.findDescendantsOfType(ASTVariableDeclaratorId.class);
        for (ASTVariableDeclaratorId variableDeclaratorId : variableDeclarators) {
            if (variableDeclaratorId.getName().toLowerCase().contains(TARGET_STRING)) {
                asCtx(data).addViolationWithMessage(variableDeclaratorId, "局部变量名【" + variableDeclaratorId.getName() + "】" + ERROR_MESSAGE);
            }
        }
        return visit((JavaNode) node, data);
    }

}
