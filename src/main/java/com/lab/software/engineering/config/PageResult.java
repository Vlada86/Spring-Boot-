package com.lab.software.engineering.config;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PageResult<T> {

	@JsonProperty("number")
	private int number;
	@JsonProperty("size")
	private int size;
	@JsonProperty("totalPages")
	private int totalPages;
	@JsonProperty("numberOfElements")
	private int numberOfElements;
	@JsonProperty("totalElements")
	private long totalElements;
	@JsonProperty("previousPage")
	private boolean previousPage;
	@JsonProperty("nextPage")
	private boolean nextPage;
	@JsonProperty("first")
	private boolean first;
	@JsonProperty("last")
	private boolean last;
	@JsonProperty("content")
	private List<T> content;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirstPage(boolean first) {
		this.first = first;
	}

	public boolean isLast() {
		return last;
	}

	public void setLastPage(boolean last) {
		this.last = last;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public boolean isPreviousPage() {
		return previousPage;
	}

	public void setPreviousPage(boolean previousPage) {
		this.previousPage = previousPage;
	}

	public boolean isNextPage() {
		return nextPage;
	}

	public void setNextPage(boolean nextPage) {
		this.nextPage = nextPage;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public void setLast(boolean last) {
		this.last = last;
	}
}
