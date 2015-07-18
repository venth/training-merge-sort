package org.venth.training;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.assertj.core.api.StrictAssertions.assertThat;
import static org.junit.Assert.assertFalse;

/**
 * @author Venth on 13/07/2015
 */
public class MergeSorterTest {

    @Test
    public void empty_array_is_already_sorted() {
        //given an empty array
        Integer[] empty = new Integer[0];

        //when the empty array is sorted
        Integer[] sortedEmptyArray = MergeSorter.sorted(empty);

        //then result is still the empty array
        assertThat(sortedEmptyArray).isEmpty();
    }

    @Test
    public void sorted_array_is_different_instance_than_the_original() {
        //given an empty array
        Integer[] empty = new Integer[0];

        //when the empty array is sorted
        Integer[] sortedEmptyArray = MergeSorter.sorted(empty);

        //then sorted array is different instance
        assertFalse(empty == sortedEmptyArray);
    }

    @Test
    public void one_element_is_always_sorted() {

        //given only one element in the array
        Integer[] oneElement = new Integer[] { 1 };

        //when one element array is sorted
        Integer[] sortedOneElementArray = MergeSorter.sorted(oneElement);

        //then sorted array is still one element
        assertThat(sortedOneElementArray).containsExactly(oneElement);

    }

    @Test
    public void sorted_array_remained_sorted() {
        //given already sorted arrays
        List<Integer[]> alreadySortedArrays = Arrays.asList(
                new Integer[] { 1, 2 },
                new Integer[] { 1, 2, 3 },
                new Integer[] { 1, 2, 3, 4 },
                new Integer[] { 1, 2, 5, 7, 8 },
                new Integer[] { 1, 2, 10, 11, 12, 13, 123 }
        );

        alreadySortedArrays.forEach(this::sorted_array_remained_sorted);
    }

    private void sorted_array_remained_sorted(Integer[] alreadySorted) {
        //when already sorted array is once again sorted
        Integer[] sortedArray = MergeSorter.sorted(alreadySorted);

        //then sorted array is still sorted ;)
        assertThat(sortedArray).containsExactly(alreadySorted);
    }

    @Test
    public void unsorted_array_will_be_sorted() {
        //given unsorted arrays
        Random random = new Random(System.currentTimeMillis());
        List<Integer[]> unsortedSortedArrays = Arrays.<Integer[]>asList(
                new Integer[] { 4, 6, 1, 2, 3 }
                , new Integer[] { 2, 1 }
                , new Integer[] { 1, 6, 2, 3, 7, 4 }
                , new Integer[] { 9, 1, 2, 9, 5, 7, 8 }
                , new Integer[] { 9, 9, 1, 2, 10, 11, 12, 13, 123, 9 }
        );

        unsortedSortedArrays.forEach(this::unsorted_array_will_be_sorted);
    }

    private void unsorted_array_will_be_sorted(Integer[] unsorted) {
        //when already sorted array is once again sorted
        Integer[] sorted = MergeSorter.sorted(unsorted);

        //then sorted array is still sorted ;)
        Integer[] expectedOrder = Stream.of(unsorted).sorted().toArray(Integer[]::new);
        assertThat(sorted).containsExactly(expectedOrder);
    }

    @Test
    public void two_unsorted_elements_array_will_be_sorted() {
        //given array with two unordered elements
        Integer[] unsortedArray = new Integer[] {2, 1};

        //when the unsorted array is sorted
        Integer[] sortedArray = MergeSorter.sorted(unsortedArray);

        //then the element's sequence is ascending
        assertThat(sortedArray).containsExactly(1, 2);
    }

    @Test
    public void duplicates_remain_unchanged() {
        //given array with two duplicated elements and one bigger than them
        Integer[] alreadySorted = new Integer[] {1, 1, 2};

        //when the already sorted array is sorted again
        Integer[] sortedArray = MergeSorter.sorted(alreadySorted);

        //then the element's sequence is unchanged
        assertThat(sortedArray).containsExactly(alreadySorted);
    }
}
