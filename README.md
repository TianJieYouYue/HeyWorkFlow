# HeyWorkFlow

使用工作流，让代码流程化

# 简单的例子

## PingPong
Ping-Pong交互打印，永不停止
```kotlin
object PingPong {
    @JvmStatic
    fun main(args: Array<String>) {
        val context = HeyWorkFlowContext()
        context.begin(delayWorkFlow("Ping", 0L, 3000L) {
            println("Ping")
            }).append("Ping", delayWorkFlow("Pong", 0L, 2000L) {
                println("Pong")
            }).linkDepend("Pong", "Ping")
            .launch()
    }
}
```
