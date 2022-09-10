package online.heyworld.workflow.user.case

import com.google.common.collect.ArrayListMultimap
import online.heyworld.workflow.HeyWorkFlow
import online.heyworld.workflow.HeyWorkFlowContext
import online.heyworld.workflow.common.util.delayWorkFlow
import online.heyworld.workflow.event.EventHandler
import online.heyworld.workflow.lifecycle.HeyWorkFlowLifecycle
import org.slf4j.LoggerFactory
import java.util.*

object PingPong {
    @JvmStatic
    fun main(args: Array<String>) {
        val context = HeyWorkFlowContext()
        context.begin(delayWorkFlow("Ping", 0L, 0L) {
            println("Ping ${Date()}")
            context.event.post("Ping","Ping","")
        }).append("Ping", delayWorkFlow("Pong", 0L, 0L) {
            println("Pong ${Date()}")
            context.event.post("Pong","Pong","")
        }).linkDepend("Pong", "Ping")
            .launch(object : HeyWorkFlowLifecycle{
                override fun onLaunchStart(context: HeyWorkFlowContext) {
                    //Ping-Pong计数器
                    context.event.register(listOf("Ping","Pong"),EventCounter())
                }

                override fun onWorkFlowBefore(workFlow: HeyWorkFlow) {}

                override fun onWorkFlowAfter(workFlow: HeyWorkFlow) {}

            })
    }
}

class EventCounter : EventHandler(false) {
    companion object{
        private val logger = LoggerFactory.getLogger(EventCounter::class.java)
    }

    private val counterMap = ArrayListMultimap.create<String,Long>()
    override fun handle(type: String, from: String, param: String): Boolean {
        counterMap[type].add(System.currentTimeMillis())
        counterMap.keySet().forEach {
            logger.info("$it:\t${counterMap[it].size}")
        }
        return true
    }
}