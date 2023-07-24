package com.xenoamess.p3c.pmd;

import net.sourceforge.pmd.PMDConfiguration;
import net.sourceforge.pmd.PmdAnalysis;
import net.sourceforge.pmd.RulePriority;
import net.sourceforge.pmd.lang.LanguageRegistry;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class IfVoidIsPrimitiveTest {
    @Test
    public void test() {
        assertTrue(Void.TYPE.isPrimitive());
    }
}
