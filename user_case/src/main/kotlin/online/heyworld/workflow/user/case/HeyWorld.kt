package online.heyworld.workflow.user.case

import online.heyworld.workflow.HeyWorkFlow
import online.heyworld.workflow.HeyWorkFlowContext

object HeyWorld {
    @JvmStatic
    fun main(args: Array<String>) {
        val workFlowContext = HeyWorkFlowContext(3)
        workFlowContext.begin(object : HeyWorkFlow("CreateWord") {
            override fun run() {
                queryContext().env.update("word","Hey,World!!!")
            }
        }).append("CreateWord",object :HeyWorkFlow("ShowWord"){
            override fun run() {
                println(queryContext().env.query("word"))
            }
        }).launch()
    }
}