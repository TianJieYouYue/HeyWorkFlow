package online.heyworld.workflow.lifecycle

import online.heyworld.workflow.HeyWorkFlow
import online.heyworld.workflow.HeyWorkFlowContext

class HeyWorkFlowLifecycleHolder : HeyWorkFlowLifecycle {

    companion object {
        val NONE: HeyWorkFlowLifecycle = object : HeyWorkFlowLifecycle {
            override fun onLaunchStart(context: HeyWorkFlowContext) {}
            override fun onWorkFlowBefore(workFlow: HeyWorkFlow) {}
            override fun onWorkFlowAfter(workFlow: HeyWorkFlow) {}
        }
    }


    private val lifecycleListenerList = mutableListOf<HeyWorkFlowLifecycle>()

    @Synchronized
    override fun onLaunchStart(context: HeyWorkFlowContext) {
        lifecycleListenerList.forEach { it.onLaunchStart(context) }
    }

    @Synchronized
    override fun onWorkFlowBefore(workFlow: HeyWorkFlow) {
        lifecycleListenerList.forEach { it.onWorkFlowBefore(workFlow) }
    }

    @Synchronized
    override fun onWorkFlowAfter(workFlow: HeyWorkFlow) {
        lifecycleListenerList.forEach { it.onWorkFlowAfter(workFlow) }
    }

    @Synchronized
    fun register(lifecycle: HeyWorkFlowLifecycle) {
        if (lifecycle != NONE){
            lifecycleListenerList.add(lifecycle)
        }
    }

    @Synchronized
    fun unregister(lifecycle: HeyWorkFlowLifecycle) {
        lifecycleListenerList.remove(lifecycle)
    }
}