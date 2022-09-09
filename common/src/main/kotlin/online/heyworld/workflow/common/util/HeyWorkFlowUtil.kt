package online.heyworld.workflow.common.util

import online.heyworld.workflow.HeyWorkFlow

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