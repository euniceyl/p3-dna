# Project 3: DNA

## Project Background and Introduction

In this project, we experiment with different implementations of a simulated [restriction enzyme cutting](https://en.wikipedia.org/wiki/Restriction_enzyme) (or cleaving) of a DNA molecule by applying the linked list data structure. Specifically, we will develop a `LinkStrand` class, an implementation of the `IDnaStrand` interface that uses an internal linked list to model recombinant DNA. This implementation aims to be more efficient for modeling DNA splicing operations than using a String or StringBuilder. The simulation is a simplification of the chemical process, but provides an example of the utility of linked lists as a data structure for certain algorithmic processes.

<details>
<summary> Historical Details</summary>

[Three scientists shared the Nobel Prize](http://nobelprize.org/nobel_prizes/medicine/laureates/1978/press.html) in 1978 for the discovery of restriction enzymes. They're also an essential part of the process called [PCR polymerase chain reaction](http://en.wikipedia.org/wiki/Polymerase_chain_reaction) which is one of the most significant discoveries/inventions in chemistry and for which Kary Mullis won the Nobel Prize in 1993.

Kary Mullis, the inventor of PCR, is an interesting character. To see more about him see this archived copy of a [1992 interview in Omni Magazine](http://web.archive.org/web/20010121194200/http://omnimag.com/archives/interviews/mullis.html) or his [personal website](http://karymullis.com/) which includes information about his autobiography Dancing Naked in the Mind Field, though you can read this free [Nobel autobiography](https://www.nobelprize.org/prizes/chemistry/1993/mullis/biographical/) as well.
 
</details>

### DNA strands and the Starter Code

For the purposes of this project, DNA is represented as a sequence of characters, specifically `a`, `c`, `g`, and `t` for the four chemical bases of DNA. Our two data files `ecoli.txt` and `ecoli_small.txt` represent the genetic information of E. coli - there are over 4.6 million bases in the full sequence in `ecoli.txt` and over 320 thousand in the `ecoli_small.txt` subsequence.

## Part 1: Running DNABenchmark, Profiling, Analysis

### `cutAndSplice` Simulation Complexity with `StringStrand` an `StringBuilderStrand`

The `main` method of `DNABenchmark` simulates a DNA splicing experiment represented by the `cutAndSplice` method (implemented in `IDnaStrand` with complexity that depends on which implementation of the interface is being used).

<details>
<summary>Complexity of cutAndSplice</summary>

The method `cutAndSplice` is not a mutator. It starts with a strand of DNA and creates a new strand by finding each and every occurrence of a restriction enzyme like `“gaattc”` and replacing this enzyme by a specified splicee -- another strand of DNA. If `dna` represents the strand `"cgatcctagatcgg"` then the call 

```java
dna.cutAndSplice("gat", "gggtttaaa")
```

would result in returning a new strand of DNA in which each occurrence of `"gat"` in `dna` is replaced by `"gggtttaaa"` -- as shown in the diagram below where the original strand is shown first, with the enzyme `"gat"` shaded in blue and the splicee `"gggtttaaa"` shaded in green. 

<div align="center">
  <img src="figures/splice.png">
</div>

The diagram illustrates how `cutAndSplice` works with both `StringStrand` and `StringBuilderStrand`. Each is a strand of 14 characters in which the restriction enzyme `"gat"` occurs twice, is replaced by `"gggtttaaa"`, resulting in creating and returning a new strand that contains 26 characters.

For instance, if the original strand has size N, then the new strand has size N + b(S-E) where b is the number of breaks, or occurrences of the enzyme, S is the length of the splicee and E is the length of the enzyme. If we assume the splicee is large, as it will be when benchmarking, we can ignore E and this becomes approximately N + bS, the size of the recombinant new strand in terms. 

</details>

### Benchmarking `StringStrand` and `StringBuilderStrand`

We run the `main` method of the `DNABenchmark` twice, once for the `StringStrand` implementation of the `IDnaStrand` interface and once for the `StringBuilderStrand` implementation.

<details>
<summary>Expand for details on running DNABenchmark</summary>

The main method benchmarks the average time (over several trials)  in milliseconds that it takes to run `cutAndSplice` for different values of N (the size of the original dna strand), b (the number of breaks / occurrences of the `enzyme`), and S (the size of the `splicee`). It also shows the size of the resulting recombininant new Strand, labeled as `recomb`, which is roughly equal to N + bS. 

First it performs several runs increasing S while holding the other values constant. Then it performs several runs increasing N and b while holding S constant. Example runs from an instructor's computer on `ecoli_small.txt` are shown below (note that your timings may differ but should show similar trends).
</details>

<details>
<summary>StringStrand DNABenchmark Example Results</summary>

```
dna length = 320,160
cutting at enzyme gaattc
----------------------------------------------------------------------
Class             dna,N   splicee,S        recomb  time(ms)  breaks,b
----------------------------------------------------------------------
StringStra:     320,160      10,000       769,890        13        45
StringStra:     320,160      20,000     1,219,890        13        45
StringStra:     320,160      40,000     2,119,890        14        45
StringStra:     320,160      80,000     3,919,890        26        45
StringStra:     320,160     160,000     7,519,890        49        45
StringStra:     320,160     320,000    14,719,890       105        45
StringStra:     320,160     640,000    29,119,890       239        45
StringStra:     320,160   1,280,000    57,919,890       481        45
StringStra:     320,160      10,000       769,890         6        45
StringStra:     640,320      10,000     1,539,780        21        90
StringStra:   1,280,640      10,000     3,079,560        84       180
StringStra:   2,561,280      10,000     6,159,120       322       360
StringStra:   5,122,560      10,000    12,318,240     1,449       720
```

</details>

<details>
<summary>StringBuilderStrand DNABenchmark Example Results</summary>

```
dna length = 320,160
cutting at enzyme gaattc
----------------------------------------------------------------------
Class             dna,N   splicee,S        recomb  time(ms)  breaks,b
----------------------------------------------------------------------
StringBuil:     320,160      10,000       769,890         1        45
StringBuil:     320,160      20,000     1,219,890         1        45
StringBuil:     320,160      40,000     2,119,890         1        45
StringBuil:     320,160      80,000     3,919,890         1        45
StringBuil:     320,160     160,000     7,519,890         2        45
StringBuil:     320,160     320,000    14,719,890         3        45
StringBuil:     320,160     640,000    29,119,890         6        45
StringBuil:     320,160   1,280,000    57,919,890         8        45
StringBuil:     320,160      10,000       769,890         1        45
StringBuil:     640,320      10,000     1,539,780         1        90
StringBuil:   1,280,640      10,000     3,079,560         3       180
StringBuil:   2,561,280      10,000     6,159,120         7       360
StringBuil:   5,122,560      10,000    12,318,240        15       720
```

</details>

## Part 2: Programming LinkStrand

### LinkStrand implements IDnaStrand

```java 
public class LinkStrand implements IDnaStrand
```

#### 1. `LinkStrand` State, Constructors and `initialize` Method
We implemented two constructors: one with no parameters (the default constructor) and one with a `String` parameter. The constructors work by calling the required initialize method. We implement the initialize method that initializes the `LinkStrand` object with a `String`.

<details>
<summary>Details on LinkStrand State, Constructors and initialize Method</summary>

```java
private Node myFirst, myLast;
private long mySize;
private int myAppends;
private int myIndex;
private Node myCurrent;
private int myLocalIndex;
```

**All constructors and methods maintain the following class invariants:**
1. `myFirst` references the first node in a linked list of nodes.
2. `myLast` references the last node in a linked list of nodes.
3. `mySize` represents the total number of characters stored in all nodes together.
4. `myAppends` is the number of times that the append method has been called. It would be useful to think of this as one less than the number of nodes in the linked list.

The following instance variables will be updated in `charAt()`:
1. `myIndex` tracks the last character we accessed with the `charAt()` method. We initialized this as `0`.
2. `myCurrent` tracks the last node holding the character at position `myIndex`. We initialized this as `myFirst`.
3. `myLocalIndex` tracks the last character we accessed within the `Node`. We initialized `this` as `0`.

Initially, when the `LinkStrand("cgatt...")` constructor is called  (though the `String` parameter can be any string) there will be a single node in the linked list that represents the DNA strand `"cgatt…"`.

<div align="center">
  <img src="figures/initialize.png">
</div>

In our two constructors: the string constructor should consist of one call to initialize which establishes the class invariant with a single node representing the entire strand of DNA as illustrated. The no-argument constructor has one line: `this("")` which calls the other constructor with a String parameter of `""`. 

The `initialize` method will maintain the class invariants when it's called. There will be a single node created after `initialize` is called.

</details>

#### 2. Implementing the `getInstance` and `size` Methods
We implement the `getInstance` method that returns a `LinkStrand` object. We then implement `size`, which is a single line getter method that runs in `O(1)` time.

#### 3. Implementing the `append` and `getAppendCount` Methods 
We implement `append` which creates one new node and updates instance variables to maintain class invariants as described in the details below. We implement `getAppendCount` which is a single line and must run in `O(1)` time.

<details>
<summary>Details on Implementing the append and getAppendCount Methods</summary>

The `append` method should add one new node to the end of the internal linked list and update state to maintain the invariant. For example, suppose that these two statements are both executed:

```java
LinkStrand dna = new LinkStrand("cgatt");
dna.append("aattcc");
```
<div align="center">
  <img src="figures/append.png">
</div>

The internal linked list maintained by `LinkStrand` after the first call is diagrammed above. After the call to append we have the following picture:

Maintaining the class invariant after this call to append would require:
1. `myFirst` doesn't change
2. `myLast` changes to point to the new node added
3. `mySize` is incremented by six
4. `myAppends` is incremented by one (because a new node is added).

</details>


#### 4. Implementing the `toString` Method
We implement `toString` which returns the `String` representation of the `LinkStrand` by looping over nodes and appending their values to a `StringBuilder` object. The method should run in `O(N)` time.

<details>
<summary>Details on Implementing the toString Method</summary>

The `toString` method returns the `String` representation of the entire DNA strand. This is a concatenation of the `String` stored in each node.

This method uses a standard `while` loop to visit each node in the internal linked list. The method creates and updates a single `StringBuilder` object by appending each `node.info` field to a `StringBuilder` object that's initially empty. The final return from `LinkStrand.toString` will simply be returning the result of calling `.toString()` on the `StringBuilder` object.

</details>

#### 5. Implementing the `reverse` Method
We implement `reverse` to return a new `LinkStrand` object that's the reverse of the object on which it's called. **This method is not a mutator, it creates a new `LinkStrand`.**

<details>
<summary>Details on Implementing the reverse Method</summary>

This method creates a new `LinkStrand` object that is the reverse of the object on which it's called. The reverse of `"cgatccgg"` is `"ggcctagc"`. This method returns a new strand; **it does not alter the strand on which it's called**. We create a new linked list with nodes in reverse order, and each string in each node also reversed. 

For example, if the original `LinkStrand` looks like
<div align="center">
  <img width=300 src="figures/beforeReverse.png">
</div>
then the `LinkStrand` returned by `reverse` should look like the following:
<div align="center">
  <img width=300 src="figures/afterReverse.png">
</div>
with no changes to the original.

</details>

#### Implementing the `charAt` Method
We implement `charAt` which returns the character at a specific index. This method requires new instance variables *to meet performance characteristics.*

<details>
<summary>Details on Implementing the charAt Method</summary>

This method returns the character at the specified index if that's a valid index, and throws an `IndexOutOfBoundsException` otherwise. To do this, we update the following instance variables: one for the current node in a sequence of calls of charAt, one for the current index into that node, and one for the overall count:

- `myIndex` is the value of the parameter in the last call to `charAt`. This means that if a call to `s.charAt(100)` is followed by `s.charAt(101)` the value of `myIndex` will be 100 after `s.charAt(100)` executes and 101 after `s.charAt(101)` executes.
- `myLocalIndex` is the value of the index within the string stored in the node last-referenced by `charAt` when the method finishes. For example, suppose a strand consists of three nodes: the first has 60 characters; followed by a node of 30 characters; followed by a node of 40 characters. The call `s.charAt(40)` will mean that `myIndex` is 40 and `myLocalIndex` is also 40 since that's the index within the first node of the list, where the character whose index is 40 is found.  Suppose this is followed by `s.charAt(70).` The character at index 60 of the entire strand will be the character with index zero of the second node -- since the first node holds characters with indexes 0-59 since its info field is a string of 60 characters. The character at index 70 of the entire strand will be the character with index 10 of the second node. This means that after the call `charAt(70)` the value of `myIndex` will be 70, the value of `myLocalIndex` will be 10, and the value of `myCurrent` (see just below) is a pointer to the second node of a three-node list.

<div align="center">
  <img src="figures/charAt.png">
</div>

- `myCurrent` is the node of the internal list referenced in the last call to `charAt`. In the example above the value of `myCurrent` would be the first node after the call `s.charAt(40)`, would be the second node after the call `s.charAt(70)` or `s.charAt(89)`, and would be the third node after the call `s.charAt(90)` since the first two nodes only contain a total of 90 characters, with indexes 0 to 89.


##### Efficiency of charAt
If the `charAt` method is not efficient, the loop below will be `O(N^2)` since the `charAt` method will be `O(k)` to access the kth character.

```java
LinkStrand dna = new LinkStrand(".....");
StringBuilder s = new StringBuilder("");
for(int k=0; k < dna.size(); k++) {
    s.append(dna.charAt(k));
}
```

This `charAt` method is called by the code in the `CharDnaIterator` class. So iterating over an `IDnaStrand` object will ultimately use the `charAt` method as shown in the code below. 

```java
LinkStrand dna = new LinkStrand(".....");
Iterator<Character> iter= dna.iterator();
for(char ch : iter) {
    System.out.print(ch);
}
System.out.println();
```

The `Iterator` object in the code above is constructed as a result of calling the default `IDnaStrand.iterator` method, the body is shown here: 

```java
return new CharDnaIterator(this);
```

##### Order of Calls Matters
If the call `.charAt(100)` is followed by the call `.charAt(30)` we need to start at the beginning of the internal linked list to find the character with index 30. If `.charAt(100)` is followed by `.charAt(350)` you won't start at the first node, but continue with the values stored in the instance variables.

</details>

## Part 3: More Benchmarking and Analysis

<details>
<summary> Complexity of cutAndSplice with LinkStrand</summary>

If `dna` represents the strand `"cgatcctagatcgg"` then the call 

```java
dna.cutAndSplice("gat", "gggtttaaa")
```

would result in returning a new strand of DNA in which each occurrence of the enzyme/strand `"gat"` in the object `dna` is replaced by the splice, `"gggtttaaa"`. 

For this example, the `LinkStrand` result is diagrammed below.

<div align="center">
  <img src="figures/link-cutsplice.png">
</div>

Each time the original strand, a single string, is cut/spliced a new node is created. The new nodes for the `splicee` can all have `String info` referencing the same `String` object in memory, as shown in the diagram for the second and fourth nodes. These represent the first and second occurrences of `"gat"`, respectively. This means `LinkStrand` only needs to represent the `splicee` string once, however many times it is being spliced in / however many breaks there are.

This diagram represents the final `LinkStrand` object after a cut-and-splice operation. That strand is created by the `default cutAndSplice` implementation that calls `toString`, `getInstance`, and `append` which in `LinkStrand` ultimately result in a sequence of nodes as shown here.

</details>

Coursework from Duke CS 201: Data Structures and Algorithms.
