package online.heyworld.workflow

/**
 * 工作流的创建流程
 */
interface HeyWorkFlowStream {
    /**
     * 开始，参数为首个工作流
     */
    fun begin(heyWorkFlow: HeyWorkFlow):HeyWorkFlowStream

    /**
     * 添加新的工作流
     * @param dependsOn 前置依赖
     * @param heyWorkFlow 当前工作流
     */
    fun append(dependsOn:String, heyWorkFlow: HeyWorkFlow):HeyWorkFlowStream

    /**
     * 对已有的工作流建立额外依赖关系
     */
    fun linkDepend(dependsOn: String,self:String):HeyWorkFlowStream

    /**
     * 创建一个不受管理的工作流
     */
    fun single(heyWorkFlow: HeyWorkFlow):HeyWorkFlowCall

    /**
     * 启动整个工作流
     */
    fun launch()
}