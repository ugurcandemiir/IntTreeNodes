# Homework 04 - Due Sunday, Sept. 23, 2018

This week, we will work on a different sorting algorithm that demonstrates the implementation of self-referential objects, and the elegance of an important data structure - a binary tree. A binary tree is a recursive data structure that starts with a root node. Each node of the tree will have three parts associated with it - a *payload*, basically the information carried in that node, a *left subtree* and a *right subtree*, where the sub-trees may be null, or they may be another tree (with it's own root, left subtree, and right subtree). At the bottom of the tree are nodes with null left and right sub-trees. These are *binary* trees because each node has at most two sub-trees.

For this excercise, we will create a binary tree that has a single integer as it's payload. We will also be careful about how we make these trees to preserve a simple rule: the value of all of the integer in the left sub-tree of a tree will be less than the value in the root node. Similarly, the values in the right sub-tree will be greater than or equal to the value at the root.

## Making the IntTreeNode class

:warning: Put all classes in the *hw04* package.

Create a new class called *IntTreeNode*. This class should have three private fields: a reference to an object in the IntTreeNode class called *left*, a reference to an object in the IntTreeNode class called *right*, and an integer *value* field.

Add a creator method for a new IntTreeNode that takes a single argument - an integer value used to initialize the *value* field. Note that when new objects are created, the *left* and *right* fields are pre-initialized by Java to null.

Create an *addNode* method that takes a reference to an IntTreeNode object as a parameter, and returns a void. This method should look at the value in the parameter's payload and compare it to this node's value. If the parameter's value is less than this value, and if the *left* field is null, then just set the *left* field to the parameter value. If the left field is not null, then invoke left.addNode passing in the parameter as an argument.  If the parameter's value is greater than or equal to this value (in other words - not less than), do the same thing with the *right* field - if *right* is null, just set it to the parameter. If *right* is not null, invoke right.addNode, passing in the parameter.

Notice that the addNode method preserves our basic rule. If the new node being added has a value less than the root, it is added in the left sub-tree. If the new node being added has a value greater than the root, it is added in the right sub-tree, as long as the parameter tree node has no left or right sub-trees.

Now we can build binary trees using the IntTreeNode class, but we need some way of looking at the trees we have built.  Let's make a *toString* method for a tree that takes no arguments, but returns a String result. One way of representing a tree as a string is to use parenthesis... basically "( left-subtree ) value ( right-subtree )", where the value of the left sub-tree is the string representation of the left sub-tree and the right sub-tree respectively. Trees are perfect for getting a feel for recursive methods - methods which invoke themselves. The toString method should only print parenthesis if the left sub-tree or right-subtree are not null. You can do this by starting off with a null result, ```String result=""```, and building up the result with statements like:
```java
if (left != null) result = result + "( " + left.toString() + " )";
```

Once you have gotten this far, add a ```public static void main(String args[])``` method to the IntTreeNode class. In the main class, create an array of integers that has at least five or six elements. Then make a reference to an IntTreeNode called *root*, initialized to null. Then, loop through each integer in the array of integers, and create a new IntTreeNode from that integer. If root is still null, assign your new tree node to root. Otherwise, invoke root.addNode on your new node.

After the loop is complete, print out your tree using the instruction:
```java
System.out.println("Tree is: " + root);
```
(Remember, Java automatic String conversion will invoke root.toString() for you when root is referenced in a string context.)

## Printing Trees
If you have ever run into the *Lisp* computer language, you will be familiar with just how annoying deeply nested parenthesis can get, and our toString method above relies on deeply nested parenthesis, making it hard to visualize just how the tree works. So here's a little trick. Let's create a *printTree* method that takes no arguments, and returns void. It's job is to print out the tree in some reasonable fashion. In order to do so, we will print the root on the left of the screen, with the left sub-tree to the left and above the root, and the right sub-tree to the right and below the root. It turns out that this can be done fairly easilly if we again think recursively.  All we have to do is print the left sub-tree first, then the root, then the right sub-tree... but if we do that, then everything is left justified, and that's not what we want... so we introduce another definition of the *printTree* method that takes a single argument... a prefix String. Notice that if we put a short prefix string on the left sub-tree, then it will be shifted to the right a little bit. Same thing for the right sub-tree. Finally, a little decoration just to make things easier to read, and the version of the printTree method that takes a prefix argument might look like:
```java
public void printTree(String prefix) {
	if (left !=null) left.printTree(prefix + "    |");
	System.out.println(prefix + value + "--+");
	if (right != null) right.printTree(prefix + "    |");
}
```
(Note that this assumes that the string representation of "value" is the same for every node... and it's not, but close enough.)

The no parameter version of printTree can just invoke the prefix version with "" as an argument.

Now, add an invocation of root.printTree to your main method and try it. Look OK?

## Sorting an Array of Integers
Trees are all very cool from a Computer Science point of view, but practically, what I really want to do is sort an array of integers. Is there any good way of doing that? Well sure. Let's add a new static method to the IntTreeNode class called *sortArray* which takes a reference to an array of integers as an argument, and returns void. The job of this method will be to put all of the elements of the array into a binary tree, and then read the values of that tree in sorted order, and rewrite the array with the values in sorted order.

We've already done some of this work -- the process of reading an array of integers and putting it into a tree -- in our main method.  All we need to do in the *sortArray* method is reproduce this. Make a local reference variable of type IntTreeNode called *root*. Then, loop through the elements of the array, making a new IntTreeNode from each element, calling that new node *root* if root is null, or invoking *root.addTree* on the new node if not.

Now that we have everything in the tree based at *root*, how do we read values out this tree and back into the array? The easiest way is to code a new recursive helper method, which I called *addTree2array*, that takes two arguments - first the array to write into, and second the position to start writing. This method should return the position of the next element in the array to write to. Here's how I coded that method in my code:
```java
private int addTree2array(int[] arr,int pos) {
	if (left != null) pos=left.addTree2array(arr,pos);
	arr[pos]=value;
	pos++;
	if (right != null) pos=right.addTree2array(arr,pos);
	return pos;
}
```
With this helper method in place, all we have to do in the *sortArray* method once we have our tree is to invoke *addTree2array(<parameter>,0)* where <parameter> is the array passed into the *sortArray* method as a parameter.
  
To test this out, add an invocation of the sortArray method from the IntTreeNode main method, and then print out the results using the Arrays.toString method to print an array. (You will need to import java.util.Arrays to use the Arrays.toString method.)

## Food for Thought

What is it about the IntTreeNode class that restricts the trees to have a payload of a simple integer? Could we have a different class that does the same thing with a payload of doubles? What about payloads of bank account referenes, or Library books? What would it take to make a generic Tree, where the payload was some arbitrary reference to an object?

## Submitting your Results

Don't forget the magic git commands:
```
git add IntTreeNode.java
git commit -am "Everything coded and tested"
git push
git rev-parse HEAD
```
Then copy and paste the resulting hash into myCourses CS140/Content/Homework Submissions/Homework 04.

## Grading Criteria

This homework is worth 10 points. If your code does not compile, you will get an 8 point deduction. If your code gets a compiler warning, you will get a 2 point deduction. The CA's will run your hw04.IntTreeNode main function. If the tester main method does not do everything requested with correct results, you will get a 4 point deduction. The CA's will also code a Tester class that creates and manipulates their own integer arrays. If there are any problems with the Tester class (e.g. method names don't match, or wronge results occur), you will get up to a 3 point deduction (depending on the severity of the problem).
