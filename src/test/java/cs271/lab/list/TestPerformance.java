package cs271.lab.list;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestPerformance {

  // TODO run test and record running times for SIZE = 10, 100, 1000, 10000, ...
  // (choose in conjunction with REPS below up to an upper limit where the clock
  // running time is in the tens of seconds)

  // TODO Question: What conclusions can you draw about the performance of LinkedList vs. ArrayList when
  // comparing their running times for AddRemove vs. Access? Record those running times in README.txt!

  // TODO (optional) refactor to DRY
  // which of the two lists performs better as the size increases?
  private int SIZE;

  // TODO choose this value in such a way that you can observe an actual effect
  // for increasing problem sizes
  private int REPS;

  private List<Integer> arrayList;

  private List<Integer> linkedList;

  public TestPerformance() {}

  @Before
  public void setUp() throws Exception {
    arrayList = new ArrayList<Integer>(SIZE);
    linkedList = new LinkedList<Integer>();
    for (var i = 0; i < SIZE; i++) {
      arrayList.add(i);
      linkedList.add(i);
    }
  }

  @After
  public void tearDown() throws Exception {
    arrayList = null;
    linkedList = null;
  }

  private void durTime(String testName, Runnable test) {
    long start = System.nanoTime();
    test.run();
    long duration = System.nanoTime() - start;
    System.out.println(testName + " took " + duration / 1_000_000.0 + " ms");
  }

  @Test
  public void testLinkedListAddRemove() {
    durTime("LinkedList Add/Remove", () -> {
      for (var r = 0; r < REPS; r++) {
        linkedList.add(0, 77);
        linkedList.remove(0);
      }
    });
  }

  @Test
  public void testArrayListAddRemove() {
    durTime("ArrayList Add/Remove", () -> {
      for (var r = 0; r < REPS; r++) {
        arrayList.add(0, 77);
        arrayList.remove(0);
      }
    });
  }

  @Test
  public void testLinkedListAccess() {
    durTime("LinkedList Access", () -> {
      var sum = 0L;
      for (var r = 0; r < REPS; r++) {
        sum += linkedList.get(r % SIZE);
      }
    });
  }

  @Test
  public void testArrayListAccess() {
    durTime("ArrayList Access", () -> {
      var sum = 0L;
      for (var r = 0; r < REPS; r++) {
        sum += arrayList.get(r % SIZE);
      }
    });
  }

  private void runTest(int size, int reps) throws Exception {
    this.SIZE = size;
    this.REPS = reps;
    setUp();
    testArrayListAddRemove();
    testLinkedListAddRemove();
    testArrayListAccess();
    testLinkedListAccess();
    tearDown();
  }

  public static void main(String[] args) throws Exception {
    TestPerformance test = new TestPerformance();
    // Test different configurations
    test.runTest(10, 1000000);
    test.runTest(100, 500000);
    test.runTest(1000, 100000);
    test.runTest(10000, 10000);
    test.runTest(100000, 1000);
  }
}
