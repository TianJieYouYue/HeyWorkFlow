package online.heyworld.workflow.user.case

import online.heyworld.workflow.common.util.hey
import online.heyworld.workflow.common.util.http

object Http {
    @JvmStatic
    fun main(args: Array<String>) {
        hey(
            http("HeyWorld-Hello", "https://heyworld.online/api/hello", "") {
                println(it)
            }.also {
                it.startDelay = 1
            }
        ).linkDepend("HeyWorld-Hello","HeyWorld-Hello")
            .launch()
    }
}