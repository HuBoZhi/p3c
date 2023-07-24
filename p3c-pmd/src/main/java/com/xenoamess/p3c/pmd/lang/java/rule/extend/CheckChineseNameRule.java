package com.xenoamess.p3c.pmd.lang.java.rule.extend;

import com.xenoamess.p3c.pmd.I18nResources;
import com.xenoamess.p3c.pmd.lang.java.rule.AbstractAliRule;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTVariableDeclaratorId;

/**
 * @author hubz
 * @date 2023/7/24 23:44
 **/
public class CheckChineseNameRule extends AbstractAliRule {

    private static final String ERROR_MESSAGE = I18nResources.getMessageWithExceptionHandled("java.extend.CheckChineseNameRule.rule.msg");

    @Override
    public Object visit(ASTClassOrInterfaceDeclaration node, Object data) {
        if (containsChinese(node.getSimpleName())) {
            addViolationWithMessage(data, node, "类名【" + node.getSimpleName() + "】" + ERROR_MESSAGE);
        }
        return super.visit(node, data);
    }

    @Override
    public Object visit(ASTMethodDeclaration node, Object data) {
        if (containsChinese(node.getName())) {
            addViolationWithMessage(data, node, "方法名【" + node.getName() + "】" + ERROR_MESSAGE);
        }
        return super.visit(node, data);
    }

    @Override
    public Object visit(ASTVariableDeclaratorId node, Object data) {
        if (containsChinese(node.getName())) {
            addViolationWithMessage(data, node, "变量名【" + node.getName() + "】" + ERROR_MESSAGE);
        }
        return super.visit(node, data);
    }

    private boolean containsChinese(String str) {
        return str.codePoints().anyMatch(codepoint ->
                Character.UnicodeScript.of(codepoint) == Character.UnicodeScript.HAN);
    }
}
