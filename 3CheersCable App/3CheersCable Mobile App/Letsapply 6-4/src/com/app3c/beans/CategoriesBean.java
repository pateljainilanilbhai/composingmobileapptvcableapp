package com.app3c.beans;

import java.util.ArrayList;

public class CategoriesBean {
	int result;
	ArrayList<Category> categories;
	public int getResult() {
		return result;
	}
	public ArrayList<Category> getCategories() {
		return categories;
	}
	public void setCategories(ArrayList<Category> categories) {
		this.categories = categories;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public static class Category {

		private String categoryId, name, serviceProvider, imgurl, language, genre, quality;
		
		public String getCategoryId() {
			return categoryId;
		}

		public void setCategoryId(String categoryId) {
			this.categoryId = categoryId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getServiceProvider() {
			return serviceProvider;
		}

		public void setServiceProvider(String serviceProvider) {
			this.serviceProvider = serviceProvider;
		}

		public String getImgurl() {
			return imgurl;
		}

		public void setImgurl(String imgurl) {
			this.imgurl = imgurl;
		}

		public String getLanguage() {
			return language;
		}

		public void setLanguage(String language) {
			this.language = language;
		}

		public String getGenre() {
			return genre;
		}

		public void setGenre(String genre) {
			this.genre = genre;
		}

		public String getQuality() {
			return quality;
		}

		public void setQuality(String quality) {
			this.quality = quality;
		}

	}
	
	
	
}
