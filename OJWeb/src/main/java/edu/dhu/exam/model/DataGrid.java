package edu.dhu.exam.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DataGrid implements java.io.Serializable {
	
	private static final long serialVersionUID = 1538274527426L;

	// 总的记录行数
//	private long totalLines;
	private long total; // 原totalLines

	// 总的页数
	@JsonIgnore
	private long totalPages;

	// 每页显示多少行
//	private long pageLines;

	// 每页显示的行数
	private long pageSize;
	// 当前所在页
	private long pageNum;

	// 当前页的数据行数量,只有在最后一页时会和每页显示多少行可能不同
	@JsonIgnore
	private long currentPageLineNum;

	// 当前页的所有数据行
	@SuppressWarnings("rawtypes")
//	private List<?> rows = new ArrayList();
	private List<?> list = new ArrayList(); // 原rows


}
