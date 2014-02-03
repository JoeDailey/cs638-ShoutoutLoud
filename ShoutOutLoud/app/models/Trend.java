package models;

import com.google.common.base.Objects;

public class Trend implements Comparable<Trend> {
	public String keyword;
	public int count;
	
	public Trend(String keyword, int count) {
		super();
		this.keyword = keyword;
		this.count = count;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Trend) {
			Trend that = (Trend)obj;
			return Objects.equal(this.keyword, that.keyword) &&
					Objects.equal(this.count, that.count);
		}

		return false;		
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hashCode(this.keyword, this.count);
	}
	
	@Override
	public int compareTo(Trend obj) {
		Trend that = (Trend)obj;
		return this.count - that.count;
	}
	
	public String getKeyword() {
		return keyword;
	}
	
	public String getKeywordWoHashTag()
	{
		return keyword.replace("#", "");
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}


	
	
	
}
