package cn.com.jinshangcheng.widget.AddressSelector;

public class DistrictModel {
	private String id;
	private String name;

	public DistrictModel() {
		super();
	}

	public DistrictModel(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "DistrictModel [name=" + name + ", id=" + id + "]";
	}

}
