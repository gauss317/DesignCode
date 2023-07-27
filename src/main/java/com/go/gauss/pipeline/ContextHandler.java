package com.go.gauss.pipeline;

/**
 * 管道中的上下文处理器
 * 1、给 ContextHandler 加一个 setNext 方法，每个实现类必须指定其下一个处理器。缺点也很明显，
 * 如果在当前管道中间加入一个新的 ContextHandler，那么要势必要修改前一个ContextHandler 的 setNext 方法；
 * 另外，代码是写给人阅读的，这样做没法一眼就直观的知道整个管道的处理链路，还要进入到每个相关的 ContextHandler 中去查看才知道。
 *
 * 2、给 ContextHandler 加上 @Order 注解，根据 @Order 中给定的数字来确定每个 ContextHandler 的序列，
 * 一开始时每个数字间隔的可以大些（比如 10、20、30），后续加入新的 ContextHandler 时，可以指定数字为
 * （11、21、31）这种，那么可以避免上面方案中要修改代码的问题，但是仍然无法避免要进入每个相关的
 *  ContextHandler 中去查看才能知道管道处理链路的问题。
 *
 * 3、提前写好一份路由表，指定好 ”Context -> 管道“ 的映射（管道用 List<ContextHandler> 来表示），以及管道中处理器的顺序 。
 * Spring 来根据这份路由表，在启动时就构建好一个 Map，Map 的键为 Context 的类型值为 管道（即 List<ContextHandler>）。
 * 这样的话，如果想知道每个管道的处理链路，直接看这份路由表就行，一目了然。
 */
public interface ContextHandler<T extends PipelineContext> {

    /**
     * 处理输入的上下文数据
     *
     * @param context 处理时的上下文数据
     * @return 返回 true 则表示由下一个 ContextHandler 继续处理，返回 false 则表示处理结束
     */
    boolean handle(T context);
}