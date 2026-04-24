package br.com.sgci.controller.schema;

import org.springframework.data.domain.Sort.Direction;

import jakarta.validation.constraints.NotNull;

public class FilterPageable {

	@NotNull
	public Integer page;

	@NotNull
	public Integer size;

	@NotNull
	public Direction direction;

	@NotNull
	public String ordenarPor;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public String getOrdenarPor() {
		return ordenarPor;
	}

	public void setOrdenarPor(String ordenarPor) {
		this.ordenarPor = ordenarPor;
	}

}