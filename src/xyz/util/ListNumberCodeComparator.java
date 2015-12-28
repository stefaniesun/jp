package xyz.util;

import java.util.Comparator;

public class ListNumberCodeComparator implements Comparator<ListNumberCode>{
	
	/*
	 * 使下拉框按照sort倒序排列
	 */
	@Override
	public  int compare(ListNumberCode  obj1, ListNumberCode obj2){
		int sort1 = obj1==null?0:obj1.getSort();
		int sort2 = obj2==null?0:obj2.getSort();
		return sort2-sort1;
	}
}
