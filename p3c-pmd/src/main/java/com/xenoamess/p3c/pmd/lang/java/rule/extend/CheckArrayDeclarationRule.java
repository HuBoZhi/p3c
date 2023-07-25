package com.xenoamess.p3c.pmd.lang.java.rule.extend;

import com.xenoamess.p3c.pmd.I18nResources;
import net.sourceforge.pmd.lang.java.ast.*;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;

import java.util.List;
import java.util.Objects;

/**
 * @author hubz
 * @date 2023/7/25 22:01
 **/
public class CheckArrayDeclarationRule extends AbstractJavaRule {

    /**
     * 检查局部变量
     *
     * @author hubz
     * @date 2023/7/25 21:59
     **/
    @Override
    public Object visit(ASTLocalVariableDeclaration node, Object data) {
        checkArrayType(node, data);
        return super.visit(node, data);
    }

    /**
     * 检查属性字段
     *
     * @author hubz
     * @date 2023/7/25 21:59
     **/
    @Override
    public Object visit(ASTFieldDeclaration node, Object data) {
        checkArrayType(node, data);
        return super.visit(node, data);
    }

    /**
     * 检查是不是数组类型
     *
     * @author hubz
     * @date 2023/7/25 22:04
     **/
    private void checkArrayType(AbstractJavaAccessNode node, Object data) {
        for (ASTVariableDeclarator declarator : node.findDescendantsOfType(ASTVariableDeclarator.class)) {
            List<ASTVariableDeclaratorId> variableDeclaratorIdList = declarator.findDescendantsOfType(ASTVariableDeclaratorId.class);
            for (ASTVariableDeclaratorId astVariableDeclaratorId : variableDeclaratorIdList) {
                if (Objects.nonNull(astVariableDeclaratorId) && astVariableDeclaratorId.isArray() &&
                        declarator.hasDescendantOfType(ASTArrayDimsAndInits.class)) {
                    String message = I18nResources.getMessageWithExceptionHandled("java.extend.CheckArrayDeclarationRule.rule.msg");
                    asCtx(data).addViolationWithMessage(node, message);
                }
            }
        }
    }
}
