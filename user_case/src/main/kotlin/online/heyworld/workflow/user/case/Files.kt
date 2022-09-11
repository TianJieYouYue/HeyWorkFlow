package online.heyworld.workflow.user.case

import online.heyworld.workflow.common.file.CopyWorkFlow
import online.heyworld.workflow.common.file.CopyWorkFlowParam
import online.heyworld.workflow.common.file.MatchRule
import online.heyworld.workflow.common.util.hey
import online.heyworld.workflow.lifecycle.HeyWorkFlowLifecycleHolder
import java.nio.file.Paths

object Files {
    @JvmStatic
    fun main(args: Array<String>) {
        println(System.getProperty("user.home"))
        hey(
            CopyWorkFlow("CopyImages",
                CopyWorkFlowParam(
                    fromDir = Paths.get(System.getProperty("user.home"),"Downloads").toFile().absolutePath,
                    toDir = Paths.get(System.getProperty("user.home"),"Downloads-Temp").toFile().absolutePath,
                    includeRules = mutableListOf(
                        MatchRule.of("*.svg"),
                        MatchRule.of("*.jpg"),
                        MatchRule.of("*.png")
                    ),
                    includeChildren = true,
                    keepDir = true
                )
            )
        ).launch(HeyWorkFlowLifecycleHolder.EXIT_AT_END)
    }
}