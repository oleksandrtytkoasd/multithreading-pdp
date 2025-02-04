1. **Atomic structutes**
    1. [AtomicInteger](https://github.com/oleksandrtytkoasd/multithreading-pdp/blob/main/src/main/java/edu/tytko/atomic/integer/Runner.java)

        Usage / features:
        * Thread Safety: AtomicInteger ensures that updates to its value are atomic, meaning that they are completed in a single operation without interruption.
        * Non-blocking: Unlike traditional synchronization methods (like synchronized blocks), AtomicInteger uses low-level atomic machine instructions, which can lead to better performance in highly concurrent applications.
   
        Advatanges:
        * Performance: Generally faster than traditional synchronization methods, as it uses low-level atomic operations (like Compare-And-Swap) to perform updates without locking.

        Disadvatages:
        * Limited Scope: Only applicable to integer values; for other data types, different atomic classes (AtomicReference) are needed.
        * Contention Issues: In scenarios with high contention (many threads trying to update the same variable), performance may degrade, and alternative solutions like LongAdder might be more efficient.

    2. [AtomicReference](https://github.com/oleksandrtytkoasd/multithreading-pdp/blob/main/src/main/java/edu/tytko/atomic/reference/Runner.java)

       Usage / features:
       * Atomic Operations: AtomicReference supports atomic operations on object references, which means that operations like reading and writing are performed as single, indivisible actions.
       * Compare-and-Swap (CAS): The class includes the compareAndSet() method, which allows for conditional updates based on the current value of the reference. This method is crucial for implementing non-blocking algorithms.
       
       Advatages:
       * Versatility: Can hold references to any object, making it suitable for managing mutable objects in a thread-safe manner.
       * Atomic Operations: Supports atomic reference updates, allowing safe changes to object references in concurrent contexts using methods like compareAndSet().
       
       Disadvantages:
       * Overhead: While it provides atomicity, the overhead of managing references can be higher compared to primitive types, especially if frequent updates are required.
       * Complexity in Usage: Managing mutable objects through AtomicReference can introduce complexity in code logic, particularly if multiple fields need to be updated atomically.
       
3. **Concurrent structures**
    1. [Blocking queue](https://github.com/oleksandrtytkoasd/multithreading-pdp/blob/main/src/main/java/edu/tytko/concurrent_structures/blocking_queue/Runner.java)

       Usage / features:
       * Provides additional methods that enable blocking operations, making it ideal for implementing producer-consumer scenarios.
       * Thread Safety: All operations on a BlockingQueue are atomic, meaning they are safe to use in concurrent environments without additional synchronization.
       * No Null Elements: BlockingQueue does not allow null elements; attempting to add a null value will throw a NullPointerException.

       Advatages:
       * Thread Safety: Automatically handles thread safety for producer-consumer scenarios without explicit synchronization.
       * Blocking Operations: Supports blocking operations like put() and take(), which wait for space or elements, reducing CPU usage from busy-waiting.
       * Bounded Capacity: Can limit memory consumption by defining a capacity, which helps manage resource usage effectively.
       
       Disadvantages:
       * Overhead: The blocking mechanism may introduce latency compared to non-blocking structures.
       * Complexity in Management: Requires careful handling of thread interruptions and potential deadlocks.

    2. [Copy on write array](https://github.com/oleksandrtytkoasd/multithreading-pdp/blob/main/src/main/java/edu/tytko/concurrent_structures/copy_on_write_array/Runner.java)

       Usage / features:
       * Thread Safety: All mutative operations (like add, set, etc.) create a new copy of the underlying array, ensuring that readers see a consistent snapshot of the list.
       * No ConcurrentModificationException: Since iterators operate on a snapshot of the array at the time they were created, they do not throw ConcurrentModificationException.
       * Performance: While read operations are fast and do not incur overhead, write operations can be costly due to the need to clone the array.
       
       Advatages:
       * Safe for Iteration: Iterators are safe to use even when the list is modified concurrently, as changes create a new copy of the underlying array.
       * Simplicity: Easy to use in scenarios where reads vastly outnumber writes, making it ideal for read-heavy applications.
       
       Disadvantages:
       * Performance on Writes: Write operations (add, remove) can be expensive since they involve copying the entire array.
       * Memory Overhead: Increased memory usage due to creating copies of the array on each modification.
       
    3. [Countdown latch](https://github.com/oleksandrtytkoasd/multithreading-pdp/blob/main/src/main/java/edu/tytko/concurrent_structures/countdownlatch/Runner.java)

       Usage / features:
       * Initialization: A CountDownLatch is initialized with a count, which represents the number of events that must occur before the latch is released.
       * Awaiting: Threads can call the await() method to block until the count reaches zero.
       * Counting Down: The countDown() method decreases the count, signaling that one event has occurred.
       
       Advatages:
       * Synchronization Aid: Allows one or more threads to wait until a set of operations in other threads completes, facilitating coordination.
       * Simple API: Easy to implement with methods like await() and countDown().
       
       Disadvantages:
       * One-Time Use: Once the count reaches zero, it cannot be reused; a new instance is needed for subsequent waits.
       * No Timeout: Does not support waiting with a timeout directly; requires additional handling for that feature.
   
    4. [Cyclic barrier](https://github.com/oleksandrtytkoasd/multithreading-pdp/blob/main/src/main/java/edu/tytko/concurrent_structures/cyclic_barrier/Runner.java)

       Usage / features:
       * Initialization: A CyclicBarrier is initialized with a count representing the number of threads that must call await() before they can proceed.
       * Barrier Action: An optional Runnable can be provided, which will execute once all parties reach the barrier, allowing for shared state updates before threads continue.
       * Reusability: After the barrier is tripped, it resets automatically, allowing threads to use the same barrier for subsequent phases of execution.
       
       Advatages:
       * Reusability: Can be reused after all parties have reached the barrier, making it suitable for repeated tasks.
       * Flexible Coordination: Allows a fixed number of threads to wait for each other at a common barrier point.
       
       Disadvantages:
       * Complexity with Timeouts: Handling timeouts can complicate the implementation.
       * Potential for Deadlock: If one thread fails to reach the barrier, all threads will be blocked indefinitely.

    5. [Hashmap](https://github.com/oleksandrtytkoasd/multithreading-pdp/blob/main/src/main/java/edu/tytko/concurrent_structures/hashmap/Runner.java)

       Usage / features:
       * Thread Safety: Supports concurrent read and write operations without locking the entire map.
       * No Null Values: Does not allow null keys or values, which helps avoid ambiguity in concurrent scenarios.
       * Segmented Locking: Internally, it divides the map into segments, allowing multiple threads to operate on different segments simultaneously.
       * Iterators: Provides fail-safe iterators that do not throw ConcurrentModificationException, enabling safe iteration even during modifications.
       
       Advatages:
       * High Concurrency: Allows multiple threads to read and write without locking the entire map, enhancing performance under concurrent access.
       * Segmented Locking: Uses fine-grained locking (or lock-free techniques) to improve throughput.
       
       Disadvantages:
       * Limited Atomic Operations: Some operations (like iterating) require additional synchronization if modifications occur during iteration.
       * Memory Overhead: More complex structure may lead to higher memory usage compared to simpler maps.

    6. [Semaphore](https://github.com/oleksandrtytkoasd/multithreading-pdp/blob/main/src/main/java/edu/tytko/concurrent_structures/semaphore/Runner.java)

        Usage / features:
        - Counting Semaphore: Semaphore maintains a count of permits, where each permit represents the ability for a thread to access a resource.
        - Blocking and Non-blocking Operations: Threads can block until a permit is available or attempt to acquire a permit without blocking.
        - Fairness: Semaphores can be configured to be fair, meaning that threads acquire permits in the order they requested them.
        
        Advatages:
        - Resource Control: Manages access to a limited number of resources by maintaining a count of available permits.
        - Flexibility in Thread Management: Can be used for signaling between threads or controlling resource access.

        Disadvantages:
        - Complexity in Usage: Requires careful management of permits and can lead to deadlocks if not handled correctly.
        - Potential for Starvation: Threads may starve if not managed properly, especially in high contention scenarios.
        
2. **Executors**
    1. [Executor](https://github.com/oleksandrtytkoasd/multithreading-pdp/blob/main/src/main/java/edu/tytko/executors/Runner.java)

       Usage / features:
       - Task Submission: Allows submission of Runnable or Callable tasks for execution.
       - Lifecycle Management: Provides methods to manage the lifecycle of the executor (e.g., shutdown).
       - Concurrency Control: Supports different types of thread pools to control concurrency levels.

       Advatages:
       - Thread Pool Management: Simplifies thread management by using a pool of worker threads, reducing the overhead of thread creation and destruction.
       - Task Submission: Allows for easy task submission with methods like execute() and submit(), improving code readability and maintainability.
       - Lifecycle Management: Offers better lifecycle management for tasks, allowing for shutdown operations and handling interruptions more gracefully than manual thread management.

       Disadvantages:
       - Limited Flexibility: The framework is primarily designed for independent tasks, making it challenging to manage complex task dependencies without additional structures like CompletableFuture.
       - Overhead: The abstraction can introduce overhead, especially when managing a fixed number of threads in a pool, which may not be optimal for all applications.
       - Lack of Fine Control: Provides less control over individual thread behavior compared to creating threads manually, which might be necessary in some specialized scenarios.


    2. [Fork join pool](https://github.com/oleksandrtytkoasd/multithreading-pdp/blob/main/src/main/java/edu/tytko/fork_join_pool/Runner.java)

       Usage / features: 
       * Work-Stealing Algorithm: When a worker thread finishes its tasks, it can "steal" tasks from other threads to ensure that all threads remain busy.
       * Recursive Task Handling: Supports two types of tasks:
           - RecursiveAction: For tasks that do not return a result.
           - RecursiveTask<V>: For tasks that return a result.
       * Dynamic Parallelism: The pool can adjust the number of threads based on the workload, allowing for efficient resource utilization.
        
       Advatages:
       * Work Stealing: Implements a work-stealing algorithm that allows idle threads to "steal" tasks from busy threads, improving efficiency in parallel processing.
       * Task Granularity: Designed for recursive task decomposition, making it ideal for divide-and-conquer algorithms where tasks can be split into smaller subtasks.
       * Performance Optimization: Optimized for handling large numbers of small tasks, which can lead to better CPU utilization in multi-core environments.
       
       Disadvantages:
       * Complexity: More complex to use than the standard Executor framework, requiring an understanding of task splitting and joining mechanisms.
       * Overhead for Simple Tasks: For simple or few tasks, the overhead of managing the ForkJoinPool may outweigh its benefits compared to simpler executors.
       * Limited Use Cases: Primarily suited for parallelizable tasks; not as effective for I/O-bound operations or tasks that do not easily fit into a recursive model.
       
