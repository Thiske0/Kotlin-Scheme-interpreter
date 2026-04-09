# Kotlin Scheme interpreter
This program reads a Scheme function definition and counts how many times it properly tail recursively calls itself.
It is written in Kotlin 2.3.0.

## Usage
Compile and build with IntelliJ IDEA.
Pass the filepath of the program to count as the first and only commandline argument.

## Example
````scheme
(define (f x)
  (if x
      (f 1 (f 4))
      (if x
          (f 1)
          4)))
````
calls itself tail-recursively 1 times.
- `(f 1 (f 4))` is in a tail-call position but the number of arguments is not the same so does not count.
- the inner `(f 4)` is not in a tail-call position so does not count.
- `(f 1)` is in a tail-call position and has the same number of arguments so is a proper tail recursive call.