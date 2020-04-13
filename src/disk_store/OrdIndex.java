package disk_store;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
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
	ArrayList<extra> index;
	
	public OrdIndex() {
		index = new ArrayList<extra>(0);
		
	}
	
	public int indexof(int key) {
		if(index.isEmpty()) {
			return -1;
		}
		int curr_pos = (index.size())/2;
		System.out.println("========new search========");
		System.out.println("looking for: " + key);
		System.out.println("start: "+curr_pos);
		int inc = curr_pos;	
		while(inc>=0) {
			System.out.print("current_pos: "+curr_pos);
			extra tmp = index.get(curr_pos);
			System.out.println("  ,  current_key:"+tmp.key);
			inc = inc/2;
			if(tmp.key == key ) {
				return curr_pos;
			}else if(inc==0){
				if(curr_pos!=0) {
					tmp = index.get(curr_pos-1);
				}				
				if(tmp.key==key) {
					System.out.print("current_pos: "+(curr_pos-1));
					System.out.println("  ,  returning:"+tmp.key);
					return curr_pos-1;
				}else {
					if(curr_pos<index.size()-1) {
						tmp = index.get(curr_pos+1);
					}			
					if(tmp.key==key) {
						System.out.print("current_pos: "+(curr_pos-1));
						System.out.println("  ,  returning:"+tmp.key);
						return curr_pos+1;
					}
				}
				return -1;
			}else if(tmp.key<key){
				curr_pos = curr_pos+inc;
			}else {				
				curr_pos = curr_pos-inc;
			}
		}		
		return -1;
	}

	
	@Override
	public List<Integer> lookup(int key) {
		
		if(indexof(key)==-1) {
			return new ArrayList<Integer>();
		}
		Set<Integer> set = new HashSet<>();
		set.addAll(index.get(indexof(key)).val);
		ArrayList<Integer> tmp = new ArrayList<Integer>(set);
		return tmp;
	}
	
	@Override
	public void insert(int key, int blockNum) {
		
		if(indexof(key)==-1) {
			extra tmp = new extra();
			tmp.val = new ArrayList<>();
			tmp.val.add(blockNum);
			tmp.key = key;
			index.add(tmp);
			return;
		}else {
			index.get(indexof(key)).val.add(blockNum);
			Collections.sort(index.get(indexof(key)).val);
		}	
	}

	@Override
	public void delete(int key, int blockNum) {
		if(index.size()<=key) {
			return;
		}else {
			int big = indexof(key);			
			int location = index.get(big).indexof(blockNum);
			System.out.println("===lookup==="+location);
			if(location == -1) {		
				return;
			}
			System.out.println("===lookup===" + location);
			index.get(big).val.remove(location);
		}
		
	}
	
	/**
	 * Return the number of entries in the index
	 * @return
	 */
	public int size() {
		// you may find it useful to implement this
		int count =0;
		for(extra i: index) {
			count = count + i.val.size();
		}
		return count;
	}
	
	@Override
	public String toString() {
		throw new UnsupportedOperationException();
	}
}

class extra{
	int key;
	ArrayList<Integer> val;
	public int indexof(int key) {
		if(val.isEmpty()) {
			return -1;
		}
		int curr_pos = (val.size())/2;
		System.out.println("========new search========");
		System.out.println("looking for: " + key);
		System.out.println("start: "+curr_pos);
		int inc = curr_pos;	
		while(inc>=0) {
			System.out.print("current_pos: "+curr_pos);
			int tmp = val.get(curr_pos);
			System.out.println("  ,  current_key:"+tmp);
			inc = inc/2;
			if(tmp == key ) {
				return curr_pos;
			}else if(inc==0){
				if(curr_pos!=0) {
					tmp = val.get(curr_pos-1);
				}				
				if(tmp==key) {
					System.out.print("current_pos: "+(curr_pos-1));
					System.out.println("  ,  returning:"+tmp);
					return curr_pos-1;
				}else {
					if(curr_pos<val.size()-1) {
						tmp = val.get(curr_pos+1);
					}			
					if(tmp==key) {
						System.out.print("current_pos: "+(curr_pos+1));
						System.out.println("  ,  returning:"+tmp);
						return curr_pos+1;
					}
				}
				return -1;
			}else if(tmp<key){
				curr_pos = curr_pos+inc;
			}else {				
				curr_pos = curr_pos-inc;
			}
		}		
		return -1;
	}
	public boolean equals(int k) {
		return key==k;
	}
}

