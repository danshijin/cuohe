package com.smm.cuohe.domain;

import java.util.List;
/**
 * 
 * @author lirubin
 *
 */
public class CompanyPOJO extends Company {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer pic;
	
	private Integer categorySource;
	
	private List<Contacter> contacters; //联系人
	
	private Integer isNew;
	
	


	public List<Contacter> getContacters() {
		return contacters;
	}

	public void setContacters(List<Contacter> contacters) {
		this.contacters = contacters;
	}

	public Integer getPic() {
		return pic;
	}

	public void setPic(Integer pic) {
		this.pic = pic;
	}

	public Integer getCategorySource() {
		return categorySource;
	}

	public void setCategorySource(Integer categorySource) {
		this.categorySource = categorySource;
	}

	public Integer getIsNew() {
		return isNew;
	}

	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}
}
