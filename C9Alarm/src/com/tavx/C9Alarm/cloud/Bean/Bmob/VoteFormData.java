package com.tavx.C9Alarm.cloud.Bean.Bmob;



import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.google.gson.annotations.Expose;
import com.tavx.C9Alarm.cloud.Ignore;
import com.tavx.C9Alarm.cloud.Interface._RemoteFormData;
import com.tavx.C9Alarm.cloud.Interface._VoteFormData;

public class VoteFormData extends BmobObject implements _VoteFormData<BmobFile, Vote_form_point>{
    private String name;
	private BmobFile pic;
	private String author;
	
	@Ignore
	public  Object my_Form_point;

	@Expose 
	private form_discount discount;

	/* (non-Javadoc)
	 * @see com.tavx.C9Alarm.cloud.Interface._VoteFormData#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	/* (non-Javadoc)
	 * @see com.tavx.C9Alarm.cloud.Interface._VoteFormData#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		this.name =name;
	}

	/* (non-Javadoc)
	 * @see com.tavx.C9Alarm.cloud.Interface._VoteFormData#setPic(java.lang.Object)
	 */
	@Override
	public void setPic(BmobFile pic) {
		// TODO Auto-generated method stub
		this.pic = pic;
	}

	/* (non-Javadoc)
	 * @see com.tavx.C9Alarm.cloud.Interface._VoteFormData#setPointForSelf(java.lang.Object)
	 */
	@Override
	public void setPointForSelf(Vote_form_point point) {
		this.my_Form_point = point;
	}

	/* (non-Javadoc)
	 * @see com.tavx.C9Alarm.cloud.Interface._VoteFormData#getPointForSelf()
	 */
	@Override
	public Vote_form_point getPointForSelf() {
		// TODO Auto-generated method stub
		return (Vote_form_point) my_Form_point;
	}

	/* (non-Javadoc)
	 * @see com.tavx.C9Alarm.cloud.Interface._VoteFormData#getAuthor()
	 */
	@Override
	public String getAuthor() {
		// TODO Auto-generated method stub
		return author;
	}

	/* (non-Javadoc)
	 * @see com.tavx.C9Alarm.cloud.Interface._VoteFormData#setAuthor(java.lang.String)
	 */
	@Override
	public void setAuthor(String a) {
		// TODO Auto-generated method stub
		this.author = a;
	}

	/* (non-Javadoc)
	 * @see com.tavx.C9Alarm.cloud.Interface._VoteFormData#getPic()
	 */
	@Override
	public BmobFile getPic() {
		// TODO Auto-generated method stub
		return pic;
	}
	    
	

		/* (non-Javadoc)
		 * @see com.tavx.C9Alarm.cloud.Interface._VoteFormData#getForm_discount()
		 */
		@Override
		public form_discount getForm_discount() {
			// TODO Auto-generated method stub
			return discount;
		}
	

}