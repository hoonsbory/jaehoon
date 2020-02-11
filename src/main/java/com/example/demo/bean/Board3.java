package com.example.demo.bean;

import java.sql.Date;

public class Board3 {
	String board_title;
	String board_content;
	String board_img;
	String board_userid;
	int board_num;
	int board_parent;
	int board_cnt;
	Date board_date;
	public String getBoard_title() {
		return board_title;
	}
	public void setBoard_title(String board_title) {
		this.board_title = board_title;
	}
	public String getBoard_content() {
		return board_content;
	}
	public void setBoard_content(String board_content) {
		this.board_content = board_content;
	}
	public String getBoard_img() {
		return board_img;
	}
	public void setBoard_img(String board_img) {
		this.board_img = board_img;
	}
	public String getBoard_userid() {
		return board_userid;
	}
	public void setBoard_userid(String board_userid) {
		this.board_userid = board_userid;
	}
	public int getBoard_num() {
		return board_num;
	}
	public void setBoard_num(int board_num) {
		this.board_num = board_num;
	}
	public int getBoard_parent() {
		return board_parent;
	}
	public void setBoard_parent(int board_parent) {
		this.board_parent = board_parent;
	}
	public int getBoard_cnt() {
		return board_cnt;
	}
	public void setBoard_cnt(int board_cnt) {
		this.board_cnt = board_cnt;
	}
	public Date getBoard_date() {
		return board_date;
	}
	public void setBoard_date(Date board_date) {
		this.board_date = board_date;
	}
	@Override
	public String toString() {
		return "Board [board_title=" + board_title + ", board_content=" + board_content + ", board_img=" + board_img
				+ ", board_userid=" + board_userid + ", board_num=" + board_num + ", board_parent=" + board_parent
				+ ", board_cnt=" + board_cnt + ", board_date=" + board_date + "]";
	}
	
	
	
	
}
