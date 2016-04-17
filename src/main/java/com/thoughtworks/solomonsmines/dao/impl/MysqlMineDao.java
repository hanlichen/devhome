package com.thoughtworks.solomonsmines.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thoughtworks.solomonsmines.dao.MinesDao;
import com.thoughtworks.solomonsmines.model.MinesModel;

@Component
@Transactional
public class MysqlMineDao implements MinesDao<MinesModel> {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<MinesModel> listMines() {

		List<MinesModel> minesModels = new ArrayList<MinesModel>();

		List<?> list = jdbcTemplate.queryForList("select id, gongsi,lianxiren,tel1, tel2 from HangYe_11");
		Iterator<?> iterator = list.iterator();
		MinesModel book = null;
		while (iterator.hasNext()) {
			Map map4book = (Map) iterator.next();
			book = new MinesModel();
			book.setId((Integer) map4book.get("id"));
			book.setGongsi((String) map4book.get("gongsi"));
			book.setLianxiren((String) map4book.get("lianxiren"));
			book.setTel1((String) map4book.get("tel1"));
			book.setTel2((String) map4book.get("tel2"));
			minesModels.add(book);
		}

		return minesModels;

	}

}
