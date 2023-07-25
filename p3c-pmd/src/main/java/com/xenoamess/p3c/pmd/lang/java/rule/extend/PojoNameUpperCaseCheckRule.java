package com.xenoamess.p3c.pmd.lang.java.rule.extend;

import com.xenoamess.p3c.pmd.I18nResources;
import com.xenoamess.p3c.pmd.lang.java.rule.AbstractAliRule;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;

/**
 * 当类的后缀为 DO / BO / DTO / VO / AO / PO /UID 等 时，必须大写
 * @author hubz
 * @date 2023/7/25 8:59
 */
public class PojoNameUpperCaseCheckRule extends AbstractAliRule {

    private static final String UPPER_CASE_DO_STR = "DO";
    private static final String LOWER_CASE_DO_STR = UPPER_CASE_DO_STR.toLowerCase();

    private static final String UPPER_CASE_DTO_STR = "DTO";
    private static final String LOWER_CASE_DTO_STR = UPPER_CASE_DTO_STR.toLowerCase();

    private static final String UPPER_CASE_BO_STR = "BO";
    private static final String LOWER_CASE_BO_STR = UPPER_CASE_BO_STR.toLowerCase();
    private static final String UPPER_CASE_VO_STR = "VO";
    private static final String LOWER_CASE_VO_STR = UPPER_CASE_VO_STR.toLowerCase();
    private static final String UPPER_CASE_AO_STR = "AO";
    private static final String LOWER_CASE_AO_STR = UPPER_CASE_AO_STR.toLowerCase();
    private static final String UPPER_CASE_PO_STR = "PO";
    private static final String LOWER_CASE_PO_STR = UPPER_CASE_PO_STR.toLowerCase();
    private static final String UPPER_CASE_UID_STR = "UID";
    private static final String LOWER_CASE_UID_STR = UPPER_CASE_UID_STR.toLowerCase();


    @Override
    public Object visit(ASTClassOrInterfaceDeclaration node, Object data) {
        check(node, data, LOWER_CASE_DO_STR, UPPER_CASE_DO_STR);
        check(node, data, LOWER_CASE_DTO_STR, UPPER_CASE_DTO_STR);
        check(node, data, LOWER_CASE_BO_STR, UPPER_CASE_BO_STR);
        check(node, data, LOWER_CASE_VO_STR, UPPER_CASE_VO_STR);
        check(node, data, LOWER_CASE_AO_STR, UPPER_CASE_AO_STR);
        check(node, data, LOWER_CASE_PO_STR, UPPER_CASE_PO_STR);
        check(node, data, LOWER_CASE_UID_STR, UPPER_CASE_UID_STR);
        return super.visit(node, data);
    }

    public void check(ASTClassOrInterfaceDeclaration node, Object data, String lowerCheck, String upperCheck) {
        String simpleName = node.getSimpleName();
        String simpleNameLowerCase = simpleName.toLowerCase();
        if (simpleNameLowerCase.endsWith(lowerCheck) && !simpleName.endsWith(upperCheck)) {
            int length = lowerCheck.length();
            int simpleNameLength = simpleName.length();
            String suffix = simpleName.substring(simpleNameLength - length - 1, simpleNameLength - 1);
            addViolationWithMessage(data, node,
                    I18nResources.getMessage("java.extend.PojoNameUpperCaseCheckRule.rule.msg",
                            simpleName, suffix));
        }
    }

}
