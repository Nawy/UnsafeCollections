package org.megalabs.collection.advanced;

import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by ermolaev on 4/7/17.
 */
public class AdvArrayListBenchmark {

    @Test
    public void jmhAdvArrayListBenchmark() throws Exception {

        Options opt = new OptionsBuilder()
                // Specify which benchmarks to run.
                // You can be more specific if you'd like to run only one benchmark per test.
                .include(this.getClass().getName() + ".*")
                // Set the following options as needed
                .mode(Mode.AverageTime)
                .timeUnit(TimeUnit.MICROSECONDS)
                .warmupTime(TimeValue.seconds(1))
                .warmupIterations(10)
                .measurementTime(TimeValue.seconds(1))
                .measurementIterations(10)
                .threads(2)
                .forks(1)
                .shouldFailOnError(true)
                .shouldDoGC(true)
                //.jvmArgs("-XX:+UnlockDiagnosticVMOptions", "-XX:+PrintInlining")
                //.addProfiler(WinPerfAsmProfiler.class)
                .build();

        new Runner(opt).run();
    }

    @State(Scope.Thread)
    public static class ArrayListInitialState {
        List<Integer> list;

        @Setup(Level.Trial)
        public void initialize() {
            list = new ArrayList<>();
        }
    }

    @State(Scope.Thread)
    public static class AdvArrayListInitialState {
        AdvList<Integer> list;

        @Setup(Level.Trial)
        public void initialize() {
            list = new AdvArrayList<>();
        }
    }

    @State(Scope.Thread)
    public static class ArrayList10000InitialState {
        List<Integer> list;

        @Setup(Level.Trial)
        public void initialize() {
            list = new ArrayList<>();
            for(int i = 0; i < 10_000; i++)
                list.add(i);
        }
    }

    @State(Scope.Thread)
    public static class AdvArrayList10000InitialState {
        AdvList<Integer> list;

        @Setup(Level.Trial)
        public void initialize() {
            list = new AdvArrayList<>();
            for(int i = 0; i < 10_000; i++)
                list.add(i);
        }
    }

//    @Benchmark
//    public void benchmarkArrayListAdd(ArrayListInitialState state) {
//        List<Integer> list = state.list;
//
//        for(int i = 0; i < 1_000_000; i++) {
//            list.add(i);
//        }
//
//        list.clear();
//    }
//
//    @Benchmark
//    public void benchmarkAdvArrayListAdd(AdvArrayListInitialState state) {
//        AdvList<Integer> list = state.list;
//
//        for(int i = 0; i < 1_000_000; i++) {
//            list.add(i);
//        }
//
//        list.clear();
//    }

    @Benchmark
    public void benchmarkArrayListContains(ArrayListInitialState state) {
        List<Integer> list = state.list;

        list.contains(5000);

        list.clear();
    }

    @Benchmark
    public void benchmarkAdvArrayListContains(AdvArrayListInitialState state) {
        AdvList<Integer> list = state.list;

        list.contains(5000);

        list.clear();
    }
}
