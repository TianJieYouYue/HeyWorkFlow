package online.heyworld.workflow.resources

import online.heyworld.workflow.CleanObject
import online.heyworld.workflow.HeyWorkFlowContext
import java.net.Socket

class HeyWorkFlowResources(private val  context: HeyWorkFlowContext) : CleanObject {

    val socketMap = mutableMapOf<String,Socket>()

    fun registerSocket(id:String,socket: Socket){
        socketMap[id]  = socket
    }

    fun querySocket(id:String):Socket?{
        return socketMap[id]
    }

    override fun clean() {
        val socket = ArrayList(socketMap.values)
        socketMap.clear()
        socket.forEach {
            it.close()
        }
    }
}