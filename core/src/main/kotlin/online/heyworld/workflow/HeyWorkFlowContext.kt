package online.heyworld.workflow

import online.heyworld.workflow.env.HeyWorkFlowEnv
import online.heyworld.workflow.event.HeyWorkFlowEventCenter
import online.heyworld.workflow.resources.HeyWorkFlowResources
import org.slf4j.LoggerFactory
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

/**
 * 工作流上下文
 */
class HeyWorkFlowContext(private val executorThreadSize:Int=100):HeyWorkFlowStream {

    companion object{
        private val logger = LoggerFactory.getLogger(HeyWorkFlowContext::class.java)
    }

    /**
     * 执行器
     */
    val executor : ScheduledExecutorService = Executors.newScheduledThreadPool(executorThreadSize)

    val resources = HeyWorkFlowResources(this)

    val event = HeyWorkFlowEventCenter(this)

    val env = HeyWorkFlowEnv(this)

    private val heyWorkFlowMap = mutableMapOf<String,HeyWorkFlow>()

    private val dependsMap = mutableMapOf<String,String>()

    internal fun beforeRun(heyWorkFlow: HeyWorkFlow) {
        logger.info("beforeRun workFlow:${heyWorkFlow.name}")
    }

    internal fun afterRun(heyWorkFlow: HeyWorkFlow) {
        logger.info("afterRun workFlow:${heyWorkFlow.name}")
        dependsMap[heyWorkFlow.name].let {
            if(it == null){
                logger.info("afterRun work flow end on :${heyWorkFlow.name}")
            }else{
                runWorkFlow(it)
            }
        }
    }

    private fun runWorkFlow(name:String){
        heyWorkFlowMap[name].let {
            if(it == null){
                logger.info("runWorkFlow work flow not found name:$name")
            }else{
                it.callRun()
            }
        }
    }

    override fun begin(heyWorkFlow: HeyWorkFlow): HeyWorkFlowStream {
        buildRoot()
        heyWorkFlow.context = this
        heyWorkFlowMap[heyWorkFlow.name] = heyWorkFlow
        dependsMap[HeyWorkFlowManifest.ROOT] = heyWorkFlow.name
        return this
    }

    override fun append(dependsOn: String, heyWorkFlow: HeyWorkFlow): HeyWorkFlowStream {
        heyWorkFlow.context = this
        heyWorkFlowMap[heyWorkFlow.name] = heyWorkFlow
        dependsMap[dependsOn] = heyWorkFlow.name
        return this
    }

    override fun linkDepend(dependsOn: String, self: String): HeyWorkFlowStream {
        dependsMap[dependsOn] = self
        return this
    }

    override fun single(heyWorkFlow: HeyWorkFlow): HeyWorkFlowCall {
        heyWorkFlow.context = this
        return HeyWorkFlowCallImpl(heyWorkFlow)
    }

    override fun launch() {
        runWorkFlow(HeyWorkFlowManifest.ROOT)
    }

    private fun buildRoot(){
        heyWorkFlowMap[HeyWorkFlowManifest.ROOT] = RootHeyWorkFlow().also {
            it.context = this
        }
    }
}