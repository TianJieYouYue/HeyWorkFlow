package online.heyworld.workflow.common.util

import online.heyworld.workflow.HeyWorkFlow
import online.heyworld.workflow.HeyWorkFlowContext
import online.heyworld.workflow.HeyWorkFlowStream
import online.heyworld.workflow.common.http.HttpWorkFlow

fun workFlow(name:String,task:Runnable):HeyWorkFlow{
    return object : HeyWorkFlow(name) {
        override fun run() {
            task.run()
        }
    }
}

fun delayWorkFlow(name:String,startDelay:Long=0L,afterDelay:Long=0L,task:Runnable):HeyWorkFlow{
    return object : HeyWorkFlow(name) {
        override fun run() {
            task.run()
        }
    }.also {
        it.startDelay = startDelay
        it.afterDelay = afterDelay
    }
}

fun http(name: String,
         url: String,
         requestBody: String,
         responseHandler: (response: String) -> Unit):HttpWorkFlow{
    return HttpWorkFlow(name, url, requestBody, responseHandler)
}

fun hey(workFlow: HeyWorkFlow):HeyWorkFlowStream{
    return HeyWorkFlowContext().begin(workFlow)
}