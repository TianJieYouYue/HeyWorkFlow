package online.heyworld.workflow.env

import online.heyworld.workflow.HeyWorkFlowContext
import org.slf4j.LoggerFactory

class HeyWorkFlowEnv(private val context: HeyWorkFlowContext) {

    companion object{
        private val logger = LoggerFactory.getLogger(HeyWorkFlowEnv::class.java)
    }

    val env = mutableMapOf<String,String>()

    fun update(key:String,value:String){
        logger.info("update [$key]=[$value]")
        env[key] = value
    }

    fun query(key:String):String{
        val value = env[key]?:""
        logger.info("query [$key]=[$value]")
        return value
    }

    fun queryInt(key:String):Int{
        val value = env[key]
        logger.info("query [$key]=[$value]")
        return value?.toIntOrNull()?:0
    }

    fun queryLong(key:String):Long{
        val value = env[key]
        logger.info("query [$key]=[$value]")
        return value?.toLongOrNull()?:0L
    }
}