package online.heyworld.workflow.user.case

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.LoggerContext
import online.heyworld.workflow.HeyWorkFlow
import online.heyworld.workflow.common.util.delayWorkFlow
import online.heyworld.workflow.common.util.hey
import online.heyworld.workflow.common.util.workFlow
import online.heyworld.workflow.lifecycle.SimpleHeyWorkFlowLifecycle
import org.slf4j.LoggerFactory
import java.util.*


data class People(
    var name: String = "",
    var age: Long = 0,
    var gender : String = "",
    var knowledge: Long = 0,
    var money: Long = 0,
    var beLike: Long = 0,
    var beDislike: Long = 0,
    var children :MutableList<People> = mutableListOf()
)

fun showPeople(people: People,workFlow: HeyWorkFlow,before:Boolean) {
    val messageBuilder = StringBuilder()
    if(before){
        messageBuilder.append(workFlow.name).append("咯")
    }else{
        messageBuilder.append(workFlow.name).append("好了")
    }
    messageBuilder.append("\n")
    messageBuilder.append(people)
    println(messageBuilder)
}

object LoserToFlyer {
    @JvmStatic
    fun main(args: Array<String>) {
        (LoggerFactory.getILoggerFactory() as? LoggerContext)?.let {
            it.getLogger("root").level = Level.OFF
        }
        val people = People()
        val nvWa = People("女娲",1000000,"女",Long.MAX_VALUE,Long.MAX_VALUE,Long.MAX_VALUE,0)
        hey(workFlow("出生") {
            nvWa.children.add(people)
            people.name = "无名小宝宝"
            people.gender = if( Random().nextBoolean()) "男" else "女"
        }).append("出生", delayWorkFlow("起名", 0L, 3000) {
            people.name = "天小月"
            people.beLike = 2
            people.age = 3
            people.knowledge+=2
        }).append("起名", delayWorkFlow("上幼儿园", 0L, 0L) {
            people.age+=3
            people.knowledge+=10
            people.beLike += Random().nextInt(20)
            Thread.sleep(3*60*1000)
        }).launch(object : SimpleHeyWorkFlowLifecycle() {
            override fun onWorkFlowBefore(workFlow: HeyWorkFlow) {
                showPeople(people,workFlow, true)
            }
            override fun onWorkFlowAfter(workFlow: HeyWorkFlow) {
                showPeople(people,workFlow, false)
            }
        })
    }
}