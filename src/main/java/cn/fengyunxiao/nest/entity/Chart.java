package cn.fengyunxiao.nest.entity;

/**
 *  name 名称
 *  value 数量
 */
public class Chart {
	private String name;
	private Integer num;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return "Chart{" +
				"name='" + name + '\'' +
				", num=" + num +
				'}';
	}
}
