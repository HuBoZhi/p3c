/*
 * Copyright 1999-2017 Alibaba Group.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.p3c.idea.compatible.inspection

import com.alibaba.smartfox.idea.common.util.PluginVersions
import com.google.common.collect.Sets
import com.intellij.codeInspection.ex.GlobalInspectionContextImpl
import com.intellij.codeInspection.ex.InspectionManagerEx
import com.intellij.codeInspection.ex.InspectionProfileImpl
import com.intellij.codeInspection.ex.InspectionToolWrapper
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.InvalidDataException
import com.intellij.openapi.util.WriteExternalException
import com.intellij.profile.codeInspection.InspectionProjectProfileManager
import com.intellij.psi.PsiElement
import org.jdom.Element
import java.util.LinkedHashSet

/**
 *
 *
 * @author caikang
 * @date 2017/03/01
 */
object InspectionProfileService {
    fun createSimpleProfile(
            toolWrapperList: List<InspectionToolWrapper<*, *>>,
            managerEx: InspectionManagerEx,
            psiElement: PsiElement?
    ): InspectionProfileImpl {
        val profile = getProjectInspectionProfile(managerEx.project)
        val allWrappers: LinkedHashSet<InspectionToolWrapper<*, *>> = Sets.newLinkedHashSet()
        allWrappers.addAll(toolWrapperList)
        val forCompile = allWrappers
        for (toolWrapper in allWrappers) {
            profile.collectDependentInspections(toolWrapper, forCompile, managerEx.project)
        }
        val model = when (PluginVersions.baseVersion) {
            in PluginVersions.baseVersion171..Int.MAX_VALUE -> {
                val clz = Class.forName("com.intellij.codeInspection.ex.InspectionProfileKt")
                val method = clz.methods.first { it.name == "createSimple" }
                method.invoke(null, "Alibaba Coding Guidelines", managerEx.project, allWrappers.toList())
                        as InspectionProfileImpl
            }
            PluginVersions.baseVersion163 -> {
                val method = profile.javaClass.methods.first {
                    it.name == "createSimple"
                }
                method.invoke(null, "Alibaba Coding Guidelines", managerEx.project, allWrappers.toList())
                        as InspectionProfileImpl
            }
            else -> {
                val method = profile.javaClass.methods.first {
                    it.name == "createSimple"
                }
                method.invoke(null, "Alibaba Coding Guidelines", managerEx.project, allWrappers.toTypedArray())
                        as InspectionProfileImpl
            }
        }
        try {
            val element = Element("toCopy")
            for (wrapper in allWrappers) {
                wrapper.tool.writeSettings(element)
                val tw = if (psiElement == null) {
                    model.getInspectionTool(wrapper.shortName, managerEx.project)
                } else {
                    model.getInspectionTool(wrapper.shortName, psiElement)
                }
                tw!!.tool.readSettings(element)
            }
        } catch (ignored: WriteExternalException) {
        } catch (ignored: InvalidDataException) {
        }
        return model
    }

    fun toggleInspection(
            project: Project,
            aliInspections: List<InspectionToolWrapper<*, *>>,
            closed: Boolean
    ) {
        val profile = getProjectInspectionProfile(project)
        val shortNames = aliInspections.map {
            it.tool.shortName
        }
        profile.removeScopes(shortNames, "AlibabaCodeAnalysis", project)
        val method = profile.javaClass.methods.first {
            it.name == if (closed) {
                "enableToolsByDefault"
            } else {
                "disableToolByDefault"
            }
        }
        method.invoke(profile, shortNames, project)
        profile.profileChanged()
        profile.scopesChanged()
    }

    fun setExternalProfile(
            profile: InspectionProfileImpl,
            inspectionContext: GlobalInspectionContextImpl
    ) {
        val method = inspectionContext.javaClass.methods.first {
            it.name == "setExternalProfile" && it.parameterTypes.size == 1 && it.parameterTypes.first().isAssignableFrom(InspectionProfileImpl::class.java)
        }
        method.invoke(inspectionContext, profile)
    }

    fun getProjectInspectionProfile(project: Project): InspectionProfileImpl {
        return InspectionProjectProfileManager.getInstance(project).currentProfile
    }
}
