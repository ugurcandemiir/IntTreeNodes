package hw04;
import java.util.Arrays;

public class IntTreeNode {
	private IntTreeNode left;
	private IntTreeNode right;
	private int value;
	
	public IntTreeNode(int value){
		this.value = value;
		left = null;
		right = null;
	}
	void addNode(IntTreeNode now) {		
		if(now.value < this.value){
			
			if(left == null){
				left = now;
			}
			else {
				left.addNode(now);
			}
			}
			
		if (now.value >= this.value){
			
			if(right != null){
				right.addNode(now);
			}
			else {
				right = now;
			}	
		}	
	}
	public String toString() {
		String result = "";
		if (left != null) {
			result = result + " ( " + left.toString() + " ) ";
		}
		result = result + this.value;
		if (right != null) {
			result = result + " ( " + right.toString() + " ) ";
		}
		return result;		
	}
	public void printTree() {
		
		printTree("");
	}
	public void printTree(String prefix) {
		if (left !=null) {
			left.printTree(prefix + "    |");
		}
		System.out.println(prefix + value + "--+");
		if (right != null) {
			right.printTree(prefix + "    |");
		}
	}
	public static void sortArray(int [] arrayOfInteger) {
	IntTreeNode root = null;
	for (int i=0; i< arrayOfInteger.length; i++) {
		IntTreeNode node = new IntTreeNode(arrayOfInteger[i]);
		if(root == null) {
			root = node;
		}
		else {
			root.addNode(node);
		}
	}
	root.addTree2array(arrayOfInteger,0);
	}
	private int addTree2array(int[] arr,int pos) {
		if (left != null) { 
			pos=left.addTree2array(arr,pos);
			}
		arr[pos]=value;
		pos++;
		if (right != null) {
			pos=right.addTree2array(arr,pos);
		}
		return pos;
		
	}
	public static void main(String args[]) {
		int[] ugurcan = {8,9,1,7,13,5,3,2,29,10};
		IntTreeNode root = null;
		for (int i=0; i< ugurcan.length; i++) {
			IntTreeNode node = new IntTreeNode(ugurcan[i]);
			if(root == null) {
				root = node;
			}
			else {
				root.addNode(node);
			}

		}
		System.out.println("Tree is: " + root);
		System.out.println("\n");
		
		root.printTree("*");
		System.out.println("\n");
		/////////////////////
		sortArray(ugurcan);
		System.out.println(Arrays.toString(ugurcan));  
			
	}
}













