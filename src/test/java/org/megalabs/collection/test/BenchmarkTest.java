package org.megalabs.collection.test;

import org.junit.Test;
import org.megalabs.collections.IntList;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by User on 05.04.2017.
 */
public class BenchmarkTest {

    @Test
    public void jmhBenchmarkTest() throws Exception {

        Options opt = new OptionsBuilder()
                // Specify which benchmarks to run.
                // You can be more specific if you'd like to run only one benchmark per test.
                .include(this.getClass().getName() + ".*")
                // Set the following options as needed
                .mode(Mode.AverageTime)
                .timeUnit(TimeUnit.MILLISECONDS)
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

    @Benchmark
    public void benchmarkArrayList(ArrayListInitialState state, Blackhole bh) {

        List<Integer> list = state.list;

        for (int i = 0; i < 1_000_000; i++) bh.consume(list.get(i));
    }

    @Benchmark
    public void benchmarkUnsafeArayList(UnsafeArrayListInitialState state, Blackhole bh) {

        IntList list = state.list;

        for (int i = 0; i < 1_000_000; i++) bh.consume(list.get(i));
    }

    // The JMH samples are the best documentation for how to use it
    // http://hg.openjdk.java.net/code-tools/jmh/file/tip/jmh-samples/src/main/java/org/openjdk/jmh/samples/
    @State(Scope.Thread)
    public static class ArrayListInitialState {
        List<Integer> list;

        @Setup(Level.Trial)
        public void initialize() {
            Random rand = new Random();
            list = new ArrayList<>();
            for (int i = 0; i < 1_000_000; i++) {
                list.add(rand.nextInt());
            }
        }
    }

    @State(Scope.Thread)
    public static class UnsafeArrayListInitialState {
        IntList list;

        @Setup(Level.Trial)
        public void initialize() {
            Random rand = new Random();
            list = new IntList();
            for (int i = 0; i < 1_000_000; i++) {
                list.add(rand.nextInt());
            }
        }
    }

    @Benchmark
    public void benchmarkArrayListInsert(ArrayListInitialStateInsert state) {

        List<Integer> list = state.list;

        for (int i = 0; i < 1_000; i++) list.add(0, i);
    }

    @Benchmark
    public void benchmarkUnsafeArayListInsert(UnsafeArrayListInitialStateInsert state) {

        IntList list = state.list;

        for (int i = 0; i < 1_000; i++) list.insert(0, i);
    }

    // The JMH samples are the best documentation for how to use it
    // http://hg.openjdk.java.net/code-tools/jmh/file/tip/jmh-samples/src/main/java/org/openjdk/jmh/samples/
    @State(Scope.Thread)
    public static class ArrayListInitialStateInsert {
        List<Integer> list;

        @Setup(Level.Trial)
        public void initialize() {
            list = new ArrayList<>();
        }
    }

    @State(Scope.Thread)
    public static class UnsafeArrayListInitialStateInsert {
        IntList list;

        @Setup(Level.Trial)
        public void initialize() {
            list = new IntList();
        }
    }
}
