package disk_store;

/* HASH ;
 * 
 * <bucket, list>
 * 
 * -inf to + inf - > mod 10 = 0 to 10
 * 
 * {0,1,2,3}
 * SET A    SET B
 * {0,2} -> {2}
 * {1,3} -> {3}
 * 
 * 
 * 
 */

import java.util.List;
import java.util.*;

/**
 * A hash index.
 * 
 */

public class HashIndex implements DBIndex {

	/**
	 * Create an new index.
	 */
	HashMap<Integer, ArrayList<Integer>> block;

	public HashIndex() {
		block = new HashMap<>();
		// throw new UnsupportedOperationException();
	}

	@Override
	public List<Integer> lookup(int key) {
		ArrayList<Integer> list = block.get(key);
		if (list != null) {
			// return value at position
			return list;
		} else {
			return new ArrayList<>();
		}
		// throw new UnsupportedOperationException();
	}

	@Override
	public void insert(int key, int blockNum) {
		// check if exists in HM

		System.out.println("SIZE: --- " + block.size());
		if (block.containsKey(key) == false) {
			ArrayList<Integer> list = new ArrayList<>();
			list.add(blockNum);
			block.put(key, list);
		} else {
			block.get(key).add(blockNum);
		}
		// throw new UnsupportedOperationException();
	}

	@Override
	public void delete(int key, int blockNum) {

		ArrayList<Integer> list = block.get(key);

		if (list != null) {
			list.remove(blockNum);

			if (list.size() <= 0) {
				block.remove(key);
			}
		}
		// throw new UnsupportedOperationException();

	}

	public int size() {
		// return block.size();
		int count = 0;

		for (Integer key : block.keySet()) {

			count += block.get(key).size();

		}
		return count;
		// throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		throw new UnsupportedOperationException();
	}
}
