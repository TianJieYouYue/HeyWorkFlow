package online.heyworld.workflow.event

import com.google.common.collect.LinkedListMultimap
import com.google.common.collect.ListMultimap
import online.heyworld.workflow.HeyWorkFlowContext

abstract class EventHandler(val oneTime:Boolean,val specFrom:String?){
    abstract fun handle(type:String,from:String,param:String):Boolean
}

class HeyWorkFlowEventCenter(private val context: HeyWorkFlowContext) {

    private val handlerMap : ListMultimap<String,EventHandler> = LinkedListMultimap.create()

    fun post(type:String,from:String,param:String){
        val iterator = handlerMap[type].iterator()
        while (iterator.hasNext()){
            val eventHandler = iterator.next()
            if(eventHandler.specFrom == null || eventHandler.specFrom == from){
                val handled = eventHandler.handle(type, from, param)
                if(handled && eventHandler.oneTime){
                    iterator.remove()
                }
            }
        }
    }

    fun register(type: String,eventHandler: EventHandler){
        handlerMap[type].add(eventHandler)
    }


}