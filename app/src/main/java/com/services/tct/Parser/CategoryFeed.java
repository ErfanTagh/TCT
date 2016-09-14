package com.services.tct.Parser;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

public class CategoryFeed implements Serializable {

	private static final long serialVersionUID = 1L;
	private int _itemcount = 0;
	private List<CategoryItem> _itemlist;

	public CategoryFeed() {
		_itemlist = new Vector<CategoryItem>(0);
	}

	public void addItem(CategoryItem item) {
		_itemlist.add(item);
		_itemcount++;
	}

	public CategoryItem getItem(int location) {
		return _itemlist.get(location);
	}

	public int getItemCount() {
		return _itemcount;
	}

}
