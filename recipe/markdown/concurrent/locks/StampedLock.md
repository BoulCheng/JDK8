


- Method tryOptimisticRead() returns a non-zero stamp only if the lock is not currently held in write mode. Method validate(long) returns true if the lock has not been acquired in write mode since obtaining a given stamp




```
class Point {
   private double x, y;
   private final StampedLock sl = new StampedLock();

   double distanceFromOrigin() { // A read-only method
     long stamp = sl.tryOptimisticRead();
     double currentX = x, currentY = y;
     if (!sl.validate(stamp)) {
        stamp = sl.readLock();
        try {
          currentX = x;
          currentY = y;
        } finally {
           sl.unlockRead(stamp);
        }
     }
     return Math.sqrt(currentX * currentX + currentY * currentY);
   }
}
```