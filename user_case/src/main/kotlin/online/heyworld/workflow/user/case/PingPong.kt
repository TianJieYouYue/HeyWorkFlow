package online.heyworld.workflow.user.case

import online.heyworld.workflow.HeyWorkFlowContext
import online.heyworld.workflow.common.util.delayWorkFlow

object PingPong {
    @JvmStatic
    fun main(args: Array<String>) {
        val context = HeyWorkFlowContext()
        context.begin(delayWorkFlow("Ping", 0L, 3000L) {
            println("Ping")
        }).append("Ping", delayWorkFlow("Pong", 0L, 2000L) {
            println("Pong")
        }).linkDepend("Pong", "Ping")
            .launch()
    }
}