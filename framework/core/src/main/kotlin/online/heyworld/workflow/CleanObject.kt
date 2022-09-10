package online.heyworld.workflow

/**
 * 可清理的对象，会在合适的时机调用
 */
interface CleanObject {
    /**
     * 执行清理
     */
    fun clean()
}