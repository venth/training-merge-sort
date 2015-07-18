package org.venth.training;

import com.google.common.collect.Iterators;
import com.google.common.collect.PeekingIterator;

import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author Venth on 13/07/2015
 */
public class MergeSorter {
    public static Integer[] sorted(Integer[] array) {
        return Stream.of(new Holder(array)).flatMap(MergeSorter::doSorted).toArray(Integer[]::new);
    }

    private static Stream<? extends Integer> doSorted(Holder h) {
        Integer[] a = h.array;
        if (h.isEmpty()) {
            return Stream.empty();
        }

        Integer first = h.first();
        if (h.containsOneElement()) {
            return Stream.of(first);
        }

        Integer last = h.last();
        if (h.containsTwoElements()) {
            if (last < first) {
                return Stream.of(last, first);
            } else {
                return Stream.of(first, last);
            }
        }

        PeekingIterator<? extends Integer> left = Iterators.peekingIterator(doSorted(new Holder(a, h.start, h.medium)).iterator());
        PeekingIterator<? extends Integer> right = Iterators.peekingIterator(doSorted(new Holder(a, h.medium + 1, h.end)).iterator());

        Iterator<Integer> merged = new Iterator<Integer>() {

            @Override
            public boolean hasNext() {
                return left.hasNext() || right.hasNext();
            }

            @Override
            public Integer next() {
                Optional<Integer> lv = Optional.empty();
                Optional<Integer> rv = Optional.empty();
                if (left.hasNext()) {
                    lv = Optional.of(left.peek());
                }
                if (right.hasNext()) {
                    rv = Optional.of(right.peek());
                }

                if (lv.isPresent() && rv.isPresent()) {
                    if (lv.get() > rv.get()) {
                        return right.next();
                    } else {
                        return left.next();
                    }
                } else if (lv.isPresent()) {
                    return left.next();
                } else {
                    return right.next();
                }
            }
        };

        Iterable<Integer> it = () -> merged;
        return StreamSupport.stream(it.spliterator(), false);
    }

    private static class Holder {
        public final Integer[] array;
        public final long start;
        public final long end;
        public final long medium;

        private Holder(Integer[] array, long start, long end) {
            this.array = array;
            this.start = start;
            this.end = end;
            this.medium = start + (end - start) / 2;
        }

        public Holder(Integer[] array) {
            this(array, 0, array.length - 1);
        }

        public boolean isEmpty() {
            return end < start;
        }

        public boolean containsOneElement() {
            return start == end;
        }

        public boolean containsTwoElements() {
            return end - start == 1;
        }

        public long medium() {
            return array[(int)medium];
        }

        public Integer first() {
            return array[(int)start];
        }
        public Integer last() {
            return array[(int)end];
        }
    }
}
