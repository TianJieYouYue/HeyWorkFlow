package online.heyworld.workflow
abstract class HeyWorkFlow(val name:String):Runnable,CleanObject {

    internal lateinit var context: HeyWorkFlowContext

    protected var exception : Exception? = null

    var startDelay = 0L
    var afterDelay = 0L


    internal fun callRun(){
        if(startDelay>0){
            Thread.sleep(startDelay)
        }
        context.beforeRun(this)
        try {
            run()
        }catch (e:Exception){
            exception = e
        }
        if(afterDelay>0){
            Thread.sleep(afterDelay)
        }
        context.afterRun(this)
    }

    protected fun queryContext():HeyWorkFlowContext{
        return context
    }

    override fun clean() {

    }
}

interface HeyWorkFlowCall{
    fun callRun()
}

class HeyWorkFlowCallImpl(val heyWorkFlow: HeyWorkFlow):HeyWorkFlowCall{
    override fun callRun() {
        heyWorkFlow.callRun()
    }
}

class RootHeyWorkFlow : HeyWorkFlow(HeyWorkFlowManifest.ROOT){
    override fun run() {
    }

    override fun clean() {
    }
}