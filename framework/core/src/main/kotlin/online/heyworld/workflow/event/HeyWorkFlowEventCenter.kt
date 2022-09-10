package online.heyworld.workflow.event

import com.google.common.collect.LinkedListMultimap
import com.google.common.collect.ListMultimap
import online.heyworld.workflow.HeyWorkFlowContext
import java.util.concurrent.Executor

abstract class EventHandler(val oneTime:Boolean,val specFrom:String? = null){

    abstract fun handle(type:String,from:String,param:String):Boolean
}

class HeyWorkFlowEventCenter(private val context: HeyWorkFlowContext,private val executor: Executor) {

    private val handlerMap : ListMultimap<String,EventHandler> = LinkedListMultimap.create()

    fun post(type:String,from:String,param:String){
        executor.execute {
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
    }

    fun register(type: String,eventHandler: EventHandler){
        executor.execute {
            handlerMap[type].add(eventHandler)
        }
    }

    fun register(types: List<String>,eventHandler: EventHandler){
        executor.execute {
            types.forEach{type->
                handlerMap[type].add(eventHandler)
            }
        }
    }
}