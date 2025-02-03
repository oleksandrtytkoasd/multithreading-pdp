1. **Atomic**
    1. [AtomicInteger](https://github.com/oleksandrtytkoasd/multithreading-pdp/blob/main/src/main/java/edu/tytko/atomic/integer/Runner.java)

       Advatanges:
       * Performance: Generally faster than traditional synchronization methods, as it uses low-level atomic operations (like Compare-And-Swap) to perform updates without locking.

        Disadvatages:
       * Limited Scope: Only applicable to integer values; for other data types, different atomic classes (AtomicReference) are needed.
       * Contention Issues: In scenarios with high contention (many threads trying to update the same variable), performance may degrade, and alternative solutions like LongAdder might be more efficient.

    2. [AtomicReference](https://github.com/oleksandrtytkoasd/multithreading-pdp/blob/main/src/main/java/edu/tytko/atomic/reference/Runner.java)
  
       Advatages:
       * Versatility: Can hold references to any object, making it suitable for managing mutable objects in a thread-safe manner.
       * Atomic Operations: Supports atomic reference updates, allowing safe changes to object references in concurrent contexts using methods like compareAndSet().
       
       Disadvantages:
       * Overhead: While it provides atomicity, the overhead of managing references can be higher compared to primitive types, especially if frequent updates are required.
       * Complexity in Usage: Managing mutable objects through AtomicReference can introduce complexity in code logic, particularly if multiple fields need to be updated atomically.
       
2. **Concurrent structures**
    1. [Blocking queue](https://github.com/oleksandrtytkoasd/multithreading-pdp/blob/main/src/main/java/edu/tytko/concurrent_structures/blocking_queue/Runner.java)

        Advatages:
       * Thread Safety: Automatically handles thread safety for producer-consumer scenarios without explicit synchronization.
       * Blocking Operations: Supports blocking operations like put() and take(), which wait for space or elements, reducing CPU usage from busy-waiting.
       * Bounded Capacity: Can limit memory consumption by defining a capacity, which helps manage resource usage effectively.
       
       Disadvantages:
       * Overhead: The blocking mechanism may introduce latency compared to non-blocking structures.
       * Complexity in Management: Requires careful handling of thread interruptions and potential deadlocks.

    2. [Copy on write array](https://github.com/oleksandrtytkoasd/multithreading-pdp/blob/main/src/main/java/edu/tytko/concurrent_structures/copy_on_write_array/Runner.java)

       Advatages:
        * Safe for Iteration: Iterators are safe to use even when the list is modified concurrently, as changes create a new copy of the underlying array.
        * Simplicity: Easy to use in scenarios where reads vastly outnumber writes, making it ideal for read-heavy applications.
       
       Disadvantages:
        * Performance on Writes: Write operations (add, remove) can be expensive since they involve copying the entire array.
        * Memory Overhead: Increased memory usage due to creating copies of the array on each modification.
       
    3. [Countdown latch](https://github.com/oleksandrtytkoasd/multithreading-pdp/blob/main/src/main/java/edu/tytko/concurrent_structures/countdownlatch/Runner.java)

       Advatages:
       * Synchronization Aid: Allows one or more threads to wait until a set of operations in other threads completes, facilitating coordination.
       * Simple API: Easy to implement with methods like await() and countDown().
       
       Disadvantages:
       * One-Time Use: Once the count reaches zero, it cannot be reused; a new instance is needed for subsequent waits.
       * No Timeout: Does not support waiting with a timeout directly; requires additional handling for that feature.
   
    4. [Cyclic barrier](https://github.com/oleksandrtytkoasd/multithreading-pdp/blob/main/src/main/java/edu/tytko/concurrent_structures/cyclic_barrier/Runner.java)

       Advatages:
        * Reusability: Can be reused after all parties have reached the barrier, making it suitable for repeated tasks.
        * Flexible Coordination: Allows a fixed number of threads to wait for each other at a common barrier point.
       
       Disadvantages:
        * Complexity with Timeouts: Handling timeouts can complicate the implementation.
        * Potential for Deadlock: If one thread fails to reach the barrier, all threads will be blocked indefinitely.

    5. [Hashmap](https://github.com/oleksandrtytkoasd/multithreading-pdp/blob/main/src/main/java/edu/tytko/concurrent_structures/hashmap/Runner.java)

       Advatages:
        * High Concurrency: Allows multiple threads to read and write without locking the entire map, enhancing performance under concurrent access.
        * Segmented Locking: Uses fine-grained locking (or lock-free techniques) to improve throughput.
       
       Disadvantages:
        * Limited Atomic Operations: Some operations (like iterating) require additional synchronization if modifications occur during iteration.
        * Memory Overhead: More complex structure may lead to higher memory usage compared to simpler maps.

    6. [Semaphore](https://github.com/oleksandrtytkoasd/multithreading-pdp/blob/main/src/main/java/edu/tytko/concurrent_structures/semaphore/Runner.java)

       Advatages:
        * Resource Control: Manages access to a limited number of resources by maintaining a count of available permits.
        * Flexibility in Thread Management: Can be used for signaling between threads or controlling resource access.
       
       Disadvantages:
        * Complexity in Usage: Requires careful management of permits and can lead to deadlocks if not handled correctly.
        * Potential for Starvation: Threads may starve if not managed properly, especially in high contention scenarios.
        
2. **Executors**
    1. [Executor](https://github.com/oleksandrtytkoasd/multithreading-pdp/blob/main/src/main/java/edu/tytko/executors/Runner.java)

       Advatages:
        * Thread Pool Management: Simplifies thread management by using a pool of worker threads, reducing the overhead of thread creation and destruction.
        * Task Submission: Allows for easy task submission with methods like execute() and submit(), improving code readability and maintainability.
        * Lifecycle Management: Offers better lifecycle management for tasks, allowing for shutdown operations and handling interruptions more gracefully than manual thread management.
       
       Disadvantages:
        * Limited Flexibility: The framework is primarily designed for independent tasks, making it challenging to manage complex task dependencies without additional structures like CompletableFuture.
        * Overhead: The abstraction can introduce overhead, especially when managing a fixed number of threads in a pool, which may not be optimal for all applications.
        * Lack of Fine Control: Provides less control over individual thread behavior compared to creating threads manually, which might be necessary in some specialized scenarios.

    2. [Fork join pool](https://github.com/oleksandrtytkoasd/multithreading-pdp/blob/main/src/main/java/edu/tytko/fork_join_pool/Runner.java)

       Advatages:
        * Work Stealing: Implements a work-stealing algorithm that allows idle threads to "steal" tasks from busy threads, improving efficiency in parallel processing.
        * Task Granularity: Designed for recursive task decomposition, making it ideal for divide-and-conquer algorithms where tasks can be split into smaller subtasks.
        * Performance Optimization: Optimized for handling large numbers of small tasks, which can lead to better CPU utilization in multi-core environments.
       
       Disadvantages:
        * Complexity: More complex to use than the standard Executor framework, requiring an understanding of task splitting and joining mechanisms.
        * Overhead for Simple Tasks: For simple or few tasks, the overhead of managing the ForkJoinPool may outweigh its benefits compared to simpler executors.
        * Limited Use Cases: Primarily suited for parallelizable tasks; not as effective for I/O-bound operations or tasks that do not easily fit into a recursive model.
       
