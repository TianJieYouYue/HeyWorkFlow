package online.heyworld.workflow

interface HeyWorkFlowStream {
    fun begin(heyWorkFlow: HeyWorkFlow):HeyWorkFlowStream

    fun append(dependsOn:String, heyWorkFlow: HeyWorkFlow):HeyWorkFlowStream

    fun linkDepend(dependsOn: String,self:String):HeyWorkFlowStream

    fun single(heyWorkFlow: HeyWorkFlow):HeyWorkFlowCall

    fun launch()
}