package entity;

import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
//data cleaning
//builder pattern只有当我们不允许用户改数据的情况下，才会使用
public class Item {
	private String itemId;
	private String name;
	private String address;
	private Set<String> keywords;
	private String imageUrl;
	private String url;
	
	private Item(ItemBuilder builder) {
		this.itemId = builder.itemId;
		this.name = builder.name;
		this.address = builder.address;
		this.keywords = builder.keywords;
		this.imageUrl = builder.imageUrl;
		this.url = builder.url;
	}
	
	//generate getter and setter in source menu
	public String getItemId() {
		return itemId;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public Set<String> getKeywords() {
		return keywords;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public String getUrl() {
		return url;
	}
	
	/* Builder Pattern. This inner class has to be static!! 
	 * 
	 * IF not static, then I cannot new ItemBuilder object unless I have an object of Item.
	 * However, we need an instance of ItemBuilder and pass it into the constructor of 
	 * Item class to new a Item object. Hence, it must be static, therefore I can new a ItemBuilder
	 * first, and then new a Item class.
	 */
	public static class ItemBuilder {
		private String itemId;
		private String name;
		private String address;
		private Set<String> keywords;
		private String imageUrl;
		private String url;
		public void setItemId(String itemId) {
			this.itemId = itemId;
		}
		public void setName(String name) {
			this.name = name;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public void setKeywords(Set<String> keywords) {
			this.keywords = keywords;
		}
		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public Item build() {
			//this represent the ItemBuilder object, pass it into the constructor 
			//of Item class.
			return new Item(this); 
		}
		
	}
	
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		obj.put("item_id", itemId);
		obj.put("name", name);
		obj.put("address", address);
		obj.put("keywords", new JSONArray(keywords));
		obj.put("image_url", imageUrl);
		obj.put("url", url);
		return obj;
	}


	
}

// ItemBuilder builder = new Item.ItemBuilder();
//builder.setItemid("abcd");
//builder.setName("vincent");
//Item item = builder.build();

