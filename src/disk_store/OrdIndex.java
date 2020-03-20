package disk_store;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * An ordered index.  Duplicate search key values are allowed,
 * but not duplicate index table entries.  In DB terminology, a
 * search key is not a superkey.
 * 
 * A limitation of this class is that only single integer search
 * keys are supported.
 *
 */


public class OrdIndex implements DBIndex {
	
	/**
	 * Create an new ordered index.
	 */
	ArrayList<ArrayList<Integer>> index;
	
	public OrdIndex() {
		index = new ArrayList<ArrayList<Integer> >(0);
	}
	
	@Override
	public List<Integer> lookup(int key) {
		
		key--;
		if(key>=index.size()) {
			return new ArrayList<Integer>();
		}
		Set<Integer> set = new HashSet<>(index.get(key));
		ArrayList<Integer> tmp = new ArrayList<Integer>(set);
		return tmp;
	}
	
	@Override
	public void insert(int key, int blockNum) {
		key--;
		if(key>=index.size()) {
			ArrayList <Integer> tmp = new ArrayList<>();
			tmp.add(blockNum);
			index.add(tmp);
			return;
		}else {
			int test = index.get(key).indexOf(blockNum);
			index.get(key).add(blockNum);
			Collections.sort(index.get(key));		
		}	
	}

	@Override
	public void delete(int key, int blockNum) {
		key--;
		if(index.size()<=key) {
			return;
		}else {
			int l = index.get(key).indexOf(blockNum);
			if(l==-1) {
				return;
			}
			index.get(key).remove(l);
		}
		
	}
	
	/**
	 * Return the number of entries in the index
	 * @return
	 */
	public int size() {
		// you may find it useful to implement this
		int count =0;
		for(ArrayList<Integer> i: index) {
			for(Integer j : i) {
				count++;
			}
		}
		return count;
	}
	
	@Override
	public String toString() {
		throw new UnsupportedOperationException();
	}
}
