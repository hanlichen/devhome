package com.thoughtworks.solomonsmines.dao;

import java.util.List;

import com.thoughtworks.solomonsmines.model.MinesModel;

public interface MinesDao<T> {
	List<MinesModel> listMines(String phone);

	List<MinesModel> listMines();

}
