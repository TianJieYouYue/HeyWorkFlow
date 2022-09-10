package online.heyworld.workflow.lifecycle

import online.heyworld.workflow.HeyWorkFlow
import online.heyworld.workflow.HeyWorkFlowContext

/**
 *
 */
interface HeyWorkFlowLifecycle {

    fun onLaunchStart(context: HeyWorkFlowContext)

    fun onWorkFlowBefore(workFlow: HeyWorkFlow)

    fun onWorkFlowAfter(workFlow: HeyWorkFlow)
}


open class SimpleHeyWorkFlowLifecycle : HeyWorkFlowLifecycle{
    override fun onLaunchStart(context: HeyWorkFlowContext) {
    }

    override fun onWorkFlowBefore(workFlow: HeyWorkFlow) {
    }

    override fun onWorkFlowAfter(workFlow: HeyWorkFlow) {
    }

}
