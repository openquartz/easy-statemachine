package com.openquartz.easystatemachine;

import static com.openquartz.easystatemachine.StateMachinePlantUMLTest.PriceAdjustmentTaskEventEnum.Agree_Over_P0_Sell;
import static com.openquartz.easystatemachine.StateMachinePlantUMLTest.PriceAdjustmentTaskEventEnum.Apply_Over_P0_Sell;
import static com.openquartz.easystatemachine.StateMachinePlantUMLTest.PriceAdjustmentTaskEventEnum.Create;
import static com.openquartz.easystatemachine.StateMachinePlantUMLTest.PriceAdjustmentTaskEventEnum.Normal_Update;
import static com.openquartz.easystatemachine.StateMachinePlantUMLTest.PriceAdjustmentTaskEventEnum.P0_Changed;
import static com.openquartz.easystatemachine.StateMachinePlantUMLTest.PriceAdjustmentTaskEventEnum.Page_Price_changed;
import static com.openquartz.easystatemachine.StateMachinePlantUMLTest.PriceAdjustmentTaskEventEnum.Reject_Over_P0_Sell;
import static com.openquartz.easystatemachine.StateMachinePlantUMLTest.PriceAdjustmentTaskEventEnum.Supplier_Agree;
import static com.openquartz.easystatemachine.StateMachinePlantUMLTest.PriceAdjustmentTaskEventEnum.Supplier_Reject;
import static com.openquartz.easystatemachine.StateMachinePlantUMLTest.PriceAdjustmentTaskEventEnum.Supplier_Timeout;
import static com.openquartz.easystatemachine.StateMachinePlantUMLTest.PriceAdjustmentTaskStatusEnum.Closed;
import static com.openquartz.easystatemachine.StateMachinePlantUMLTest.PriceAdjustmentTaskStatusEnum.Price_Manager_Processing;
import static com.openquartz.easystatemachine.StateMachinePlantUMLTest.PriceAdjustmentTaskStatusEnum.Supplier_Manager_Processing;
import static com.openquartz.easystatemachine.StateMachinePlantUMLTest.PriceAdjustmentTaskStatusEnum.Supplier_Processing;

import com.openquartz.easystatemachine.StateMachineTest.Context;
import com.openquartz.easystatemachine.builder.StateMachineBuilder;
import com.openquartz.easystatemachine.builder.StateMachineBuilderFactory;
import com.openquartz.easystatemachine.impl.Debugger;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.Test;

/**
 * StateMachinePlantUMLTest
 */
public class StateMachinePlantUMLTest {

    enum PriceAdjustmentTaskStatusEnum {
        /**
         * 待商家处理
         */
        Supplier_Processing,
        /**
         * 待控商小二处理
         */
        Supplier_Manager_Processing,
        /**
         * 待价格管控小二处理
         */
        Price_Manager_Processing,
        /**
         * 退出
         */
        Closed
    }

    enum PriceAdjustmentTaskEventEnum {

        // 系统事件
        Create,
        Normal_Update,
        /**
         * 合理价变更
         */
        P0_Changed,
        /**
         * 页面价变合理
         */
        Page_Price_changed,

        // 商家事件
        Supplier_Reject,
        Supplier_Agree,
        Supplier_Timeout,

        // 控商小二事件
        Apply_Over_P0_Sell,

        // 价格小二事件
        Agree_Over_P0_Sell,
        Reject_Over_P0_Sell;

        public boolean isSupplierTimeout() {
            return this == Supplier_Timeout;
        }

        public boolean isSystemEvent() {
            return this == Create ||
                this == Normal_Update ||
                this == P0_Changed ||
                this == Page_Price_changed;
        }
    }

    @Before
    public void init() {
        Debugger.enableDebug();
    }

    @Test
    public void testPlantUML() {
        StateMachineBuilder<PriceAdjustmentTaskStatusEnum, PriceAdjustmentTaskEventEnum, Context> builder =
            StateMachineBuilderFactory.<PriceAdjustmentTaskStatusEnum, PriceAdjustmentTaskEventEnum, Context>declare(PriceAdjustmentTaskStatusEnum.class)
                .end(Closed)
                .create();

        builder.initTransition()
            .init(Supplier_Processing)
            .on(Create)
            .when(checkCondition())
            .perform(doAction());

        // 商家调价
        Stream.of(Supplier_Processing, Supplier_Manager_Processing, Price_Manager_Processing)
            .forEach(status ->
                builder.externalTransition()
                    .from(status)
                    .to(Closed)
                    .on(Supplier_Agree)
                    .when(checkCondition())
                    .perform(doAction())
            );

        // 商家 -上升至-> 控商小二
        builder.externalTransition()
            .from(Supplier_Processing)
            .to(Supplier_Manager_Processing)
            .on(Supplier_Reject)
            .when(checkCondition())
            .perform(doAction());

        builder.externalTransition()
            .from(Supplier_Processing)
            .to(Supplier_Manager_Processing)
            .on(Supplier_Timeout)
            .when(checkCondition())
            .perform(doAction());

        // 申请申请高于P0售卖
        builder.externalTransition()
            .from(Supplier_Manager_Processing)
            .to(Price_Manager_Processing)
            .on(Apply_Over_P0_Sell)
            .when(checkCondition())
            .perform(doAction());

        // 同意高于P0价售卖
        builder.externalTransition()
            .from(Price_Manager_Processing)
            .to(Closed)
            .on(Agree_Over_P0_Sell)
            .when(checkCondition())
            .perform(doAction());

        // 拒绝高于P0价售卖
        builder.externalTransition()
            .from(Price_Manager_Processing)
            .to(Supplier_Manager_Processing)
            .on(Reject_Over_P0_Sell)
            .when(checkCondition())
            .perform(doAction());

        // 普通字段更新事件
        Stream.of(Supplier_Processing, Supplier_Manager_Processing, Price_Manager_Processing)
            .forEach(status -> builder
                .internalTransition()
                .within(status)
                .on(Normal_Update)
                .when(checkCondition())
                .perform(doAction())
            );

        // P0价变更事件、页面价高于合理价事件
        Stream.of(P0_Changed, Page_Price_changed)
            .forEach(event -> builder.externalTransitions()
                .fromAmong(Supplier_Processing, Supplier_Manager_Processing, Price_Manager_Processing)
                .to(Closed)
                .on(event)
                .when(checkCondition())
                .perform(doAction()));

        StateMachine<PriceAdjustmentTaskStatusEnum, PriceAdjustmentTaskEventEnum, Context> stateMachine =
            builder.build("AdjustPriceTask");
        String plantUmlStr = stateMachine.accept(Visitor.PLANT_UML);
        System.out.println(plantUmlStr);

        PriceAdjustmentTaskStatusEnum targetStatus = stateMachine.fireEvent(Create, new Context());
        System.out.println(targetStatus);
    }

    private Guard<Context> checkCondition() {
        return (ctx) -> true;
    }

    private Action<PriceAdjustmentTaskStatusEnum, PriceAdjustmentTaskEventEnum, StateMachineTest.Context> doAction() {
        return (from, to, event, ctx) -> System.out.println(ctx.operator + " is operating " + ctx.entityId + " from:" + from + " to:" + to + " on:" + event);
    }
}
