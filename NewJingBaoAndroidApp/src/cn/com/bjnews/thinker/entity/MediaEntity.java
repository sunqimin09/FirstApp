package cn.com.bjnews.thinker.entity;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class MediaEntity implements Serializable, Parcelable {

//	public String getPic() {
//		return pic;
//	}
//
//	public void setPic(String pic) {
//		this.pic = pic;
//	}
//
//	public String getCaption() {
//		return caption;
//	}
//
//	public void setCaption(String caption) {
//		this.caption = caption;
//	}
//
//	public String getVideo() {
//		return video;
//	}
//
//	public void setVideo(String video) {
//		this.video = video;
//	}
//
//	public int getFlag() {
//		return flag;
//	}
//
//	public void setFlag(int flag) {
//		this.flag = flag;
//	}
//	
	/**
	 * 将from 中 的 数据复制到 本地 中
	 * @param from
	 * @return
	 */
	public MediaEntity copy(MediaEntity from ){
		this.pic = from.pic;
		this.video = from.video;
		this.caption = from.caption;
		this.flag = from.flag;
		return this;
	}
	
	public String pic;

	public String caption;

	public String video;

	public int flag = 0;// 0: 视频，1 上部图片 2，下部图片

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel out, int flags) {
		out.writeString(pic);
		out.writeString(caption);
		out.writeString(video);
		out.writeInt(flag);
	}

	public static final Parcelable.Creator<MediaEntity> CREATOR = new Parcelable.Creator<MediaEntity>() {
		public MediaEntity createFromParcel(Parcel in) {
			MediaEntity msg = new MediaEntity();
			msg.pic = in.readString();
			msg.caption = in.readString();
			msg.video = in.readString();
			msg.flag = in.readInt();
			return msg;
		}

		public MediaEntity[] newArray(int size) {
			return new MediaEntity[size];
		}
	};

}
