 



# Sorting

callbacks(compare function) is used to sort any type of data


there is also comparable API call

Implement compareTo() so that v.compareTo(w).

Helper functions.

less: return boolean;
exchange: swap item in array at index i with element at index j

Test if an array is sorted:

```
    for (...arrya) {
        if (less(a[i], a[i-1])) return false;
        return true;
    }
```

## selection sort

search for smallest in remaining entries and swap with first

N^2 / 2 and N exchanges

## insertion sort

moving i index, swap a[i] with each larger entry to its left

1/4 N^2 exchanges

## shell sort

move entries more than one position at a time by h-sorting the array:

N^3/2

## shuffling

assign random number to item. sort them by this number. expensive


how to linear: Knuth shuffle

- in iteration i, pick integer r between 0 and i uniformly at random
- swap a[i] and a[r]

## convex hull

The convex hull of a set of N points is the smallest perimeter fence enclosing the points.

Equivalen definitions:
smallest convex set containing all the points
smallest are convex polygon enclosing the points
convex polygon enclosing the points, whose vertices are points in set

**Graham scan:**

- choose pointn p with smallest y-coordinaet
- sort points by polar andgle with p
- consider point in order; discard unless it create a ccw turn



