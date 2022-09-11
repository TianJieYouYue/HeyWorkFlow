package online.heyworld.workflow.common.file

import online.heyworld.workflow.HeyWorkFlow
import online.heyworld.workflow.common.util.NormalStringMatchUtil
import org.slf4j.LoggerFactory
import java.io.File
import java.util.Stack

enum class MatchRuleType{
    NORMAL,
    REGEX
}

data class MatchRule(var type:MatchRuleType,val rule:String){
    fun match(value:String):Boolean{
        return when(type){
            MatchRuleType.NORMAL->{
                NormalStringMatchUtil.isMatch(value,rule)
            }
            MatchRuleType.REGEX->{
                value.matches(Regex(rule))
            }
        }
    }

    companion object{
        fun of(rule:String):MatchRule{
            return MatchRule(MatchRuleType.NORMAL,rule)
        }
    }
}

data class CopyWorkFlowParam(
    val fromDir: String,
    val toDir: String,
    val includeRules: MutableList<MatchRule>,
    val excludeRules: MutableList<MatchRule> = mutableListOf(),
    val includeChildren: Boolean = true,
    val keepDir: Boolean = false
)

class CopyWorkFlow(
    name: String,
    val param: CopyWorkFlowParam
) : HeyWorkFlow(name) {

    companion object{

        private val logger = LoggerFactory.getLogger(CopyWorkFlow::class.java)

        fun copyDir(base:File,from:File,to:File,keepDir: Boolean,includeChildren:Boolean,test:(file:File)->Boolean){
            from.listFiles()?.forEach {
                if(it.isFile){
                    if(test(it)){
                        if(keepDir){
                            val f = ensureDir(base,it,to)
                            if(!f.exists()){
                                it.copyTo(f)
                            }
                        }else{
                            val f = File(to,it.name)
                            if(!f.exists()){
                                it.copyTo(f)
                            }
                        }
                    }
                }else{
                    if(includeChildren){
                        copyDir(base,it,to,keepDir, includeChildren, test)
                    }
                }
            }
        }

        fun ensureDir(base:File,f:File,to:File):File{
            val dirStack = Stack<String>()
            var temp = f
            while (temp.parentFile!=base){
                dirStack.push(temp.parentFile.name)
                temp = temp.parentFile

            }
            var targetDir = to
            while (!dirStack.empty()){
                targetDir = File(targetDir,dirStack.pop())
            }
            if(!targetDir.exists()){
                targetDir.mkdirs()
            }
            return File(targetDir,f.name)
        }
    }

    override fun run() {
        val base = File(param.fromDir)
        val to = File(param.toDir)
        copyDir(base,base,to,param.keepDir,param.includeChildren){ file ->
            val name = file.name
            var match = false
            param.includeRules.forEach {rule->
                if (rule.match(name)){
                    match = true
                }
            }
            param.excludeRules.forEach {rule->
                if (rule.match(name)){
                    match = false
                }
            }
            logger.info("match ${file.name} $match")
            if(notifyProcess){
                queryContext().event.post("CopyWorkFlow-Notify",name,"${file.name} ${
                    if(match) "匹配成功"
                    else "不匹配"
                }")
            }
            return@copyDir match
        }
    }
}